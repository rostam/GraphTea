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
 * Generates the Heawood graph: 14 vertices, 21 edges.
 * The Heawood graph is 3-regular and bipartite.
 * It is the smallest 6-cage (girth 6) and the unique (3,6)-cage.
 *
 * Construction: LCF notation [5,-5]^7 on a 14-cycle.
 * Every vertex i in the cycle is also joined to vertex (i+5) mod 14
 * (even positions) or (i-5) mod 14 (odd positions).
 */
@CommandAttitude(name = "generate_heawood", abbreviation = "_g_heawood",
    description = "Generates the Heawood graph (14 vertices, 21 edges)")
public class HeawoodGraph implements GraphGeneratorExtension, Parametrizable,
    SimpleGeneratorInterface {

    private static final int NUM_VERTICES = 14;

    // 14-cycle edges + LCF [5,-5]^7 chords
    private static final int[][] EDGE_LIST = {
        // 14-cycle
        {0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 5}, {5, 6}, {6, 7},
        {7, 8}, {8, 9}, {9, 10}, {10, 11}, {11, 12}, {12, 13}, {13, 0},
        // LCF chords: vertex i to (i+5) mod 14 for even i, (i-5+14) mod 14 for odd i
        {0, 5}, {1, 10}, {2, 7}, {3, 12}, {4, 9}, {6, 11}, {8, 13}
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
        return "Heawood Graph";
    }

    @Override
    public String getDescription() {
        return "Heawood graph: 14 vertices, 21 edges. "
            + "3-regular bipartite graph, smallest 6-cage.";
    }

    @Override
    public String getCategory() {
        return "Benchmark Graphs";
    }
}
