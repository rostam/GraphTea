// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.basicreports;

import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.reports.extension.GraphReportExtension;

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
		return "General";
	}
}