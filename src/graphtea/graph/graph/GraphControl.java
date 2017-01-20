// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.graph.graph;

import graphtea.graph.event.EdgeEvent;
import graphtea.graph.event.GraphControlListener;
import graphtea.graph.event.GraphEvent;
import graphtea.graph.event.VertexEvent;
import graphtea.library.util.Pair;
import graphtea.platform.core.BlackBoard;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.util.Iterator;

/**
 * @author Azin Azadi, roozbeh ebrahimi, Ali Ershadi
 *         Mohsen Mansouryar > Added edge control for loops
 */
public class GraphControl implements MouseListener, MouseWheelListener, MouseMotionListener {
    private GraphModel g;
    private JPanel gv;
    BlackBoard blackboard;
    private GraphControlListener listener;
    private Vertex lastVertexPressed = null;
    private Edge lastEdgePressed = null;
    GPoint p = new GPoint();
//    boolean edgesCurved;

    public static final int EDGE_CURVE_CPNTROL_BOX_DIAMETER = 10;
    /**
     * just a short cut to EDGE_CURVE_CPNTROL_BOX_DIAMETER
     */
    private static final int cd = EDGE_CURVE_CPNTROL_BOX_DIAMETER;
    /**
     * determines the min x and y of graph bounds
     */
    double minx = 0;
    double miny = 0;

    public void setListener(GraphControlListener l) {
        this.listener = l;
    }

    public GraphControl(GraphModel g, JPanel gv, BlackBoard bb) {
        this.g = g;
        this.gv = gv;
        blackboard = bb;
        gv.addMouseListener(this);
        gv.addMouseMotionListener(this);
        gv.addMouseWheelListener(this);
    }

//todo: single click != double click

    public void mouseClicked(MouseEvent mouseEvent) {
        Pair<Vertex, Double> p = mindistv(g, mousePos(mouseEvent));
        Vertex v = p.first;
        if (v != null && isPointOnVertex(g, v, mousePos(mouseEvent))) {
            if (mouseEvent.getClickCount() > 1)
                sendEventToBlackBoard(VertexEvent.doubleClicked(v, mousePos(mouseEvent, v), mouseEvent.getButton(), mouseEvent.getModifiersEx()));
            else
                sendEventToBlackBoard(VertexEvent.clicked(v, mousePos(mouseEvent, v), mouseEvent.getButton(), mouseEvent.getModifiersEx()));
            return;
        }
        Pair<Edge, Double> pp;
        pp = mindiste(g, mousePos(mouseEvent));
        Edge e = pp.first;
        double dist = pp.second;
        if (g.isEdgesCurved()) {
            if (pp.second <= (EDGE_CURVE_CPNTROL_BOX_DIAMETER)) {
                sendEventToBlackBoard(EdgeEvent.clicked(e, mousePos(mouseEvent, e), mouseEvent.getButton()));
                return;
            }
        } else if (Math.sqrt(dist) < 4 ) {
//                if (doubleClicked)
//                    sendEventToBlackBoard(EdgeEvent.mouseDoubleClicked(e, mousePos(mouseEvent), mouseEvent.getButton()));
//                else
//                System.err.println("&^&^&^&^&^&^&");
            sendEventToBlackBoard(EdgeEvent.clicked(e, mousePos(mouseEvent, e), mouseEvent.getButton()));
            return;
        } else {
            if (e != null) {
                if (e.isLoop()) {
                    sendEventToBlackBoard(EdgeEvent.clicked(e, mousePos(mouseEvent, e), mouseEvent.getButton()));
                    return;
                }
            }
        }


        sendEventToBlackBoard(GraphEvent.mouseClicked(g, mousePos(mouseEvent), mouseEvent.getButton(), mouseEvent.getModifiersEx()));
    }


    /**
     * 0 1 2 3 4
     * 0 1 2 3 4
     *
     * @param mouseEvent The event of the mouse
     * @param e The given edge
     * @return The position of the mouse minus the edge e
     */
    private GPoint mousePos(MouseEvent mouseEvent, Edge e) {
        return new GPoint(mousePos(mouseEvent).x - e.source.getLocation().x, mousePos(mouseEvent).y - e.source.getLocation().y);
    }

    private GPoint mousePos(MouseEvent mouseEvent, Vertex v) {
        return new GPoint(mousePos(mouseEvent).x - v.getLocation().x, mousePos(mouseEvent).y - v.getLocation().y);
    }

    /**
     * the zoom factor of graph, It will be synced on calling mousePos(mouseEvent)
     */
    double zf;

    private GPoint mousePos(MouseEvent mouseEvent) {
        zf = g.getZoomFactor();
        GPoint p = new GPoint(mouseEvent.getX() / zf, mouseEvent.getY() / zf);
        //the graphics moves automatically to solve the swing JScrollPane negative positions problem, Im not sure

        if (minx < 0)
            p.x += minx / zf;
        if (miny < 0)
            p.y += miny / zf;
        return p;
    }

    public void mouseEntered(java.awt.event.MouseEvent mouseEvent) {
        sendEventToBlackBoard(GraphEvent.mouseEntered(g, mousePos(mouseEvent), mouseEvent.getButton(), mouseEvent.getModifiersEx()));
    }

    public void mouseExited(java.awt.event.MouseEvent mouseEvent) {
        sendEventToBlackBoard(GraphEvent.mouseExited(g, mousePos(mouseEvent), mouseEvent.getButton(), mouseEvent.getModifiersEx()));
    }

    public void mousePressed(java.awt.event.MouseEvent mouseEvent) {

        lastVertexPressed = null;

        gv.requestFocusInWindow();
        GPoint mousePos = mousePos(mouseEvent);
        Pair p = mindistv(g, mousePos);
        Vertex v = (Vertex) p.first;
        int mbuton = mouseEvent.getModifiersEx();
        if (v != null && isPointOnVertex(g, v, mousePos)) {
            sendEventToBlackBoard(VertexEvent.draggingStarted(v, mousePos(mouseEvent, v), mouseEvent.getButton(), mbuton));
            if (lastVertexPressed != null) System.err.println("last = " + lastVertexPressed.getLabel());
            lastVertexPressed = v;
            return;
        }
        lastVertexPressed = null;
        if (g.isEdgesCurved()) {
            Pair<Edge, Double> pair = mindiste(g, mousePos);
            if (pair.first != null) {
                if (pair.second <= EDGE_CURVE_CPNTROL_BOX_DIAMETER) {
                    lastEdgePressed = pair.first;
                    sendEventToBlackBoard(EdgeEvent.draggingStarted(pair.first, mousePos(mouseEvent, lastEdgePressed), mbuton));
                    return;
                }
            }
        }

        sendEventToBlackBoard(GraphEvent.mouseDraggingStarted(g, mousePos, mouseEvent.getButton(), mbuton));
    }


    public void mouseReleased(MouseEvent mouseEvent) {
        Pair p = mindistv(g, mousePos(mouseEvent));
        Vertex v = (Vertex) p.first;
        int mouseButton = mouseEvent.getModifiersEx();
        if (v != null && isPointOnVertex(g, v, mousePos(mouseEvent)) && lastVertexPressed != null) {
            sendEventToBlackBoard(VertexEvent.dropped(v, mousePos(mouseEvent, v), mouseEvent.getButton(), mouseButton));
            lastVertexPressed = null;
        } else if (lastVertexPressed != null) {
            sendEventToBlackBoard(VertexEvent.released(lastVertexPressed, mousePos(mouseEvent, lastVertexPressed), mouseEvent.getButton(), mouseButton));
            lastVertexPressed = null;
        } else if (lastEdgePressed != null) {
            sendEventToBlackBoard(EdgeEvent.released(lastEdgePressed, mousePos(mouseEvent, lastEdgePressed), mouseButton));
            lastEdgePressed = null;
        } else
            sendEventToBlackBoard(GraphEvent.mouseDropped(g, mousePos(mouseEvent), mouseEvent.getButton(), mouseButton));
    }

    public void mouseWheelMoved(java.awt.event.MouseWheelEvent mouseWheelEvent) {
        sendEventToBlackBoard(GraphEvent.mouseWheelMoved(g, mousePos(mouseWheelEvent), mouseWheelEvent.getWheelRotation(), mouseWheelEvent.getModifiersEx()));
    }

    public void mouseDragged(MouseEvent mouseEvent) {
        int modex = mouseEvent.getModifiersEx();
        if (lastVertexPressed != null) {
            sendEventToBlackBoard(VertexEvent.dragging(lastVertexPressed, mousePos(mouseEvent, lastVertexPressed), mouseEvent.getButton(), modex));
        } else if (lastEdgePressed != null) {
            sendEventToBlackBoard(EdgeEvent.dragging(lastEdgePressed, mousePos(mouseEvent, lastEdgePressed), modex));
        } else
            sendEventToBlackBoard(GraphEvent.dragging(g, mousePos(mouseEvent), mouseEvent.getButton(), modex));

    }

    public void mouseMoved(MouseEvent e) {
        sendEventToBlackBoard(GraphEvent.mouseMoved(g, mousePos(e), e.getButton(), e.getModifiersEx()));
    }

    private void sendEventToBlackBoard(GraphEvent value) {
        if (listener != null)
            listener.ActionPerformed(value);
    }

    private void sendEventToBlackBoard(VertexEvent value) {
        if (listener != null)
            listener.ActionPerformed(value);
    }

    private void sendEventToBlackBoard(EdgeEvent value) {
        if (listener != null)
            listener.ActionPerformed(value);
    }

    /**
     * @return the minimum distanse edge and its distance to the given GPoint,
     *         If edges are curved the distance will be calculated to Curve Control Points
     */
    public static Pair<Edge, Double> mindiste(GraphModel g, GPoint p) {
        double min = 100000;
        boolean loopDetected = false;
        Edge mine = null;
        Iterator<Edge> ei = g.lightEdgeIterator();
        if (g.isEdgesCurved()) {
            for (; ei.hasNext();) {
                Edge e = ei.next();
                GPoint cnp = e.getCurveControlPoint();
                GPoint s = e.source.getLocation();
                GPoint t = e.target.getLocation();
                GPoint cp = new GPoint((s.x + t.x) / 2.0 + cnp.x, (s.y + t.y) / 2.0 + cnp.y);
                if (e.isLoop())
                    cp = new GPoint(s.x + cnp.x, s.y + cnp.y);
                double dist = GPoint.distance(cp.x, cp.y, p.x, p.y);
                if (min > dist) {
                    min = dist;
                    mine = e;
                }
            }
            if (min < EDGE_CURVE_CPNTROL_BOX_DIAMETER) {
                min = 0;
            }
        } else {
            for (; ei.hasNext();) {
                Edge e = ei.next();
                if (!isInBounds(e, p) && !e.isLoop())
                    continue;
                GPoint sloc = e.source.getLocation();
                GPoint tloc = e.target.getLocation();
                GPoint cnp = e.getCurveControlPoint();
                GLine l = new GLine(sloc.x, sloc.y, tloc.x, tloc.y);
                double dist = l.ptLineDistSq(p);

                if (e.isLoop()) {
//                    double d = EDGE_CURVE_CPNTROL_BOX_DIAMETER;
//                    // This circle indicates the maximum boundary of the loop
//                    Ellipse2D.Double loopOuter = new Ellipse2D.Double(
//                                (e.getLoopCenter().x - e.getLoopWidth()/2 - d/4) * g.getZoomFactor(),
//                                (e.getLoopCenter().y - e.getLoopWidth()/2 - d/4) * g.getZoomFactor(),
//                                (e.getLoopWidth() + d/2) * g.getZoomFactor(),
//                                (e.getLoopWidth() + d/2) * g.getZoomFactor());
//                    // This circle indicates the area inside the loop
//                    Ellipse2D.Double loopInner = new Ellipse2D.Double(
//                                (e.getLoopCenter().x - (e.getLoopWidth()/2 + d/4) * g.getZoomFactor()),
//                                (e.getLoopCenter().y - (e.getLoopWidth()/2 + d/4) * g.getZoomFactor()),
//                                (e.getLoopWidth() - d/2) * g.getZoomFactor(),
//                                (e.getLoopWidth() - d/2) * g.getZoomFactor());
//                    // a point on the loop must be inside its maximum boundary
//                    // but outside its inside area
//                    if (loopOuter.intersects((p.x - d/4) * g.getZoomFactor(),
//                            (p.y - d/4) * g.getZoomFactor(),
//                            d/2 * g.getZoomFactor() , d/2 * g.getZoomFactor())
//                            &&
//                            !loopInner.intersects((p.x - d/4) * g.getZoomFactor(),
//                            (p.y - d/4) * g.getZoomFactor(),
//                            d/2 * g.getZoomFactor() , d/2 * g.getZoomFactor())) {
//                        mine = e;
//                        loopDetected = true;
//                    }
                    //Distance of p from the center of the loop
                    double cdist = GPoint.distance(p.x,
                            p.y,
                            e.getLoopCenter().x,
                            e.getLoopCenter().y);
                    //if cdist is near to radius of the loop then it is on the loop
                    if (Math.abs(cdist - e.getLoopWidth()/2) < Edge.MIN_LOOP_WIDTH/3f){
                        mine = e;
                        loopDetected = true;
                    }

                }
 
                if ((min > dist && !loopDetected) || Math.sqrt(dist) < 4) {
                    min = dist;
                    mine = e;
                }
            }
        }
        return new Pair<>(mine, min);
    }

    private static boolean isInBounds(Edge e, GPoint p) {
        GPoint l1 = e.source.getLocation();
        GPoint l2 = e.target.getLocation();
        return Math.min(l1.x, l2.x) <= p.x + 5 && Math.max(l1.x, l2.x) >= p.x - 5 && Math.min(l1.y, l2.y) <= p.y + 5 && Math.max(l1.y, l2.y) >= p.y - 5;
    }

    /**
     * @return the minimum distance vertex to the given location, and its distanse square(^2).
     */
    public static Pair<Vertex, Double> mindistv(GraphModel g, GPoint p) {
        double min = 100000;
        Vertex minv = null;
        for (Vertex v : g) {
            double dist = Math.pow(v.getLocation().x - p.x, 2) + Math.pow(v.getLocation().y - p.y, 2);
            if (min > dist) {
                min = dist;
                minv = v;
            }
        }
        return new Pair<>(minv, min);
    }

    /**
     * @return True if the given point in on the given vertex
     */
    public static boolean isPointOnVertex(GraphModel g, Vertex v, GPoint p) {
        double zf = g.getZoomFactor();
        GPoint l = v.getLocation();
        GPoint s = v.getSize();
        double sx = s.x / zf;
        double sy = s.y / zf;
        return l.x - sx / 2 <= p.x && l.x + sx / 2 >= p.x && l.y - sy / 2 <= p.y && l.y + sy / 2 >= p.y;
    }

}
