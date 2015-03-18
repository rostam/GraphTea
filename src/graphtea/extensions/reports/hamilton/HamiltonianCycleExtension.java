// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.hamilton;


import graphtea.graph.graph.SubGraph;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.reports.extension.GraphReportExtension;

/**
 * @author Ali Rostami
 */

@CommandAttitude(name = "hamiltonianـcycle", abbreviation = "_hc")
public class HamiltonianCycleExtension implements GraphReportExtension {
    public String getName() {
        return "Hamiltonian Cycle";
    }

    public String getDescription() {
        return "Hamiltonian Cycle";
    }

    public Object calculate(GraphData gd) {
        SubGraph sg = new SubGraph();

        HamiltonianCycle hc = new HamiltonianCycle();
        double[][] adj = gd.getGraph().getAdjacencyMatrix().getArray();
        int[][] adjMatrix = new int[gd.getGraph().getVerticesCount()]
                [gd.getGraph().getVerticesCount()];

        for(int i=0;i<gd.getGraph().getVerticesCount();i++) {
            for(int j=0;j<gd.getGraph().getVerticesCount();j++) {
                if(adj[i][j] == 0) adjMatrix[i][j]=0;
                else adjMatrix[i][j]=1;
            }
        }
        int[] path = hc.HamiltonCycle(adjMatrix);

        if(path == null) return sg;

        for(int i=0 ;i<path.length;i++) {
            sg.vertices.add(gd.getGraph().getVertex(path[i]));
        }

        for(int i=0 ;i<path.length-1;i++) {
            sg.edges.add(gd.getGraph().getEdge(gd.getGraph().getVertex(path[i]),
                    gd.getGraph().getVertex(path[i + 1])));
        }

        sg.edges.add(gd.getGraph().getEdge(gd.getGraph().getVertex(path[0]),
                gd.getGraph().getVertex(path[path.length-1])));

        return sg;
    }

	@Override
	public String getCategory() {
		return "Hamilton";
	}
}
