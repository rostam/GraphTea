// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.graph.event;

import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.Vertex;

/**
 * An event which indicates that a vertex action occurred.
 *
 * @author azin azadi
 * @see graphtea.graph.event.GraphEvent
 */
public class VertexEvent {

    public final static String EVENT_KEY = "VertexEvent";


    public static final int CLICKED = 0;
    /**
     * DROPPED occurs in the case that mouse started dragging from a vertex and being dropped on another one, in this case
     * a dropped event will be fired on the second vertex
     */
    public static final int DROPPED = 1;
    public static final int DRAGGING_STARTED = 2;
    public static final int DRAGGING = 4;
    public static final int NOTIFIED = 5;
    public static final int PRESSED = 6;
    /**
     * RELEASED occurs in the case that mouse started dragging from a vertex and dropped on an empty region of the graph,
     * in this case a RELEASED event will be fired on starting vertex
     */
    public static final int RELEASED = 7;
    public static final int DOUBLECLICKED = 8;

    public int eventType;
    public Vertex v;

    /**
     * @see javax.swing.event.MenuEvent -> getModifiersEx
     */
    public int modifiers;

    public static VertexEvent clicked(Vertex v, GPoint mousePos, int mouseBtn, int modifiersEx) {
        return new VertexEvent(CLICKED, v, mousePos, mouseBtn, false, false, modifiersEx);
    }

    public static VertexEvent doubleClicked(Vertex v, GPoint mousePos, int mouseBtn, int modifiersEx) {
        return new VertexEvent(DOUBLECLICKED, v, mousePos, mouseBtn, false, false, modifiersEx);
    }

    public static VertexEvent released(Vertex v, GPoint mousePos, int mouseBtn, int modifiersEx) {
        return new VertexEvent(RELEASED, v, mousePos, mouseBtn, false, false, modifiersEx);
    }

    public static VertexEvent dropped(Vertex v, GPoint mousePos, int mouseBtn, int modifiersEx) {
        return new VertexEvent(DROPPED, v, mousePos, mouseBtn, false, false, modifiersEx);
    }

    public static VertexEvent draggingStarted(Vertex v, GPoint mousePos, int mouseBtn, int modifiersEx) {
        return new VertexEvent(DRAGGING_STARTED, v, mousePos, mouseBtn, false, false, modifiersEx);
    }

    public static VertexEvent dragging(Vertex v, GPoint mousePos, int mouseBtn, int modifiersEx) {
        return new VertexEvent(DRAGGING, v, mousePos, mouseBtn, false, false, modifiersEx);
    }

    public VertexEvent(int eventType, Vertex v, GPoint mousePos, int mouseBtn, boolean isNotified, boolean isMouseEntered, int modifiersEx) {
        this.eventType = eventType;
        this.v = v;
        this.mousePos = mousePos;
        this.mouseBtn = mouseBtn;
        this.isMouseEntered = isMouseEntered;
        this.modifiers = modifiersEx;
    }

    public GPoint posOnGraph() {
        GPoint ret = new GPoint(mousePos);
        ret.add(v.getLocation());
        return ret;
    }

    //position of mouse according to top left point of vertex
    public GPoint mousePos;
    public int mouseBtn;

    /**
     * in the case that event occurs because of a VERTEX_MOUSE_ENTERED_EXITED event
     * isMouseEntered will show that is mouse entered to the vertex (true) otherwise mouse exited
     * from the vertex (false)
     */
    public boolean isMouseEntered;
}
