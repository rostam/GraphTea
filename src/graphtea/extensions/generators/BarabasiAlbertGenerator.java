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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Generates a Barabasi-Albert (BA) preferential attachment graph.
 *
 * The BA model produces scale-free networks whose degree distribution follows
 * a power law, matching many real-world networks (the Internet, citation graphs,
 * biological networks).
 *
 * Algorithm:
 *   1. Seed with a complete graph on m vertices.
 *   2. Add new vertices one at a time up to n total.
 *   3. Each new vertex attaches m edges to existing vertices chosen with
 *      probability proportional to their current degree (preferential attachment).
 *
 * Reference: Barabasi and Albert, Science 286:509-512, 1999.
 */
@CommandAttitude(name = "generate_ba", abbreviation = "_g_ba",
    description = "Generates a Barabasi-Albert scale-free graph")
public class BarabasiAlbertGenerator implements GraphGeneratorExtension, Parametrizable,
    SimpleGeneratorInterface {

    @Parameter(name = "n", description = "Total number of vertices")
    public static Integer n = 30;

    @Parameter(name = "m", description = "Edges added per new vertex (seed clique size)")
    public static Integer m = 2;

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
        List<int[]> edges = new ArrayList<>();
        // degree[i] tracks current degree of vertex i for preferential attachment
        int[] degree = new int[n];

        // Seed: complete graph on first m vertices
        for (int i = 0; i < m; i++) {
            for (int j = i + 1; j < m; j++) {
                edges.add(new int[]{i, j});
                degree[i]++;
                degree[j]++;
            }
        }

        Random rng = new Random();

        // Grow: add vertices m..n-1
        for (int newNode = m; newNode < n; newNode++) {
            int sumDegree = 0;
            for (int k = 0; k < newNode; k++) {
                sumDegree += degree[k];
            }
            // Avoid division by zero for degenerate cases
            if (sumDegree == 0) {
                sumDegree = 1;
            }

            int edgesAdded = 0;
            boolean[] connected = new boolean[newNode];
            int attempts = 0;
            int maxAttempts = newNode * m * 10;

            while (edgesAdded < Math.min(m, newNode) && attempts < maxAttempts) {
                attempts++;
                // Roulette-wheel selection proportional to degree
                int pick = rng.nextInt(sumDegree) + 1;
                int cumulative = 0;
                int target = -1;
                for (int k = 0; k < newNode; k++) {
                    cumulative += (degree[k] == 0 ? 1 : degree[k]);
                    if (cumulative >= pick) {
                        target = k;
                        break;
                    }
                }
                if (target == -1 || connected[target]) {
                    continue;
                }
                connected[target] = true;
                edges.add(new int[]{newNode, target});
                degree[newNode]++;
                degree[target]++;
                sumDegree += 2;
                edgesAdded++;
            }
        }

        generatedEdges = edges.toArray(new int[0][]);
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
        if (n < 1) {
            return "n must be at least 1";
        }
        if (m < 1) {
            return "m must be at least 1";
        }
        if (m >= n) {
            return "m must be less than n";
        }
        return null;
    }

    @Override
    public GraphModel generateGraph() {
        return GraphGenerator.getGraph(false, this);
    }

    @Override
    public String getName() {
        return "Barabasi-Albert Graph";
    }

    @Override
    public String getDescription() {
        return "Barabasi-Albert preferential attachment model: scale-free graph "
            + "with power-law degree distribution. Parameters: n (total vertices), "
            + "m (edges per new vertex / seed clique size).";
    }

    @Override
    public String getCategory() {
        return "Benchmark Graphs";
    }
}
