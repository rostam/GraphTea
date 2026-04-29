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
 * Generates the Pappus graph: 18 vertices, 27 edges.
 * The Pappus graph is 3-regular and bipartite.
 * It is the unique (3,6)-bipartite cage (smallest 3-regular bipartite graph of girth 6).
 *
 * Construction: LCF notation [5,7,-7,7,-7,-5]^3 on an 18-cycle.
 */
@CommandAttitude(name = "generate_pappus", abbreviation = "_g_pappus",
    description = "Generates the Pappus graph (18 vertices, 27 edges)")
public class PappusGraph implements GraphGeneratorExtension, Parametrizable,
    SimpleGeneratorInterface {

    private static final int NUM_VERTICES = 18;

    // 18-cycle edges + LCF [5,7,-7,7,-7,-5]^3 chords
    // Chord at position i: offset = LCF_SEQ[i % 6], edge from i to (i + offset + 18) % 18
    // Unique chords (deduplicated): (0,5),(1,8),(2,13),(3,10),(4,15),(6,11),(7,14),(9,16),(12,17)
    private static final int[][] EDGE_LIST = {
        // 18-cycle
        {0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 5}, {5, 6}, {6, 7}, {7, 8}, {8, 9},
        {9, 10}, {10, 11}, {11, 12}, {12, 13}, {13, 14}, {14, 15}, {15, 16}, {16, 17}, {17, 0},
        // LCF chords
        {0, 5}, {1, 8}, {2, 13}, {3, 10}, {4, 15}, {6, 11}, {7, 14}, {9, 16}, {12, 17}
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
        return "Pappus Graph";
    }

    @Override
    public String getDescription() {
        return "Pappus graph: 18 vertices, 27 edges. "
            + "3-regular bipartite graph of girth 6.";
    }

    @Override
    public String getCategory() {
        return "Benchmark Graphs";
    }
}
