// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.graph.event;

import graphlab.graph.graph.Edge;
import graphlab.graph.graph.GraphPoint;

/**
 * An event which indicates that a Edge action occurred.
 *
 * @author Azin Azadi ,roozbeh ebrahimi
 * @see graphlab.graph.event.GraphEvent
 */
public class EdgeEvent {
    public final static String EVENT_KEY = "EdgeEvent";
    public static final int CLICKED = 0;
    public static final int RELEASED = 1;
    public static final int DRAGGING = 2;
    public static final int DRAGGING_STARTED = 3;
    /**
     * dropped is not implemented yet
     */
//    public static final int DROPPED = 4;

    public int eventType;
    public Edge e;

    //position of mouse according to top left point of edge
    public GraphPoint mousePos;
    public int mouseBtn;

    /**
     * in the case that event occurs because of a EDGE_MOUSE_ENTERED_EXITED event
     * isMouseEntered will show that is mouse entered to the vertex (true) otherwise mouse exited
     * from the vertex (false)
     */
    public boolean isMouseEntered;


    public EdgeEvent(int eventType, Edge e, GraphPoint mousePos, int mouseBtn, boolean isNotified, boolean isMouseEntered) {
        this.eventType = eventType;
        this.e = e;
        this.mousePos = mousePos;
        this.mouseBtn = mouseBtn;
        this.isMouseEntered = isMouseEntered;
    }

    public static EdgeEvent clicked(Edge e, GraphPoint mousePos, int mouseBtn) {
        return new EdgeEvent(CLICKED, e, mousePos, mouseBtn, false, false);
    }

    public static EdgeEvent released(Edge e, GraphPoint mousePos, int mouseBtn) {
        return new EdgeEvent(RELEASED, e, mousePos, mouseBtn, false, false);
    }

    public static EdgeEvent draggingStarted(Edge e, GraphPoint mousePos, int mouseBtn) {
        return new EdgeEvent(DRAGGING_STARTED, e, mousePos, mouseBtn, false, false);
    }

//    public static EdgeEvent dropped(Edge e, GraphPoint mousePos, int mouseBtn) {
//        return new EdgeEvent(DROPPED, e, mousePos, mouseBtn, false, false);
//    }

    public static EdgeEvent dragging(Edge e, GraphPoint mousePos, int mouseBtn) {
        return new EdgeEvent(DRAGGING, e, mousePos, mouseBtn, false, false);
    }

    public GraphPoint posOnGraph() {
        GraphPoint ret = new GraphPoint(mousePos);
        ret.add(e.source.getLocation());
        return ret;
    }

}
