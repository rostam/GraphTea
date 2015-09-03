// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.hamilton;


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
        int[][] adjMatrix = new int[g.getVerticesCount()]
                [g.getVerticesCount()];

        for(int i=0;i<g.getVerticesCount();i++) {
            for(int j=0;j<g.getVerticesCount();j++) {
                if(adj[i][j] == 0) adjMatrix[i][j]=0;
                else adjMatrix[i][j]=1;
            }
        }
        int[] path = hc.HamiltonCycle(adjMatrix);

        if(path == null) return sg;

        for(int i=0 ;i<path.length;i++) {
            sg.vertices.add(g.getVertex(path[i]));
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
