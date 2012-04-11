// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.core.actions.edge;

import graphlab.graph.atributeset.GraphAttrSet;
import graphlab.graph.graph.Edge;
import graphlab.graph.graph.GraphModel;
import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;

/**
 * @author  Ruzbeh
 */
public class DeleteEdge extends AbstractAction {
    public DeleteEdge(BlackBoard bb) {
        super(bb);
        listen4Event(EdgeSelectData.EVENT_KEY);
    }

    public void performAction(String eventName, Object value) {
        EdgeSelectData esd = blackboard.getData(EdgeSelectData.EVENT_KEY);
        GraphModel g = blackboard.getData(GraphAttrSet.name);

        Edge e = esd.edge;
        g.removeEdge(e);
    }

}