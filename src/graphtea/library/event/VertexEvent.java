// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.library.event;

import graphtea.library.BaseEdge;
import graphtea.library.BaseGraph;
import graphtea.library.BaseVertex;

/**
 * Happens when a vertex's color changes or a new vertex
 * is added to the graph or ...
 *
 * @author Omid Aladini
 */

//I know, I know, some might say, EdgeType is not needed here. 
//It's just for future vertex events dependent on edge type.

public class VertexEvent<VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
        implements Event {
    public final VertexType vertex;
    public final BaseGraph<VertexType, EdgeType> graph;
    public final EventType eventType;

    public static enum EventType {
        COLOR_CHANGE,
        NEW_VERTEX,
        MARK
    }

    /**
     * Constructs an event that means an event is occured on a specified vertex.
     *
     * @param vertex The vertex which the event occurs on it.
     * @param et     Type of the event occured on the first parameter <code>vertex</code>;
     * @throws NullPointerException if <code>vertex</code> is null.
     */
    public VertexEvent(BaseGraph<VertexType, EdgeType> graph, VertexType vertex, EventType et) {
        if (vertex == null || et == null || graph == null)
            throw new NullPointerException("Null argument supplied.");

        this.vertex = vertex;
        eventType = et;
        this.graph = graph;
    }

    /**
     * Constructs an event that means a new vertex is added.
     *
     * @param vertex The vertex which the event occurs on it.
     * @throws NullPointerException if <code>vertex</code> is null.
     */
    public VertexEvent(BaseGraph<VertexType, EdgeType> graph, VertexType vertex) {
        this(graph, vertex, EventType.NEW_VERTEX);
    }

    public String getID() {
        return "VertexEvent";
    }

    public String getDescription() {
        return "VertexEvent occurred.";
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
