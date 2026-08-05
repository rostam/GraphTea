// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.graphgenerator;

import graphtea.extensions.generators.*;
import graphtea.graph.event.GraphEvent;
import graphtea.graph.graph.*;
import graphtea.graph.ui.GTabbedGraphPane;
import graphtea.graph.ui.GraphRectRegionSelect;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.platform.plugin.PluginMethods;
import graphtea.plugins.graphgenerator.core.GraphGeneratorInterface;
import graphtea.plugins.graphgenerator.core.SimpleGeneratorInterface;
import graphtea.plugins.main.core.CorePluginMethods;
import graphtea.plugins.main.core.actions.vertex.AddVertex;

import java.awt.*;

/**
 * @author azin azadi

 */
public class GraphGenerator implements PluginMethods {

    /** Gap left between the generated graph and the edge of the visible canvas. */
    private static final int VIEWPORT_MARGIN = 60;

    /** Fallback size used when the canvas has not been laid out yet. */
    private static final int DEFAULT_EXTENT = 550;

    /**
     * Largest square a generated graph is laid out in. Without a cap, a maximised window
     * spreads three vertices across a metre of screen.
     */
    private static final int MAX_EXTENT = 620;

    /**
     * Generates a graph and drops it straight onto the visible canvas.
     *
     * <p>This used to put the application into an unannounced modal state: after the parameter
     * dialog closed, nothing appeared, and the only clue was a line of status-bar text at the
     * very bottom of the window asking the user to drag out a rectangle. People reported it as
     * "generating a graph does nothing". The graph is now placed automatically, centred in
     * whatever part of the canvas the user is looking at; {@link #placeGraphManually} is still
     * available for anyone who wants to choose the region.
     *
     * @param gen        the generator interface
     * @param blackboard the blackboard
     */
    public static void generateInRectangularBounds(final GraphGeneratorInterface gen, final BlackBoard blackboard) {
        generateGraph(gen, blackboard, visibleCanvasRegion(blackboard));
    }

    /**
     * Asks the user to drag out a region, then generates the graph into it.
     *
     * @param gen        the generator interface
     * @param blackboard the blackboard
     */
    public static void placeGraphManually(final GraphGeneratorInterface gen, final BlackBoard blackboard) {
        blackboard.setData(AddVertex.DISABLE, true);
        GTabbedGraphPane.showNotificationMessage(
                "Drag a rectangle on the canvas to place the graph.", blackboard, true);
        new GraphRectRegionSelect(blackboard) {

            public void onMouseMoved(GraphEvent data) {
                //do nothing;
            }

            public void onDrop(GraphEvent data) {
                new Thread(() -> {
                    synchronized (gv) {
                        generateGraph(gen, blackboard, rect);
                        GTabbedGraphPane.hideNotificationMessage(blackboard);
                    }
                }).start();
            }
        }.startSelectingRegion();
    }

    /**
     * Works out where on the canvas a newly generated graph should go: the part the user can
     * currently see, inset by a margin and kept square so layouts that assume equal axes are
     * not stretched.
     *
     * @param blackboard the blackboard
     * @return a region in graph coordinates
     */
    private static Rectangle visibleCanvasRegion(BlackBoard blackboard) {
        AbstractGraphRenderer renderer = blackboard.getData(AbstractGraphRenderer.EVENT_KEY);
        if (renderer == null || renderer.getWidth() <= 0 || renderer.getHeight() <= 0) {
            return new Rectangle(VIEWPORT_MARGIN, VIEWPORT_MARGIN, DEFAULT_EXTENT, DEFAULT_EXTENT);
        }
        Rectangle visible = renderer.getVisibleRect();
        if (visible.width <= 0 || visible.height <= 0) {
            visible = new Rectangle(0, 0, renderer.getWidth(), renderer.getHeight());
        }
        int extent = Math.min(MAX_EXTENT, Math.max(120,
                Math.min(visible.width, visible.height) - 2 * VIEWPORT_MARGIN));
        int x = visible.x + (visible.width - extent) / 2;
        int y = visible.y + (visible.height - extent) / 2;
        return new Rectangle(x, y, extent, extent);
    }

    /**
     * Generates a graph into the given region of the current graph, off the event thread.
     *
     * @param gen        the generator interface
     * @param blackboard the blackboard
     * @param rect       where to put it, in graph coordinates
     */
    public static void generateGraph(final GraphGeneratorInterface gen, final BlackBoard blackboard, final Rectangle rect) {
        final AbstractGraphRenderer ren = blackboard.getData(AbstractGraphRenderer.EVENT_KEY);
        if (rect.width < 5) {
            rect.width = DEFAULT_EXTENT;
        }
        if (rect.height < 5) {
            rect.height = DEFAULT_EXTENT;
        }
        new Thread(() -> {
            try {
                if (ren == null) {
                    generateGraphInRect(blackboard, gen, rect);
                } else {
                    ren.ignoreRepaints(() -> generateGraphInRect(blackboard, gen, rect));
                }
            } finally {
                // Whatever happened, do not leave click-to-add-a-vertex switched off.
                blackboard.setData(AddVertex.DISABLE, false);
            }
            if (ren != null) {
                ren.repaint();
            }
        }, "graphtea-generate").start();
    }

    /**
     * Generates a graph and merges it into the current one.
     *
     * @param blackboard the blackboard
     * @param gen        the generator interface
     * @param rect       where to put it, in graph coordinates
     * @return the generated graph, or null if the generator failed
     */
    public static GraphModel generateGraphInRect(BlackBoard blackboard, GraphGeneratorInterface gen, Rectangle rect) {
        GTabbedGraphPane.showNotificationMessage("Generating graph…", blackboard, true);
        try {
            GraphModel gg = CorePluginMethods.getGraph(blackboard);
            GraphModel g = gen.generateGraph();
            gg.addSubGraph(g, rect);
            GTabbedGraphPane.showTimeNotificationMessage(
                    "Generated " + g.getVerticesCount() + " vertices and " + g.getEdgesCount() + " edges",
                    blackboard, 4000, true);
            return g;
        } catch (RuntimeException e) {
            // Previously this escaped into the generator thread and vanished, leaving the
            // canvas unchanged with no explanation.
            GTabbedGraphPane.hideNotificationMessage(blackboard);
            ExceptionHandler.catchException("Generating a graph", e);
            return null;
        }
    }

    /**
     * generates and return a graph from the given interface, not showing it on GUI
     */
    public static GraphModel getGraph(boolean isDirected, SimpleGeneratorInterface gi) {
        GraphModel ret = new GraphModel(isDirected);
        Vertex[] vertices = gi.getVertices();
        GPoint[] pos = gi.getVertexPositions();
        Edge[] edges = gi.getEdges();
        for (Vertex v : vertices)
            ret.insertVertex(v);

        for (int i = 0; i < vertices.length; i++) {
            vertices[i].setLocation(new GPoint(pos[i].x, pos[i].y));
        }

        for (Edge e : edges) {
            ret.insertEdge(e);
        }

        return ret;
    }

    //________________    Graph Generators    _______________
    /**
     * @see graphtea.extensions.generators.CircleGenerator#generateCircle(int)
     */
    public static GraphModel generateCircle(int n) {
        return CircleGenerator.generateCircle(n);
    }

    /**
     * @see graphtea.extensions.generators.CmnGenerator#generateCmn(int,int)
     */
    public static GraphModel generateCmn(int m, int n) {
        return CmnGenerator.generateCmn(m, n);
    }

    /**
     * @see graphtea.extensions.generators.GeneralizedPetersonGenerator#generateGeneralizedPeterson(int,int)
     */
    public static GraphModel generateGeneralizedPeterson(int n, int k) {
        return GeneralizedPetersonGenerator.generateGeneralizedPeterson(n, k);
    }

    /**
     * @see graphtea.extensions.generators.CompleteGraphGenerator#generateCompleteGraph(int)
     */
    public static GraphModel generateCompleteGraph(int n) {
        return CompleteGraphGenerator.generateCompleteGraph(n);
    }

    /**
     * @see KndKneserGraphGenerator#generateKenserGraph(int,int)
     */
    public static GraphModel generateKenserGraph(int n, int d) {
        return KndKneserGraphGenerator.generateKenserGraph(n, d);
    }

    /**
     * @see graphtea.extensions.generators.KmnGenerator#generateKmn(int,int)
     */
    public static GraphModel generateKmn(int m, int n) {
        return KmnGenerator.generateKmn(m, n);
    }

    /**
     * @see graphtea.extensions.generators.PathGenerator#generatePath(int)
     */
    public static GraphModel generatePath(int n) {
        return PathGenerator.generatePath(n);
    }

    /**
     * @see graphtea.extensions.generators.PmnGenerator#generatePmn(int,int)
     */
    public static GraphModel generatePmn(int m, int n) {
        return PmnGenerator.generatePmn(m, n);
    }

    /**
     * @see graphtea.extensions.generators.RandomGenerator#generateRandomGraph(int,int)
     */
    public static GraphModel generateRandomGraph(int n, int e) {
        return RandomGenerator.generateRandomGraph(n, e);
    }

    /**
     * @see graphtea.extensions.generators.StarGenerator#generateStar(int)
     */
    public static GraphModel generateStar(int n) {
        return StarGenerator.generateStar(n);
    }

    /**
     * @see graphtea.extensions.generators.TreeGenerator#generateTree(int,int)
     */
    public static GraphModel generateTree(int depth, int degree) {
        return TreeGenerator.generateTree(depth, degree);
    }

    /**
     * @see graphtea.extensions.generators.WheelGenerator#generateWheel(int)
     */
    public static GraphModel generateWheel(int n) {
        return WheelGenerator.generateWheel(n);
    }
}
