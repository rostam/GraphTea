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
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.graphgenerator.GraphGenerator;
import graphtea.plugins.graphgenerator.core.PositionGenerators;
import graphtea.plugins.graphgenerator.core.SimpleGeneratorInterface;
import graphtea.plugins.graphgenerator.core.extension.GraphGeneratorExtension;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Generates a Watts-Strogatz small-world graph.
 *
 * Small-world networks combine high clustering (like regular lattices) with short
 * average path lengths (like random graphs), matching the topology of many social,
 * biological, and technological networks.
 *
 * Algorithm:
 *   1. Start with n vertices arranged in a ring.
 *   2. Connect each vertex to its k nearest neighbours on each side (k/2 left, k/2 right).
 *   3. For each edge (u, v) with v > u, rewire v to a uniformly random vertex
 *      with probability beta (avoiding self-loops and multi-edges).
 *
 * beta=0 → regular ring lattice; beta=1 → Erdos-Renyi-like random graph.
 *
 * Reference: Watts and Strogatz, Nature 393:440-442, 1998.
 */
@CommandAttitude(name = "generate_ws", abbreviation = "_g_ws",
    description = "Generates a Watts-Strogatz small-world graph")
public class WattsStrogatzGenerator implements GraphGeneratorExtension, Parametrizable,
    SimpleGeneratorInterface {

    @Parameter(name = "n", description = "Number of vertices")
    public static Integer n = 20;

    @Parameter(name = "k", description = "Each vertex is connected to k nearest neighbours "
        + "(must be even and k < n)")
    public static Integer k = 4;

    @Parameter(name = "betaPercent",
        description = "Rewiring probability as a percentage 0-100 "
            + "(0 = regular lattice, 100 = random graph)")
    public static Integer betaPercent = 10;

    private Vertex[] v;
    private int[][] generatedEdges;

    @Override
    public Vertex[] getVertices() {
        v = new Vertex[n];
        for (int i = 0; i < n; i++) {
            v[i] = new Vertex();
        }
        buildEdges();
        return v;
    }

    private void buildEdges() {
        double beta = betaPercent / 100.0;
        Random rng = new Random();
        // Use a set of canonical pairs to prevent duplicate edges
        Set<Long> edgeSet = new HashSet<>();

        int half = k / 2;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j <= half; j++) {
                int neighbour = (i + j) % n;
                long key = edgeKey(i, neighbour);
                edgeSet.add(key);
            }
        }

        Set<Long> rewired = new HashSet<>();
        for (int i = 0; i < n; i++) {
            for (int j = 1; j <= half; j++) {
                int neighbour = (i + j) % n;
                long origKey = edgeKey(i, neighbour);
                if (!edgeSet.contains(origKey)) {
                    continue;
                }
                if (rng.nextDouble() < beta) {
                    // Pick a new random target (no self-loop, no existing edge)
                    int newTarget = rng.nextInt(n);
                    int tries = 0;
                    while ((newTarget == i || edgeSet.contains(edgeKey(i, newTarget)))
                        && tries < n * 2) {
                        newTarget = rng.nextInt(n);
                        tries++;
                    }
                    if (newTarget != i && !edgeSet.contains(edgeKey(i, newTarget))) {
                        edgeSet.remove(origKey);
                        edgeSet.add(edgeKey(i, newTarget));
                        rewired.add(edgeKey(i, newTarget));
                    }
                }
            }
        }

        generatedEdges = new int[edgeSet.size()][2];
        int idx = 0;
        for (long key : edgeSet) {
            generatedEdges[idx][0] = (int) (key >>> 32);
            generatedEdges[idx][1] = (int) (key & 0xFFFFFFFFL);
            idx++;
        }
    }

    private static long edgeKey(int u, int w) {
        int lo = Math.min(u, w);
        int hi = Math.max(u, w);
        return ((long) lo << 32) | (hi & 0xFFFFFFFFL);
    }

    @Override
    public Edge[] getEdges() {
        Edge[] ret = new Edge[generatedEdges.length];
        for (int i = 0; i < generatedEdges.length; i++) {
            ret[i] = new Edge(v[generatedEdges[i][0]], v[generatedEdges[i][1]]);
        }
        return ret;
    }

    @Override
    public GPoint[] getVertexPositions() {
        return PositionGenerators.circle(5, 5, 100000, 100000, n);
    }

    @Override
    public String checkParameters() {
        if (n < 3) {
            return "n must be at least 3";
        }
        if (k < 2 || k % 2 != 0) {
            return "k must be a positive even number";
        }
        if (k >= n) {
            return "k must be less than n";
        }
        if (betaPercent < 0 || betaPercent > 100) {
            return "betaPercent must be between 0 and 100";
        }
        return null;
    }

    @Override
    public GraphModel generateGraph() {
        return GraphGenerator.getGraph(false, this);
    }

    @Override
    public String getName() {
        return "Watts-Strogatz Graph";
    }

    @Override
    public String getDescription() {
        return "Watts-Strogatz small-world model. Parameters: n (vertices), "
            + "k (nearest-neighbour connections, must be even), "
            + "betaPercent (rewiring probability 0-100).";
    }

    @Override
    public String getCategory() {
        return "Benchmark Graphs";
    }
}
