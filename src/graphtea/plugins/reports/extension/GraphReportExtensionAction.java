// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.reports.extension;

import graphtea.extensions.reports.boundcheck.forall.IterGraphs;
import graphtea.graph.graph.GraphModel;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.platform.ui.UserNotifier;
import graphtea.plugins.main.GraphData;
import graphtea.ui.UIUtils;
import graphtea.ui.components.gpropertyeditor.GCellRenderer;
import graphtea.ui.components.utils.GFrameLocationProvider;
import graphtea.ui.extension.AbstractExtensionAction;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

/**
 * Runs a report and shows the answer.
 *
 * <p>Several things about the previous version made reports hard to trust. A report that threw
 * produced no window and no message, so it was indistinguishable from a mis-click. A report
 * that returned null did the same. A report that took a long time &mdash; chromatic number and
 * maximum clique are exponential &mdash; gave no sign it was running, so the application simply
 * looked frozen. The result window was built off the event thread and shown before it had any
 * contents. And every window opened at one fixed screen position, so a second report landed
 * exactly on top of the first.
 *
 * @author M. Ali Rostami - Conjecture check
 * @author Hooman Mohajeri Moghaddam - added save button, fixed recalculate button
 * @author azin azadi
 */
public class GraphReportExtensionAction extends AbstractExtensionAction {

    /** Offset between successive result windows, so they cascade instead of stacking. */
    private static final int CASCADE_STEP = 26;

    /** How many windows to cascade before starting again at the top left. */
    private static final int CASCADE_WRAP = 12;

    /** Delay before the "working" window appears; quick reports never show it at all. */
    private static final int PROGRESS_DELAY_MS = 400;

    private static int openResultWindows;

    protected GraphReportExtension mr;
    public static IterGraphs ig = null;

    public GraphReportExtensionAction(BlackBoard bb, GraphReportExtension gg) {
        super(bb, gg);
        this.mr = gg;
    }

    public String getParentMenuName() {
        return "Reports";
    }

    public void performExtension() {
        GraphModel graph = new GraphData(blackboard).getGraph();
        String problem = unmetPrecondition(graph);
        if (problem != null) {
            JOptionPane.showMessageDialog(UIUtils.getGFrame(blackboard), problem,
                    mr.getName(), JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        runAndShow(graph);
    }

    /**
     * Checks that the report can meaningfully run against this graph.
     *
     * <p>Without this an empty graph produced either an invisible
     * {@code ArrayIndexOutOfBoundsException} or, worse, a confident wrong number: the chromatic
     * number of a graph with no vertices was reported as 2.
     *
     * @param graph the graph to report on, may be null
     * @return a sentence explaining why the report cannot run, or null when it can
     */
    private String unmetPrecondition(GraphModel graph) {
        if (graph == null) {
            return "There is no graph open. Use File ▸ New Tab to start one.";
        }
        if (graph.getVerticesCount() == 0) {
            return mr.getName() + " needs a graph with at least one vertex.\n\n"
                    + "Click the canvas to add vertices, or pick something from the "
                    + "Generate Graph menu.";
        }
        return null;
    }

    /**
     * Calculates off the event thread, showing progress if it takes a noticeable time, then
     * hands the answer to {@link #showResult}.
     *
     * @param graph the graph to report on
     */
    private void runAndShow(GraphModel graph) {
        JDialog progress = buildProgressDialog();
        Timer reveal = new Timer(PROGRESS_DELAY_MS, e -> progress.setVisible(true));
        reveal.setRepeats(false);
        reveal.start();

        progress.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // The calculation cannot be interrupted safely, but the user can stop waiting
                // on it. The worker is a daemon, so it will not hold up shutdown.
                reveal.stop();
                progress.dispose();
            }
        });

        Thread worker = new Thread(() -> {
            Object result;
            try {
                result = calculate(graph);
            } catch (Throwable t) {
                SwingUtilities.invokeLater(() -> {
                    reveal.stop();
                    progress.dispose();
                });
                ExceptionHandler.catchException(mr.getName(), t);
                return;
            }
            final Object answer = result;
            SwingUtilities.invokeLater(() -> {
                reveal.stop();
                progress.dispose();
                if (answer == null) {
                    // Previously a bare return, indistinguishable from nothing happening.
                    JOptionPane.showMessageDialog(UIUtils.getGFrame(blackboard),
                            mr.getName() + " produced no result for this graph.",
                            mr.getName(), JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                showResult(graph, answer);
            });
        }, "graphtea-report-" + mr.getName());
        worker.setDaemon(true);
        worker.start();
    }

    private Object calculate(GraphModel graph) {
        if (ig != null && ig.activeConjCheck && !"Bound Check".equals(mr.getName())) {
            return ig.wrapper(mr);
        }
        return mr.calculate(graph);
    }

    private JDialog buildProgressDialog() {
        JProgressBar bar = new JProgressBar();
        bar.setIndeterminate(true);

        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));
        panel.add(new JLabel("Calculating " + mr.getName() + "…"), BorderLayout.NORTH);
        panel.add(bar, BorderLayout.CENTER);
        panel.add(new JLabel("<html><i>Close this window to stop waiting.</i></html>"),
                BorderLayout.SOUTH);

        JDialog d = new JDialog(UIUtils.getGFrame(blackboard), mr.getName(), false);
        d.setContentPane(panel);
        d.pack();
        d.setLocationRelativeTo(UIUtils.getGFrame(blackboard));
        return d;
    }

    /**
     * Builds and shows the result window. Runs on the event thread.
     *
     * @param graph  the graph the answer is about
     * @param result the answer
     */
    private void showResult(GraphModel graph, Object result) {
        final Component[] body = {renderOf(result)};

        JPanel content = new JPanel(new BorderLayout(0, 10));
        content.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        content.add(new JLabel(mr.getDescription() + ':'), BorderLayout.NORTH);
        content.add(body[0], BorderLayout.CENTER);

        JDialog d = new JDialog(UIUtils.getGFrame(blackboard), windowTitle(graph), false);

        JButton recalc = new JButton("Recalculate");
        JButton save = new JButton("Save…");
        JButton close = new JButton("Close");

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttons.add(recalc);
        buttons.add(save);
        buttons.add(close);
        content.add(buttons, BorderLayout.SOUTH);

        final Object[] current = {result};

        recalc.addActionListener(e -> {
            GraphModel now = new GraphData(blackboard).getGraph();
            String problem = unmetPrecondition(now);
            if (problem != null) {
                JOptionPane.showMessageDialog(d, problem, mr.getName(), JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            try {
                Object again = calculate(now);
                if (again == null) {
                    JOptionPane.showMessageDialog(d,
                            mr.getName() + " produced no result for this graph.",
                            mr.getName(), JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                current[0] = again;
                content.remove(body[0]);
                body[0] = renderOf(again);
                content.add(body[0], BorderLayout.CENTER);
                d.setTitle(windowTitle(now));
                d.pack();
                d.repaint();
            } catch (Throwable t) {
                ExceptionHandler.catchException(mr.getName(), t);
            }
        });

        save.addActionListener(e -> save(d, current[0], body[0]));
        close.addActionListener(e -> d.dispose());

        d.setContentPane(content);
        // pack() before setVisible(): the window used to be shown empty and then grow as its
        // contents were added, which read as a flash of broken UI.
        d.pack();
        d.setLocation(cascadeLocation());
        d.getRootPane().setDefaultButton(close);
        d.setVisible(true);
    }

    /**
     * @param result a value a report produced
     * @return a component showing it; never null
     */
    private static Component renderOf(Object result) {
        Component rendered = GCellRenderer.getRendererFor(result);
        if (rendered == null) {
            // Not every result type has a registered renderer. Show the value rather than
            // an empty window.
            JTextArea fallback = new JTextArea(String.valueOf(result));
            fallback.setEditable(false);
            rendered = new JScrollPane(fallback);
        }
        rendered.setEnabled(true);
        return rendered;
    }

    /**
     * @param graph the graph the report ran against
     * @return a title saying what the answer is, and what it is about
     */
    private String windowTitle(GraphModel graph) {
        String name = graph == null ? null : graph.getLabel();
        String when = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        if (name == null || name.isBlank()) {
            return mr.getName() + " — " + when;
        }
        return mr.getName() + " — " + name + " — " + when;
    }

    /**
     * @return where to put the next result window, cascaded off the last one
     */
    private static synchronized Point cascadeLocation() {
        Point base = GFrameLocationProvider.getPopUpLocation();
        int step = (openResultWindows++ % CASCADE_WRAP) * CASCADE_STEP;
        return new Point(base.x + step, base.y + step);
    }

    /**
     * Writes the result to a text file.
     *
     * <p>The old implementation cast the rendering component to {@code JScrollPane} and its
     * view to {@code JTable} or {@code JList}, throwing invisibly for anything else, and wrote
     * {@code JList.toString()} &mdash; a Java object dump &mdash; in the list case. It also
     * added a new file filter on every click, so the format dropdown grew a duplicate entry
     * each time it was used. This version formats the result value itself.
     *
     * @param parent   window to parent the file chooser to
     * @param result   the value the report produced
     * @param rendered the component showing it, used for tabular results
     */
    private void save(JDialog parent, Object result, Component rendered) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save " + mr.getName());
        chooser.setFileFilter(new FileNameExtensionFilter("Text file (*.txt)", "txt"));
        chooser.setSelectedFile(new File(suggestedFileName()));
        if (chooser.showSaveDialog(parent) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File file = chooser.getSelectedFile();
        if (file == null) {
            return;
        }
        if (!file.getName().contains(".")) {
            file = new File(file.getParentFile(), file.getName() + ".txt");
        }
        if (file.exists()) {
            int overwrite = JOptionPane.showConfirmDialog(parent,
                    file.getName() + " already exists. Replace it?", "Replace file?",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (overwrite != JOptionPane.YES_OPTION) {
                return;
            }
        }
        try (FileWriter out = new FileWriter(file)) {
            out.write(mr.getName());
            out.write(System.lineSeparator());
            out.write(format(result, rendered));
        } catch (IOException ex) {
            ExceptionHandler.catchException("Saving " + mr.getName(), ex);
            return;
        }
        UserNotifier.toast((JComponent) parent.getContentPane(), "Saved to " + file.getName());
    }

    private String suggestedFileName() {
        return mr.getName().replaceAll("[^A-Za-z0-9]+", "-").toLowerCase() + ".txt";
    }

    /**
     * Renders a result value as text.
     *
     * @param result   the value
     * @param rendered the component showing it; a table is read row by row so that what is
     *                 saved matches what is on screen
     * @return the text to write
     */
    private String format(Object result, Component rendered) {
        JTable table = tableIn(rendered);
        if (table != null) {
            StringBuilder sb = new StringBuilder();
            for (int row = 0; row < table.getRowCount(); row++) {
                for (int col = 0; col < table.getColumnCount(); col++) {
                    if (col > 0) {
                        sb.append(',');
                    }
                    sb.append(table.getValueAt(row, col));
                }
                sb.append(System.lineSeparator());
            }
            return sb.toString();
        }
        return formatValue(result);
    }

    private static String formatValue(Object value) {
        StringBuilder sb = new StringBuilder();
        if (value instanceof Collection<?> c) {
            for (Object o : c) {
                sb.append(o).append(System.lineSeparator());
            }
        } else if (value != null && value.getClass().isArray()) {
            int n = Array.getLength(value);
            for (int i = 0; i < n; i++) {
                sb.append(formatValue(Array.get(value, i)));
            }
        } else {
            sb.append(value).append(System.lineSeparator());
        }
        return sb.toString();
    }

    private static JTable tableIn(Component c) {
        if (c instanceof JTable t) {
            return t;
        }
        if (c instanceof JScrollPane sp) {
            JViewport vp = sp.getViewport();
            if (vp != null && vp.getView() instanceof JTable t) {
                return t;
            }
        }
        return null;
    }
}
