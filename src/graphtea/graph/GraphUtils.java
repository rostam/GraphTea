// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.graph;

import graphtea.graph.graph.*;
import graphtea.graph.ui.GTabbedGraphPane;
import graphtea.library.util.Pair;
import graphtea.platform.core.BlackBoard;

import java.awt.*;

/**
 * @author Azin Azadi
 */
public class GraphUtils {
    //_____________________    Graph Renderer    ___________________________
    /**
     * @see graphtea.graph.graph.AbstractGraphRenderer#ignoreRepaints(Runnable,boolean)
     */
    public void ignoreRepaints(AbstractGraphRenderer renderer, Runnable run, boolean repaintAfter) {
        renderer.ignoreRepaints(run, repaintAfter);
    }

//______________________     GTabbedGraphPane     ______________________

    /**
     * @see graphtea.graph.ui.GTabbedGraphPane#showNotificationMessage(String,graphtea.platform.core.BlackBoard,boolean)
     */
    public static void showNotificationMessage(String message, BlackBoard b, boolean formatIt) {
        GTabbedGraphPane.showNotificationMessage(message, b, formatIt);
    }



    /**
     * @see graphtea.graph.ui.GTabbedGraphPane#setMessage(String,graphtea.platform.core.BlackBoard,boolean)
     */
    public static void setMessage(String message, BlackBoard b, boolean formatIt) {
        GTabbedGraphPane.setMessage(message, b, formatIt);
    }

    /**
     * @see graphtea.graph.ui.GTabbedGraphPane#hideNotificationMessage(graphtea.platform.core.BlackBoard)
     */
    public static void hideNotificationMessage(BlackBoard b) {
        GTabbedGraphPane.hideNotificationMessage(b);
    }

    /**
     * @see graphtea.graph.ui.GTabbedGraphPane#showTimeNotificationMessage(String,graphtea.platform.core.BlackBoard,long,boolean)
     */
    public static void showTimeNotificationMessage(String message, final BlackBoard b, final long timeMillis, boolean formatIt) {
        GTabbedGraphPane.showTimeNotificationMessage(message, b, timeMillis, formatIt);
    }

    //______________________     GraphControl     ______________________
    /**
     * @see graphtea.graph.graph.GraphControl#isPointOnVertex(graphtea.graph.graph.GraphModel, graphtea.graph.graph.Vertex, GPoint)
     */
    public static boolean isPointOnVertex(GraphModel g, Vertex v, GPoint p) {
        return GraphControl.isPointOnVertex(g, v, p);
    }

    /**
     * @see graphtea.graph.graph.GraphControl#mindistv(graphtea.graph.graph.GraphModel, GPoint)
     */
    public static Pair<Vertex, Double> mindistv(GraphModel g, GPoint p) {
        return GraphControl.mindistv(g, p);
    }

    /**
     * @see graphtea.graph.graph.GraphControl#mindiste(graphtea.graph.graph.GraphModel, GPoint)
     */
    public static Pair<Edge, Double> mindiste(GraphModel g, GPoint p) {
        return GraphControl.mindiste(g, p);
    }

    //______________________     GraphModel     ______________________
    /**
     * @see graphtea.graph.graph.GraphModel#getColor(Integer)
     */
    public static Color getColor(Integer i) {
        return GraphModel.getColor(i);
    }

    /**
     * @see graphtea.graph.graph.GraphModel#addGlobalUserDefinedAttribute(String,Object)
     * @see graphtea.graph.graph.Vertex#setUserDefinedAttribute(String,Object)
     */
    public static void addGraphGlobalUserdefinedAttribute(String name, Object defaultvalue) {
        GraphModel.addGlobalUserDefinedAttribute(name, defaultvalue);
    }

    /**
     * @see graphtea.graph.graph.GraphModel#removeGlobalUserDefinedAttribute(String)
     */
    public static void removeGraphGlobalUserdefinedAttribute(String name) {
        GraphModel.removeGlobalUserDefinedAttribute(name);
    }

    //______________________     Vertex     ______________________
    /**
     * @see graphtea.graph.graph.Vertex#addGlobalUserDefinedAttribute(String,Object)
     * @see graphtea.graph.graph.Vertex#setUserDefinedAttribute(String,Object)
     */
    public static void addVertexGlobalUserdefinedAttribute(String name, Object defaultvalue) {
        Vertex.addGlobalUserDefinedAttribute(name, defaultvalue);
    }

    /**
     * @see graphtea.graph.graph.Vertex#removeGlobalUserDefinedAttribute(String)
     */
    public static void removeVertexGlobalUserdefinedAttribute(String name) {
        Vertex.removeGlobalUserDefinedAttribute(name);
    }

    //______________________     Edge     ______________________
    /**
     * @see graphtea.graph.graph.Edge#addGlobalUserDefinedAttribute(String,Object)
     * @see graphtea.graph.graph.Vertex#setUserDefinedAttribute(String,Object)
     */
    public static void addEdgeGlobalUserdefinedAttribute(String name, Object defaultvalue) {
        Edge.addGlobalUserDefinedAttribute(name, defaultvalue);
    }

    /**
     * @see graphtea.graph.graph.Edge#removeGlobalUserDefinedAttribute(String)
     */
    public static void removeEdgeGlobalUserdefinedAttribute(String name) {
        Edge.removeGlobalUserDefinedAttribute(name);
    }

    //______________________     GPoint     ______________________

    /**
     * creates a new graph point from the given point according too graph zoom,
     * The input x and y are typically are directly from view,
     * notice that inside the GraphTea everything are GPoint, so they are independent of zoom
     * use this method only if you want to convert a view point to graph point!
     *
     * @param g     the graph that zoom and center values are used from.
     * @param viewx initial x potition
     * @param viewy initial y position
     * @return a0 GPoint object which have x and y acording to zoom and center
     */
    public static GPoint createGraphPoint(GraphModel g, int viewx, int viewy) {
        double factor = g.getZoomFactor();
        return new GPoint(viewx / factor, viewy / factor);
    }

    public static Point createViewPoint(GraphModel g, GPoint p) {
        double factor = g.getZoomFactor();
        return new Point((int) (p.x * factor), (int) (p.y * factor));
    }

    public static Rectangle createViewRectangle(GraphModel g, Rectangle r) {
        double factor = g.getZoomFactor();
        return new Rectangle((int) (r.x * factor), (int) (r.y * factor), (int) (r.width * factor), (int) (r.height * factor));

    }
}
