// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.core.actions.edge;

import graphtea.graph.GraphUtils;
import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.event.GraphEvent;
import graphtea.graph.event.VertexEvent;
import graphtea.graph.graph.*;
import graphtea.graph.old.AcceleratedRenderer;
import graphtea.graph.old.GStroke;
import graphtea.library.util.Pair;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.main.select.Select;
import graphtea.plugins.main.select.SelectPluginMethods;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * @author azin azadi
 */

public class AddEdge extends AbstractAction implements PaintHandler {
    //    private double lasty;
    //    private double lastx;
    private GPoint lastPos;
    private SelectPluginMethods selMethods;

    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */
    public AddEdge(BlackBoard bb) {
        super(bb);
        selMethods = new SelectPluginMethods(bb);
        listen4Event(VertexEvent.EVENT_KEY);
//        listen4Event(GraphEvent.name);
//        listen4Event(VertexDropData.event);
//        listen4Event(VertexSelectData.name);
    }

    protected Vertex v1, vc1;
    protected GraphModel g;
    protected AbstractGraphRenderer gv;
    protected boolean isClick = false;
    protected boolean isDrag = false;

    /**
     * used for determining whether user has exited a vertex while dragging
     */
    boolean exitedFromV1;

    public void track(){}
    public void performAction(String eventName, Object value) {
// our old lovely Add Edge
//        if (name == VertexSelectData.name) {
//            VertexSelectData vsd = blackboard.getLog(VertexSelectData.name).getLast();
//            if (!isClick) {
//                vc1 = vsd.v;
//                v1=vc1;
//                startPainting();
//            } else {
//                Vertex v2=vsd.v;
//                Edge e = doJob(v2.g, v2, vc1);
//                addUndoData(e);
//                stopPainting();
//            }
//            isClick=true;
//            isDrag=false;
//        }
        VertexEvent ve = blackboard.getData(VertexEvent.EVENT_KEY);

        if ((ve.modifiers & MouseEvent.SHIFT_DOWN_MASK) == MouseEvent.SHIFT_DOWN_MASK)
            return;

        SubGraph sd = Select.getSelection(blackboard);
        g = blackboard.getData(GraphAttrSet.name);
        gv = blackboard.getData(AbstractGraphRenderer.EVENT_KEY);


        if (eventName == VertexEvent.EVENT_KEY) {
            if (ve.eventType == VertexEvent.DRAGGING_STARTED) {
                if (!sd.vertices.contains(ve.v)) {   //start if the vertex is not selected
//            VertexDragData vdrag = blackboard.get(VertexDragData.name);
                    v1 = ve.v;
                    startPainting();
                    exitedFromV1 = false;
//                    if (!selMethods.getSelectedVertices().contains(v1)) {
//                        //the vertex doesn't selected
//                    } else
//                        v1 = null;
                }
            }
            if (ve.eventType == VertexEvent.DROPPED) {
                if (v1 != null) {
                    Vertex v2 = ve.v;
                    if (v2 != null && isDrag)   //!it was released on empty space
                        if (!v1.equals(v2) || exitedFromV1) {
                            doJob(g, v1, v2);
                            super.track();
                        }
//            unListenEvent(VertexMouseDraggingData.event);
                }
                stopPainting();
                v1 = null;
            }

            if (ve.eventType == VertexEvent.DRAGGING) {
                lastPos = ve.mousePos;
                GPoint absPosOnGraph = GraphUtils.createGraphPoint(g, x1, y1);
                absPosOnGraph.add(lastPos);
                if (g.getVerticesCount() < 300) {
                    Pair<Vertex, Double> p = GraphControl.mindistv(g, absPosOnGraph);
                    if (p.first != null)
                        curVertexUnderMouse = GraphControl.isPointOnVertex(g, p.first, absPosOnGraph) ? p.first : null;
                }
                if (!(gv instanceof AcceleratedRenderer))
                    gv.repaint();

                isDrag = true;
                isClick = false;

                if (v1 != null) {
                    if (curVertexUnderMouse == null)
                        exitedFromV1 = true;
                }
            }

            if (ve.eventType == VertexEvent.RELEASED) { //mouse released on a blank area of graph
                v1 = null;
                stopPainting();
            }
        } else if (eventName == GraphEvent.EVENT_KEY) {
//                if (ge.eventType == GraphEvent.) {
//                    stopPainting();
//                }
        }
    }

    protected void stopPainting() {
        gv.removePaintHandler(this);
//        g.view.repaint();
    }

    protected void startPainting() {
        GPoint location = v1.getLocation();
        Point viewPoint = GraphUtils.createViewPoint(g, location);
        cx = v1.getCenter().x / 2;
        x1 = viewPoint.x;
        cy = v1.getCenter().y / 2;
        y1 = viewPoint.y;
//        listen4Event(VertexMouseDraggingData.event);
        gv.addPostPaintHandler(this);
    }

    public static Edge doJob(GraphModel g, Vertex v1, Vertex v2) {
        Edge e = new Edge(v1, v2);
        g.insertEdge(e);
        return e;
    }

    protected int x1;
    protected int y1;
    protected int cx;
    protected int cy;

    protected Vertex curVertexUnderMouse = null;

    /**
    * paints incomplete edges for the user while he/she is adding the edge. (A preview)
    */
    public void paint(Graphics g, Object component, Boolean drawExtras) {
        if (!drawExtras)
            return;
        Graphics2D gg = (Graphics2D) g;
        gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Stroke stroke = gg.getStroke();
        gg.setStroke(GStroke.dashed.stroke);
        if (lastPos != null) {
            Point viewPoint = GraphUtils.createViewPoint(this.g, lastPos);
            gg.setColor(Color.DARK_GRAY);
            if (curVertexUnderMouse == null)
                gg.drawLine(x1, y1, x1 + viewPoint.x, y1 + viewPoint.y);
            else if (v1.equals(curVertexUnderMouse) && exitedFromV1) {
                Edge e = new Edge(v1, v1);
                double zf = this.g.getZoomFactor();
//                gg.setStroke(GStroke.strong.stroke);
                gg.drawOval(
                    ((int) (zf * (e.getLoopCenter().x - e.getLoopWidth()/2))),
                    ((int) (zf * (e.getLoopCenter().y - e.getLoopWidth()/2))),
                    ((int) (zf * (e.getLoopWidth()))),
                    ((int) (zf * (e.getLoopWidth()))));
            } 
            else {
                gg.setStroke(GStroke.strong.stroke);
                GPoint loc = curVertexUnderMouse.getLocation();
                viewPoint = GraphUtils.createViewPoint(this.g, loc);
                gg.drawLine(x1, y1, viewPoint.x, viewPoint.y);
            }
        }
        gg.setStroke(stroke);
    }
}
