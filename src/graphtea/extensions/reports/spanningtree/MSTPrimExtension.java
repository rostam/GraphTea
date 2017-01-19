// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.spanningtree;


import graphtea.extensions.Utils;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.SubGraph;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

/**
 * @author Ali Rostami
 */

@CommandAttitude(name = "mst_prim", abbreviation = "_mst_p")
public class MSTPrimExtension implements GraphReportExtension {
    public String getName() {
        return "Prim";
    }

    public String getDescription() {
        return "Prim";
    }

    public Object calculate(GraphModel g) {
        SubGraph sg = new SubGraph();
        MSTPrim mp = new MSTPrim();
        double[][] adj = g.getAdjacencyMatrix().getArray();
        int[][] adjMatrix = Utils.getBinaryPattern(adj,g.getVerticesCount());
        int[] parent = mp.prim(adjMatrix);
        for(int i=0;i<g.getVerticesCount();i++) {
            if(parent[i] != -1) {
                sg.edges.add(g.getEdge(g.getVertex(i),
                        g.getVertex(parent[i])));
            }
        }
        return sg;
    }

	@Override
	public String getCategory() {
		return "Minimum Spanning Tree";
	}

}
