// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.extensions.algorithms.shortestpath.algs;


import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

import java.util.Arrays;
import java.util.Iterator;

/**
 * This method finds the shortest paths between any two vertices of
 * a graph.
 *
 * @author Soroush Sabet, edited by Omid Aladini
 */
public class FloydWarshall {
    /**
     * @param graph The given graph
     * @return All shortest paths
     */
    public Integer[][] getAllPairsShortestPath(final GraphModel graph) {
        final Integer[][] dist = new Integer[graph.getVerticesCount()][graph.getVerticesCount()];
        Iterator<Edge> iet = graph.edgeIterator();
        Edge edge;
        for (Integer[] integers : dist) Arrays.fill(integers, graph.numOfVertices() * 2);

        for(Vertex v:graph)
            dist[v.getId()][v.getId()] = 0;

        while (iet.hasNext()) {
            edge = iet.next();
            dist[edge.target.getId()][edge.source.getId()] = edge.getWeight();
            dist[edge.source.getId()][edge.target.getId()] = edge.getWeight();
        }

        for (Vertex w : graph)
            for (Vertex u : graph)
                for (Vertex v : graph) {
                    if ((dist[v.getId()][w.getId()] + dist[w.getId()][u.getId()]) < dist[v.getId()][u.getId()])
                        dist[v.getId()][u.getId()] = dist[v.getId()][w.getId()] + dist[w.getId()][u.getId()];
                }

        return dist;
    }


    /**
     * @param graph The given graph
     * @return All shortest paths
     */
    public Integer[][] getAllPairsShortestPathWithoutWeight(final GraphModel graph) {
        final Integer[][] dist = new Integer[graph.getVerticesCount()][graph.getVerticesCount()];
        Iterator<Edge> iet = graph.edgeIterator();
        for(int i = 0; i < graph.getVerticesCount();i++)
            for(int j = 0; j < graph.getVerticesCount();j++)
                dist[i][j] = graph.numOfVertices();

        for(Vertex v:graph)
            dist[v.getId()][v.getId()] = 0;

        while (iet.hasNext()) {
            Edge edge = iet.next();
            dist[edge.target.getId()][edge.source.getId()] = 1;
            dist[edge.source.getId()][edge.target.getId()] = 1;
        }

        for (Vertex w : graph)
            for (Vertex u : graph)
                for (Vertex v : graph) {
                    if ((dist[v.getId()][w.getId()] + dist[w.getId()][u.getId()]) < dist[v.getId()][u.getId()])
                        dist[v.getId()][u.getId()] = dist[v.getId()][w.getId()] + dist[w.getId()][u.getId()];
                }

        return dist;
    }
}
