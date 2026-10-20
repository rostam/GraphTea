// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.reports.ui;

import graphtea.extensions.G6Format;
import graphtea.extensions.reports.boundcheck.ConjectureRunner;
import graphtea.extensions.reports.boundcheck.Outcome;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.platform.ui.UserNotifier;
import graphtea.plugins.graphgenerator.core.PositionGenerators;
import graphtea.plugins.main.GraphData;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * The right-hand side of the Conjecture Checker: a one-line verdict, then the graphs behind it,
 * which can be opened in a tab, copied or exported.
 *
 * @author M. Ali Rostami
 */
final class ConjectureResultsPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    /** The outcomes the "Show" list offers, in its order; null means every kept graph. */
    private static final Outcome[] VIEWS = {Outcome.FAILS, Outcome.TIGHT, Outcome.UNDEFINED, null};

    private final transient BlackBoard blackboard;

    private final JTextArea note = new JTextArea();
    private final JLabel headline = new JLabel();
    private final JLabel detail = new JLabel();
    private final JLabel warning = new JLabel();
    private final JProgressBar progress = new JProgressBar();

    private final BoundsModel boundsModel = new BoundsModel();
    private final JTable boundsTable = new JTable(boundsModel);
    private final JScrollPane boundsScroll = new JScrollPane(boundsTable);

    private final JComboBox<String> view = new JComboBox<>();
    private final JLabel shown = new JLabel();
    private final RowsModel rowsModel = new RowsModel();
    private final JTable rowsTable = new JTable(rowsModel);
    private final JButton open = new JButton("Open in New Tab");
    private final JButton copy = new JButton("Copy graph6");
    private final JButton export = new JButton("Export…");
    private final JPanel body = new JPanel(new BorderLayout(0, 8));

    private transient ConjectureRunner.Result result;
    private boolean updatingView;

    ConjectureResultsPanel(BlackBoard blackboard) {
        super(new BorderLayout(0, 10));
        this.blackboard = blackboard;
        setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));

        add(top(), BorderLayout.NORTH);
        add(body, BorderLayout.CENTER);
        buildBody();
        body.setVisible(false);
        showIntro();
    }

    private JPanel top() {
        note.setEditable(false);
        note.setLineWrap(true);
        note.setWrapStyleWord(true);
        note.setOpaque(true);
        note.setBackground(UIManager.getColor("TextField.background"));
        note.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 3, 0, 0, UIManager.getColor("Component.accentColor") != null
                        ? UIManager.getColor("Component.accentColor") : CheckerStyle.muted()),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));
        note.setVisible(false);

        headline.setFont(headline.getFont().deriveFont(Font.BOLD, headline.getFont().getSize2D() + 5f));
        detail.setForeground(CheckerStyle.muted());
        warning.setForeground(CheckerStyle.warning());
        progress.setStringPainted(true);
        progress.setVisible(false);

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        for (Component c : new Component[] {note, headline, detail, warning, progress}) {
            ((javax.swing.JComponent) c).setAlignmentX(LEFT_ALIGNMENT);
            p.add(c);
            p.add(Box.createVerticalStrut(c == note ? 10 : 4));
        }
        return p;
    }

    private void buildBody() {
        boundsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        boundsTable.setAutoCreateRowSorter(false);
        boundsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                refreshView();
            }
        });
        boundsScroll.setPreferredSize(new Dimension(100, 150));
        boundsScroll.setBorder(BorderFactory.createTitledBorder("Each bound — select one to see its graphs"));

        view.addActionListener(e -> {
            if (!updatingView) {
                applyView();
            }
        });
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        bar.add(new JLabel("Show:"));
        bar.add(view);
        bar.add(shown);
        shown.setForeground(CheckerStyle.muted());

        rowsTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        rowsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rowsTable.setRowSorter(new TableRowSorter<>(rowsModel));
        rowsTable.setDefaultRenderer(Double.class, new NumberRenderer());
        rowsTable.setDefaultRenderer(Long.class, new NumberRenderer());
        rowsTable.setDefaultRenderer(Integer.class, new NumberRenderer());
        rowsTable.setDefaultRenderer(Outcome.class, new OutcomeRenderer());
        rowsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && rowsTable.getSelectedRow() >= 0) {
                    openSelected();
                }
            }
        });
        rowsTable.getSelectionModel().addListSelectionListener(e -> updateButtons());
        open.addActionListener(e -> openSelected());
        copy.addActionListener(e -> copySelected());
        export.addActionListener(e -> exportShown());
        open.setToolTipText("Open the selected graph in a new tab (or double-click it)");
        export.setToolTipText("Save the graphs shown as a CSV table or a graph6 file");

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        buttons.add(open);
        buttons.add(copy);
        buttons.add(export);

        JPanel table = new JPanel(new BorderLayout(0, 6));
        table.add(bar, BorderLayout.NORTH);
        table.add(new JScrollPane(rowsTable), BorderLayout.CENTER);
        table.add(buttons, BorderLayout.SOUTH);

        body.add(boundsScroll, BorderLayout.NORTH);
        body.add(table, BorderLayout.CENTER);
    }

    // ------------------------------------------------------------------ states

    /** The state before anything has been checked. */
    void showIntro() {
        headline.setText("No conjecture checked yet");
        headline.setForeground(CheckerStyle.text());
        detail.setText("<html>Write a conjecture on the left, choose the graphs to test it on, and press Check.<br>"
                + "Or pick one of the Examples to see how it works.</html>");
        warning.setVisible(false);
    }

    /**
     * Shows or hides the explanation of the example being run.
     *
     * @param text the explanation, or null to hide it
     */
    void showNote(String text) {
        note.setText(text);
        note.setVisible(text != null);
    }

    /**
     * Shows that a run has started.
     *
     * @param what the conjecture and graphs, for the detail line
     */
    void showRunning(String what) {
        headline.setText("Checking…");
        headline.setForeground(CheckerStyle.text());
        detail.setText(what);
        warning.setVisible(false);
        progress.setIndeterminate(true);
        progress.setString("Starting");
        progress.setVisible(true);
        body.setVisible(false);
    }

    /**
     * @param p how far the run has got
     */
    void showProgress(ConjectureRunner.Progress p) {
        String failures = p.failures() == 0 ? "no counterexample yet"
                : p.failures() == 1 ? "1 counterexample" : CheckerStyle.count(p.failures()) + " counterexamples";
        if (p.expected() > 0) {
            progress.setIndeterminate(false);
            progress.setMaximum(1000);
            progress.setValue((int) Math.min(1000, 1000 * p.tested() / p.expected()));
            progress.setString("Tested " + CheckerStyle.count(p.tested()) + " of "
                    + CheckerStyle.count(p.expected()) + " · " + failures);
        } else {
            progress.setString("Tested " + CheckerStyle.count(p.tested()) + " · " + failures);
        }
    }

    /**
     * Shows a run that could not be carried out.
     *
     * @param message why
     */
    void showError(String message) {
        progress.setVisible(false);
        body.setVisible(false);
        headline.setText("The check could not run");
        headline.setForeground(CheckerStyle.failure());
        detail.setText("<html>" + escape(message) + "</html>");
        warning.setVisible(false);
    }

    /**
     * Shows a finished run.
     *
     * @param r what it found
     */
    void showResult(ConjectureRunner.Result r) {
        this.result = r;
        progress.setVisible(false);
        verdict(r);
        String err = r.firstError();
        warning.setText(err == null ? "" : "<html>Some values could not be computed and count as undefined. "
                + "First problem: " + escape(err) + "</html>");
        warning.setVisible(err != null);

        boolean several = r.conjecture().rightLabels().size() > 1;
        boundsModel.setResult(several ? r : null);
        boundsScroll.setVisible(several);
        rowsModel.setResult(r);
        sizeColumns(several);
        // A new result opens on its counterexamples, whatever the last one was showing.
        updatingView = true;
        view.removeAllItems();
        updatingView = false;
        if (several) {
            boundsTable.setRowSelectionInterval(0, 0);
        }
        refreshView();
        body.setVisible(r.tested() > 0);
        revalidate();
        repaint();
    }

    /** Narrow columns for counts, room for the verdict; many bounds scroll sideways instead of squeezing. */
    private void sizeColumns(boolean several) {
        rowsTable.setAutoResizeMode(several ? JTable.AUTO_RESIZE_OFF : JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        int last = rowsModel.getColumnCount() - 1;
        for (int c = 0; c <= last; c++) {
            int w;
            if (c == 0) {
                w = 55;
            } else if (c < 3) {
                w = 40;
            } else if (c == last - 1) {
                w = 140;
            } else if (c == last) {
                w = 110;
            } else {
                w = several ? 80 : 110;
            }
            rowsTable.getColumnModel().getColumn(c).setPreferredWidth(w);
        }
    }

    private void verdict(ConjectureRunner.Result r) {
        long fails = r.count(Outcome.FAILS);
        long undefined = r.count(Outcome.UNDEFINED);
        long defined = r.tested() - undefined;
        String head;
        if (r.tested() == 0) {
            head = "No graphs were tested";
            headline.setForeground(CheckerStyle.warning());
        } else if (fails > 0) {
            head = r.stoppedAtFirst() ? "✗  False — a counterexample was found"
                    : "✗  False on " + CheckerStyle.count(fails) + " of " + CheckerStyle.graphs(r.tested());
            headline.setForeground(CheckerStyle.failure());
        } else if (defined == 0) {
            head = "?  Undefined on every graph tested";
            headline.setForeground(CheckerStyle.warning());
        } else {
            head = (r.cancelled() ? "✓  No counterexample in the " : "✓  Holds on all ")
                    + CheckerStyle.graphs(defined) + (r.cancelled() ? " tested before stopping" : " tested");
            headline.setForeground(CheckerStyle.success());
        }
        headline.setText(head);
        detail.setText("<html>" + escape(r.conjecture().statement()) + "<br>on " + escape(r.graphs())
                + "<br>" + facts(r) + "</html>");
    }

    private static String facts(ConjectureRunner.Result r) {
        List<String> parts = new ArrayList<>();
        parts.add(CheckerStyle.graphs(r.tested()) + " tested");
        if (r.count(Outcome.TIGHT) > 0) {
            parts.add("equality on " + CheckerStyle.count(r.count(Outcome.TIGHT)));
        }
        if (r.count(Outcome.UNDEFINED) > 0) {
            parts.add("undefined on " + CheckerStyle.count(r.count(Outcome.UNDEFINED)));
        }
        if (r.excluded() > 0) {
            parts.add(CheckerStyle.count(r.excluded()) + " skipped by the condition");
        }
        if (r.cancelled()) {
            parts.add("stopped early");
        }
        parts.add(String.format("%.1f s", r.millis() / 1000.0));
        String s = String.join(" · ", parts);
        if (r.count(Outcome.FAILS) == 0 && r.tested() > 0) {
            s += "<br><i>Not a proof: only these graphs were tested.</i>";
        }
        return s;
    }

    // ------------------------------------------------------------------ views

    /** The comparison the table is about: -1 for the conjecture as a whole. */
    private int comparison() {
        if (result == null || result.conjecture().rightLabels().size() == 1) {
            return -1;
        }
        int row = boundsTable.getSelectedRow();
        return row <= 0 ? -1 : row - 1;
    }

    private long countFor(Outcome o) {
        int c = comparison();
        if (o == null) {
            return result.tested();
        }
        return c < 0 ? result.count(o) : result.count(c, o);
    }

    /** Rebuilds the "Show" list's counts, keeping the choice where it still makes sense. */
    private void refreshView() {
        if (result == null) {
            return;
        }
        Outcome chosen = view.getSelectedIndex() >= 0 ? VIEWS[view.getSelectedIndex()] : null;
        boolean first = view.getItemCount() == 0 || view.getSelectedIndex() < 0;
        updatingView = true;
        view.removeAllItems();
        String[] names = {"Counterexamples", "Holds with equality", "Undefined", "All graphs"};
        for (int i = 0; i < VIEWS.length; i++) {
            view.addItem(names[i] + " (" + CheckerStyle.count(countFor(VIEWS[i])) + ")");
        }
        int pick = first || countFor(chosen) == 0 ? defaultView() : indexOf(chosen);
        view.setSelectedIndex(pick);
        updatingView = false;
        applyView();
    }

    private int defaultView() {
        for (int i = 0; i < VIEWS.length - 1; i++) {
            if (countFor(VIEWS[i]) > 0) {
                return i;
            }
        }
        return VIEWS.length - 1;
    }

    private static int indexOf(Outcome o) {
        for (int i = 0; i < VIEWS.length; i++) {
            if (VIEWS[i] == o) {
                return i;
            }
        }
        return VIEWS.length - 1;
    }

    private void applyView() {
        if (result == null || view.getSelectedIndex() < 0) {
            return;
        }
        Outcome o = VIEWS[view.getSelectedIndex()];
        int c = comparison();
        List<ConjectureRunner.Row> rows = new ArrayList<>();
        for (ConjectureRunner.Row row : result.rows()) {
            Outcome actual = c < 0 ? row.overall() : row.outcome(c);
            if (o == null || actual == o) {
                rows.add(row);
            }
        }
        rowsModel.setRows(rows);
        long total = countFor(o);
        shown.setText(rows.size() < total ? "showing the first " + CheckerStyle.count(rows.size()) + " of "
                + CheckerStyle.count(total) : "");
        if (!rows.isEmpty()) {
            rowsTable.setRowSelectionInterval(0, 0);
        }
        updateButtons();
    }

    private void updateButtons() {
        boolean selected = rowsTable.getSelectedRow() >= 0;
        open.setEnabled(selected);
        copy.setEnabled(selected);
        export.setEnabled(rowsModel.getRowCount() > 0);
    }

    // ------------------------------------------------------------------ actions

    private ConjectureRunner.Row selectedRow() {
        int viewRow = rowsTable.getSelectedRow();
        return viewRow < 0 ? null : rowsModel.rows.get(rowsTable.convertRowIndexToModel(viewRow));
    }

    private void openSelected() {
        ConjectureRunner.Row row = selectedRow();
        if (row == null) {
            return;
        }
        GraphModel g = row.n() == 0 ? new GraphModel(false) : G6Format.stringToGraphModel(row.g6());
        GPoint[] at = PositionGenerators.circle(320, 380, 320, Math.max(1, g.getVerticesCount()));
        int i = 0;
        for (Vertex v : g) {
            v.setLocation(at[i++]);
        }
        String kind = row.overall() == Outcome.FAILS ? "Counterexample" : "Graph";
        g.setLabel(kind + " " + row.index());
        new GraphData(blackboard).core.showGraph(g);
    }

    private void copySelected() {
        ConjectureRunner.Row row = selectedRow();
        if (row != null) {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(row.g6()), null);
            UserNotifier.toast(this, "Copied " + row.g6());
        }
    }

    private void exportShown() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter csv = new FileNameExtensionFilter("Table with all values (*.csv)", "csv");
        FileNameExtensionFilter g6 = new FileNameExtensionFilter("Graphs only, graph6 (*.g6)", "g6");
        chooser.addChoosableFileFilter(csv);
        chooser.addChoosableFileFilter(g6);
        chooser.setFileFilter(csv);
        chooser.setSelectedFile(new File("conjecture-graphs.csv"));
        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        boolean asG6 = chooser.getFileFilter() == g6 || chooser.getSelectedFile().getName().endsWith(".g6");
        File f = chooser.getSelectedFile();
        if (!f.getName().contains(".")) {
            f = new File(f.getParentFile(), f.getName() + (asG6 ? ".g6" : ".csv"));
        }
        if (f.exists() && JOptionPane.showConfirmDialog(this, f.getName() + " already exists. Replace it?",
                "Replace file?", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
            return;
        }
        try (PrintWriter out = new PrintWriter(f, StandardCharsets.UTF_8)) {
            if (asG6) {
                for (int r = 0; r < rowsTable.getRowCount(); r++) {
                    out.println(rowsModel.rows.get(rowsTable.convertRowIndexToModel(r)).g6());
                }
            } else {
                writeCsv(out);
            }
        } catch (IOException e) {
            ExceptionHandler.catchException("Exporting graphs", e);
            return;
        }
        UserNotifier.toast(this, "Saved " + CheckerStyle.graphs(rowsTable.getRowCount()) + " to " + f.getName());
    }

    private void writeCsv(PrintWriter out) {
        List<String> header = new ArrayList<>();
        for (int c = 0; c < rowsModel.getColumnCount(); c++) {
            header.add(csv(rowsModel.getColumnName(c)));
        }
        out.println(String.join(",", header));
        for (int r = 0; r < rowsTable.getRowCount(); r++) {
            int m = rowsTable.convertRowIndexToModel(r);
            List<String> cells = new ArrayList<>();
            for (int c = 0; c < rowsModel.getColumnCount(); c++) {
                Object v = rowsModel.getValueAt(m, c);
                cells.add(csv(v instanceof Outcome o ? o.label() : String.valueOf(v)));
            }
            out.println(String.join(",", cells));
        }
    }

    private static String csv(String s) {
        return s.contains(",") || s.contains("\"") ? "\"" + s.replace("\"", "\"\"") + "\"" : s;
    }

    private static String escape(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    // ------------------------------------------------------------------ table models

    /** One row per tested graph: index, n, m, both sides, the verdict and the graph6 code. */
    private final class RowsModel extends AbstractTableModel {
        private static final long serialVersionUID = 1L;
        private transient List<ConjectureRunner.Row> rows = new ArrayList<>();
        private List<String> rights = List.of();
        private String left = "";

        void setResult(ConjectureRunner.Result r) {
            left = r.conjecture().leftLabel();
            rights = r.conjecture().rightLabels();
            rows = new ArrayList<>();
            fireTableStructureChanged();
        }

        void setRows(List<ConjectureRunner.Row> newRows) {
            rows = newRows;
            fireTableDataChanged();
        }

        private boolean single() {
            return rights.size() == 1;
        }

        @Override
        public int getRowCount() {
            return rows.size();
        }

        @Override
        public int getColumnCount() {
            return 3 + 1 + rights.size() + (single() ? 1 : 0) + 2;
        }

        @Override
        public String getColumnName(int c) {
            int k = rights.size();
            if (c < 3) {
                return new String[] {"#", "n", "m"}[c];
            }
            if (c == 3) {
                return left;
            }
            if (c < 4 + k) {
                return rights.get(c - 4);
            }
            int rest = c - 4 - k - (single() ? 1 : 0);
            if (rest < 0) {
                return "Left − right";
            }
            return rest == 0 ? "Result" : "graph6";
        }

        @Override
        public Class<?> getColumnClass(int c) {
            int k = rights.size();
            if (c == 0) {
                return Long.class;
            }
            if (c < 3) {
                return Integer.class;
            }
            if (c < 4 + k + (single() ? 1 : 0)) {
                return Double.class;
            }
            return c == getColumnCount() - 2 ? Outcome.class : String.class;
        }

        @Override
        public Object getValueAt(int r, int c) {
            ConjectureRunner.Row row = rows.get(r);
            int k = rights.size();
            if (c == 0) {
                return row.index();
            }
            if (c == 1) {
                return row.n();
            }
            if (c == 2) {
                return row.m();
            }
            if (c == 3) {
                return row.left();
            }
            if (c < 4 + k) {
                return row.right(c - 4);
            }
            if (single() && c == 4 + k) {
                return row.left() - row.right(0);
            }
            return c == getColumnCount() - 2 ? row.overall() : row.g6();
        }
    }

    /** One row for the conjecture as a whole, then one per bound, with its counts. */
    private static final class BoundsModel extends AbstractTableModel {
        private static final long serialVersionUID = 1L;
        private static final String[] COLUMNS = {"Bound", "Holds", "With equality", "Fails", "Undefined"};
        private static final Outcome[] OUTCOMES = {Outcome.HOLDS, Outcome.TIGHT, Outcome.FAILS, Outcome.UNDEFINED};
        private transient ConjectureRunner.Result result;

        void setResult(ConjectureRunner.Result r) {
            result = r;
            fireTableDataChanged();
        }

        @Override
        public int getRowCount() {
            return result == null ? 0 : result.conjecture().rightLabels().size() + 1;
        }

        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }

        @Override
        public String getColumnName(int c) {
            return COLUMNS[c];
        }

        @Override
        public Object getValueAt(int r, int c) {
            if (c == 0) {
                if (r == 0) {
                    return "All bounds together";
                }
                String label = result.conjecture().rightLabels().get(r - 1);
                return result.count(r - 1, Outcome.FAILS) == 0 ? "✓ " + label : "✗ " + label;
            }
            Outcome o = OUTCOMES[c - 1];
            return CheckerStyle.count(r == 0 ? result.count(o) : result.count(r - 1, o));
        }
    }

    /** Right-aligned numbers; in a table of several bounds, the bounds a graph breaks are in red. */
    private final class NumberRenderer extends DefaultTableCellRenderer {
        private static final long serialVersionUID = 1L;

        NumberRenderer() {
            setHorizontalAlignment(SwingConstants.RIGHT);
        }

        @Override
        public Component getTableCellRendererComponent(JTable t, Object value, boolean selected, boolean focus,
                                                       int row, int col) {
            super.getTableCellRendererComponent(t, value, selected, focus, row, col);
            setText(value instanceof Number n ? CheckerStyle.number(n.doubleValue()) : String.valueOf(value));
            int c = t.convertColumnIndexToModel(col) - 4;
            boolean broken = false;
            if (!selected && !rowsModel.single() && c >= 0 && c < rowsModel.rights.size()) {
                broken = rowsModel.rows.get(t.convertRowIndexToModel(row)).outcome(c) == Outcome.FAILS;
            }
            if (!selected) {
                setForeground(broken ? CheckerStyle.failure() : t.getForeground());
            }
            return this;
        }
    }

    private static final class OutcomeRenderer extends DefaultTableCellRenderer {
        private static final long serialVersionUID = 1L;

        @Override
        public Component getTableCellRendererComponent(JTable t, Object value, boolean selected, boolean focus,
                                                       int row, int col) {
            Outcome o = (Outcome) value;
            super.getTableCellRendererComponent(t, o == null ? "" : o.label(), selected, focus, row, col);
            if (!selected && o != null) {
                setForeground(o == Outcome.FAILS ? CheckerStyle.failure() : o == Outcome.UNDEFINED
                        ? CheckerStyle.warning() : o == Outcome.TIGHT ? CheckerStyle.text() : CheckerStyle.success());
            }
            return this;
        }
    }
}
