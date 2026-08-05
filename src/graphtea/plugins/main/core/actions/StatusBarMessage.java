// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.main.core.actions;

import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.ui.UIUtils;
import graphtea.ui.components.GComponentInterface;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.Component;
import java.awt.event.ActionEvent;

/**
 * A one-line message in the status bar, driven from anywhere via the blackboard.
 *
 * <p>Declare it in the UI XML as
 * {@code <bar class="graphtea.plugins.main.core.actions.StatusBarMessage" id="user message"/>}.
 *
 * <p>The previous implementation spawned a raw thread for every message, which set the label's
 * text, background and opacity and called {@code repaint()} from off the event dispatch thread
 * — the one thing Swing components may not have done to them. It also reported an interrupt of
 * that sleep through the normal exception path, which now raises a visible error dialog, so a
 * harmless interrupt would have shown the user a stack trace. And the auto-clear timer was held
 * in a single static field, so two messages in quick succession left the first one's timer to
 * wipe out the second one's text.
 *
 * @author azin azadi
 */
public class StatusBarMessage extends AbstractAction implements GComponentInterface {

    /** How long {@link #showQuickMessage} leaves a message up. */
    private static final int QUICK_MESSAGE_MS = 3000;

    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */
    public StatusBarMessage(BlackBoard bb) {
        super(bb);
    }

    public void performAction(String eventName, Object value) {

    }

    /**
     * Shows a message and clears it again after a few seconds.
     *
     * @param b       the blackboard whose frame carries the status bar
     * @param message the message to show
     */
    public static void showQuickMessage(final BlackBoard b, String message) {
        setLabelMessage(b, message);
        // One timer per message. A shared static timer meant a second message inherited the
        // first message's deadline and was cleared early.
        Timer timer = new Timer(QUICK_MESSAGE_MS, e -> setLabelMessage(b, ""));
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Shows a message until something replaces it.
     *
     * @param b the blackboard whose frame carries the status bar
     * @param s the message to show
     */
    public static void setMessage(BlackBoard b, String s) {
        setLabelMessage(b, s);
    }

    private static void setLabelMessage(BlackBoard b, String msg) {
        Runnable update = () -> {
            Component c = UIUtils.getComponent(b, "user message");
            // The bar is only present once the UI XML has been read; callers during startup
            // would otherwise get a NullPointerException here.
            if (c instanceof JLabel label) {
                label.setText(msg);
            }
        };
        if (SwingUtilities.isEventDispatchThread()) {
            update.run();
        } else {
            SwingUtilities.invokeLater(update);
        }
    }

    public void actionPerformed(ActionEvent e) {
        //nothing to do :D
    }

    private final JLabel label = new JLabel();

    public Component getComponent(BlackBoard b) {
        label.setOpaque(false);
        return label;
    }
}
