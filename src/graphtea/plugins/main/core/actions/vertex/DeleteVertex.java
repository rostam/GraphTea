// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.core.actions.vertex;

import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;

/**
 * Author: Ruzbeh Ebrahimi
 */
public class DeleteVertex extends AbstractAction {
    public DeleteVertex(BlackBoard bb) {
        super(bb);
        this.listen4Event(VertexSelectData.EVENT_KEY);
    }
    public void track(){}
    

    public void performAction(String eventName, Object value) {
        VertexSelectData vsd = blackboard.getData(VertexSelectData.EVENT_KEY);
        GraphModel g = blackboard.getData(GraphAttrSet.name);
        Vertex v = vsd.v;
        doJob(g, v);
    }

    public static void doJob(GraphModel g, Vertex v) {

        g.removeVertex(v);
    }
}