// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.extensions.algorithms.shortestpath.algs;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.library.algorithms.Algorithm;
import graphtea.library.algorithms.AutomatedAlgorithm;
import graphtea.library.exceptions.InvalidVertexException;

import java.util.*;

/**
 * This method finds the shortest path from a vertex to all vertices
 * of a graph.
 * [Should be tested]
 *
 * @author Omid Aladini
 * @author Mohammad Ali Rostami
 */
public class Dijkstra extends Algorithm implements AutomatedAlgorithm {
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

    public Vector<Vertex>
    getShortestPath(final GraphModel graph,
                    Vertex vertex)
            throws InvalidVertexException {
        graph.checkVertex(vertex);

        final Integer[] dist = new Integer[graph.getVerticesCount()];
        //the edge connected to i'th vertex
        final HashMap<Vertex, Edge> edges = new HashMap<>();
        Vector<Vertex> prev = new Vector<>();

        Arrays.fill(dist, Integer.MAX_VALUE);

        dist[vertex.getId()] = 0;

        class VertexComparator implements Comparator<Vertex> {
            public int compare(Vertex o1, Vertex o2) {
                return dist[o1.getId()].compareTo(dist[o2.getId()]);
            }
        }

        VertexComparator vComp = new VertexComparator();

        //selected vertices
        HashSet<Vertex> selectedVertices = new HashSet<>();

        PriorityQueue<Vertex> Q = new PriorityQueue<>(1, vComp);

        Q.add(vertex);
        //dispatchEvent(new VertexEvent<Vertex, Edge>(graph, vertex, VertexEvent.EventType.MARK));
        vertex.setMark(true);
        while (!Q.isEmpty()) {
            Vertex vMin = Q.poll();
            vMin.setMark(true);
            Edge edg = edges.get(vMin);
            if (edg != null)
                edg.setMark(true);
//            vMin.setColor(2);

            //EventUtils.algorithmStep(this, "");
            selectedVertices.add(vMin);
            Iterator<Edge> iet = graph.edgeIterator(vMin);
            while ((iet.hasNext())) {
                Edge edge = iet.next();
                edge.setColor((int) (Math.random() * 10));
           //EventUtils.algorithmStep(this,600);
                Vertex target = vMin == edge.source ? edge.target : edge.source;
                Vertex source = vMin;
                if (!selectedVertices.contains(target)) {
                    if (dist[target.getId()] > dist[source.getId()] + edge.getWeight()) {
                        dist[target.getId()] = dist[source.getId()] + edge.getWeight();
                        //dispatchEvent(new EdgeEvent<Vertex, Edge>(graph, edge, EdgeEvent.EventType.MARK));
                        //dispatchEvent(new VertexEvent<Vertex, Edge>(graph, target, VertexEvent.EventType.MARK));
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
//        GraphRequest<Vertex, Edge> gr = new GraphRequest<>();
//        dispatchEvent(gr);
//        GraphModel g = gr.getGraph();
//        VertexRequest<Vertex, Edge> vr = new VertexRequest<>(g, "Please choose a vertex for the Dijkstra algorithm.");
//        dispatchEvent(vr);
//        Vertex v = vr.getVertex();
//        System.out.println("Bah bah " + v.toString());
//        getShortestPath(g, v);
//        System.out.println("Bah bah 1" + v.toString());
    }
}
