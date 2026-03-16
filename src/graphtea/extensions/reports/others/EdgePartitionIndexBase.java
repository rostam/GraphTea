// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.others;

import graphtea.extensions.algorithms.shortestpath.algs.FloydWarshall;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;

/**
 * Shared computation for edge-partition topological indices (Szeged, PI, Mostar, …).
 *
 * For each edge (u,v) the Floyd-Warshall distance matrix is used to partition
 * vertices into three sets:
 * <ul>
 *   <li>nu — vertices strictly closer to u</li>
 *   <li>nv — vertices strictly closer to v</li>
 *   <li>nequal — vertices equidistant from u and v</li>
 * </ul>
 * Subclasses supply the per-edge contribution formula via {@link Contribution}.
 */
class EdgePartitionIndexBase {

    @FunctionalInterface
    interface Contribution {
        double of(GraphModel g, Edge e, int nu, int nv, int nequal);
    }

    static double sum(GraphModel g, Contribution fn) {
        int[][] dists = new FloydWarshall().getAllPairsShortestPathWithoutWeight(g);
        double total = 0;
        for (Edge e : g.getEdges()) {
            int u = e.source.getId();
            int v = e.target.getId();
            int nu = 0;
            int nv = 0;
            int nequal = 0;
            for (int i = 0; i < dists[0].length; i++) {
                if (dists[u][i] > dists[v][i]) {
                    nu++;
                } else if (dists[u][i] < dists[v][i]) {
                    nv++;
                } else {
                    nequal++;
                }
            }
            total += fn.of(g, e, nu, nv, nequal);
        }
        return total;
    }
}
