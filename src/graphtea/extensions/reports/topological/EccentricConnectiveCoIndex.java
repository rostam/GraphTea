// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.topological;

import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.reports.others.Eccentricity;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

/**
 * @author azin azadi
 */
@CommandAttitude(name = "eccentric_connective_co_index", abbreviation = "_ECcoindex")
public class EccentricConnectiveCoIndex implements GraphReportExtension<Double> {

    public String getName() {
        return "Eccentric Connective Co-Index";
    }

    public String getDescription() {
        return "Eccentric Connective Co-Index";
    }

    public Double calculate(GraphModel g) {
        int[][] dist = AlgorithmUtils.getAllPairsDistances(g);
        double sum = 0;
        double n = g.getVerticesCount();
        for (Vertex v : g) {
            sum += g.getDegree(v) * (n - 1 - Eccentricity.eccentricity(g, v.getId(), dist));
        }
        return sum;
    }

    @Override
    public String getCategory() {
        return "Topological Indices-Distance";
    }
}
