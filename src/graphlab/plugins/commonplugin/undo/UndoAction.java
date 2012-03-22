// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.commonplugin.undo;
/*
author :roozbeh
*/

import graphlab.graph.graph.GraphModel;
import graphlab.graph.io.GraphJSON;
import graphlab.graph.io.GraphML;
import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.plugins.commonplugin.Init;
import graphlab.plugins.main.GraphData;
import graphlab.ui.UIUtils;

public class UndoAction extends AbstractAction {

    public static final String EVENT_KEY = UIUtils.getUIEventKey("Undo Action");

    public UndoAction(BlackBoard bb) {
        super(bb);
        listen4Event(EVENT_KEY);
    }

    public void performAction(String eventName, Object value) {
        undo(blackboard);
    }

    /**
     * undo the last undoabe operation done in the context of current blackboard
     *
     * @param blackboard
     */
    public static void undo(BlackBoard blackboard) {
        GraphData gd = new GraphData(blackboard);
        UndoManager logManager = Init.undoers.get(gd.getGraph());
        String ud = logManager.getNextUndoData();
        GraphModel gm = GraphJSON.Json2Graph(ud);
//        gd.getGraph().addSubGraph();
        gd.getGraphRenderer().setGraph(gm);
    }

}