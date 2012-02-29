// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.core.actions.edge;

import graphlab.graph.atributeset.GraphAttrSet;
import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.GraphModel;
import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.plugins.commonplugin.undo.Undoable;
import graphlab.plugins.commonplugin.undo.UndoableActionOccuredData;

/**
 * @author  Ruzbeh
 */
public class DeleteEdge extends AbstractAction implements Undoable {
    public DeleteEdge(BlackBoard bb) {
        super(bb);
        listen4Event(EdgeSelectData.EVENT_KEY);
    }

    public void performAction(String eventName, Object value) {
//        GraphSelectPointData gpd = blackboard.getLog(GraphSelectPointData.name).getLast();
//        GraphModel graph = gpd.g;
        EdgeSelectData esd = blackboard.getData(EdgeSelectData.EVENT_KEY);
        GraphModel g = blackboard.getData(GraphAttrSet.name);

        EdgeModel e = esd.edge;
        UndoableActionOccuredData uaod = new UndoableActionOccuredData(this);
        uaod.properties.put("DeletedEdge", e);
        uaod.properties.put("Graph", g);

        blackboard.setData(UndoableActionOccuredData.EVENT_KEY, uaod);
        g.removeEdge(e);


    }

    public void undo(UndoableActionOccuredData uaod) {
        EdgeModel e = (EdgeModel) uaod.properties.get("DeletedEdge");
        GraphModel g = (GraphModel) uaod.properties.get("Graph");
        g.insertEdge(e);
        uaod.properties.put("Done?", true);
    }

    public void redo(UndoableActionOccuredData uaod) {
        EdgeModel e = (EdgeModel) uaod.properties.get("DeletedEdge");
        GraphModel g = (GraphModel) uaod.properties.get("Graph");
        g.removeEdge(e);
    }
}