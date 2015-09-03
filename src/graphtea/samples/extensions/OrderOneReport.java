// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.samples.extensions;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.plugins.reports.extension.GraphReportExtension;

public class OrderOneReport implements GraphReportExtension {
    public String getName() {
        return "order one";
    }

    public String getDescription() {
        return "Number of vertices with degree 1";
    }

    public Object calculate(GraphModel graph) {
        int ret = 0;
        for (Vertex v : graph) {
            if (graph.getInDegree(v) + graph.getOutDegree(v) == 1) {
                ret++;
            }
        }
        return ret;
    }

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return null;
	}
}