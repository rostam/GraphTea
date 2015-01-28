// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.spanningtree;


import graphtea.graph.graph.SubGraph;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.reports.extension.GraphReportExtension;

/**
 * @author Ali Rostami
 */

@CommandAttitude(name = "mst_prim", abbreviation = "_mst_p")
public class MSTPrimExtension implements GraphReportExtension {
    public String getName() {
        return "MST Prim";
    }

    public String getDescription() {
        return "MST Prim";
    }

    public Object calculate(GraphData gd) {
        SubGraph sg = new SubGraph();
        MSTPrim mp = new MSTPrim();
        double[][] adj = gd.getGraph().getAdjacencyMatrix().getArray();
        int[][] adjMatrix = new int[gd.getGraph().getVerticesCount()]
                [gd.getGraph().getVerticesCount()];

        for(int i=0;i<gd.getGraph().getVerticesCount();i++) {
            for(int j=0;j<gd.getGraph().getVerticesCount();j++) {
                if(adj[i][j] == 0) adjMatrix[i][j]=0;
                else adjMatrix[i][j]=1;
            }
        }
        int[] parent = mp.prim(adjMatrix);
        for(int i=0;i<gd.getGraph().getVerticesCount();i++) {
            if(parent[i] != -1) {
                sg.edges.add(gd.getGraph().getEdge(gd.getGraph().getVertex(i),
                        gd.getGraph().getVertex(parent[i])));
            }
        }
        return sg;
    }

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "Minimum Spanning Tree";
	}

}
