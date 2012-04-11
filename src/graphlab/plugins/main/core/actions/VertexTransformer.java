// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.main.core.actions;

import graphlab.graph.event.GraphEvent;
import graphlab.graph.graph.*;
import graphlab.graph.ui.GraphRectRegionSelect;
import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.plugins.main.GraphData;
import graphlab.plugins.main.core.AlgorithmUtils;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;

/**
 * A class to transform the seleected vertices using mouse
 * This class is not completely working yet!
 * <p/>
 * Now has conflicts with GraphRectRegionSelect
 *
 * @author Azin Azadi
 */
public class VertexTransformer extends AbstractAction implements PaintHandler<AbstractGraphRenderer> {
    GraphData gd;

    //the moving recangles
    Rectangle2D.Double up;
    Rectangle2D.Double down;
    Rectangle2D.Double left;
    Rectangle2D.Double right;

    //Center Points of boxes
    GraphPoint upp, downp, leftp, rightp;
    public static String IS_TRANSFORMING = "VertexTransformer.isTransforming";
    private SubGraph sd;
    private GraphPoint[] verticesPositionsBackUp;

    double drgStartMouseX, drgStartMouseY;
    double drgStartWidth, drgStartHeight;
    double drgFixX, drgFixY;

    /**
     * the active transforming box , null if it isn't any
     */
    String activeBox;

    public VertexTransformer(BlackBoard blackboard) {
        super(blackboard);
        gd = new GraphData(blackboard);
        listen4Event(GraphEvent.EVENT_KEY);
    }

    public void performAction(String eventName, Object value) {
        if (GraphRectRegionSelect.isSelecting)
            return;

        HashSet<Vertex> selectedVertices = gd.select.getSelectedVertices();
        if (selectedVertices.size() <= 1)
            return;

        GraphModel g = gd.getGraph();
        GraphEvent ge = (GraphEvent) value;

        if (ge.eventType == GraphEvent.DRAGGING_STARTED) {
//            System.out.println("start drag ***********");
            gd.getGraphRenderer().addPostPaintHandler(this);
            //Gather initial and undo information
            GraphPoint pos = ge.mousePos;
            verticesPositionsBackUp = new GraphPoint[g.getVerticesCount()];
            for (Vertex _ : g) {
                verticesPositionsBackUp[_.getId()] = _.getLocation();
            }
            drgStartMouseX = ge.mousePos.x;
            drgStartMouseY = ge.mousePos.y;
            if (up == null)//not initialized yet
                return;
            if (up.contains(pos)) {
//                blackboard.setData(IS_TRANSFORMING, true);
                activeBox = "up";
                drgFixX = leftp.x;
                drgFixY = downp.y;
                drgStartWidth = rightp.x - leftp.x;
                drgStartHeight = -downp.y + upp.y;
            }
            if (down.contains(pos)) {
//                blackboard.setData(IS_TRANSFORMING, true);
                activeBox = "down";
                drgFixX = leftp.x;
                drgFixY = upp.y;
                drgStartWidth = rightp.x - leftp.x;
                drgStartHeight = downp.y - upp.y;
            }
            if (left.contains(pos)) {
//                blackboard.setData(IS_TRANSFORMING, true);
                activeBox = "left";
                drgFixX = rightp.x;
                drgFixY = upp.y;
                drgStartWidth = -rightp.x + leftp.x;
                drgStartHeight = downp.y - upp.y;
            }
            if (right.contains(pos)) {
                activeBox = "right";
                drgFixX = leftp.x;
                drgFixY = upp.y;
                drgStartWidth = rightp.x - leftp.x;
                drgStartHeight = downp.y - upp.y;
            }

        }
        if (ge.eventType == GraphEvent.DRAGGING) {
//            System.out.println("drag");
            if (activeBox == null) {    //if not transforming then exit
                return;
            }
            blackboard.setData(IS_TRANSFORMING, true);

//            System.out.println("active drag");
            //process event
            double dx = 0, dy = 0;
            if (activeBox.equals("up")) {
                dy = ge.mousePos.y - drgStartMouseY;
            }
            if (activeBox.equals("down")) {
                dy = ge.mousePos.y - drgStartMouseY;
            }
            if (activeBox.equals("left")) {
                dx = ge.mousePos.x - drgStartMouseX;
            }
            if (activeBox.equals("right")) {
                dx = ge.mousePos.x - drgStartMouseX;
            }

            //snap
            //transform vertices
//            System.out.println(Math.abs((drgStartWidth + dx) - (drgStartHeight + dy)));
//            System.out.println(Math.abs(drgStartWidth) + dx - Math.abs(drgStartHeight) + dy);
            int snapAmount = 15;
            if (abs(abs(drgStartWidth + dx) - abs(drgStartHeight + dy)) < snapAmount) {
                if (dx == 0) {
                    double d1 = Math.signum(dy) * abs(abs(drgStartWidth) + abs(drgStartHeight));
                    double d2 = Math.signum(dy) * abs(abs(drgStartHeight) - abs(drgStartWidth));
                    if (abs(dy - d1) > abs(dy - d2)) {
                        dy = d2;
                    } else {
                        dy = d1;
                    }
                } else {//dy==0
                    double d1 = Math.signum(dx) * abs(abs(drgStartWidth) + abs(drgStartHeight));
                    double d2 = Math.signum(dx) * abs(abs(drgStartHeight) - abs(drgStartWidth));
                    if (abs(dx - d1) > abs(dx - d2)) {
                        dx = d2;
                    } else {
                        dx = d1;
                    }
                }
            }

            //transform
            for (Vertex v : selectedVertices) {
                GraphPoint loc = verticesPositionsBackUp[v.getId()];
                double vx = loc.x;
                double vy = loc.y;
                v.setLocation(new GraphPoint(loc.x + dx * (vx - drgFixX) / drgStartWidth, loc.y + dy * (vy - drgFixY) / drgStartHeight));
            }
        }

        if (ge.eventType == GraphEvent.DROPPED) {
//            System.out.println("dropped");
            //add undo data
            GraphPoint[] newPos = new GraphPoint[gd.getGraph().getVerticesCount()];
            for (Vertex _ : gd.getGraph()) {
                newPos[_.getId()] = _.getLocation();
            }

            blackboard.setData(IS_TRANSFORMING, false);
        }

//        gd.select.setSelected(sd);
    }

    private double abs(double dy) {
        return Math.abs(dy);
    }

    public void paint(Graphics g, Object destinationComponent, Boolean drawExtras) {
        HashSet<Vertex> selectedVertices = gd.select.getSelectedVertices();
        if (selectedVertices.size() <= 1)
            return;

        //just get a backup
        sd = gd.select.getSelected();
        g.setColor(Color.LIGHT_GRAY);
        Rectangle2D.Double boundingRegion = AlgorithmUtils.getBoundingRegion(selectedVertices);
        int x = (int) boundingRegion.x;
        int y = (int) boundingRegion.y;
        int width = (int) boundingRegion.width;
        int height = (int) boundingRegion.height;
        g.drawRoundRect(x, y, width, height, 15, 15);

        int trboxsz = 15;
        //draw transform boxes
        upp = new GraphPoint(x + width / 2, y);
        downp = new GraphPoint(x + width / 2, y + height);
        leftp = new GraphPoint(x, y + height / 2);
        rightp = new GraphPoint(x + width, y + height / 2);
        up = createTransformControlRect(upp, trboxsz);
        down = createTransformControlRect(downp, trboxsz);
        left = createTransformControlRect(leftp, trboxsz);
        right = createTransformControlRect(rightp, trboxsz);

        drawRoundRect(g, up, trboxsz / 3);
        drawRoundRect(g, down, trboxsz / 3);
        drawRoundRect(g, left, trboxsz / 3);
        drawRoundRect(g, right, trboxsz / 3);
    }

    private Rectangle2D.Double createTransformControlRect(GraphPoint rr, int trboxsz) {
        return new Rectangle2D.Double(rr.x - trboxsz / 2, rr.y - trboxsz / 2, trboxsz, trboxsz);
    }

    public static void drawRoundRect(Graphics g, Rectangle2D.Double rect, int arcsize) {
        g.drawRoundRect((int) rect.x, (int) rect.y, (int) rect.height, (int) rect.width, arcsize, arcsize);

    }

    /**
     * @return true if the given position is on some of resize boxes according to the graph assigned
     *         to the blackboard, if any of boxes are visible
     */
    public static boolean isPositionOnResizeBoxes(GraphPoint mousPos, BlackBoard b) {
        Boolean aBoolean = b.getData(IS_TRANSFORMING);
        if (aBoolean != null && aBoolean) {
            return true;
        }

        GraphData gd = new GraphData(b);
        HashSet<Vertex> selectedVertices = gd.select.getSelectedVertices();
        if (selectedVertices.size() <= 1)
            return false;

        Rectangle2D.Double boundingRegion = AlgorithmUtils.getBoundingRegion(selectedVertices);
        int x = (int) boundingRegion.x;
        int y = (int) boundingRegion.y;
        int width = (int) boundingRegion.width;
        int height = (int) boundingRegion.height;

        //the moving recangles
        Rectangle2D.Double up;
        Rectangle2D.Double down;
        Rectangle2D.Double left;
        Rectangle2D.Double right;

        int trboxsz = 15;
        //draw transform boxes
        up = new Rectangle2D.Double(x + width / 2 - trboxsz / 2, y - trboxsz / 2, trboxsz, trboxsz);
        down = new Rectangle2D.Double(x + width / 2 - trboxsz / 2, y + height - trboxsz / 2, trboxsz, trboxsz);
        left = new Rectangle2D.Double(x - trboxsz / 2, y + height / 2 - trboxsz / 2, trboxsz, trboxsz);
        right = new Rectangle2D.Double(x + width - trboxsz / 2, y + height / 2 - trboxsz / 2, trboxsz, trboxsz);

        return up.contains(mousPos) | down.contains(mousPos) | left.contains(mousPos) | right.contains(mousPos);

    }

}
