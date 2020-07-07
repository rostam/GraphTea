// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.topological;

import graphtea.extensions.algorithms.shortestpath.algs.FloydWarshall;
import graphtea.extensions.reports.others.Eccentricity;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

/**
 * @author azin azadi

 */


@CommandAttitude(name = "eccentric_connective_index", abbreviation = "_ECindex")
public class EccentricConnectiveIndex implements GraphReportExtension<Double> {
    private double a;

    public String getName() {
        return "Eccnetric Connective Index";
    }

    public String getDescription() {
        return "Eccnetric Connective Index";
    }

    public void setA(double value) {
        a = value;
    }

    public Double calculate(GraphModel g) {
        FloydWarshall fw = new FloydWarshall();
        int[][] dist = fw.getAllPairsShortestPathWithoutWeight(g);
    	double sum = 0;
        for (Vertex v : g) {
        	sum += g.getDegree(v) * Eccentricity.eccentricity(g, v.getId(), dist);
        }
        return sum;
    }

	@Override
	public String getCategory() {
		return "Topological Indices-Distance";
	}
}
