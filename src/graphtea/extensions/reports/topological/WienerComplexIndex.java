// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.topological;

import graphtea.extensions.algorithms.shortestpath.algs.FloydWarshall;
import graphtea.extensions.reports.topological.WienerIndex;
import graphtea.extensions.reports.others.Eccentricity;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import Jama.Matrix;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.HashSet;
import java.util.Set;

/**
 * @author azin azadi

 */


@CommandAttitude(name = "eccentric_connective_complexity", abbreviation = "_ECComplexity")
public class WienerComplexIndex implements GraphReportExtension<Integer> {
    public String getName() {
        return "Wiener Complexity";
    }

    public String getDescription() {
        return "Wiener Complexity";
    }

    public Integer calculate(GraphModel g) {
        FloydWarshall fw = new FloydWarshall();
        int[][] dist = fw.getAllPairsShortestPathWithoutWeight(g);
        Set<Integer> uniqueEccentricities = new HashSet<>();
		int sum =0;
        int max = 0;
Matrix adj = g.getAdjacencyMatrix();
	        for(int i=0;i < adj.getColumnDimension();i++) {
						sum=0;
            for(int j=0;j < adj.getRowDimension();j++) {
			sum += dist[i][j]; 
			adj.set(i,j,0);
                 }
			adj.set(i,i,sum); 			 		
			uniqueEccentricities.add(sum);			 
        }
			
			
		
			


        return uniqueEccentricities.size();
    }

    @Override
    public String getCategory() {
        return "Topological Indices-Distance";
    }
}