// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.zagreb;

import graphtea.extensions.algs4.AdjMatrixEdgeWeightedDigraph;
import graphtea.extensions.algs4.DirectedEdge;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

/**
 * @author azin azadi

 */


@CommandAttitude(name = "wiener_index", abbreviation = "_windex")
public class WienerIndex implements GraphReportExtension<Object> {
    public String getName() {
        return "Wiener Index";
    }

    public String getDescription() {
        return "Wiener Index";
    }

    public Object calculate(GraphModel g) {
        int sum =0;
        AdjMatrixEdgeWeightedDigraph G = new AdjMatrixEdgeWeightedDigraph(g.numOfVertices());
        for(Edge e : g.edges()) {
            G.addEdge(new DirectedEdge(e.source.getId(), e.target.getId(), 1d));
            G.addEdge(new DirectedEdge(e.target.getId(), e.source.getId(), 1d));
        }
        graphtea.extensions.algs4.FloydWarshall spt = new graphtea.extensions.algs4.FloydWarshall(G);
        double max = 0;
        for (int v = 0; v < G.V(); v++) {
            for (int u = v+1; u < G.V(); u++) {
                if(spt.hasPath(v,u)) {
                    double dist = spt.dist(u,v);
                    if(dist > max) {
                        sum += dist;
                    }
                }
            }
        }
        return sum;
    }

	@Override
	public String getCategory() {
		return "Topological Indices";
	}
}
