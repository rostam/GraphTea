// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui.actions;

import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.ui.UIUtils;

import javax.swing.*;

/**
 * just a simple test action showing a dialog on the screen
 *
 * @author  azin azadi
 */
public class TestAction extends AbstractAction {
    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */
    public TestAction(BlackBoard bb) {
        super(bb);
        listen4Event(UIUtils.getUIEventKey("ttest"));
    }

    /**
     * like Action
     *
     * @param eventName The event name
     * @param value The value
     */
    public void performAction(String eventName, Object value) {
        JOptionPane.showMessageDialog(null, "test is ok :D, ", "tessst", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("test is ok :D");
    }
}
