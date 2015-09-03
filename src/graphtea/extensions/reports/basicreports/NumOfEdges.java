// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.basicreports;

import graphtea.graph.graph.GraphModel;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

/**
 * @author azin azadi

 */


@CommandAttitude(name = "num_of_edges", abbreviation = "_esize")
public class NumOfEdges implements GraphReportExtension<Integer> {
    public String getName() {
        return "Number of Edges";
    }

    public String getDescription() {
        return "Number of edges in the Graph";
    }

    public Integer calculate(GraphModel g) {
        return g.getEdgesCount();
    }

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "General";
	}
}
