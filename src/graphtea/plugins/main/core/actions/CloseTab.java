// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.core.actions;

import graphtea.graph.ui.GTabbedGraphPane;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.ui.UIUtils;

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
