// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.graph.old;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.ArrayX;
import graphtea.platform.lang.FromStringProvider;
import graphtea.platform.preferences.GraphPreferences;
import graphtea.platform.preferences.UserDefinedEligiblity;
import graphtea.platform.preferences.lastsettings.StorableOnExit;
import graphtea.platform.preferences.lastsettings.UserModifiableProperty;

import java.awt.*;
import java.util.HashMap;
import java.util.Vector;


public class ArrowHandler implements StorableOnExit, UserDefinedEligiblity, FromStringProvider<Arrow> {
    //I thinked and decided to choose the base arrows to be Polygons. i think it will be enough for the most needs, but it can't handle more complex needs, like arrows that are images.

    @UserModifiableProperty(displayName = "Default Arrwo Size for Edges")
    public static Integer arrowSize = 10;


    /**
     * draws the arrow of the edge on the graphics
     *
     * @param zoomFactor the zoom factor of Graph Model
     */
    public static void paint(Graphics _g, GraphModel gr, Edge e, double zoomFactor) {
//        new ArrowHandler();
        Arrow arrow = e.getArrow();
        Graphics2D g = (Graphics2D) _g;

        double angle = 0d;
        if (!gr.isEdgesCurved())
            angle = e.getAngle();
        else {
            GPoint src = e.source.getLocation();    // it is the center of the vertex
            GPoint trg = e.target.getLocation();
            GPoint ctrlPnt = e.getCurveControlPoint();
            double edgecenterx = (src.x + trg.x) / 2;
            int ctrlPntViewX = (int) (edgecenterx + ctrlPnt.x);
            double edgecentery = (src.y + trg.y) / 2;
            int ctrlPntViewY = (int) (edgecentery + ctrlPnt.y);

            GPoint targetLoc = e.target.getLocation();
            angle = Math.atan2((4 * ctrlPntViewY - src.y - trg.y) / 2 - targetLoc.y,
                    (4 * ctrlPntViewX - src.x - trg.x) / 2 - targetLoc.x);
            if (angle < 0) {
                // atan2 returns getAngle in phase -pi to pi, which means
                // we have to convert the answer into 0 to 2pi range.
                angle += 2 * Math.PI;
            }
        }
        Vertex v2 = e.target;
        GPoint v2ShapeSize = v2.getSize();
        int w = (int) v2ShapeSize.getX();
        int h = (int) v2ShapeSize.getY();
        double t = Math.atan2(w * Math.sin(angle), h * Math.cos(angle));
        GPoint loc = e.target.getLocation();
        int x2 = (int) ((zoomFactor * loc.x) + ((w / 2) * Math.cos(t)));
        int y2 = (int) ((zoomFactor * loc.y) + ((h / 2) * Math.sin(t)));
//        g.drawLine((int)loc.x-30, (int)loc.y-30, (int)loc.x+30, (int)loc.y+30);
        g.translate(x2 + 1, y2 + 1);
        g.rotate(angle + Math.PI);
        arrow.paintArrow(g, arrowSize, arrowSize);
        g.rotate(-angle - Math.PI);
        g.translate(-x2 - 1, -y2 - 1);
    }

    public static PolygonArrow defaultArrow;

    static {
        //create and add default arrows
        int xPoints[] = {0, -15, -15};
        int yPoints[] = {0, -15 / 2, 15 / 2};
        defaultArrow = new PolygonArrow(new Polygon(xPoints, yPoints, 3), "Default");
        PolygonArrow ar1 = new PolygonArrow(new Polygon(new int[]{0, -8, -8}, new int[]{0, -4, 4}, 3), "Small");
        PolygonArrow ar4 = new PolygonArrow(new Polygon(new int[]{0, -15, -10, -15}, new int[]{0, -7, 0, 7}, 4), "Narrow");
        PolygonArrow ar2 = new PolygonArrow(new Polygon(new int[]{0, -15, -5, -15}, new int[]{0, -7, 0, 7}, 4), "Very Narrow");
        PolygonArrow ar3 = new PolygonArrow(new Polygon(new int[]{0, -15, -30, -15}, new int[]{0, -7, 0, 7}, 4), "Box");
        knownArrows = new Vector<Arrow>();
        registerArrow(defaultArrow);
        registerArrow(ar1);
        registerArrow(ar2);
        registerArrow(ar3);
        registerArrow(ar4);

    }

    public static Vector<Arrow> knownArrows;

    public static void registerArrow(Arrow arrow) {
        knownArrows.add(arrow);
    }

    public GraphPreferences GraphPrefFactory() {
        return new GraphPreferences(this.getClass().getSimpleName(), this, "Graph Drawings");
    }

    public HashMap<Object, ArrayX> defineEligibleValuesForSettings(HashMap<Object, ArrayX> objectValues) {
        ArrayX t = new ArrayX(new Integer(3));
        t.addValidValue(new Integer(10));
        t.addValidValue(new Integer(20));
        t.addValidValue(new Integer(30));
//        t.addValidValue(15);

        try {
            objectValues.put(this.getClass().getField("arrowSize"), t);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return objectValues;
    }

    public Arrow fromString(String toString) {
        for (Arrow s : knownArrows) {
            if (s.getName().equals(toString)) {
                return s;
            }
        }
        return null;
    }
}