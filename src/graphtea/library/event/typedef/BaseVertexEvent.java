// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/
package graphtea.library.event.typedef;

import graphtea.library.BaseEdge;
import graphtea.library.BaseGraph;
import graphtea.library.BaseVertex;
import graphtea.library.event.VertexEvent;

/**
 * @author Omid Aladini
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
