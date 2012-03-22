// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.core.actions.vertex;

import graphlab.graph.atributeset.GraphAttrSet;
import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.VertexModel;
import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;

import java.util.Iterator;

/**
 * Author: Ruzbeh Ebrahimi
 */
public class DeleteVertex extends AbstractAction {
    public DeleteVertex(BlackBoard bb) {
        super(bb);
        this.listen4Event(VertexSelectData.EVENT_KEY);
    }

    public void performAction(String eventName, Object value) {
        VertexSelectData vsd = blackboard.getData(VertexSelectData.EVENT_KEY);
        GraphModel g = blackboard.getData(GraphAttrSet.name);
        VertexModel v = vsd.v;
        doJob(g, v);
    }

    public static void doJob(GraphModel g, VertexModel v) {

        g.removeVertex(v);
    }
}