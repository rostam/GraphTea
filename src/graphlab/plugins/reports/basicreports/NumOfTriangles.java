// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.reports.basicreports;

import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.VertexModel;
import graphlab.platform.lang.CommandAttitude;
import graphlab.plugins.main.GraphData;
import graphlab.plugins.main.core.AlgorithmUtils;
import graphlab.plugins.reports.extension.GraphReportExtension;

/**
 * @author Mohammad Ali Rostami
 */

@CommandAttitude(name = "num_of_triangles", abbreviation = "_notri")
public class NumOfTriangles implements GraphReportExtension {
    public Object calculate(GraphData gd) {
        GraphModel graph = gd.getGraph();
        return getNumOfTriangles(graph);
    }

    /**
     * @return the number of triangles in the given graph
     */
    public static int getNumOfTriangles(GraphModel graph) {
        int cc = 0;
        for (VertexModel i : graph) {
            for (VertexModel j : AlgorithmUtils.getNeighbors(graph, i))
                for (VertexModel k : AlgorithmUtils.getNeighbors(graph, j)) {
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
		return "Property";
	}
}
