// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.extensions.algorithms.shortestpath.algs;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.library.algorithms.AutomatedAlgorithm;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.algorithmanimator.core.GraphAlgorithm;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This method finds the shortest path from a source vertex v, to all
 * vertices of the graph.
 * Unlike dijkstra, this method will works properly, for
 * graphs with negative edges, as well as graphs with non negative edge's
 * weights.
 *
 * @author Soroush Sabet
 */
public class BellmanFord extends GraphAlgorithm implements AutomatedAlgorithm {
    private GraphModel graph;

    public BellmanFord(BlackBoard blackBoard) {
        super(blackBoard);
    }

    /**
     * A graph with a negative cycle is not well defined
     * as the input of a shortest path algorithm. Bellman-Ford
     * algorithms checks for this inconvenience, along with
     * solving the single source shortest path algorithm.
     *
     * @param graph  with arbitrary edge weights.
     * @param Vertex as the source.
     * @return null if there be a negative cycle in the graph;
     *         and the vector of predecessors, otherwise.
     */

    public List<Vertex> computePaths
            (final GraphModel graph, Vertex Vertex) {

//        graph.checkVertex(Vertex);

        int n = graph.getVerticesCount();
        Integer[] dist = new Integer[n];
        // predecessors: pred[i] = the vertex that immediately precedes vertex i
        //               on the shortest path from Vertex to i
        List<Vertex> pred = new ArrayList<>(Collections.nCopies(n, null));

        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[Vertex.getId()] = 0;

        for (int i = 1; i < n; i++) {
            for (Edge edge : graph.getEdges()) {
                // guard against Integer.MAX_VALUE + weight overflow
                if (dist[edge.source.getId()] == Integer.MAX_VALUE) continue;
                if (dist[edge.target.getId()] > dist[edge.source.getId()] + edge.getWeight()) {
                    dist[edge.target.getId()] = dist[edge.source.getId()] + edge.getWeight();
                    pred.set(edge.target.getId(), edge.source);
                }
            }
        }

        // negative-cycle detection
        for (Edge edge : graph.getEdges()) {
            if (dist[edge.source.getId()] != Integer.MAX_VALUE &&
                    dist[edge.target.getId()] > dist[edge.source.getId()] + edge.getWeight()) {
                return null;
            }
        }
        return pred;
    }

    public void doAlgorithm() {
//        GraphRequest<Vertex, Edge> gr = new GraphRequest<>();
//        dispatchEvent(gr);
//        this.graph = gr.getGraph();
//        VertexRequest<Vertex, Edge> vr = new VertexRequest<>(graph, "Please choose a vertex for the BellmanFord algorithm.");
//        dispatchEvent(vr);
//        List<Vertex> vv = this.computePaths(graph, vr.getVertex());
//        for (Vertex v : vv)
//            v.setColor(v.getColor() + 1);
//        //how to show the results??
    }
}
