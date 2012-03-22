// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.extensions.reports.basicreports;

import graphlab.platform.lang.CommandAttitude;
import graphlab.plugins.main.GraphData;
import graphlab.plugins.reports.extension.GraphReportExtension;

/**
 * @author azin azadi
 */

@CommandAttitude(name = "num_of_vertices", abbreviation = "_vsize")
public class NumOfVertices implements GraphReportExtension {
    public String getName() {
        return "Number of Vertices";
    }

    public String getDescription() {
        return "Number of vertices in the Graph";
    }

    public Object calculate(GraphData gd) {
        return gd.getGraph().getVerticesCount();
    }

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "Property";
	}
}