// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.main.core.actions;

import graphtea.graph.ui.GHTMLPageComponent;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.ui.UIUtils;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.net.URL;
import java.util.prefs.Preferences;

/**
 * Shows the getting-started page in a window the user can actually read.
 *
 * <p>The page used to be pushed into the tab's helper strip, which the layout fixes at 75
 * pixels high &mdash; far too small for anything but a one-line status message. Combined with
 * the page being fetched from a URL that no longer resolves, the practical effect was that
 * GraphTea had no onboarding at all. It now opens in its own window on first run, and stays
 * reachable from <b>Help &rsaquo; Getting Started</b>.
 */
public class ShowWelcomeDialog extends AbstractAction {

    /** UI event this action listens for. */
    public static final String EVENT = UIUtils.getUIEventKey("show welcome");

    private static final String PREF_NODE = "graphtea";
    private static final String PREF_SHOW_ON_STARTUP = "showWelcomeOnStartup";

    private final BlackBoard bb;
    private JDialog dialog;

    /**
     * @param bb the blackboard of the action
     */
    public ShowWelcomeDialog(BlackBoard bb) {
        super(bb);
        this.bb = bb;
        listen4Event(EVENT);
    }

    public void performAction(String eventName, Object value) {
        show();
    }

    /**
     * @return whether the user still wants this window at startup
     */
    public static boolean showOnStartup() {
        try {
            return Preferences.userRoot().node(PREF_NODE).getBoolean(PREF_SHOW_ON_STARTUP, true);
        } catch (RuntimeException e) {
            return true;
        }
    }

    private static void setShowOnStartup(boolean show) {
        try {
            Preferences.userRoot().node(PREF_NODE).putBoolean(PREF_SHOW_ON_STARTUP, show);
        } catch (RuntimeException e) {
            ExceptionHandler.catchExceptionQuietly(e);
        }
    }

    /**
     * Opens the window, building it the first time.
     */
    public void show() {
        if (GraphicsEnvironment.isHeadless()) {
            return;
        }
        SwingUtilities.invokeLater(() -> {
            if (dialog == null) {
                dialog = build();
            }
            dialog.setVisible(true);
            dialog.toFront();
        });
    }

    private JDialog build() {
        // GHTMLPageComponent rather than a plain JEditorPane: it understands the
        // "?handler=PerformExtension" links, so the example graphs on the page are clickable.
        GHTMLPageComponent page = new GHTMLPageComponent(bb);
        URL welcome = graphtea.plugins.main.Init.class.getResource("welcome.html");
        if (welcome != null) {
            page.setPage(welcome);
        }

        JCheckBox showAgain = new JCheckBox("Show this when GraphTea starts", showOnStartup());
        showAgain.addActionListener(e -> setShowOnStartup(showAgain.isSelected()));

        JButton close = new JButton("Start using GraphTea");
        close.addActionListener(e -> dialog.setVisible(false));

        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        buttons.setBorder(BorderFactory.createEmptyBorder(10, 14, 12, 14));
        buttons.add(showAgain);
        buttons.add(Box.createHorizontalGlue());
        buttons.add(close);

        JPanel content = new JPanel(new BorderLayout());
        content.add(page, BorderLayout.CENTER);
        content.add(buttons, BorderLayout.SOUTH);

        JDialog d = new JDialog(UIUtils.getGFrame(bb), "Getting started with GraphTea", false);
        d.setContentPane(content);
        d.setPreferredSize(new Dimension(780, 640));
        d.pack();
        d.setLocationRelativeTo(UIUtils.getGFrame(bb));
        d.getRootPane().setDefaultButton(close);
        return d;
    }
}
