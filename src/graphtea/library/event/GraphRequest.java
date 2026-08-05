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
public class GraphRequest<VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
        implements Event {

    private BaseGraph<VertexType, EdgeType> graph;

    /**
     * @param graph The graph to set.
     */
    public void setGraph(BaseGraph<VertexType, EdgeType> graph) {
        if (graph == null)
            throw new NullPointerException("Null graph supplied");

        this.graph = graph;
    }

    public BaseGraph<VertexType, EdgeType> getGraph() {
        return graph;
    }

    public String getID() {
        return "GraphRequest";
    }

    public String getDescription() {
        return "Select a Graph";
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
