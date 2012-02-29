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
import graphlab.library.event.VertexEvent;

/**
 * @author Omid
 */
public class BaseVertexEvent extends VertexEvent<BaseVertex, BaseEdge<BaseVertex>> {

    public BaseVertexEvent(BaseGraph<BaseVertex, BaseEdge<BaseVertex>> graph, BaseVertex vertex, EventType et) {
        super(graph, vertex, et);
        // TODO Auto-generated constructor stub
    }

    public BaseVertexEvent(BaseGraph<BaseVertex, BaseEdge<BaseVertex>> graph, BaseVertex vertex) {
        super(graph, vertex);
        // TODO Auto-generated constructor stub
    }

}
