// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.reports.ui;

import graphtea.extensions.G6Format;
import graphtea.extensions.reports.boundcheck.Conjecture;
import graphtea.extensions.reports.boundcheck.ConjectureRunner;
import graphtea.extensions.reports.boundcheck.Example;
import graphtea.extensions.reports.boundcheck.Expression;
import graphtea.extensions.reports.boundcheck.GraphClass;
import graphtea.extensions.reports.boundcheck.GraphSource;
import graphtea.extensions.reports.boundcheck.Invariants;
import graphtea.extensions.reports.boundcheck.Relation;
import graphtea.extensions.reports.boundcheck.SmallGraphs;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.graph.ui.GTabbedGraphPane;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.extension.ExtensionLoader;
import graphtea.plugins.graphgenerator.core.extension.GraphGeneratorExtension;
import graphtea.plugins.reports.extension.GraphReportExtension;
import graphtea.ui.UIUtils;
import graphtea.ui.extension.AbstractExtensionAction;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.JTextComponent;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Tests a conjecture about graph invariants on many graphs at once, and shows the
 * counterexamples.
 *
 * <p>This replaces the "Bound Check" report. That was a report whose parameters (eleven of
 * them, from "Column ID for Filter" to "Post Processing Value") switched on a hidden mode, in
 * which every other table report silently ran on all graphs instead of the open one, until
 * Bound Check was run again with the box unticked. It needed graph files that GraphTea does not
 * ship, reported only how many graphs satisfied each column, and never showed a counterexample.
 *
 * <p>Here the whole task is one window: write the conjecture as a formula, such as
 * {@code energy <= 2*m}, or pick a report whose columns hold a quantity and its candidate
 * bounds; choose the graphs; press Check. The verdict comes with the graphs behind it, which
 * open in a tab with a double-click.
 *
 * @author M. Ali Rostami
 */
public final class ConjectureCheckerDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    private static final String FORMULA = "formula";
    private static final String COLUMNS = "columns";
    private static final String[] SOURCES = {
        "Every graph on n vertices", "A graph family", "Random graphs", "A graph6 file", "The graphs open in tabs",
    };
    private static final long LARGE = 2_000_000;

    private static ConjectureCheckerDialog instance;

    private final transient BlackBoard blackboard;
    private final transient Invariants invariants;
    private final transient Map<String, GraphReportExtension<RenderTable>> tableReports = new LinkedHashMap<>();
    private final transient Map<String, GraphGeneratorExtension> generators = new LinkedHashMap<>();

    // Conjecture
    private final JToggleButton formulaMode = new JToggleButton("Formula", true);
    private final JToggleButton columnsMode = new JToggleButton("Columns of a report");
    private final CardLayout conjectureCards = new CardLayout();
    private final JPanel conjecturePanel = new CardPanel(conjectureCards);
    private final JTextField formula = new JTextField("energy <= 2*m");
    private final JLabel formulaStatus = new JLabel(" ");
    private final JTextField condition = new JTextField();
    private final JLabel conditionStatus = new JLabel(" ");
    private final JComboBox<String> report = new JComboBox<>();
    private final JComboBox<String> leftColumn = new JComboBox<>();
    private final JComboBox<Relation> relation = new JComboBox<>(Relation.values());
    private final JList<String> boundColumns = new JList<>();
    private final JLabel columnsStatus = new JLabel(" ");
    private transient List<String> columnTitles = List.of();
    private transient JTextComponent lastFormulaField = formula;
    private transient InvariantPicker picker;

    // Graphs
    private final JComboBox<String> source = new JComboBox<>(SOURCES);
    private final JComboBox<GraphClass> graphClass = new JComboBox<>(GraphClass.values());
    private final JPanel classRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
    private final CardLayout sourceCards = new CardLayout();
    private final JPanel sourcePanel = new CardPanel(sourceCards);
    private final JSpinner exFrom = spinner(1, 1, 22);
    private final JSpinner exTo = spinner(7, 1, 22);
    private final JComboBox<String> generator = new JComboBox<>();
    private final JLabel familyParameter = new JLabel("n from");
    private final JSpinner famFrom = spinner(3, 0, 1000);
    private final JSpinner famTo = spinner(20, 0, 1000);
    private final JSpinner rndFrom = spinner(10, 1, 500);
    private final JSpinner rndTo = spinner(20, 1, 500);
    private final JSpinner probability = new JSpinner(new SpinnerNumberModel(0.3, 0.0, 1.0, 0.05));
    private final JSpinner perSize = spinner(20, 1, 1_000_000);
    private final JTextField seed = new JTextField("1", 8);
    private final JTextField file = new JTextField(18);
    private final JLabel tabsInfo = new JLabel();
    private final JLabel sourceStatus = new JLabel(" ");
    private final JCheckBox stopAtFirst = new JCheckBox("Stop at the first counterexample");

    // Running
    private final JButton examples = new JButton("Examples ▾");
    private final JButton check = new JButton("Check");
    private final JLabel checkStatus = new JLabel(" ");
    private final ConjectureResultsPanel results;
    private final List<JComponent> setupControls = new ArrayList<>();
    private transient SwingWorker<ConjectureRunner.Result, ConjectureRunner.Progress> worker;
    private final AtomicBoolean cancelled = new AtomicBoolean();
    private transient String pendingNote;

    private ConjectureCheckerDialog(BlackBoard blackboard) {
        super(UIUtils.getGFrame(blackboard), "Conjecture Checker", false);
        this.blackboard = blackboard;
        List<GraphReportExtension<?>> reports = new ArrayList<>();
        loadExtensions(reports);
        this.invariants = Invariants.withReports(reports);
        this.results = new ConjectureResultsPanel(blackboard);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, setupPanel(), results);
        split.setResizeWeight(0);
        split.setBorder(null);
        setContentPane(split);

        wireValidation();
        updateConjectureMode();
        updateSource();
        validateAll();

        getRootPane().setDefaultButton(check);
        getRootPane().registerKeyboardAction(e -> closeOrStop(), KeyStroke.getKeyStroke("ESCAPE"),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeOrStop();
            }
        });
        setSize(new Dimension(1180, 720));
        setLocationRelativeTo(getOwner());
    }

    /**
     * Shows the checker, creating it the first time; it keeps its settings between uses.
     *
     * @param blackboard the application's blackboard
     */
    public static void open(BlackBoard blackboard) {
        if (instance == null || instance.blackboard != blackboard) {
            instance = new ConjectureCheckerDialog(blackboard);
        }
        instance.updateTabsInfo();
        instance.setVisible(true);
        instance.toFront();
        instance.formula.requestFocusInWindow();
    }

    @SuppressWarnings("unchecked")
    private void loadExtensions(List<GraphReportExtension<?>> reports) {
        List<GraphReportExtension<RenderTable>> tables = new ArrayList<>();
        List<GraphGeneratorExtension> gens = new ArrayList<>();
        for (AbstractAction a : ExtensionLoader.loadedInstances.values()) {
            if (!(a instanceof AbstractExtensionAction<?> ea)) {
                continue;
            }
            Object target = ea.getTarget();
            if (target instanceof GraphReportExtension<?> r) {
                reports.add(r);
                if (Invariants.answerClass(r) == RenderTable.class) {
                    tables.add((GraphReportExtension<RenderTable>) r);
                }
            } else if (target instanceof GraphGeneratorExtension g && GraphSource.integerParameter(g) != null) {
                gens.add(g);
            }
        }
        tables.sort(Comparator.comparing(GraphReportExtension::getName));
        gens.sort(Comparator.comparing(GraphGeneratorExtension::getName));
        for (GraphReportExtension<RenderTable> r : tables) {
            tableReports.putIfAbsent(r.getName(), r);
            report.addItem(r.getName());
        }
        for (GraphGeneratorExtension g : gens) {
            generators.putIfAbsent(g.getName(), g);
            generator.addItem(g.getName());
        }
    }

    // ------------------------------------------------------------------ layout

    private JComponent setupPanel() {
        JPanel p = new WidthTrackingPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createEmptyBorder(8, 14, 12, 14));

        JPanel intro = new JPanel(new BorderLayout(10, 0));
        intro.add(CheckerStyle.hint("<html><body style='width:250px'>Test a conjecture on many graphs and see "
                + "the counterexamples.</body></html>"),
                BorderLayout.CENTER);
        intro.add(examples, BorderLayout.EAST);
        examples.addActionListener(e -> showExamples());
        add(p, intro);

        add(p, CheckerStyle.heading("1   Conjecture"));
        ButtonGroup modes = new ButtonGroup();
        modes.add(formulaMode);
        modes.add(columnsMode);
        formulaMode.addActionListener(e -> updateConjectureMode());
        columnsMode.addActionListener(e -> updateConjectureMode());
        JPanel modeRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        modeRow.add(formulaMode);
        modeRow.add(columnsMode);
        add(p, modeRow);
        conjecturePanel.add(formulaCard(), FORMULA);
        conjecturePanel.add(columnsCard(), COLUMNS);
        add(p, conjecturePanel);

        add(p, CheckerStyle.heading("2   Graphs"));
        add(p, sourceRow());
        classRow.add(new JLabel("Class:"));
        classRow.add(graphClass);
        add(p, classRow);
        sourcePanel.add(exhaustiveCard(), SOURCES[0]);
        sourcePanel.add(familyCard(), SOURCES[1]);
        sourcePanel.add(randomCard(), SOURCES[2]);
        sourcePanel.add(fileCard(), SOURCES[3]);
        sourcePanel.add(tabsCard(), SOURCES[4]);
        add(p, sourcePanel);
        add(p, sourceStatus);
        add(p, stopAtFirst);

        p.add(Box.createVerticalStrut(14));
        JPanel run = new JPanel(new BorderLayout(10, 0));
        check.putClientProperty("JButton.buttonType", "default");
        check.setFont(check.getFont().deriveFont(java.awt.Font.BOLD));
        check.addActionListener(e -> checkOrStop());
        run.add(check, BorderLayout.WEST);
        run.add(checkStatus, BorderLayout.CENTER);
        add(p, run);
        p.add(Box.createVerticalGlue());

        JScrollPane scroll = new JScrollPane(p);
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setPreferredSize(new Dimension(450, 600));
        scroll.setMinimumSize(new Dimension(400, 200));
        return scroll;
    }

    private static void add(JPanel p, JComponent c) {
        c.setAlignmentX(Component.LEFT_ALIGNMENT);
        if (!(c instanceof CardPanel)) {
            c.setMaximumSize(new Dimension(Integer.MAX_VALUE, c.getPreferredSize().height));
        }
        p.add(c);
    }

    private JPanel formulaCard() {
        formula.setFont(CheckerStyle.monospaced(formula));
        condition.setFont(CheckerStyle.monospaced(condition));
        formula.putClientProperty("JTextField.placeholderText", "e.g. energy <= 2*sqrt(m*n)");
        condition.putClientProperty("JTextField.placeholderText", "optional, e.g. m = n   or   mindeg >= 2");
        formula.setToolTipText("<html>Compare two expressions with &lt;=, &lt;, &gt;=, &gt;, = or !=.<br>"
                + "Names such as n, m, maxdeg, energy and wiener_index are invariants of the graph.</html>");
        condition.setToolTipText("Only graphs on which this is true are tested; the others are counted as skipped.");

        JButton insert = new JButton("Invariants…");
        insert.setToolTipText("Search the invariants and insert one into the formula");
        insert.addActionListener(e -> showPicker(insert));
        formula.addFocusListener(focusTracker(formula));
        condition.addFocusListener(focusTracker(condition));

        JPanel card = new JPanel(new GridBagLayout());
        GridBagConstraints c = gbc();
        c.gridwidth = 2;
        card.add(new JLabel("Every graph satisfies"), c);
        c.gridy++;
        c.gridwidth = 1;
        c.weightx = 1;
        card.add(formula, c);
        c.gridx = 1;
        c.weightx = 0;
        card.add(insert, c);
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 2;
        card.add(formulaStatus, c);
        c.gridy++;
        c.insets = new Insets(8, 0, 2, 0);
        card.add(new JLabel("Only graphs where"), c);
        c.gridy++;
        c.insets = new Insets(2, 0, 2, 0);
        card.add(condition, c);
        c.gridy++;
        card.add(conditionStatus, c);
        return card;
    }

    private java.awt.event.FocusAdapter focusTracker(JTextComponent field) {
        return new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                lastFormulaField = field;
            }
        };
    }

    private JPanel columnsCard() {
        boundColumns.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        boundColumns.setVisibleRowCount(6);
        report.addActionListener(e -> loadColumns());
        leftColumn.addActionListener(e -> defaultBounds());
        boundColumns.addListSelectionListener(e -> validateAll());
        relation.setSelectedItem(Relation.AT_MOST);

        JPanel card = new JPanel(new GridBagLayout());
        GridBagConstraints c = gbc();
        c.gridwidth = 3;
        card.add(CheckerStyle.hint("<html><body style='width:330px'>For reports that compute a quantity and "
                + "candidate bounds in one table, such as the Verification reports.</body></html>"), c);
        c.gridy++;
        c.gridwidth = 1;
        card.add(new JLabel("Report:"), c);
        c.gridx = 1;
        c.gridwidth = 2;
        c.weightx = 1;
        card.add(report, c);
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 1;
        c.weightx = 0;
        card.add(new JLabel("Column"), c);
        c.gridx = 1;
        c.weightx = 1;
        card.add(leftColumn, c);
        c.gridx = 2;
        c.weightx = 0;
        card.add(relation, c);
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 3;
        card.add(new JLabel("each of these columns (Ctrl-click to choose):"), c);
        c.gridy++;
        JScrollPane boundsScroll = new JScrollPane(boundColumns);
        boundsScroll.setMinimumSize(new Dimension(120, 110));
        boundsScroll.setPreferredSize(new Dimension(120, 130));
        card.add(boundsScroll, c);
        c.gridy++;
        card.add(columnsStatus, c);
        return card;
    }

    private JPanel sourceRow() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        row.add(new JLabel("Test on:"));
        row.add(source);
        source.addActionListener(e -> updateSource());
        graphClass.setSelectedItem(GraphClass.CONNECTED);
        graphClass.addActionListener(e -> validateAll());
        return row;
    }

    private JPanel exhaustiveCard() {
        JPanel p = flow();
        p.add(new JLabel("n from"));
        p.add(exFrom);
        p.add(new JLabel("to"));
        p.add(exTo);
        return p;
    }

    private JPanel familyCard() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = gbc();
        p.add(generator, c);
        c.gridy++;
        JPanel range = flow();
        range.add(familyParameter);
        range.add(famFrom);
        range.add(new JLabel("to"));
        range.add(famTo);
        p.add(range, c);
        c.gridy++;
        p.add(CheckerStyle.hint("The generator's other parameters keep their current values."), c);
        generator.addActionListener(e -> {
            updateFamilyParameter();
            validateAll();
        });
        updateFamilyParameter();
        return p;
    }

    private JPanel randomCard() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = gbc();
        JPanel a = flow();
        a.add(new JLabel("n from"));
        a.add(rndFrom);
        a.add(new JLabel("to"));
        a.add(rndTo);
        p.add(a, c);
        c.gridy++;
        JPanel b = flow();
        b.add(perSize);
        b.add(new JLabel("graphs for each n,  edge probability"));
        b.add(probability);
        p.add(b, c);
        c.gridy++;
        JPanel d = flow();
        d.add(new JLabel("Seed"));
        d.add(seed);
        d.add(CheckerStyle.hint("the same seed draws the same graphs"));
        p.add(d, c);
        return p;
    }

    private JPanel fileCard() {
        JPanel p = flow();
        JButton browse = new JButton("Browse…");
        browse.addActionListener(e -> chooseFile());
        file.putClientProperty("JTextField.placeholderText", "graph6 file, e.g. from nauty's geng");
        p.add(file);
        p.add(browse);
        return p;
    }

    private JPanel tabsCard() {
        JPanel p = flow();
        p.add(tabsInfo);
        return p;
    }

    /** A card panel as tall as the card showing, not as the tallest card. */
    private static final class CardPanel extends JPanel {
        private static final long serialVersionUID = 1L;

        CardPanel(CardLayout layout) {
            super(layout);
        }

        @Override
        public Dimension getPreferredSize() {
            for (Component c : getComponents()) {
                if (c.isVisible()) {
                    return c.getPreferredSize();
                }
            }
            return super.getPreferredSize();
        }

        @Override
        public Dimension getMaximumSize() {
            return new Dimension(Integer.MAX_VALUE, getPreferredSize().height);
        }
    }

    /** The form: as wide as the space it is given, scrolling only vertically. */
    private static final class WidthTrackingPanel extends JPanel implements javax.swing.Scrollable {
        private static final long serialVersionUID = 1L;

        @Override
        public Dimension getPreferredScrollableViewportSize() {
            return getPreferredSize();
        }

        @Override
        public int getScrollableUnitIncrement(java.awt.Rectangle r, int orientation, int direction) {
            return 16;
        }

        @Override
        public int getScrollableBlockIncrement(java.awt.Rectangle r, int orientation, int direction) {
            return r.height;
        }

        @Override
        public boolean getScrollableTracksViewportWidth() {
            return true;
        }

        @Override
        public boolean getScrollableTracksViewportHeight() {
            return false;
        }
    }

    private static JPanel flow() {
        return new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 2));
    }

    private static GridBagConstraints gbc() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(2, 0, 2, 0);
        return c;
    }

    private static JSpinner spinner(int value, int min, int max) {
        JSpinner s = new JSpinner(new SpinnerNumberModel(value, min, max, 1));
        ((JSpinner.DefaultEditor) s.getEditor()).getTextField().setColumns(4);
        return s;
    }

    // ------------------------------------------------------------------ behaviour

    private void wireValidation() {
        DocumentListener revalidate = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                validateAll();
            }

            public void removeUpdate(DocumentEvent e) {
                validateAll();
            }

            public void changedUpdate(DocumentEvent e) {
                validateAll();
            }
        };
        formula.getDocument().addDocumentListener(revalidate);
        condition.getDocument().addDocumentListener(revalidate);
        file.getDocument().addDocumentListener(revalidate);
        seed.getDocument().addDocumentListener(revalidate);
        for (JSpinner s : new JSpinner[] {exFrom, exTo, famFrom, famTo, rndFrom, rndTo, perSize, probability}) {
            s.addChangeListener(e -> validateAll());
        }
        setupControls.addAll(List.of(formulaMode, columnsMode, formula, condition, report, leftColumn, relation,
                boundColumns, source, graphClass, exFrom, exTo, generator, famFrom, famTo, rndFrom, rndTo,
                probability, perSize, seed, file, stopAtFirst, examples));
    }

    private void updateConjectureMode() {
        conjectureCards.show(conjecturePanel, formulaMode.isSelected() ? FORMULA : COLUMNS);
        conjecturePanel.revalidate();
        if (columnsMode.isSelected() && columnTitles.isEmpty() && report.getItemCount() > 0) {
            loadColumns();
        }
        validateAll();
    }

    private void updateSource() {
        String s = (String) source.getSelectedItem();
        sourceCards.show(sourcePanel, s);
        sourcePanel.revalidate();
        classRow.setVisible(!SOURCES[1].equals(s));
        updateTabsInfo();
        validateAll();
    }

    private void updateTabsInfo() {
        int n = openGraphs().size();
        tabsInfo.setText(n == 0 ? "No graphs are open." : n == 1 ? "1 graph is open." : n + " graphs are open.");
    }

    private void updateFamilyParameter() {
        GraphGeneratorExtension g = generators.get((String) generator.getSelectedItem());
        Field f = g == null ? null : GraphSource.integerParameter(g);
        familyParameter.setText((f == null ? "n" : f.getName()) + " from");
    }

    private void loadColumns() {
        GraphReportExtension<RenderTable> r = tableReports.get((String) report.getSelectedItem());
        leftColumn.removeAllItems();
        columnTitles = List.of();
        boundColumns.setListData(new String[0]);
        if (r == null) {
            return;
        }
        try {
            columnTitles = Conjecture.columnTitles(r);
        } catch (IllegalStateException e) {
            setStatus(columnsStatus, e.getMessage(), false);
            validateAll();
            return;
        }
        for (String t : columnTitles) {
            leftColumn.addItem(t.trim());
        }
        boundColumns.setListData(columnTitles.stream().map(String::trim).toArray(String[]::new));
        leftColumn.setSelectedIndex(0);
        defaultBounds();
    }

    /** Selects every column but the bounded one as a bound. */
    private void defaultBounds() {
        int left = leftColumn.getSelectedIndex();
        List<Integer> picks = new ArrayList<>();
        for (int i = 0; i < columnTitles.size(); i++) {
            if (i != left) {
                picks.add(i);
            }
        }
        boundColumns.setSelectedIndices(picks.stream().mapToInt(Integer::intValue).toArray());
        validateAll();
    }

    private void showPicker(Component anchor) {
        if (picker == null) {
            picker = new InvariantPicker(this, invariants);
        }
        picker.showFor(lastFormulaField, anchor);
    }

    private void chooseFile() {
        JFileChooser chooser = new JFileChooser(file.getText().isBlank() ? null : new File(file.getText()));
        chooser.setFileFilter(new FileNameExtensionFilter("graph6 files (*.g6, *.txt)", "g6", "txt"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            file.setText(chooser.getSelectedFile().getPath());
        }
    }

    private List<GraphModel> openGraphs() {
        GTabbedGraphPane tabs = blackboard.getData(GTabbedGraphPane.NAME);
        return tabs == null ? List.of() : new ArrayList<>(tabs.getGraphs().values());
    }

    // ------------------------------------------------------------------ validation

    /** Checks everything, says what is wrong next to it, and enables Check only when all is well. */
    private void validateAll() {
        boolean conjectureOk = formulaMode.isSelected() ? validateFormula() : validateColumns();
        String sourceProblem = sourceProblem();
        setStatus(sourceStatus, sourceProblem == null ? sourceSummary() : sourceProblem, sourceProblem == null);
        if (sourceProblem == null) {
            sourceStatus.setForeground(sizeWarning() ? CheckerStyle.warning() : CheckerStyle.muted());
        }
        boolean ok = conjectureOk && sourceProblem == null;
        if (worker == null) {
            check.setEnabled(ok);
            checkStatus.setText(ok ? "" : "Fix the problems above to check.");
            checkStatus.setForeground(CheckerStyle.muted());
        }
    }

    private boolean validateFormula() {
        boolean ok = true;
        try {
            Expression e = Expression.parse(formula.getText());
            String unknown = invariants.unknownName(e);
            if (unknown != null) {
                setStatus(formulaStatus, unknown, false);
                ok = false;
            } else if (e.relation() == null) {
                setStatus(formulaStatus, "Compare two sides with <=, <, >=, >, = or !=, e.g. energy <= 2*m.", false);
                ok = false;
            } else {
                setStatus(formulaStatus, "Tests " + Conjecture.formula(e).statement(), true);
            }
        } catch (Expression.SyntaxException e) {
            setStatus(formulaStatus, e.getMessage() + where(formula.getText(), e.position()), false);
            ok = false;
        }
        if (condition.getText().isBlank()) {
            setStatus(conditionStatus, "Leave empty to test every graph.", true);
            conditionStatus.setForeground(CheckerStyle.muted());
            return ok;
        }
        try {
            Expression c = Expression.parse(condition.getText());
            String unknown = invariants.unknownName(c);
            setStatus(conditionStatus, unknown != null ? unknown : "Graphs where this is false are skipped.",
                    unknown == null);
            return ok && unknown == null;
        } catch (Expression.SyntaxException e) {
            setStatus(conditionStatus, e.getMessage() + where(condition.getText(), e.position()), false);
            return false;
        }
    }

    private static String where(String text, int position) {
        if (position >= text.length()) {
            return " (at the end)";
        }
        return " (at \"" + text.substring(position, Math.min(text.length(), position + 12)) + "\")";
    }

    private boolean validateColumns() {
        if (tableReports.isEmpty()) {
            setStatus(columnsStatus, "No table reports are loaded.", false);
            return false;
        }
        if (columnTitles.isEmpty()) {
            return false;
        }
        int n = boundColumns.getSelectedIndices().length;
        boolean includesLeft = boundColumns.isSelectedIndex(leftColumn.getSelectedIndex());
        if (n == 0 || includesLeft && n == 1) {
            setStatus(columnsStatus, "Choose at least one column to compare with.", false);
            return false;
        }
        int bounds = includesLeft ? n - 1 : n;
        setStatus(columnsStatus, "Tests " + bounds + (bounds == 1 ? " bound" : " bounds") + " at once.", true);
        return true;
    }

    private String sourceProblem() {
        switch (source.getSelectedIndex()) {
            case 0:
                return exhaustiveProblem();
            case 1:
                if (generators.isEmpty()) {
                    return "No generators with a size parameter are loaded.";
                }
                return intValue(famFrom) > intValue(famTo) ? "The first value is larger than the last." : null;
            case 2:
                if (intValue(rndFrom) > intValue(rndTo)) {
                    return "The smallest n is larger than the largest.";
                }
                return parseSeed() == null ? "The seed must be a whole number." : null;
            case 3:
                if (file.getText().isBlank()) {
                    return "Choose a graph6 file.";
                }
                return new File(file.getText()).isFile() ? null : "There is no file at " + file.getText();
            default:
                return openGraphs().isEmpty() ? "Open or draw a graph first." : null;
        }
    }

    private String exhaustiveProblem() {
        int from = intValue(exFrom);
        int to = intValue(exTo);
        if (from > to) {
            return "The smallest n is larger than the largest.";
        }
        GraphClass cls = (GraphClass) graphClass.getSelectedItem();
        int limit = GraphSource.exhaustiveLimit(cls);
        if (to > limit) {
            if (cls != GraphClass.TREES && limit == SmallGraphs.MAX_ALL) {
                return "<html>Without nauty, GraphTea lists all graphs up to " + limit + " vertices. Install "
                        + "nauty (it provides geng) to go further, or test random graphs.</html>";
            }
            return "All " + cls.toString().toLowerCase() + " are listed up to " + limit + " vertices.";
        }
        return null;
    }

    private String sourceSummary() {
        if (source.getSelectedIndex() != 0) {
            return " ";
        }
        long n = buildSource().expectedCount();
        if (n < 0) {
            return " ";
        }
        return sizeWarning() ? CheckerStyle.graphs(n) + " — this will take a long time; Stop is always available."
                : CheckerStyle.graphs(n) + " to test.";
    }

    private boolean sizeWarning() {
        return source.getSelectedIndex() == 0 && exhaustiveProblem() == null && buildSource().expectedCount() > LARGE;
    }

    private static void setStatus(JLabel label, String text, boolean ok) {
        label.setText(text);
        label.setToolTipText(text.isBlank() ? null : text);
        label.setForeground(ok ? CheckerStyle.success() : CheckerStyle.failure());
    }

    private static int intValue(JSpinner s) {
        return ((Number) s.getValue()).intValue();
    }

    private Long parseSeed() {
        try {
            return Long.parseLong(seed.getText().trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // ------------------------------------------------------------------ building a run

    private GraphSource buildSource() {
        GraphClass cls = (GraphClass) graphClass.getSelectedItem();
        switch (source.getSelectedIndex()) {
            case 0:
                return GraphSource.exhaustive(cls, intValue(exFrom), intValue(exTo));
            case 1:
                GraphGeneratorExtension g = generators.get((String) generator.getSelectedItem());
                return GraphSource.family(g, GraphSource.integerParameter(g), intValue(famFrom), intValue(famTo));
            case 2:
                Long s = parseSeed();
                return GraphSource.random(cls, intValue(rndFrom), intValue(rndTo),
                        ((Number) probability.getValue()).doubleValue(), intValue(perSize), s == null ? 1 : s);
            case 3:
                return GraphSource.file(new File(file.getText().trim()), cls);
            default:
                return GraphSource.of(copies(openGraphs()), "the graphs open in tabs", cls);
        }
    }

    /** Copies open graphs, since reports such as Chromatic Number recolour the graph they are given. */
    private static List<GraphModel> copies(List<GraphModel> graphs) {
        List<GraphModel> out = new ArrayList<>();
        for (GraphModel g : graphs) {
            out.add(g.getVerticesCount() == 0 ? new GraphModel(false)
                    : G6Format.stringToGraphModel(G6Format.graphToG6(g)));
        }
        return out;
    }

    private Conjecture buildConjecture() {
        if (formulaMode.isSelected()) {
            return Conjecture.formula(Expression.parse(formula.getText()));
        }
        GraphReportExtension<RenderTable> r = tableReports.get((String) report.getSelectedItem());
        int left = leftColumn.getSelectedIndex();
        int[] rights = java.util.Arrays.stream(boundColumns.getSelectedIndices()).filter(i -> i != left).toArray();
        return Conjecture.columns(r, columnTitles, left, rights, (Relation) relation.getSelectedItem());
    }

    // ------------------------------------------------------------------ running

    private void checkOrStop() {
        if (worker != null) {
            cancelled.set(true);
            check.setEnabled(false);
            check.setText("Stopping…");
            return;
        }
        if (!check.isEnabled()) {
            return;
        }
        Conjecture conjecture = buildConjecture();
        GraphSource src = buildSource();
        Expression cond = formulaMode.isSelected() && !condition.getText().isBlank()
                ? Expression.parse(condition.getText()) : null;
        ConjectureRunner.Settings settings = new ConjectureRunner.Settings().condition(cond)
                .stopAtFirst(stopAtFirst.isSelected()).cancelled(cancelled::get);

        results.showNote(pendingNote);
        pendingNote = null;
        results.showRunning(conjecture.statement() + " — on " + src.describe());
        setRunning(true);
        worker = new SwingWorker<>() {
            @Override
            protected ConjectureRunner.Result doInBackground() throws Exception {
                return ConjectureRunner.run(conjecture, invariants, src, settings.progress(this::publish));
            }

            @Override
            protected void process(List<ConjectureRunner.Progress> chunks) {
                results.showProgress(chunks.get(chunks.size() - 1));
            }

            @Override
            protected void done() {
                finish(this);
            }
        };
        worker.execute();
    }

    private void finish(SwingWorker<ConjectureRunner.Result, ConjectureRunner.Progress> w) {
        try {
            results.showResult(w.get());
        } catch (ExecutionException e) {
            Throwable cause = e.getCause() == null ? e : e.getCause();
            results.showError(cause.getMessage() == null ? cause.toString() : cause.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        worker = null;
        setRunning(false);
        validateAll();
    }

    private void setRunning(boolean running) {
        cancelled.set(false);
        for (JComponent c : setupControls) {
            c.setEnabled(!running);
        }
        check.setText(running ? "Stop" : "Check");
        check.setEnabled(true);
        checkStatus.setText(running ? "Esc or Stop ends the run; what was found so far is kept." : "");
        checkStatus.setForeground(CheckerStyle.muted());
    }

    private void closeOrStop() {
        if (worker != null) {
            cancelled.set(true);
            return;
        }
        if (picker != null) {
            picker.dispose();
        }
        setVisible(false);
    }

    // ------------------------------------------------------------------ examples

    private void showExamples() {
        JPopupMenu menu = new JPopupMenu();
        for (Example ex : Example.all()) {
            JMenuItem item = new JMenuItem(ex.title());
            item.setToolTipText("<html><div style='width:320px'>" + ex.explanation() + "</div></html>");
            item.addActionListener(e -> runExample(ex));
            menu.add(item);
        }
        menu.show(examples, 0, examples.getHeight());
    }

    /**
     * Fills the form in from an example and runs it, so the form shows how it was set up.
     *
     * @param ex the example
     */
    private void runExample(Example ex) {
        if (ex.formula() != null) {
            formulaMode.setSelected(true);
            formula.setText(ex.formula());
            condition.setText(ex.condition());
        } else {
            columnsMode.setSelected(true);
            report.setSelectedItem(ex.report());
            loadColumns();
            relation.setSelectedItem(ex.relation());
        }
        updateConjectureMode();
        graphClass.setSelectedItem(ex.graphClass());
        switch (ex.source()) {
            case FAMILY:
                source.setSelectedIndex(1);
                generator.setSelectedItem(ex.generator());
                famFrom.setValue(ex.from());
                famTo.setValue(ex.to());
                break;
            case RANDOM:
                source.setSelectedIndex(2);
                rndFrom.setValue(ex.from());
                rndTo.setValue(ex.to());
                probability.setValue(ex.probability());
                perSize.setValue(ex.perSize());
                seed.setText("1");
                break;
            default:
                source.setSelectedIndex(0);
                exFrom.setValue(ex.from());
                exTo.setValue(ex.to());
        }
        stopAtFirst.setSelected(ex.stopAtFirst());
        validateAll();
        pendingNote = "Example — " + ex.title() + "\n" + ex.explanation();
        if (check.isEnabled()) {
            checkOrStop();
        } else {
            results.showNote(pendingNote);
        }
    }
}
