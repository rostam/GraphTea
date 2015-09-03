// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.basicreports;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.main.core.AlgorithmUtils;
import graphtea.plugins.reports.extension.GraphReportExtension;

/**
 * @author Mohammad Ali Rostami
 */

@CommandAttitude(name = "num_of_triangles", abbreviation = "_notri")
public class NumOfTriangles implements GraphReportExtension {
    public Object calculate(GraphModel g) {
        return getNumOfTriangles(g);
    }

    /**
     * @return the number of triangles in the given graph
     */
    public static int getNumOfTriangles(GraphModel graph) {
        int cc = 0;
        for (Vertex i : graph) {
            for (Vertex j : AlgorithmUtils.getNeighbors(graph, i))
                for (Vertex k : AlgorithmUtils.getNeighbors(graph, j)) {
                    if (k.getId() != i.getId() && graph.isEdge(k, i))
                        cc++;
                }

        }
        return cc / 6;
    }

    public String getName() {
        return "Number Of Triangles";
    }

    public String getDescription() {
        return "Number Of Triangles";
    }

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "General";
	}
}
