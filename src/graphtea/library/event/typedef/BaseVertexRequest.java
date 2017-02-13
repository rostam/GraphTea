// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/
package graphtea.library.event.typedef;

import graphtea.library.BaseEdge;
import graphtea.library.BaseGraph;
import graphtea.library.BaseVertex;
import graphtea.library.event.VertexRequest;

/**
 * @author Omid Aladini
 */
public class BaseVertexRequest extends VertexRequest<BaseVertex, BaseEdge<BaseVertex>> {

    public BaseVertexRequest(BaseGraph<BaseVertex, BaseEdge<BaseVertex>> graph) {
        super(graph);
    }

}
