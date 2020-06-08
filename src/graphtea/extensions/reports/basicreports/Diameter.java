// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.basicreports;

import graphtea.extensions.algorithms.shortestpath.algs.FloydWarshall;
import graphtea.graph.graph.GraphModel;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

/**
 * @author Mohammad Ali Rostami
 */

@CommandAttitude(name = "graph_diameter", abbreviation = "_gd")
public class Diameter implements GraphReportExtension<Integer> {

    public Integer calculate(GraphModel g) {
        FloydWarshall spt = new FloydWarshall();
        int[][] dist = spt.getAllPairsShortestPathWithoutWeight(g);
        double max = 0;
        for (int v = 0; v < g.numOfVertices(); v++) {
            for (int u = 0; u < g.numOfVertices(); u++) {
                if(dist[v][u] < g.numOfVertices()) {
                    double distance = dist[u][v];
                    if(distance > max) {
                        max = distance;
                    }
                }
            }
        }
        return (int)max;
    }

    public String getName() {
        return "Graph Diameter";
    }

    public String getDescription() {
        return "Graph Diameter";
    }


	@Override
	public String getCategory() {
		return "General";
	}
}
