// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.core.actions.vertex;


import graphtea.graph.graph.Vertex;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;

/**
 * @author Ruzbeh
 */
public class MoveVertex extends AbstractAction {
    public MoveVertex(BlackBoard bb) {
        super(bb);
        listen4Event(VertexMoveData.EVENT_KEY);
    }

    Vertex v;
    
    
    public void track(){}

    public void performAction(String eventName, Object value) {
        VertexMoveData vmd = blackboard.getData(VertexMoveData.EVENT_KEY);
//        GraphModel g = blackboard.get(GraphAttrSet.name);

        Vertex v1 = vmd.v;
        v1.setLocation(vmd.newPosition);
    }
}