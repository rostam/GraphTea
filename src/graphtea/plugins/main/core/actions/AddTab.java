// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.core.actions;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.ui.GTabbedGraphPane;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.ui.UIUtils;

import javax.swing.*;

/**
 * @author azin azadi
 */
public class AddTab extends AbstractAction {

    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */
    public AddTab(BlackBoard bb) {
        super(bb);
        listen4Event(UIUtils.getUIEventKey("add tab"));
    }

    public void performAction(String eventName, Object value) {
        addTab(blackboard);
    }

    /**
     * creates a graph ans add it as a tab to the current editing graph window
     */
    public static void addTab(BlackBoard blackboard) {
        GTabbedGraphPane gtgp = blackboard.getData(GTabbedGraphPane.NAME);
        String[] kinds = {"Undirected", "Directed", "Cancel"};
        int a = JOptionPane.showOptionDialog(UIUtils.getGFrame(blackboard),
                "Should the edges of the new graph have a direction?", "New Graph",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, kinds, kinds[0]);
        if (a == 0 || a == 1) {
            gtgp.addGraph(new GraphModel(a == 1));
        }
    }

    /**
     * creates a graph and add it as a tab to the current editing graph window
     */
    public static void addTabNoGUI(boolean isdirected , BlackBoard blackboard) {
        GTabbedGraphPane gtgp = blackboard.getData(GTabbedGraphPane.NAME);
        GraphModel g = new GraphModel(isdirected);
        gtgp.addGraph(g);
    }

    /**
     * displays the givven graph in GraphTea
     */
    public static void displayGraph(GraphModel g, BlackBoard blackBoard) {
        GTabbedGraphPane gtgp = blackBoard.getData(GTabbedGraphPane.NAME);
        gtgp.addGraph(g);
    }
}
