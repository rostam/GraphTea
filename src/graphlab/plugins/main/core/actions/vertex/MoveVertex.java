// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.core.actions.vertex;


import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.GraphPoint;
import graphlab.graph.graph.VertexModel;
import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.plugins.commonplugin.undo.Undoable;
import graphlab.plugins.commonplugin.undo.UndoableActionOccuredData;

/**
 * @author Ruzbeh
 */
public class MoveVertex extends AbstractAction implements Undoable {
    public MoveVertex(BlackBoard bb) {
        super(bb);
        listen4Event(VertexMoveData.EVENT_KEY);
    }

    VertexModel v;

    public void performAction(String eventName, Object value) {
        VertexMoveData vmd = blackboard.getData(VertexMoveData.EVENT_KEY);
//        GraphModel g = blackboard.get(GraphAttrSet.name);

        VertexModel v1 = vmd.v;
        v1.setLocation(vmd.newPosition);
    }


    public void undo(UndoableActionOccuredData uaod) {
        VertexModel v = (VertexModel) uaod.properties.get("MovedVertex");
        GraphPoint oldPos = (GraphPoint) uaod.properties.get("OldPos");
//        int oldX = (Integer) uaod.properties.get("OldX");
//        int oldY = (Integer) uaod.properties.get("OldY");
        GraphModel g = (GraphModel) uaod.properties.get("Graph");
        v.setLocation(oldPos);

    }

    public void redo(UndoableActionOccuredData uaod) {
        VertexModel v = (VertexModel) uaod.properties.get("MovedVertex");
        GraphPoint newPosition = (GraphPoint) uaod.properties.get("NewPos");
//        int oldX = (Integer) uaod.properties.get("OldX");
//        int oldY = (Integer) uaod.properties.get("OldY");
        GraphModel g = (GraphModel) uaod.properties.get("Graph");
        v.setLocation(newPosition);

    }
}