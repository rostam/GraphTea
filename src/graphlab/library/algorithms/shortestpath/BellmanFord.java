// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphlab.library.algorithms.shortestpath;

import graphlab.library.BaseEdge;
import graphlab.library.BaseGraph;
import graphlab.library.BaseVertex;
import graphlab.library.algorithms.Algorithm;
import graphlab.library.algorithms.AutomatedAlgorithm;
import graphlab.library.event.GraphRequest;
import graphlab.library.event.VertexRequest;

import java.util.Iterator;
import java.util.Vector;

/**
 * This method finds the shortest path from a source vertex v, to all
 * vertices of the graph.
 * Unlike dijkstra, this method will works properly, for
 * graphs with negative edges, as well as graphs with non negative edge's
 * weights.
 *
 * @author Soroush Sabet
 */
public class BellmanFord<VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>> extends Algorithm implements AutomatedAlgorithm {
    private BaseGraph<VertexType, EdgeType> graph;

    /**
     * A graph with a negative cycle is not well defined
     * as the input of a shortest path algorithm. Bellman-Ford
     * algorithms checks for this inconvenienc, along with
     * solving the single source shortest path algorithm.
     *
     * @param graph  with arbitrary edge weights.
     * @param Vertex as the source.
     * @return null if there be a negative cycle in the graph;
     *         and the vector of predecessors, otherwise.
     */

    public Vector<VertexType> computePaths
            (final BaseGraph<VertexType, EdgeType> graph, VertexType Vertex) {

//        graph.checkVertex(Vertex);

        Integer[] dist;
        dist = new Integer[graph.getVerticesCount()];
        Vector<VertexType> ret = new Vector<VertexType>();

        for (int i = 0; i < dist.length; i++)
            dist[i] = Integer.MAX_VALUE;

        dist[Vertex.getId()] = 0;

        EdgeType edge;
        int i;
        Iterator<EdgeType> iet;

        for (i = 1; i < graph.getVerticesCount(); i++) {
            iet = graph.edgeIterator();
            while (iet.hasNext()) {
                edge = iet.next();
                if (dist[edge.source.getId()] > dist[edge.target.getId()] + edge.getWeight()) {
                    dist[edge.source.getId()] = dist[edge.target.getId()] + edge.getWeight();
                    ret.add(edge.source.getId(), edge.target);
                }
            }
        }

        iet = graph.edgeIterator();
        while (iet.hasNext()) {
            edge = iet.next();
            if (dist[edge.source.getId()] > dist[edge.target.getId()] + edge.getWeight())
                return null;
        }
        return ret;
    }

    public void doAlgorithm() {
        GraphRequest<VertexType, EdgeType> gr = new GraphRequest<VertexType, EdgeType>();
        dispatchEvent(gr);
        this.graph = gr.getGraph();
        VertexRequest<VertexType, EdgeType> vr = new VertexRequest<VertexType, EdgeType>(graph, "Please choose a vertex for the BellmanFord algorithm.");
        dispatchEvent(vr);
        Vector<VertexType> vv = this.computePaths(graph, vr.getVertex());
        for (VertexType v : vv)
            v.setColor(v.getColor() + 1);
        //how to show the results??
    }
}
