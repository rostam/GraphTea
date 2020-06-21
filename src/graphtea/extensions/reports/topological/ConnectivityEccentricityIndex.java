// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.topological;

import graphtea.extensions.algorithms.shortestpath.algs.FloydWarshall;
import graphtea.graph.graph.GraphModel;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

/**
 * @author azin azadi

 */


@CommandAttitude(name = "connectivityeccentricity_index", abbreviation = "_CEindex")
public class ConnectivityEccentricityIndex implements GraphReportExtension<Double> {
    public String getName() {
        return "CE Index";
    }

    public String getDescription() {
        return "CE Index";
    }

    public Double calculate(GraphModel g) {
        FloydWarshall fw = new FloydWarshall();
        int[][] spt = fw.getAllPairsShortestPathWithoutWeight(g);
    	double maxVUDistance;
    	double sum = 0;

        for (int v = 0; v < g.numOfVertices(); v++) {
        	maxVUDistance = 0;
            for (int u = 0; u < g.numOfVertices(); u++) {
            	if (v == u) {
            		continue;
            	}
                if(spt[v][u] < g.numOfVertices()+1) {
                	double dist = spt[u][v];
                    if(dist > maxVUDistance) {
                    	maxVUDistance = dist;                    	
                    }
                }
            }
            
            if (maxVUDistance > 0) {
            	sum += g.getDegree(g.getVertex(v)) / maxVUDistance;
            }
        }

        return sum;
    }

	@Override
	public String getCategory() {
		return "Topological Indices";
	}
}
