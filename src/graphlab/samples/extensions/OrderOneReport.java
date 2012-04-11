// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.samples.extensions;

import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.Vertex;
import graphlab.plugins.main.GraphData;
import graphlab.plugins.reports.extension.GraphReportExtension;

public class OrderOneReport implements GraphReportExtension {
    public String getName() {
        return "order one";
    }

    public String getDescription() {
        return "Number of vertices with degree 1";
    }

    public Object calculate(GraphData gd) {
        int ret = 0;
        GraphModel graph = gd.getGraph();
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