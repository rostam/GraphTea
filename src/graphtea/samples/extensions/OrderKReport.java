// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.samples.extensions;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.reports.extension.GraphReportExtension;

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

    public Object calculate(GraphModel graph) {
        int ret = 0;
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