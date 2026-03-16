// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.topological;

import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.reports.others.Eccentricity;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.plugins.reports.extension.GraphReportExtension;

/**
 * Shared template for indices of the form sum_v [ weight(v, D_v, ecc_v) ],
 * where D_v is the sum of distances from v to all other vertices.
 *
 * <p>Used by {@link EccentricDistanceSum} and {@link AdjacentEccentricDistanceSum},
 * which differ only in how they weight each vertex's contribution.
 */
abstract class EccentricDistanceSumBase implements GraphReportExtension<Double> {

    @Override
    public final Double calculate(GraphModel g) {
        int[][] dist = AlgorithmUtils.getAllPairsDistances(g);
        double sum = 0;
        for (Vertex v : g) {
            double eccV = Eccentricity.eccentricity(g, v.getId(), dist);
            int distSum = 0;
            for (Vertex u : g) {
                if (v.getId() != u.getId()) {
                    distSum += dist[v.getId()][u.getId()];
                }
            }
            sum += weight(g, v, distSum, eccV);
        }
        return sum;
    }

    /**
     * Per-vertex contribution to the index sum.
     *
     * @param g           the graph
     * @param v           the current vertex
     * @param distanceSum sum of shortest-path distances from v to all other vertices
     * @param eccentricity eccentricity of v
     * @return value to add to the running sum
     */
    protected abstract double weight(GraphModel g, Vertex v, int distanceSum, double eccentricity);

    @Override
    public String getCategory() {
        return "Topological Indices-Distance";
    }
}
