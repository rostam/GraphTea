// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.core.actions;

import graphlab.platform.core.BlackBoard;
import graphlab.platform.core.exception.ExceptionHandler;
import graphlab.ui.UIUtils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;


/**
 * displays the about dialog of graphlab
 *
 * @author azin azadi
 */
public class ShowAboutDialog extends graphlab.platform.core.AbstractAction {
    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */
    public ShowAboutDialog(BlackBoard bb) {
        super(bb);
        listen4Event(UIUtils.getUIEventKey("show about"));
    }


    public void performAction(String eventName, Object value) {
        new Thread() {
            public void run() {
                showAbout();
            }
        }.start();
    }

    public static void showAbout() {
        JFrame f = new JFrame("About GraphLab");
        JEditorPane browserPane = new JEditorPane();
        browserPane.setContentType("text/html");
        browserPane.setEditable(false);
        try {
            browserPane.setPage(new File("doc/about.html").toURL());
        } catch (IOException e) {
            ExceptionHandler.catchException(e);
        }
        f.add(new JScrollPane(browserPane));
        f.setVisible(true);
        f.setSize(500, 500);
        f.validate();
        f.setResizable(false);
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
