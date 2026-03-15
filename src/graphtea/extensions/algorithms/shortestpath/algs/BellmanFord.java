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

        Integer[] dist;
        dist = new Integer[graph.getVerticesCount()];
        List<Vertex> ret = new ArrayList<>();


        Arrays.fill(dist, Integer.MAX_VALUE);

        dist[Vertex.getId()] = 0;

        int i;

        for (i = 1; i < graph.getVerticesCount(); i++) {
            for (Edge edge : graph.getEdges()) {
                if (dist[edge.source.getId()] > dist[edge.target.getId()] + edge.getWeight()) {
                    dist[edge.source.getId()] = dist[edge.target.getId()] + edge.getWeight();
                    ret.add(edge.source.getId(), edge.target);
                }
            }
        }

        for (Edge edge : graph.getEdges()) {
            if (dist[edge.source.getId()] > dist[edge.target.getId()] + edge.getWeight())
                return null;
        }
        return ret;
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
