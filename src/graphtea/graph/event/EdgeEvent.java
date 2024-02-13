// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.graph.event;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GPoint;

/**
 * An event which indicates that a Edge action occurred.
 *
 * @author Azin Azadi ,roozbeh ebrahimi
 * @see graphtea.graph.event.GraphEvent
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
    public GPoint mousePos;
    public int mouseBtn;

    /**
     * in the case that event occurs because of a EDGE_MOUSE_ENTERED_EXITED event
     * isMouseEntered will show that is mouse entered to the vertex (true) otherwise mouse exited
     * from the vertex (false)
     */
    public boolean isMouseEntered;


    public EdgeEvent(int eventType, Edge e, GPoint mousePos, int mouseBtn, boolean isNotified, boolean isMouseEntered) {
        this.eventType = eventType;
        this.e = e;
        this.mousePos = mousePos;
        this.mouseBtn = mouseBtn;
        this.isMouseEntered = isMouseEntered;
    }

    public static EdgeEvent clicked(Edge e, GPoint mousePos, int mouseBtn) {
        return new EdgeEvent(CLICKED, e, mousePos, mouseBtn, false, false);
    }

    public static EdgeEvent released(Edge e, GPoint mousePos, int mouseBtn) {
        return new EdgeEvent(RELEASED, e, mousePos, mouseBtn, false, false);
    }

    public static EdgeEvent draggingStarted(Edge e, GPoint mousePos, int mouseBtn) {
        return new EdgeEvent(DRAGGING_STARTED, e, mousePos, mouseBtn, false, false);
    }

//    public static EdgeEvent dropped(Edge e, GPoint mousePos, int mouseBtn) {
//        return new EdgeEvent(DROPPED, e, mousePos, mouseBtn, false, false);
//    }

    public static EdgeEvent dragging(Edge e, GPoint mousePos, int mouseBtn) {
        return new EdgeEvent(DRAGGING, e, mousePos, mouseBtn, false, false);
    }

    public GPoint posOnGraph() {
        GPoint ret = new GPoint(mousePos);
        ret.add(e.source.getLocation());
        return ret;
    }

}
