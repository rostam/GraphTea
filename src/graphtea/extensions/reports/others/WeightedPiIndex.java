// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.others;

import graphtea.graph.graph.GraphModel;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

/**
 * @author Ali Rostami
 */
@CommandAttitude(name = "Weighted_PI_Index", abbreviation = "_Weighted PI Index")
public class WeightedPiIndex implements GraphReportExtension<Integer> {
    public String getName() { return "Weighted PI Index"; }
    public String getDescription() { return "Weighted PI Index"; }
    public String getCategory() { return "Topological Indices-Distance"; }

    public Integer calculate(GraphModel g) {
        return (int) EdgePartitionIndexBase.sum(g, (graph, e, nu, nv, nequal) -> {
            int degSum = graph.getDegree(e.source) + graph.getDegree(e.target);
            return degSum * (nu + nv);
        });
    }
}
