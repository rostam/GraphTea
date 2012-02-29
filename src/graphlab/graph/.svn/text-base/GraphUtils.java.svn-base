// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.graph;

import graphlab.graph.graph.*;
import graphlab.graph.ui.GTabbedGraphPane;
import graphlab.library.util.Pair;
import graphlab.platform.core.BlackBoard;

import java.awt.*;

/**
 * @author Azin Azadi
 */
public class GraphUtils {
    //_____________________    Graph Renderer    ___________________________
    /**
     * @see graphlab.graph.graph.AbstractGraphRenderer#ignoreRepaints(Runnable,boolean)
     */
    public void ignoreRepaints(AbstractGraphRenderer renderer, Runnable run, boolean repaintAfter) {
        renderer.ignoreRepaints(run, repaintAfter);
    }

//______________________     GTabbedGraphPane     ______________________

    /**
     * @see graphlab.graph.ui.GTabbedGraphPane#showNotificationMessage(String,graphlab.platform.core.BlackBoard,boolean)
     */
    public static void showNotificationMessage(String message, BlackBoard b, boolean formatIt) {
        GTabbedGraphPane.showNotificationMessage(message, b, formatIt);
    }

    /**
     * @see graphlab.graph.ui.GTabbedGraphPane#setMessage(String,graphlab.platform.core.BlackBoard,boolean)
     */
    public static void setMessage(String message, BlackBoard b, boolean formatIt) {
        GTabbedGraphPane.setMessage(message, b, formatIt);
    }

    /**
     * @see graphlab.graph.ui.GTabbedGraphPane#hideNotificationMessage(graphlab.platform.core.BlackBoard)
     */
    public static void hideNotificationMessage(BlackBoard b) {
        GTabbedGraphPane.hideNotificationMessage(b);
    }

    /**
     * @see graphlab.graph.ui.GTabbedGraphPane#showTimeNotificationMessage(String,graphlab.platform.core.BlackBoard,long,boolean)
     */
    public static void showTimeNotificationMessage(String message, final BlackBoard b, final long timeMillis, boolean formatIt) {
        GTabbedGraphPane.showTimeNotificationMessage(message, b, timeMillis, formatIt);
    }

    //______________________     GraphControl     ______________________
    /**
     * @see graphlab.graph.graph.GraphControl#isPointOnVertex(graphlab.graph.graph.GraphModel,graphlab.graph.graph.VertexModel,graphlab.graph.graph.GraphPoint)
     */
    public static boolean isPointOnVertex(GraphModel g, VertexModel v, GraphPoint p) {
        return GraphControl.isPointOnVertex(g, v, p);
    }

    /**
     * @see graphlab.graph.graph.GraphControl#mindistv(graphlab.graph.graph.GraphModel,graphlab.graph.graph.GraphPoint)
     */
    public static Pair<VertexModel, Double> mindistv(GraphModel g, GraphPoint p) {
        return GraphControl.mindistv(g, p);
    }

    /**
     * @see graphlab.graph.graph.GraphControl#mindiste(graphlab.graph.graph.GraphModel,graphlab.graph.graph.GraphPoint)
     */
    public static Pair<EdgeModel, Double> mindiste(GraphModel g, GraphPoint p) {
        return GraphControl.mindiste(g, p);
    }

    //______________________     GraphModel     ______________________
    /**
     * @see graphlab.graph.graph.GraphModel#getColor(Integer)
     */
    public static Color getColor(Integer i) {
        return GraphModel.getColor(i);
    }

    /**
     * @see graphlab.graph.graph.GraphModel#addGlobalUserDefinedAttribute(String,Object)
     * @see graphlab.graph.graph.VertexModel#setUserDefinedAttribute(String,Object)
     */
    public static void addGraphGlobalUserdefinedAttribute(String name, Object defaultvalue) {
        GraphModel.addGlobalUserDefinedAttribute(name, defaultvalue);
    }

    /**
     * @see graphlab.graph.graph.GraphModel#removeGlobalUserDefinedAttribute(String)
     */
    public static void removeGraphGlobalUserdefinedAttribute(String name) {
        GraphModel.removeGlobalUserDefinedAttribute(name);
    }

    //______________________     VertexModel     ______________________
    /**
     * @see graphlab.graph.graph.VertexModel#addGlobalUserDefinedAttribute(String,Object)
     * @see graphlab.graph.graph.VertexModel#setUserDefinedAttribute(String,Object)
     */
    public static void addVertexGlobalUserdefinedAttribute(String name, Object defaultvalue) {
        VertexModel.addGlobalUserDefinedAttribute(name, defaultvalue);
    }

    /**
     * @see graphlab.graph.graph.VertexModel#removeGlobalUserDefinedAttribute(String)
     */
    public static void removeVertexGlobalUserdefinedAttribute(String name) {
        VertexModel.removeGlobalUserDefinedAttribute(name);
    }

    //______________________     EdgeModel     ______________________
    /**
     * @see graphlab.graph.graph.EdgeModel#addGlobalUserDefinedAttribute(String,Object)
     * @see graphlab.graph.graph.VertexModel#setUserDefinedAttribute(String,Object)
     */
    public static void addEdgeGlobalUserdefinedAttribute(String name, Object defaultvalue) {
        EdgeModel.addGlobalUserDefinedAttribute(name, defaultvalue);
    }

    /**
     * @see graphlab.graph.graph.EdgeModel#removeGlobalUserDefinedAttribute(String)
     */
    public static void removeEdgeGlobalUserdefinedAttribute(String name) {
        EdgeModel.removeGlobalUserDefinedAttribute(name);
    }

    //______________________     GraphPoint     ______________________

    /**
     * creates a new graph point from the given point according too graph zoom,
     * The input x and y are typically are directly from view,
     * notice that inside the GraphLab everything are GraphPoint, so they are independent of zoom
     * use this method only if you want to convert a view point to graph point!
     *
     * @param g     the graph that zoom and center values are used from.
     * @param viewx initial x potition
     * @param viewy initial y position
     * @return a0 GraphPoint object which have x and y acording to zoom and center
     */
    public static GraphPoint createGraphPoint(GraphModel g, int viewx, int viewy) {
        double factor = g.getZoomFactor();
        return new GraphPoint(viewx / factor, viewy / factor);
    }

    public static Point createViewPoint(GraphModel g, GraphPoint p) {
        double factor = g.getZoomFactor();
        return new Point((int) (p.x * factor), (int) (p.y * factor));
    }

    public static Rectangle createViewRectangle(GraphModel g, Rectangle r) {
        double factor = g.getZoomFactor();
        return new Rectangle((int) (r.x * factor), (int) (r.y * factor), (int) (r.width * factor), (int) (r.height * factor));

    }
}
