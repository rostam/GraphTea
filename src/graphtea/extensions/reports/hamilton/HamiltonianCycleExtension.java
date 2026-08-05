// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.hamilton;

import graphtea.extensions.AlgorithmUtils;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.SubGraph;
import graphtea.plugins.reports.extension.GraphReportExtension;

/**
 * @author Ali Rostami
 */

public class HamiltonianCycleExtension implements GraphReportExtension<SubGraph> {
    public String getName() {
        return "Hamiltonian Cycle";
    }

    public SubGraph calculate(GraphModel g) {
        SubGraph sg = new SubGraph();
        HamiltonianCycle hc = new HamiltonianCycle();
        double[][] adj = g.getAdjacencyMatrix().getArray();
        int[][] adjMatrix = AlgorithmUtils.getBinaryPattern(adj,g.getVerticesCount());
        int[] path = hc.HamiltonCycle(adjMatrix);
        if(path == null) return sg;
        for (int aPath : path) {
            sg.vertices.add(g.getVertex(aPath));
        }

        // getEdge is direction sensitive and returns null for a non-adjacent pair; a null
        // collected here reaches SubGraphRenderer, which dereferences every edge.
        for (int i = 0; i < path.length - 1; i++) {
            addIfPresent(sg, AlgorithmUtils.getEdgeBetween(g,
                    g.getVertex(path[i]), g.getVertex(path[i + 1])));
        }

        // The cycle closes from the last vertex back to the first. This asked for the edge the
        // other way round, which on a directed graph is a different edge, usually absent.
        addIfPresent(sg, AlgorithmUtils.getEdgeBetween(g,
                g.getVertex(path[path.length - 1]), g.getVertex(path[0])));

        return sg;
    }

	@Override
	public String getCategory() {
		return "Hamilton";
	}

    private static void addIfPresent(SubGraph sg, Edge e) {
        if (e != null) {
            sg.edges.add(e);
        }
    }
}
