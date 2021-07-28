// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.topological;

import graphtea.extensions.algorithms.shortestpath.algs.FloydWarshall;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.Vector;

/**
 * @author Ali Rostami

 */


@CommandAttitude(name = "largest_sum_distance", abbreviation = "_largestsumdistance")
public class LD implements GraphReportExtension<Integer> {
    public String getName() {
        return "LD";
    }

    public String getDescription() {
        return "LD";
    }

    public int eccL_T(Vertex v, int[][] dist, Vector<Vertex> L_T) {
        int max = 0;
        for(Vertex u : L_T) {
            if(u != v) {
                if (max < dist[u.getId()][v.getId()]) {
                    max = dist[u.getId()][v.getId()];
                }
            }
        }
        return max;
    }

    public int uni_T(Vertex v, int[][] dist, Vector<Vertex> L_T) {
        int min = 1000000;
        for (Vertex u : L_T) {
            if (min > dist[u.getId()][v.getId()]) {
                min = dist[u.getId()][v.getId()];
            }
        }
        return min;
    }

    public int delta_T(Vertex v, int[][] dist, Vector<Vertex> L_T) {
        return eccL_T(v,dist,L_T) - uni_T(v, dist, L_T);
    }

    /**
     * In chemical graph theory, the Wiener index (also Wiener number)
     * introduced by Harry Wiener, is a topological index of a molecule,
     * defined as the sum of the lengths of the shortest paths between all pairs of vertices
     * in the chemical graph representing the non-hydrogen atoms in the molecule.
     *
     * @param g the given graph
     * @return the largest_sum_distance
     */
    public Integer calculate(GraphModel g) {
        int sum =0;
		int max1 =0;
        FloydWarshall fw = new FloydWarshall();
        int[][] dist = fw.getAllPairsShortestPathWithoutWeight(g);
        //All leaves
        Vector<Vertex> L_T = new Vector<>();
        for (int v = 0; v < g.numOfVertices(); v++) {
            Vertex temp = g.getVertex(v);
            if(g.getDegree(temp) == 1) {
                L_T.add(temp);
            }
        }

        int delta_T = eccL_T();
        return max1;
    }

	@Override
	public String getCategory() {
		return "Topological Indices-Wiener Types";
	}
}
