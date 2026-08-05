// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.platform.ui;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * The single place where GraphTea tells the user that something went wrong.
 *
 * <p>Historically failures were printed to {@code System.err} and forwarded to analytics, which
 * meant a user who clicked a report that threw saw nothing at all &mdash; no dialog, no message,
 * no hint that anything had happened. This class gives those failures a visible, dismissible home.
 *
 * <p>Problems are collected into one modeless window rather than one dialog per failure, so a
 * repeating error cannot bury the application under a stack of popups. The window never steals
 * focus and can be silenced for the rest of the session.
 */
public final class UserNotifier {

    /** Cap on retained entries, so a runaway loop cannot exhaust memory. */
    private static final int MAX_ENTRIES = 200;

    private static final List<Problem> PROBLEMS = new ArrayList<>();

    private static JDialog dialog;
    private static DefaultListModel<Problem> listModel;
    private static JList<Problem> list;
    private static JTextArea details;
    private static JCheckBox muted;

    /**
     * Problems are collected but not shown until the main window exists. Without this a
     * failure while loading plugins would pop a dialog over the splash screen, before the
     * user has anything to relate it to.
     */
    private static boolean armed;

    private UserNotifier() {
    }

    /** Called once the main window is up; releases anything reported during startup. */
    public static void arm() {
        if (GraphicsEnvironment.isHeadless()) {
            return;
        }
        SwingUtilities.invokeLater(() -> {
            armed = true;
            List<Problem> pending = new ArrayList<>(PROBLEMS);
            PROBLEMS.clear();
            for (Problem p : pending) {
                add(p);
            }
        });
    }

    /** One reported failure: what the user was doing, and what went wrong. */
    private static final class Problem {
        private final String context;
        private final Throwable error;
        private final String stackTrace;

        private Problem(String context, Throwable error) {
            this.context = context;
            this.error = error;
            StringWriter sw = new StringWriter();
            error.printStackTrace(new PrintWriter(sw));
            this.stackTrace = sw.toString();
        }

        private String headline() {
            String m = error.getLocalizedMessage();
            if (m == null || m.isBlank()) {
                m = error.getClass().getSimpleName();
            }
            return m;
        }

        @Override
        public String toString() {
            return context == null || context.isBlank() ? headline() : context + " — " + headline();
        }
    }

    /**
     * Reports a failure to the user.
     *
     * @param context what the user was doing, in their words (e.g. {@code "Chromatic Number"}); may be null
     * @param error   the failure; ignored if null
     */
    public static void report(String context, Throwable error) {
        if (error == null || GraphicsEnvironment.isHeadless()) {
            return;
        }
        Problem p = new Problem(context, error);
        SwingUtilities.invokeLater(() -> add(p));
    }

    /**
     * Shows a short, self-dismissing message that is not an error. Use for confirmations
     * ("Saved to notes.txt") rather than for anything the user must act on.
     *
     * @param parent  component to centre on; may be null
     * @param message the text to show
     */
    public static void toast(JComponent parent, String message) {
        if (GraphicsEnvironment.isHeadless()) {
            return;
        }
        SwingUtilities.invokeLater(() -> {
            JLabel label = new JLabel(message);
            label.setBorder(BorderFactory.createEmptyBorder(14, 18, 14, 18));
            JDialog d = new JDialog(parent == null ? null : SwingUtilities.getWindowAncestor(parent));
            d.setUndecorated(true);
            d.setFocusableWindowState(false);
            d.getContentPane().add(label);
            d.pack();
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            d.setLocation((screen.width - d.getWidth()) / 2, screen.height - d.getHeight() - 120);
            d.setVisible(true);
            Timer t = new Timer(2600, e -> d.dispose());
            t.setRepeats(false);
            t.start();
        });
    }

    private static void add(Problem p) {
        if (!armed) {
            PROBLEMS.add(p);
            while (PROBLEMS.size() > MAX_ENTRIES) {
                PROBLEMS.remove(0);
            }
            return;
        }
        if (muted != null && muted.isSelected()) {
            return;
        }
        ensureDialog();
        listModel.addElement(p);
        while (listModel.size() > MAX_ENTRIES) {
            listModel.remove(0);
        }
        list.setSelectedIndex(listModel.size() - 1);
        list.ensureIndexIsVisible(listModel.size() - 1);
        if (!dialog.isVisible()) {
            dialog.setVisible(true);
        }
        dialog.setTitle(title());
    }

    private static String title() {
        int n = listModel.size();
        return n == 1 ? "A problem occurred" : n + " problems occurred";
    }

    private static void ensureDialog() {
        if (dialog != null) {
            return;
        }
        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setVisibleRowCount(6);
        list.addListSelectionListener(e -> {
            Problem sel = list.getSelectedValue();
            details.setText(sel == null ? "" : sel.stackTrace);
            details.setCaretPosition(0);
        });

        details = new JTextArea(10, 70);
        details.setEditable(false);
        details.setLineWrap(false);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                new JScrollPane(list), new JScrollPane(details));
        split.setResizeWeight(0.35);

        JLabel blurb = new JLabel("<html>GraphTea could not finish an action. "
                + "Your graph has not been changed.<br>"
                + "The technical detail below is useful when reporting the problem.</html>");
        blurb.setBorder(BorderFactory.createEmptyBorder(12, 12, 8, 12));

        muted = new JCheckBox("Don't show again this session");

        JButton copy = new JButton("Copy details");
        copy.addActionListener(e -> {
            Problem sel = list.getSelectedValue();
            if (sel != null) {
                Toolkit.getDefaultToolkit().getSystemClipboard()
                        .setContents(new StringSelection(sel.stackTrace), null);
            }
        });
        JButton close = new JButton("Close");
        close.addActionListener(e -> dialog.setVisible(false));

        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        buttons.setBorder(BorderFactory.createEmptyBorder(8, 12, 12, 12));
        buttons.add(muted);
        buttons.add(Box.createHorizontalGlue());
        buttons.add(copy);
        buttons.add(Box.createHorizontalStrut(8));
        buttons.add(close);

        JPanel content = new JPanel(new BorderLayout());
        content.add(blurb, BorderLayout.NORTH);
        content.add(split, BorderLayout.CENTER);
        content.add(buttons, BorderLayout.SOUTH);

        dialog = new JDialog((java.awt.Frame) null, title(), false);
        dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialog.setContentPane(content);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        // Never take focus from whatever the user is doing.
        dialog.setAutoRequestFocus(false);
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (muted.isSelected()) {
                    listModel.clear();
                }
            }
        });
    }

    /**
     * Asks the user a yes/no question, returning the answer. Returns {@code defaultAnswer}
     * when running without a display.
     *
     * @param title         window title
     * @param message       the question, may contain HTML
     * @param yes           label for the affirmative button
     * @param no            label for the negative button
     * @param defaultAnswer what to return when headless
     * @return true when the user picked {@code yes}
     */
    public static boolean ask(String title, String message, String yes, String no, boolean defaultAnswer) {
        if (GraphicsEnvironment.isHeadless()) {
            return defaultAnswer;
        }
        int choice = JOptionPane.showOptionDialog(null, message, title,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                new Object[]{yes, no}, defaultAnswer ? yes : no);
        return choice == JOptionPane.YES_OPTION;
    }
}
