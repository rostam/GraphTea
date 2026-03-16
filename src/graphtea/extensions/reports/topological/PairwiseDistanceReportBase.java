// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.topological;

import graphtea.extensions.AlgorithmUtils;
import graphtea.graph.graph.GraphModel;
import graphtea.plugins.reports.extension.GraphReportExtension;

/**
 * Shared template for topological indices computed as a sum over all unordered
 * vertex pairs (u, v) connected in the graph.
 *
 * <p>Subclasses implement {@link #contribution(GraphModel, int, int, int)} to
 * supply the per-pair term. The Floyd-Warshall distance matrix is computed once
 * and passed to that method.
 *
 * <p>Eliminates the dead {@code max} variable that appeared in all eight
 * original classes: the variable was initialised to 0 and never updated, so
 * {@code UVdist > max} was always {@code true} and had no effect.
 */
abstract class PairwiseDistanceReportBase implements GraphReportExtension<Double> {

    @Override
    public final Double calculate(GraphModel g) {
        int[][] dist = AlgorithmUtils.getAllPairsDistances(g);
        int n = g.numOfVertices();
        double sum = 0;
        for (int v = 0; v < n; v++) {
            for (int u = v + 1; u < n; u++) {
                if (dist[v][u] < n + 1) {
                    sum += contribution(g, v, u, dist[v][u]);
                }
            }
        }
        return sum;
    }

    /**
     * Per-pair contribution to the index sum.
     *
     * @param g    the graph
     * @param v    index of the first vertex (v &lt; u)
     * @param u    index of the second vertex
     * @param dist shortest-path distance between v and u
     * @return the value to add to the running sum
     */
    protected abstract double contribution(GraphModel g, int v, int u, int dist);

    @Override
    public String getCategory() {
        return "Topological Indices-Distance";
    }
}
