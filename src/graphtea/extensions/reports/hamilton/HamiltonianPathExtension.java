// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.hamilton;


import graphtea.extensions.Utils;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.SubGraph;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

/**
 * @author Ali Rostami
 */

@CommandAttitude(name = "hamiltonianـpath", abbreviation = "_hp")
public class HamiltonianPathExtension implements GraphReportExtension {
    public String getName() {
        return "Hamiltonian Path";
    }

    public String getDescription() {
        return "Hamiltonian Path";
    }

    public Object calculate(GraphModel g) {
        SubGraph sg = new SubGraph();

        HamiltonianCycle hc = new HamiltonianCycle();
        double[][] adj = g.getAdjacencyMatrix().getArray();
        int[][] adjMatrix = Utils.getBinaryPattern(adj,g.getVerticesCount());
        int[] path = hc.HamiltonCycle(adjMatrix);

        if(path == null) return sg;

        for (int aPath : path) {
            sg.vertices.add(g.getVertex(aPath));
        }

        for(int i=0 ;i<path.length-1;i++) {
            sg.edges.add(g.getEdge(g.getVertex(path[i]),
                    g.getVertex(path[i + 1])));
        }

        return sg;
    }

	@Override
	public String getCategory() {
		return "Hamilton";
	}
}
