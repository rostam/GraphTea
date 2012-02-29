// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.core.actions;

import graphlab.graph.graph.GraphModel;
import graphlab.graph.ui.GTabbedGraphPane;
import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.ui.UIUtils;

import javax.swing.*;
import java.awt.*;

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
        int a = JOptionPane.showOptionDialog(null, "Graph Direction :", "New Graph", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Directed", "Undirected","Cancel"}, "Undirected");
        if(a!=-1 && a!=JOptionPane.CANCEL_OPTION){
        GraphModel g = new GraphModel(a == 0);
        gtgp.addGraph(g);
        }
    }

    /**
     * creates a graph ans add it as a tab to the current editing graph window
     */
    public static void addTabNoGUI(boolean isdirected , BlackBoard blackboard) {
        GTabbedGraphPane gtgp = blackboard.getData(GTabbedGraphPane.NAME);
        GraphModel g = new GraphModel(isdirected);
        gtgp.addGraph(g);
    }


    /**
     * displays the givven graph in GraphLab
     */
    public static void displayGraph(GraphModel g, BlackBoard blackBoard) {
        GTabbedGraphPane gtgp = blackBoard.getData(GTabbedGraphPane.NAME);
        gtgp.addGraph(g);
    }

}
