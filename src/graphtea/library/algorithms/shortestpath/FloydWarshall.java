// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.library.algorithms.shortestpath;


import graphtea.library.BaseEdge;
import graphtea.library.BaseGraph;
import graphtea.library.BaseVertex;

import java.util.Iterator;

/**
 * This method finds the shortest paths between any two vertices of
 * a graph.
 *
 * @author Soroush Sabet, edited by Omid Aladini
 */
public class FloydWarshall<VertexType extends BaseVertex,
        EdgeType extends BaseEdge<VertexType>> {
    /**
     * @param graph
     * @return
     */
    public Integer[][] getAllPairsShortestPath(final BaseGraph<VertexType, EdgeType> graph) {

        final Integer dist[][] = new Integer[graph.getVerticesCount()][graph.getVerticesCount()];
        Iterator<EdgeType> iet = graph.edgeIterator();
        EdgeType edge;
        for (Integer i : dist[0])
            for (Integer j : dist[0]) {
                dist[i][j] = Integer.MAX_VALUE;
            }

        while (iet.hasNext()) {
            edge = iet.next();
            dist[edge.target.getId()][edge.source.getId()] = edge.getWeight();
        }

        for (VertexType v : graph)
            for (VertexType u : graph)
                for (VertexType w : graph) {
                    if ((dist[v.getId()][w.getId()] + dist[w.getId()][u.getId()]) < dist[v.getId()][u.getId()])
                        dist[v.getId()][u.getId()] = dist[v.getId()][w.getId()] + dist[w.getId()][u.getId()];
                }

        return dist;

    }

}
