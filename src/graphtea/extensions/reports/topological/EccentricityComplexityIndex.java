// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.topological;

import graphtea.extensions.algorithms.shortestpath.algs.FloydWarshall;
import graphtea.graph.graph.GraphModel;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Ali Rostami
 */

@CommandAttitude(name = "eccentricitycomplexity_index", abbreviation = "_Ecomplexindex")
public class EccentricityComplexityIndex implements GraphReportExtension<Integer> {
    public String getName() {
        return "E-complex Index";
    }

    public String getDescription() {
        return "E-complex Index";
    }

    public Integer calculate(GraphModel g) {
        FloydWarshall fw = new FloydWarshall();
        int[][] spt = fw.getAllPairsShortestPathWithoutWeight(g);
    	int vEccentricity;
    	Set<Integer> uniqueEccentricities = new HashSet<>();

        for (int v = 0; v < g.numOfVertices(); v++) {
        	vEccentricity = 0;
            for (int u = 0; u < g.numOfVertices(); u++) {
            	if (v == u) {
            		continue;
            	}
                if(spt[v][u] < g.numOfVertices() + 1) {
                	int dist = spt[u][v];
                    if(dist > vEccentricity) {
                    	vEccentricity = dist;                    	
                    }
                }
            }
            
            if (vEccentricity > 0) {
            	uniqueEccentricities.add(vEccentricity);
            }
        }

        return uniqueEccentricities.size();
    }

	@Override
	public String getCategory() {
		return "Topological Indices-Distance";
	}
}
