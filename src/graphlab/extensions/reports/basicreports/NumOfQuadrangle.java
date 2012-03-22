// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.extensions.reports.basicreports;

import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.VertexModel;
import graphlab.platform.lang.CommandAttitude;
import graphlab.plugins.main.GraphData;
import graphlab.plugins.main.core.AlgorithmUtils;
import graphlab.plugins.reports.extension.GraphReportExtension;

/**
 * @author Mohammad Ali Rostami
 */

@CommandAttitude(name = "num_of_quadrangle", abbreviation = "_noqa")
public class NumOfQuadrangle implements GraphReportExtension {
    public String getName() {
        return "number of quadrangle";
    }

    public String getDescription() {
        return "number of quadrangle";
    }

    public Object calculate(GraphData gd) {
        GraphModel graph = gd.getGraph();
        return getNumOfQuadrangles(graph);
    }

    /**
     * @return number of quadrangles in the given graph
     */
    public static int getNumOfQuadrangles(GraphModel graph) {
        int quadrangles = 0;
        for (VertexModel i : graph)
            for (VertexModel j : AlgorithmUtils.getNeighbors(graph, i))
                for (VertexModel k : AlgorithmUtils.getNeighbors(graph, j))
                    for (VertexModel l : AlgorithmUtils.getNeighbors(graph, k))
                        if (l != j &&
                                l != i &&
                                k != i &&
                                graph.isEdge(i, l)) quadrangles++;

        return quadrangles / 8;
    }

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "Property";
	}
}
