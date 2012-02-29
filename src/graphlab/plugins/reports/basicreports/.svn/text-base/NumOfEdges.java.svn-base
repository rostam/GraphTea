// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.reports.basicreports;

import graphlab.platform.lang.CommandAttitude;
import graphlab.plugins.main.GraphData;
import graphlab.plugins.reports.extension.GraphReportExtension;

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

    public Integer calculate(GraphData gd) {
        return gd.getGraph().getEdgesCount();
    }

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "Property";
	}
}
