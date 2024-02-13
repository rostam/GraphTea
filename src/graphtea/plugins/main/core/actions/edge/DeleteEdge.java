// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.core.actions.edge;

import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;

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