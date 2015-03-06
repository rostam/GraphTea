// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.library.algorithms.shortestpath;

import graphtea.library.BaseEdge;
import graphtea.library.BaseGraph;
import graphtea.library.BaseVertex;
import graphtea.library.algorithms.Algorithm;
import graphtea.library.algorithms.AutomatedAlgorithm;
import graphtea.library.algorithms.util.EventUtils;
import graphtea.library.event.Event;
import graphtea.library.event.GraphRequest;
import graphtea.library.event.VertexEvent;
import graphtea.library.event.VertexRequest;
import graphtea.library.exceptions.InvalidVertexException;

import java.util.*;


/**
 * This method finds the shortest path from a vertex to all vertices
 * of a graph.
 * [Should be tested]
 *
 * @author Omid Aladini
 */
public class Dijkstra<VertexType extends BaseVertex,
        EdgeType extends BaseEdge<VertexType>> extends Algorithm
        implements AutomatedAlgorithm {
    /**
     * This method finds a reference array using Dijkstra algorithm
     * from which, one can find
     * the shortest paths of all vertices of a graph from an arbitrary
     * given vertex.
     *
     * @param graph  Graph object to be searched.
     * @param vertex The source of the paths.
     * @return Vector of vertices that for each i, it has a reference to
     *         the vertex, before the vertex with ID number i in the shortest path
     *         from "vertex" to i, or null if there is no such vertex.
     * @throws InvalidVertexException if the supplied vertices are invalid.
     */
    public Vector<VertexType>
    getShortestPath(final BaseGraph<VertexType, EdgeType> graph,
                    VertexType vertex)
            throws InvalidVertexException {
        graph.checkVertex(vertex);

        final Integer dist[] = new Integer[graph.getVerticesCount()];
        //the edge connected to i'th vertex
        final HashMap<VertexType, EdgeType> edges = new HashMap<VertexType, EdgeType>();
        Vector<VertexType> prev = new Vector<VertexType>();

        for (int i = 0; i < dist.length; i++)
            dist[i] = Integer.MAX_VALUE;

        dist[vertex.getId()] = 0;

        class VertexComparator implements Comparator<VertexType> {
            public int compare(VertexType o1, VertexType o2) {
                if (dist[o1.getId()] < dist[o2.getId()])
                    return -1;
                if (dist[o1.getId()] == dist[o2.getId()])
                    return 0;
                else
                    return 1;
            }
        }

        VertexComparator vComp = new VertexComparator();

        //selected vertices
        HashSet<VertexType> selectedVertices = new HashSet<VertexType>();

        PriorityQueue<VertexType> Q = new PriorityQueue<VertexType>(1, vComp);

        Q.add(vertex);
        //dispatchEvent(new VertexEvent<VertexType, EdgeType>(graph, vertex, VertexEvent.EventType.MARK));
        vertex.setMark(true);
        while (!Q.isEmpty()) {
            VertexType vMin = Q.poll();
            vMin.setMark(true);
            EdgeType edg = edges.get(vMin);
            if (edg != null)
                edg.setMark(true);
//            vMin.setColor(2);

            //EventUtils.algorithmStep(this, "");
            selectedVertices.add(vMin);
            Iterator<EdgeType> iet = graph.edgeIterator(vMin);
            while ((iet.hasNext())) {
                EdgeType edge = iet.next();
                edge.setColor((int) (Math.random() * 10));
           //EventUtils.algorithmStep(this,600);
                VertexType target = vMin == edge.source ? edge.target : edge.source;
                VertexType source = vMin;
                if (!selectedVertices.contains(target)) {
                    if (dist[target.getId()] > dist[source.getId()] + edge.getWeight()) {
                        dist[target.getId()] = dist[source.getId()] + edge.getWeight();
                        //dispatchEvent(new EdgeEvent<VertexType, EdgeType>(graph, edge, EdgeEvent.EventType.MARK));
                        //dispatchEvent(new VertexEvent<VertexType, EdgeType>(graph, target, VertexEvent.EventType.MARK));
                        edge.setMark(true);
                        edges.put(target, edge);
                        target.setMark(true);
                        target.setColor(5);
                        Q.add(target);
                        prev.add(edge.source.getId(), edge.target);
                    }
                }
            }
        }

        return prev;
    }

    public void doAlgorithm() {
        GraphRequest<VertexType, EdgeType> gr = new GraphRequest<VertexType, EdgeType>();
        dispatchEvent(gr);
        BaseGraph<VertexType, EdgeType> g = gr.getGraph();
        VertexRequest<VertexType, EdgeType> vr = new VertexRequest<VertexType, EdgeType>(g, "Please choose a vertex for the Dijkstra algorithm.");
        dispatchEvent(vr);
        VertexType v = vr.getVertex();
        System.out.println("Bah bah " + v.toString());
        getShortestPath(g, v);
        System.out.println("Bah bah 1" + v.toString());
    }
}
