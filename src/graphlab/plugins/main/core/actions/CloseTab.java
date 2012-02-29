// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.core.actions;

import graphlab.graph.ui.GTabbedGraphPane;
import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.ui.UIUtils;

/**
 * @author azin azadi
 */
public class CloseTab extends AbstractAction {
    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */
    public CloseTab(BlackBoard bb) {
        super(bb);
        listen4Event(UIUtils.getUIEventKey("close tab"));
    }

    public void performAction(String eventName, Object value) {
        dojob(blackboard);
    }

    /**
     * removes the current tab from current editing graph window
     */
    public static void dojob(BlackBoard blackboard) {
        GTabbedGraphPane gtgp = blackboard.getData(GTabbedGraphPane.NAME);
        gtgp.getTabedPane().remove(gtgp.getTabedPane().getSelectedIndex());
        //todo: ask for save before close
    }
}
