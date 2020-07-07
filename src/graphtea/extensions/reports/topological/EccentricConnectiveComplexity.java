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

import java.util.HashSet;
import java.util.Set;

/**
 * @author azin azadi

 */


@CommandAttitude(name = "eccentric_connective_complexity", abbreviation = "_ECComplexity")
public class EccentricConnectiveComplexity implements GraphReportExtension<Integer> {
    public String getName() {
        return "Eccentirc Connective Complexity";
    }

    public String getDescription() {
        return "Eccentirc Connective Complexity";
    }

    public Integer calculate(GraphModel g) {
        FloydWarshall fw = new FloydWarshall();
        int[][] dist = fw.getAllPairsShortestPathWithoutWeight(g);
        Set<Double> uniqueEccentricities = new HashSet<>();
        for (Vertex v : g) {
            uniqueEccentricities.add(g.getDegree(v) * 1.0 * Eccentricity.eccentricity(g, v, dist));
        }
        return uniqueEccentricities.size();
    }

    @Override
    public String getCategory() {
        return "Topological Indices-Distance";
    }
}