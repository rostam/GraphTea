// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.generators;

import graphtea.platform.lang.CommandAttitude;

/**
 * Generates the Pappus graph: 18 vertices, 27 edges.
 * The Pappus graph is 3-regular and bipartite.
 * It is the unique (3,6)-bipartite cage (smallest 3-regular bipartite graph of girth 6).
 *
 * Construction: LCF notation [5,7,-7,7,-7,-5]^3 on an 18-cycle.
 */
@CommandAttitude(name = "generate_pappus", abbreviation = "_g_pappus",
    description = "Generates the Pappus graph (18 vertices, 27 edges)")
public class PappusGraph extends AbstractFixedGraphGenerator {

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

    @Override
    protected int numVertices() {
        return NUM_VERTICES;
    }

    @Override
    protected int[][] edgeList() {
        return EDGE_LIST;
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
