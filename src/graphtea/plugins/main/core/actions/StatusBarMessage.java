// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.core.actions;

import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.ui.UIUtils;
import graphtea.ui.components.GComponentInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * with using of this class you can have a message in status bar of the program, by just putting a bar in the XML file as following
 * <bar	class="graphtea.plugins.main.graph.actions.StatusBarMessage"	id="user message" />
 *
 * @author azin azadi
 */
public class StatusBarMessage extends AbstractAction implements GComponentInterface {

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

    static Timer t;

    /**
     * shows a message in the status bar of the Frame loaded and assigned to current blackboard
     * the showing message will be hide after 3 seconds
     */
    public static void showQuickMessage(final BlackBoard b, String message) {
        setLabelMessage(b, message);
        t = new Timer(3000, e -> {
            t.stop();
            setLabelMessage(b, "");
        });
        t.start();
    }
//    private static void setLastMessage(blackboard b, String msg) {
//        b.set("Last Status Message", msg);
//    }
//    private static String getLastMessage(blackboard b, String msg) {
//        return b.get("Last Status Message");
//    }

    /**
     * shows a message in the status bar of the Frame loaded and assigned to current blackboard
     * note that at each time just 1 message can be shown on that place
     */
    public static void setMessage(BlackBoard b, String s) {
//        setLastMessage(b, s);
        setLabelMessage(b, s);
    }

    private static void setLabelMessage(BlackBoard b, String msg) {
        final JLabel l = (JLabel) UIUtils.getComponent(b, "user message");
        l.setText(msg);
        new Thread() {
            public void run() {
                l.setBackground(Color.white);
                l.setOpaque(true);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                l.repaint();
                l.setOpaque(false);
            }
        }.start();
    }

    public void actionPerformed(ActionEvent e) {
        //nothing to do :D
    }

    JLabel l = new JLabel();

    public Component getComponent(BlackBoard b) {
//        l.setBackground(Color.lightGray.brighter());

        l.setOpaque(false);
        return l;
    }
}
