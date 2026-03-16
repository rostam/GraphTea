// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.topological;

import graphtea.extensions.AlgorithmUtils;
import graphtea.graph.graph.GraphModel;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

/**
 * @author Ali Rostami
 */
@CommandAttitude(name = "totaleccentricity_index", abbreviation = "_TEindex")
public class TotalEccentricityIndex implements GraphReportExtension<Double> {
    public String getName() {
        return "Total eccentrity Index";
    }

    public String getDescription() {
        return "Total eccentrity Index";
    }

    public Double calculate(GraphModel g) {
        int[] ecc = AlgorithmUtils.getEccentricities(g);
        double sum = 0;
        for (int v = 0; v < g.numOfVertices(); v++) {
            if (ecc[v] > 0) {
                sum += g.getDegree(g.getVertex(v)) * ecc[v];
            }
        }
        return sum;
    }

    @Override
    public String getCategory() {
        return "Topological Indices-Distance";
    }
}
