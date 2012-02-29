// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphlab.library.event;

import graphlab.library.BaseEdge;
import graphlab.library.BaseGraph;
import graphlab.library.BaseVertex;

/**
 * @author Omid
 */
public class VertexRequest<VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
        implements Event {

    private VertexType vertex;
    public final BaseGraph<VertexType, EdgeType> graph;
    public String message = "Please select a vertex";

    public VertexRequest(BaseGraph<VertexType, EdgeType> graph, String message) {
        this.graph = graph;
        this.message = message;
    }

    public VertexRequest(BaseGraph<VertexType, EdgeType> graph) {
        this.graph = graph;

    }

    /**
     * @param vertex The vertex to set.
     */
    public void setVertex(VertexType vertex) {
        if (vertex == null)
            throw new NullPointerException("Null vertex supplied");

        graph.checkVertex(vertex);

        this.vertex = vertex;
    }

    /**
     * @return Returns the vertex.
     */
    public VertexType getVertex() {
        return vertex;
    }

    public String getID() {
        return "VertexRequest";
    }

    public String getDescription() {
        return "Select a Vertex";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
