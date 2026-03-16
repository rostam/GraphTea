// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.topological;

import graphtea.extensions.AlgorithmUtils;
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
        int[] ecc = AlgorithmUtils.getEccentricities(g);
        Set<Integer> uniqueEccentricities = new HashSet<>();
        for (int v = 0; v < g.numOfVertices(); v++) {
            if (ecc[v] > 0) {
                uniqueEccentricities.add(ecc[v]);
            }
        }
        return uniqueEccentricities.size();
    }

    @Override
    public String getCategory() {
        return "Topological Indices-Distance";
    }
}
