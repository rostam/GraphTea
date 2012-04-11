// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.core.actions.vertex;


import graphlab.graph.graph.Vertex;
import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;

/**
 * @author Ruzbeh
 */
public class MoveVertex extends AbstractAction {
    public MoveVertex(BlackBoard bb) {
        super(bb);
        listen4Event(VertexMoveData.EVENT_KEY);
    }

    Vertex v;

    public void performAction(String eventName, Object value) {
        VertexMoveData vmd = blackboard.getData(VertexMoveData.EVENT_KEY);
//        GraphModel g = blackboard.get(GraphAttrSet.name);

        Vertex v1 = vmd.v;
        v1.setLocation(vmd.newPosition);
    }
}