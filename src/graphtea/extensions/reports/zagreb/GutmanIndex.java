// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.zagreb;

import graphtea.extensions.algorithms.shortestpath.algs.FloydWarshall;
import graphtea.extensions.algs4.AdjMatrixEdgeWeightedDigraph;
import graphtea.extensions.algs4.DirectedEdge;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

/**
 * @author azin azadi

 */


@CommandAttitude(name = "gutman_index", abbreviation = "_gutmanindex")
public class GutmanIndex implements GraphReportExtension<Object> {
    public String getName() {
        return "Gutman Index";
    }

    public String getDescription() {
        return "Gutman Index";
    }

    public Object calculate(GraphModel g) {
        int sum =0;
        AdjMatrixEdgeWeightedDigraph G = new AdjMatrixEdgeWeightedDigraph(g.numOfVertices());
        for(Edge e : g.edges()) {
            G.addEdge(new DirectedEdge(e.source.getId(), e.target.getId(), 1d));
            G.addEdge(new DirectedEdge(e.target.getId(), e.source.getId(), 1d));
        }

        FloydWarshall fw = new FloydWarshall();
        Integer[][] spt = fw.getAllPairsShortestPathWithoutWeight(g);

        double max = 0;
        for (int v = 0; v < G.V(); v++) {
            for (int u = v+1; u < G.V(); u++) {
                if(spt[v][u] < g.numOfVertices() + 1) {
                    double UVdist = spt[u][v];
                    if(UVdist > max) {
                    	int degreeOfU = g.getDegree(g.getVertex(u));
                    	int degreeOfV = g.getDegree(g.getVertex(v));

                    	sum += (degreeOfU * degreeOfV) * UVdist;
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
