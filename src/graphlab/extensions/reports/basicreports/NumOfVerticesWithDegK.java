// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.extensions.reports.basicreports;

import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.Vertex;
import graphlab.platform.parameter.Parameter;
import graphlab.platform.parameter.Parametrizable;
import graphlab.plugins.main.GraphData;
import graphlab.plugins.reports.extension.GraphReportExtension;

/**
 * @author azin azadi
 */

public class NumOfVerticesWithDegK implements GraphReportExtension, Parametrizable {
    @Parameter(name = "K")
    public static Integer k = 1;

    public String getName() {
        return "Number of Vertices with Deg k";
    }

    public String getDescription() {
        return "Number of vertices in the Graph which degrees are k";
    }

    public Object calculate(GraphData gd) {
        int ret = 0;

        GraphModel g = gd.getGraph();
        int t = (g.isDirected() ? 1 : 2);
        for (Vertex v : g) {
            if ((g.getInDegree(v) + g.getOutDegree(v)) / t == k)
                ret++;
        }
        return ret;
    }

    public String checkParameters() {
        return (k < 0 ? "K must be positive" : null);
    }

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "Property";
	}
}
