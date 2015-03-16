// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.zagreb;

import graphtea.graph.graph.Edge;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.reports.extension.GraphReportExtension;

/**
 * @author azin azadi

 */


@CommandAttitude(name = "edge_degree", abbreviation = "_edegree")
public class EdgeDegree implements GraphReportExtension<Integer> {
    public String getName() {
        return "Edge Degree";
    }

    public String getDescription() {
        return "For the edge u~v the edge degree is d(u)+d(v)-2.";
    }

    public Integer calculate(GraphData gd) {
        Edge ee = null;
        for(Edge e : gd.getGraph().getEdges()) {
            if(e.isSelected()) {
                ee = e;
                break;
            }
        }
        if(ee != null) {
            return gd.getGraph().getDegree(ee.source) +
                    gd.getGraph().getDegree(ee.target) -
                    2;
        }

        return 0;
    }

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "General";
	}
}
