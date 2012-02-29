// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

/**
 *
 */
package graphlab.library.event.typedef;

import graphlab.library.BaseEdge;
import graphlab.library.BaseGraph;
import graphlab.library.BaseVertex;
import graphlab.library.event.EdgeEvent;

/**
 * @author Omid
 */
public class BaseEdgeEvent extends EdgeEvent<BaseVertex, BaseEdge<BaseVertex>> {

    public BaseEdgeEvent(BaseGraph<BaseVertex, BaseEdge<BaseVertex>> graph, BaseEdge<BaseVertex> edge) {
        super(graph, edge);
        // TODO Auto-generated constructor stub
    }

    public BaseEdgeEvent(BaseGraph<BaseVertex, BaseEdge<BaseVertex>> graph, BaseEdge<BaseVertex> edge, EventType et) {
        super(graph, edge, et);
        // TODO Auto-generated constructor stub
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
