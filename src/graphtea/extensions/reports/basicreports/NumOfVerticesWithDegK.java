// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.basicreports;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.reports.extension.GraphReportExtension;

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

    public Object calculate(GraphModel g) {
        return numOfVerticesWithDegK(g,k);
    }

    public String checkParameters() {
        return (k < 0 ? "K must be positive" : null);
    }

    public static int numOfVerticesWithDegK(GraphModel g, int k) {
        int ret = 0;

        int t = (g.isDirected() ? 1 : 2);
        for (Vertex v : g) {
            if ((g.getInDegree(v) + g.getOutDegree(v)) / t == k)
                ret++;
        }
        return ret;
    }

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "General";
	}
}
