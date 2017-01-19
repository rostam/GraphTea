// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.main.core.actions.edge;

import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.event.EdgeEvent;
import graphtea.graph.event.GraphEvent;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.PaintHandler;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;

import java.awt.*;

/**
 * @author Rouzbeh Ebrahimi, Azin Azadi
 *         Email: ruzbehus@gmail.com
 */
public class DragEdge extends AbstractAction implements PaintHandler {
    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */
    public DragEdge(BlackBoard bb) {
        super(bb);
        listen4Event(EdgeEvent.EVENT_KEY);
        listen4Event(GraphEvent.EVENT_KEY);

    }

    protected Edge edge;
    protected GraphModel g;
    //    protected AbstractGraphRenderer gv;
    //    protected boolean isClick = false;
    //    protected boolean isDrag = false;
    //    protected int x1;
    //    protected int y1;
    //    protected int cx;
    //    protected int cy;
    private GPoint lastPos, firstPos, cpfirstPos;
    private GPoint clickPos;

    public void track(){}
    public void performAction(String eventName, Object value) {
        g = blackboard.getData(GraphAttrSet.name);
        if (!g.isEdgesCurved())
            return;
//        gv = blackboard.getData(AbstractGraphRenderer.name);
        if (eventName == EdgeEvent.EVENT_KEY) {
            EdgeEvent ee = (EdgeEvent) value;
            edge = ee.e;
            if (ee.eventType == EdgeEvent.DRAGGING_STARTED) {
//                if (ee.mouseBtn == MouseEvent.BUTTON3) {
                lastPos = ee.mousePos;
                firstPos = ee.mousePos;
                cpfirstPos = edge.getCurveControlPoint();
//                startPainting(ee.e);
//                }
//                else {
//                    isDrag = false;
//                    clickPos = ee.mousePos;
//                    isClick = true;
//                    startPainting(ee.e);
////                    gv.repaint();
//                }


            } else if (ee.eventType == EdgeEvent.DRAGGING) {
                GPoint ctrlPnt = edge.getCurveControlPoint();
                if (!edge.isLoop()) {
                    edge.setCurveControlPoint(
                            new GPoint(ctrlPnt.x - lastPos.x + ee.mousePos.x,
                                    ctrlPnt.y - lastPos.y + ee.mousePos.y));
                } else {
                    edge.setCurveControlPoint(new GPoint(ee.mousePos.x,  ee.mousePos.y));
                }
                lastPos = ee.mousePos;
//                gv.repaint();
//                isDrag = true;
//                isClick = false;
            } else if (ee.eventType == EdgeEvent.RELEASED) {
                GPoint ctrlPnt = edge.getCurveControlPoint();
                lastPos = ee.mousePos;
                GPoint newpos = new GPoint(ctrlPnt.x + lastPos.x - ee.mousePos.x,
                        ctrlPnt.y + lastPos.y - ee.mousePos.y);
                edge.setCurveControlPoint(
                        newpos);
            }

        }
//        else if (eventName ==GraphEvent.name.getName())) {
//            GraphEvent ge = (GraphEvent) value;
//            if (ge.eventType == GraphEvent.DROPPED) {
//                stopPainting();
//            }
//
//        }
    }

    protected void stopPainting() {
//        gv.removePaintHandler(this);
    }

    protected void startPainting(Edge e) {
//
//        GPoint location = new GPoint(e.curve.getCtrlX(), e.curve.getCtrlY());
//        Point viewPoint = GPoint.createViewPoint(g, location);
//        x1 = viewPoint.x;
//        y1 = viewPoint.y;
////        listen4Event(VertexMouseDraggingData.event);
//        gv.addPostPaintHandler(this);
    }


    public void paint(Graphics g, Object destinationComponent, Boolean drawExtras) {
//        if (g != null) {
//            if (isDrag) {
////                double yDiff = (lastPos.getY() - firstPos.getY());
////                double xDiff = (lastPos.getX() - firstPos.getX());
////                GPoint difference=new GPoint(xDiff, yDiff);
//                Point viewPoint = GPoint.createViewPoint(this.g, lastPos);
//                Point2D p1 = e1.curve.getP1();
//                Point2D p2 = e1.curve.getP2();
//                Point2D pMouse = viewPoint;
//                Point2D ctrP = e1.curve.getCtrlPt();
//                Point2D newCtrlP = new Point2D.Double((Math.signum(pMouse.getX()) * Math.pow(pMouse.getX(), 1) + ctrP.getX()) / 2, (Math.signum(pMouse.getY()) * Math.pow(pMouse.getY(), 1) + ctrP.getY()) / 2);
//                e1.curve.setCurve(p1, newCtrlP, p2);
//            } else if (isClick) {
//                Point viewPoint = GPoint.createViewPoint(this.g, clickPos);
//                Point2D p1 = e1.curve.getP1();
//                Point2D p2 = e1.curve.getP2();
//                int ctrlX1 = (int) (p1.getX() + viewPoint.getX()) / 2 + 30;
//                int ctrlY1 = 30 + (int) (p1.getY() + viewPoint.getY()) / 2;
//                double dividingPointX = viewPoint.getX();
//                double dividingPointY = viewPoint.getY();
////                QuadCurve2D leftCurve = new QuadCurve2D.Double(p1.getX(), p1.getY(), ctrlX1, ctrlY1, dividingPointX, dividingPointY);
//                int ctrlX2 = (int) (p2.getX() + dividingPointX) / 2 - 30;
//                int ctrlY2 = (int) (p2.getY() + dividingPointY) / 2 - 30;
////                QuadCurve2D rightCurve = new QuadCurve2D.Double(dividingPointX, dividingPointY, ctrlX2, ctrlY2,p2.getX(),p2.getY() );
//                QuadCurve2D rightCurve = new QuadCurve2D.Double(), leftCurve = new QuadCurve2D.Double();
//                e1.curve.subdivide(leftCurve, rightCurve);
//                Point2D p = leftCurve.getCtrlPt();
//                int gh1 = (int) (p.getX() + 30);
//                int gh2 = (int) (p.getY() + 30);
//                p = new Point2D.Double(gh1, gh2);
//                leftCurve.setCurve(leftCurve.getP1(), p, leftCurve.getP2());
//                Point2D p4 = rightCurve.getCtrlPt();
//                int fh1 = (int) (p4.getX() - 30);
//                int fh2 = (int) (p4.getY() - 30);
//                p = new Point2D.Double(fh1, fh2);
//                rightCurve.setCurve(rightCurve.getP1(), p, rightCurve.getP2());
//                Graphics2D gg = (Graphics2D) g;
//                gg.draw(leftCurve);
//                g.drawString("*", (int) dividingPointX, (int) dividingPointY);
//                gg.draw(rightCurve);
////                stopPainting();
//            }
//
////                gg.draw(e1.curve);
//        }
//
    }

}
