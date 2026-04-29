// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.generators;

import graphtea.platform.lang.CommandAttitude;

/**
 * Generates the Grotzsch graph: 11 vertices, 20 edges.
 * The Grotzsch graph is the Mycielski graph of the 5-cycle (C5).
 * It is triangle-free with chromatic number 4, the smallest such graph.
 *
 * Construction (Mycielski of C5):
 *   - Outer 5-cycle: vertices 0-4
 *   - Inner copies (one per outer vertex): vertices 5-9
 *   - Hub vertex: 10
 *   - Edges: C5 edges, copy-edges mirroring C5 neighbourhood, hub-to-copies
 */
@CommandAttitude(name = "generate_grotzsch", abbreviation = "_g_grotzsch",
    description = "Generates the Grotzsch graph (11 vertices, 20 edges)")
public class GrotzschGraph extends AbstractFixedGraphGenerator {

    private static final int NUM_VERTICES = 11;

    // copy[i] = i+5 mirrors neighbours of vertex i in C5; hub = vertex 10
    private static final int[][] EDGE_LIST = {
        // C5 outer cycle
        {0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 0},
        // copy edges: copy[0]=5 connects to neighbours of 0 (1,4)
        {5, 1}, {5, 4},
        // copy[1]=6 connects to neighbours of 1 (0,2)
        {6, 0}, {6, 2},
        // copy[2]=7 connects to neighbours of 2 (1,3)
        {7, 1}, {7, 3},
        // copy[3]=8 connects to neighbours of 3 (2,4)
        {8, 2}, {8, 4},
        // copy[4]=9 connects to neighbours of 4 (3,0)
        {9, 3}, {9, 0},
        // hub to copies
        {10, 5}, {10, 6}, {10, 7}, {10, 8}, {10, 9}
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
        return "Grotzsch Graph";
    }

    @Override
    public String getDescription() {
        return "Grotzsch graph: 11 vertices, 20 edges. "
            + "Triangle-free with chromatic number 4 (smallest such graph).";
    }

    @Override
    public String getCategory() {
        return "Benchmark Graphs";
    }
}
