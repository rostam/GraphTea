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
@CommandAttitude(name = "mostar_index", abbreviation = "_windex")
public class MostarIndex implements GraphReportExtension<Integer> {
    public String getName() { return "Mostar Index"; }
    public String getDescription() { return "Mostar Index"; }
    public String getCategory() { return "Topological Indices-Degree Based"; }

    public Integer calculate(GraphModel g) {
        return (int) EdgePartitionIndexBase.sum(g, (graph, e, nu, nv, nequal) -> Math.abs(nu - nv));
    }
}
