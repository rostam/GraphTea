// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.graph.event;

import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.GraphModel;

import java.awt.event.MouseWheelEvent;

/**
 * An event which indicates that a graph action occurred.
 *
 * @author Azin Azadi
 */
public class GraphEvent {
    public final static String EVENT_KEY = "GraphEvent";


    public static final int CLICKED = 0;
    public static final int MOUSE_ENTERED_EXITED = 3;
    public static final int NOTIFIED = 5;


    /**
     * indicates the start of dragging of mouse on graph.
     * It is somehow like Mouse Pressed event in swing.
     */
    public static final int DRAGGING_STARTED = 12;
    /**
     * after start of dragging ,DRAGGING mouse move event will occur until the
     * drop action
     */
    public static final int DRAGGING = 6;
    /**
     * indicates drop action after a drag event. (Dragging finished)
     */
    public static final int DROPPED = 7;
    /**
     * indicates moving of the mouse on graph
     */
    public static final int MOUSE_MOVED = 8;
    /**
     * indicates moving the wheel of the mouse on graph
     */
    public static final int MOUSE_WHEEL_MOVED = 9;

    public int eventType;
    public GraphModel graph;

    /**
     * position of mouse according to top left point of graph
     */
    public GPoint mousePos;

    /**
     * the amount which mouse wheel is scrolled, positive or negative
     *
     * @see MouseWheelEvent
     */
    public int mouseWheelMoveAmount;
    public int mouseBtn;

    /**
     * @see javax.swing.event.MenuEvent -> getModifiersEx
     */
    public int modifiers;

    /**
     * in the case that event occurs because of a GRAPH_MOUSE_ENTERED_EXITED event
     * isMouseEntered will show that is mouse entered to the vertex (true) otherwise mouse exited
     * from the vertex (false)
     */
    public boolean isMouseEntered;

    /**
     * in the case that event occurs because of a NOTIFIED of UNNOTIFIED event
     * isNotified will show that is vertex notified (true) or unNotified (false)
     */
    public boolean isNotified;

    /**
     * in the case that event occurs because of a DRAGGING_STARTED or DRAGGING_FINISHED event
     * isDragged will show that is vertex Dragged (true) or Dropped (false)
     */
    private boolean isDragged;


    public GraphEvent(int eventType, GraphModel e, GPoint mousePos, int mouseBtn_or_mouseWheelMoveAmount, boolean isNotified, boolean isMouseEntered, boolean isDragged, int modifiersEx) {
        this.eventType = eventType;
        this.graph = e;
        this.mousePos = mousePos;
        this.isMouseEntered = isMouseEntered;
        this.isNotified = isNotified;
        this.modifiers = modifiersEx;
        if (eventType != MOUSE_WHEEL_MOVED)
            this.mouseBtn = mouseBtn_or_mouseWheelMoveAmount;
        else
            this.mouseWheelMoveAmount = mouseBtn_or_mouseWheelMoveAmount;
    }

    public static GraphEvent mouseClicked(GraphModel g, GPoint mousePos, int mouseBtn, int modifiersEx) {
        return new GraphEvent(CLICKED, g, mousePos, mouseBtn, false, false, false, modifiersEx);
    }

    public static GraphEvent mouseEntered(GraphModel g, GPoint mousePos, int mouseBtn, int modifiersEx) {
        return new GraphEvent(MOUSE_ENTERED_EXITED, g, mousePos, mouseBtn, false, true, false, modifiersEx);
    }

    public static GraphEvent mouseExited(GraphModel g, GPoint mousePos, int mouseBtn, int modifiersEx) {
        return new GraphEvent(MOUSE_ENTERED_EXITED, g, mousePos, mouseBtn, false, false, false, modifiersEx);
    }

    public static GraphEvent mouseDraggingStarted(GraphModel g, GPoint mousePos, int mouseBtn, int modifiersEx) {
        return new GraphEvent(DRAGGING_STARTED, g, mousePos, mouseBtn, false, false, true, modifiersEx);
    }

    public static GraphEvent dragging(GraphModel g, GPoint mousePos, int mouseBtn, int modifiersEx) {
        return new GraphEvent(DRAGGING, g, mousePos, mouseBtn, false, false, true, modifiersEx);
    }

    public static GraphEvent mouseDropped(GraphModel g, GPoint mousePos, int mouseBtn, int modifiersEx) {
        return new GraphEvent(DROPPED, g, mousePos, mouseBtn, false, false, false, modifiersEx);
    }

    public static GraphEvent mouseMoved(GraphModel g, GPoint mousePos, int mouseBtn, int modifiersEx) {
        return new GraphEvent(MOUSE_MOVED, g, mousePos, mouseBtn, false, false, false, modifiersEx);
    }

    public static GraphEvent graphNotified(GraphModel g, int modifiersEx) {
        return new GraphEvent(NOTIFIED, g, null, 0, true, false, false, modifiersEx);
    }

    public static GraphEvent graphUnNotified(GraphModel g, int modifiersEx) {
        return new GraphEvent(NOTIFIED, g, null, 0, false, false, false, modifiersEx);
    }

    public static GraphEvent mouseWheelMoved(GraphModel g, GPoint mousePos, int mouseWheelMoveAmount, int modifiersEx) {
        return new GraphEvent(MOUSE_WHEEL_MOVED, g, mousePos, mouseWheelMoveAmount, false, false, false, modifiersEx);
    }

}
