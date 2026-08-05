// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.library.event;

import graphtea.library.BaseEdge;
import graphtea.library.BaseGraph;
import graphtea.library.BaseVertex;

/**
 * @author Omid
 */
public class EdgeRequest<VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
        implements Event {

    private EdgeType edge;
    public final BaseGraph<VertexType, EdgeType> graph;
    private String message;

    public EdgeRequest(BaseGraph<VertexType, EdgeType> graph) {
        this.graph = graph;

    }

    /**
     * @param edge The edge to set.
     */
    public void setEdge(EdgeType edge) {
        if (edge == null)
            throw new NullPointerException("Null edge supplied");

        graph.checkVertex(edge.target);
        graph.checkVertex(edge.source);

        this.edge = edge;
    }

    /**
     * @return Returns the edge.
     */
    public EdgeType getEdge() {
        return edge;
    }

    public String getID() {
        return "EdgeRequest";
    }

    public String getDescription() {
        return "Select an edge.";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
