// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.samples.extensions;

import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.Vertex;
import graphlab.platform.parameter.Parameter;
import graphlab.platform.parameter.Parametrizable;
import graphlab.plugins.main.GraphData;
import graphlab.plugins.reports.extension.GraphReportExtension;

public class OrderKReport implements GraphReportExtension, Parametrizable {
    //you can set a (display) name and a description for the parameter, this is optional.
    @Parameter(name = "k", description = "the degree of desired vertices")
    public int k;


    public String checkParameters() {
        if (k <= 0) return "K must be positive";
        else
            return null;
    }

    public String getName() {
        return "order k";
    }

    public String getDescription() {
        return "Number of vertices with degree k";
    }

    public Object calculate(GraphData gd) {
        int ret = 0;
        GraphModel graph = gd.getGraph();
        for (Vertex v : graph) {
            if (graph.getInDegree(v) + graph.getOutDegree(v) == k) {
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