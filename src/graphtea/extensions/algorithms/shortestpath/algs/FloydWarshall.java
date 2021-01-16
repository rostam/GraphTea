// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.extensions.algorithms.shortestpath.algs;


import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

/**
 * This method finds the shortest paths between any two vertices of
 * a graph.
 *
 * @author Soroush Sabet, edited by Omid Aladini, Ali Rostami
 */
public class FloydWarshall {
    /**
     * @param graph The given graph
     * @return All shortest paths
     */
    public int[][] getAllPairsShortestPathWithoutWeight(final GraphModel graph) {
        final int[][] dist = new int[graph.getVerticesCount()][graph.getVerticesCount()];
        for (Vertex u : graph) {
            for (Vertex v : graph) {
                if (v.getId() == u.getId())
                    dist[v.getId()][v.getId()] = 0;
                else if (graph.isEdge(v, u)) {
                    dist[v.getId()][u.getId()] = 1;
                    dist[u.getId()][v.getId()] = 1;
                } else {
                    dist[v.getId()][u.getId()] = graph.numOfVertices() * 2;
                    dist[u.getId()][v.getId()] = graph.numOfVertices() * 2;
                }
            }
        }

        for (Vertex w : graph) {
            for (Vertex u : graph) {
                for (Vertex v : graph) {
                    if ((dist[v.getId()][w.getId()] + dist[w.getId()][u.getId()]) < dist[v.getId()][u.getId()])
                        dist[v.getId()][u.getId()] = dist[v.getId()][w.getId()] + dist[w.getId()][u.getId()];
                }
            }
        }

        return dist;
    }
}
