// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.library.event;

import graphtea.library.BaseEdge;
import graphtea.library.BaseGraph;
import graphtea.library.BaseVertex;

/**
 * @author Omid Aladini
 *         Happens when a global event has occured on the graph.
 */
public class GraphEvent<VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
        implements Event {
    public final BaseGraph<VertexType, EdgeType> graph;
    public final EventType eventType;

    public enum EventType {
        NEW_GRAPH
    }

    /**
     * Constructs a GraphEvent object corresponding to graph <code>g</code> and event <code>et</code>.
     *
     * @param g  The graph related to the GraphEvent.
     * @param et Represents type of the event happened on the graph.
     */
    public GraphEvent(BaseGraph<VertexType, EdgeType> g, EventType et) {
        if (g == null || et == null)
            throw new NullPointerException("Null graph object or event supplied.");

        graph = g;
        eventType = et;
    }

    /**
     * Constructs a GraphEvent object that represents construction of a new graph.
     *
     * @param g Graph object that is just constructed.
     */
    public GraphEvent(BaseGraph<VertexType, EdgeType> g) {
        this(g, EventType.NEW_GRAPH);

    }

    public String getID() {
        return "GraphEvent";
    }

    public String getDescription() {
        return "Graph event occurred.";
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
