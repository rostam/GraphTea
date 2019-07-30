// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.zagreb;

import graphtea.extensions.algs4.AdjMatrixEdgeWeightedDigraph;
import graphtea.extensions.algs4.DirectedEdge;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.Iterator;

/**
 * @author Ali Rostami

 */


@CommandAttitude(name = "mostar_index", abbreviation = "_windex")
public class MostarIndex implements GraphReportExtension<Object> {
    public String getName() {
        return "Mostar Index";
    }

    public String getDescription() {
        return "Mostar Index";
    }

    public Integer[][] getAllPairsShortestPathWithoutWeight(final GraphModel g) {
        final Integer dist[][] = new Integer[g.numOfVertices()][g.numOfVertices()];
        Iterator<Edge> iet = g.edgeIterator();
        for (int i = 0; i < g.getVerticesCount(); i++)
            for (int j = 0; j < g.getVerticesCount(); j++)
                dist[i][j] = g.numOfVertices();

        for (Vertex v : g)
            dist[v.getId()][v.getId()] = 0;

        while (iet.hasNext()) {
            Edge edge = iet.next();
            dist[edge.target.getId()][edge.source.getId()] = 1;
            dist[edge.source.getId()][edge.target.getId()] = 1;
        }

        for (Vertex v : g)
            for (Vertex u : g)
                for (Vertex w : g) {
                    if ((dist[v.getId()][w.getId()] + dist[w.getId()][u.getId()]) < dist[v.getId()][u.getId()])
                        dist[v.getId()][u.getId()] = dist[v.getId()][w.getId()] + dist[w.getId()][u.getId()];
                }

        return dist;
    }

    public Object calculate(GraphModel g) {
        Integer[][] dists = getAllPairsShortestPathWithoutWeight(g);
        int sum = 0;
        for(Edge e : g.getEdges()) {
            int u = e.source.getId();
            int v = e.target.getId();
            int nu = 0, nv = 0;
            for(int i=0;i<dists[0].length;i++) {
                if(dists[u][i] > dists[v][i]) nu++;
                if(dists[u][i] < dists[v][i]) nv++;
            }
            sum += Math.abs(nu - nv);
        }
        return sum;
    }

	@Override
	public String getCategory() {
		return "Topological Indices";
	}
}
