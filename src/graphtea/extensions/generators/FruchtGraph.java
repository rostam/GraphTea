// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.generators;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.graphgenerator.GraphGenerator;
import graphtea.plugins.graphgenerator.core.PositionGenerators;
import graphtea.plugins.graphgenerator.core.SimpleGeneratorInterface;
import graphtea.plugins.graphgenerator.core.extension.GraphGeneratorExtension;

/**
 * Generates the Frucht graph: 12 vertices, 18 edges.
 * The Frucht graph is 3-regular and is the smallest graph
 * that has a trivial automorphism group (no symmetry).
 */
@CommandAttitude(name = "generate_frucht", abbreviation = "_g_frucht",
    description = "Generates the Frucht graph (12 vertices, 18 edges)")
public class FruchtGraph implements GraphGeneratorExtension, Parametrizable,
    SimpleGeneratorInterface {

    private static final int NUM_VERTICES = 12;

    // 3-regular graph with trivial automorphism group
    private static final int[][] EDGE_LIST = {
        {0, 1}, {0, 2}, {0, 3},
        {1, 4}, {1, 5},
        {2, 4}, {2, 6},
        {3, 7}, {3, 8},
        {4, 9},
        {5, 6}, {5, 10},
        {6, 11},
        {7, 9}, {7, 10},
        {8, 9}, {8, 11},
        {10, 11}
    };

    private Vertex[] v;

    @Override
    public Vertex[] getVertices() {
        v = new Vertex[NUM_VERTICES];
        for (int i = 0; i < NUM_VERTICES; i++) {
            v[i] = new Vertex();
        }
        return v;
    }

    @Override
    public Edge[] getEdges() {
        Edge[] ret = new Edge[EDGE_LIST.length];
        for (int i = 0; i < EDGE_LIST.length; i++) {
            ret[i] = new Edge(v[EDGE_LIST[i][0]], v[EDGE_LIST[i][1]]);
        }
        return ret;
    }

    @Override
    public GPoint[] getVertexPositions() {
        return PositionGenerators.circle(5, 5, 100000, 100000, NUM_VERTICES);
    }

    @Override
    public String checkParameters() {
        return null;
    }

    @Override
    public GraphModel generateGraph() {
        return GraphGenerator.getGraph(false, this);
    }

    @Override
    public String getName() {
        return "Frucht Graph";
    }

    @Override
    public String getDescription() {
        return "Frucht graph: 12 vertices, 18 edges. "
            + "Smallest 3-regular graph with trivial automorphism group (no symmetry).";
    }

    @Override
    public String getCategory() {
        return "Benchmark Graphs";
    }
}
