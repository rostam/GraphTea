// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.graph.event;

import graphlab.graph.graph.GraphPoint;
import graphlab.graph.graph.VertexModel;

/**
 * An event which indicates that a vertex action occurred.
 *
 * @author azin azadi
 * @see graphlab.graph.event.GraphEvent
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
    public VertexModel v;

    /**
     * @see javax.swing.event.MenuEvent -> getModifiersEx
     */
    public int modifiers;

    public static VertexEvent clicked(VertexModel v, GraphPoint mousePos, int mouseBtn, int modifiersEx) {
        return new VertexEvent(CLICKED, v, mousePos, mouseBtn, false, false, modifiersEx);
    }

    public static VertexEvent doubleClicked(VertexModel v, GraphPoint mousePos, int mouseBtn, int modifiersEx) {
        return new VertexEvent(DOUBLECLICKED, v, mousePos, mouseBtn, false, false, modifiersEx);
    }

    public static VertexEvent released(VertexModel v, GraphPoint mousePos, int mouseBtn, int modifiersEx) {
        return new VertexEvent(RELEASED, v, mousePos, mouseBtn, false, false, modifiersEx);
    }

    public static VertexEvent dropped(VertexModel v, GraphPoint mousePos, int mouseBtn, int modifiersEx) {
        return new VertexEvent(DROPPED, v, mousePos, mouseBtn, false, false, modifiersEx);
    }

    public static VertexEvent draggingStarted(VertexModel v, GraphPoint mousePos, int mouseBtn, int modifiersEx) {
        return new VertexEvent(DRAGGING_STARTED, v, mousePos, mouseBtn, false, false, modifiersEx);
    }

    public static VertexEvent dragging(VertexModel v, GraphPoint mousePos, int mouseBtn, int modifiersEx) {
        return new VertexEvent(DRAGGING, v, mousePos, mouseBtn, false, false, modifiersEx);
    }

    public VertexEvent(int eventType, VertexModel v, GraphPoint mousePos, int mouseBtn, boolean isNotified, boolean isMouseEntered, int modifiersEx) {
        this.eventType = eventType;
        this.v = v;
        this.mousePos = mousePos;
        this.mouseBtn = mouseBtn;
        this.isMouseEntered = isMouseEntered;
        this.modifiers = modifiersEx;
    }

    //position of mouse according to top left point of vertex
    public GraphPoint mousePos;
    public int mouseBtn;

    /**
     * in the case that event occurs because of a VERTEX_MOUSE_ENTERED_EXITED event
     * isMouseEntered will show that is mouse entered to the vertex (true) otherwise mouse exited
     * from the vertex (false)
     */
    public boolean isMouseEntered;
}
