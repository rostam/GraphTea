// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.generators;

import graphtea.platform.lang.CommandAttitude;

/**
 * Generates the Frucht graph: 12 vertices, 18 edges.
 * The Frucht graph is 3-regular and is the smallest graph
 * that has a trivial automorphism group (no symmetry).
 */
@CommandAttitude(name = "generate_frucht", abbreviation = "_g_frucht",
    description = "Generates the Frucht graph (12 vertices, 18 edges)")
public class FruchtGraph extends AbstractFixedGraphGenerator {

    private static final int NUM_VERTICES = 12;

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
