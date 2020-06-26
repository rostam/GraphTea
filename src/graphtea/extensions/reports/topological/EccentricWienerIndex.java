// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.topological;

import graphtea.extensions.algorithms.shortestpath.algs.FloydWarshall;
import graphtea.extensions.reports.others.Eccentricity;
import graphtea.graph.graph.GraphModel;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

/**
 * @author Ali Rostami
 */


@CommandAttitude(name = "eccentric_wiener_index", abbreviation = "_ewindex")
public class EccentricWienerIndex implements GraphReportExtension<Integer> {
    public String getName() {
        return "Eccentric Wiener Index";
    }

    public String getDescription() {
        return "Eccentric Wiener Index";
    }

    /**
     * In chemical graph theory, the Wiener index (also Wiener number)
     * introduced by Harry Wiener, is a topological index of a molecule,
     * defined as the sum of the lengths of the shortest paths between all pairs of vertices
     * in the chemical graph representing the non-hydrogen atoms in the molecule.
     *
     * @param g the given graph
     * @return the wiener index
     */
    public Integer calculate(GraphModel g) {
        int sum =0;
        FloydWarshall fw = new FloydWarshall();
        int[][] dist = fw.getAllPairsShortestPathWithoutWeight(g);
        for (int v = 0; v < g.numOfVertices(); v++) {
            for (int u = v+1; u < g.numOfVertices(); u++) {
                double eu = Eccentricity.eccentricity(g,u,dist);
                double ev = Eccentricity.eccentricity(g,v,dist);
                double epsilon = dist[v][u] == Math.max(eu,ev) ? dist[v][u] : 0;
                sum += epsilon;
            }
        }
        return sum/2;
    }

	@Override
	public String getCategory() {
		return "Topological Indices-Wiener Types";
	}
}
