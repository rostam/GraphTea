// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.commonplugin.undo;
/*
author :roozbeh
*/

import graphlab.graph.graph.GraphModel;
import graphlab.graph.io.GraphML;
import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.plugins.commonplugin.Init;
import graphlab.plugins.main.GraphData;
import graphlab.ui.UIUtils;

public class RedoAction extends AbstractAction {

    public static final String EVENT_KEY = UIUtils.getUIEventKey("Redo Action");


    public RedoAction(BlackBoard bb) {
        super(bb);
        listen4Event(EVENT_KEY);
    }

    public void performAction(String eventName, Object value) {
        redo(blackboard);
    }

    /**
     * undo the last undoabe operation done in the context of current blackboard
     *
     * @param blackboard
     */
    public static void redo(BlackBoard blackboard) {
        GraphData gd = new GraphData(blackboard);
        UndoManager logManager = Init.undoers.get(gd.getGraph());
        String gml = logManager.getNextRedoData();
        GraphModel gm = GraphML.GraphML2Graph(gml);
        gd.getGraphRenderer().setGraph(gm);
    }
}