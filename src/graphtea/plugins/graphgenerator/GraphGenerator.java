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

    /**
     * first request a GUI rectangular region from user and then generates and adds a graph from gen to current working graph
     *
     * @param gen The generator interface
     * @param blackboard The blackboard
     */
    public static void generateInRectangularBounds(final GraphGeneratorInterface gen, final BlackBoard blackboard) {
        blackboard.setData(AddVertex.DISABLE, true);

        //select a region and then generate the graph
        GTabbedGraphPane.showNotificationMessage("<HTML><BODY><Center><h1>Please select a region to put the generated graph.</h1></center></body></html>", blackboard, true);
//        JOptionPane.showMessageDialog(null, "Please select a region to put the generated graph");
        new GraphRectRegionSelect(blackboard) {

            public void onMouseMoved(GraphEvent data) {
                //do nothing;
            }

            public void onDrop(GraphEvent data) {
//                final blackboard blackboard = blackboard;
                new Thread() {
                    public void run() {
                        synchronized (gv) {
                            generateGraph(gen, blackboard, rect);
                            GTabbedGraphPane.hideNotificationMessage(blackboard);
                        }
                    }
                }.start();
            }
        }.startSelectingRegion();
    }

    /**
     * generates and adds a graph from gen to current working graph
     *
     * @param gen The generator interface
     * @param blackboard The blackboard
     */
    public static void generateGraph(final GraphGeneratorInterface gen, final BlackBoard blackboard, final Rectangle rect) {
        //                            g.view.ignoreUpdates = true;
//                            blackboard blackboard = g.blackboard;
        final AbstractGraphRenderer ren = blackboard.getData(AbstractGraphRenderer.EVENT_KEY);
        if (rect.width < 5)
            rect.width = 550;
        if (rect.height < 5)
            rect.height = 550;
        new Thread() {
            public void run() {
                ren.ignoreRepaints(() -> {
                    generateGraphInRect(blackboard, gen, rect);
                    blackboard.setData(AddVertex.DISABLE, false);
                });

            }
        }.start();
    }

    public static GraphModel generateGraphInRect(BlackBoard blackboard, GraphGeneratorInterface gen, Rectangle rect) {
        GTabbedGraphPane.showNotificationMessage("Generating Graph...", blackboard, true);
        GraphModel gg = CorePluginMethods.getGraph(blackboard);
        GraphModel g = gen.generateGraph();
        gg.addSubGraph(g, rect);

        GTabbedGraphPane.showTimeNotificationMessage("The Graph generated Completely: Vertices:" + g.getVerticesCount() + ", Edges:" + g.getEdgesCount(), blackboard, 4000, true);
        return g;

    }

    /**
     * generates and return a graph from the given interface, not showing it on GUI
     */
    public static GraphModel getGraph(boolean isDirected, SimpleGeneratorInterface gi) {
        GraphModel ret = new GraphModel(isDirected);
        gi.setWorkingGraph(ret);
        Vertex[] vertices = gi.getVertices();
        Point[] pos = gi.getVertexPositions();
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
     * @see graphtea.extensions.generators.KenserGraphGenerator#generateKenserGraph(int,int)
     */
    public static GraphModel generateKenserGraph(int n, int d) {
        return KenserGraphGenerator.generateKenserGraph(n, d);
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

