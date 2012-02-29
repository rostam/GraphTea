// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphlab.library.event;

import graphlab.library.BaseEdge;
import graphlab.library.BaseGraph;
import graphlab.library.BaseVertex;

/**
 * Happens when an edge's color or weight changes or a new edge
 * is added to the graph.
 *
 * @author Omid Aladini
 */
public class EdgeEvent<VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
        implements Event {
    public final EdgeType edge;
    public final BaseGraph<VertexType, EdgeType> graph;
    public final EventType eventType;

    public static enum EventType {
        COLOR_CHANGE,
        WEIGHT_CHANGE,
        NEW_EDGE,
        MARK
    }

    /**
     * Constructs an event that means an event is occured on a specified edge.
     *
     * @param edge The edge which the event occurs on it.
     * @param et   Type of the event occured on the first parameter <code>edge</code>;
     * @throws NullPointerException if <code>edge</code> is null.
     */
    public EdgeEvent(BaseGraph<VertexType, EdgeType> graph, EdgeType edge, EventType et) {
        if (edge == null || et == null || graph == null)
            throw new NullPointerException("Null argument supplied.");

        eventType = et;
        this.edge = edge;
        this.graph = graph;
    }

    /**
     * Constructs an event that means a new edge is added to the graph.
     *
     * @param edge The edge which the event occurs on it.
     * @throws NullPointerException if <code>edge</code> is null.
     */
    public EdgeEvent(BaseGraph<VertexType, EdgeType> graph, EdgeType edge) {
        this(graph, edge, EventType.NEW_EDGE);
    }

    public String getID() {
        return "EdgeEvent";
    }

    public String getDescription() {
        return "Edge event:" + eventType;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
