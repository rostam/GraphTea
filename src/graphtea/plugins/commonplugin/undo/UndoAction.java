// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.commonplugin.undo;
/*
author :roozbeh
*/

import graphtea.extensions.io.GraphSaveObject;
import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.graph.GraphModel;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.commonplugin.Init;
import graphtea.plugins.main.GraphData;
import graphtea.ui.UIUtils;

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
        GraphModel cur = gd.getGraph();
        byte[] bb = blackboard.popUndo(cur.getLabel());
        if(bb == null) return;
        GraphSaveObject gso = GraphSaveObject.getGraphSaveOobjectfromBytes(bb);
        GraphModel g = GraphSaveObject.getGraphFromBytes(bb);
        cur.clear();
        gso.insertIntoGraph(cur);
        gd.getGraphRenderer().repaintGraph();
    }

}