// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.basicreports;

import graphtea.extensions.reports.MaxIndependentSetReport;
import graphtea.graph.graph.GraphModel;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

/**
 * @author Mohammad Ali Rostami
 */

@CommandAttitude(name = "domination_number", abbreviation = "_dn")
public class DominationNumber implements GraphReportExtension<Integer> {

    public Integer calculate(GraphModel g) {
        return MaxIndependentSetReport.getMaxIndependentSetSize(g, false);
    }

    public String getName() {
        return "Graph Domination Number";
    }

    public String getDescription() {
        return "Graph Domination Number";
    }

    @Override
    public String getCategory() {
        return "General";
    }
}
