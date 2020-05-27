// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.extensions.algorithms.spanningtree;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.library.algorithms.Algorithm;
import graphtea.library.algorithms.AutomatedAlgorithm;
import graphtea.library.algorithms.util.EventUtils;
import graphtea.library.exceptions.InvalidGraphException;
import graphtea.library.exceptions.InvalidVertexException;
import graphtea.library.genericcloners.EdgeVertexCopier;
import graphtea.library.util.Pair;

import java.util.*;


/**
 * Implementation of Prim algorithm to find minimum spanning tree.
 * The output of this method is a new independent graph representing
 * the spanning tree.
 *
 * @author Omid Aladini
 */
public class Prim extends Algorithm implements AutomatedAlgorithm {
    /**
     * Temporary reference to the graph the algorithm is going to run on it.
     */
    final GraphModel graph;
    /**
     * Reference to a GraphConverter object which is responsible for duplication
     * of the graph elements, because graph edges and vertices are going to be
     * copied to the newly created spanning tree.
     */
    private EdgeVertexCopier<Vertex, Edge> gc;
    /**
     * Priority queue implemented as a binary heap to store edges, sorted according
     * to their weights.
     */
    private PriorityQueue<Edge> pq;

    /**
     * Compares two edges of type Edge.
     *
     * @author Omid
     */
    public static class DefaultEdgeComparator implements Comparator<Edge> {
        public int compare(Edge o1, Edge o2) {
            return Integer.compare(o1.getWeight(), o2.getWeight());
        }
    }

    /**
     * Reference to EdgeComparator object needed by the priority queue.
     */
    Comparator<Edge> ec;

    /**
     * Constructor of the Prim algorithm.
     *
     * @param graph Graph the algorithm is going to find it's minimum spanning tree.
     * @param gc    Reference to a GraphConverter object which is responsible for duplication
     *              of the graph elements, because graph edges and vertices are going to be
     *              copied to the newly created spanning tree.
     */
    public Prim(GraphModel graph,
                EdgeVertexCopier<Vertex, Edge> gc) {
        //if (gc == null || graph == null)
        //	throw new NullPointerException();

        this.graph = graph;
        this.gc = gc;
        this.ec = new DefaultEdgeComparator();
    }

    /**
     * Finds minimum spanning tree starting at vertex v. Note that if
     * your graph is not connected, the algorithm falls in an infinite loop
     * it's the caller's task to check connectivity.
     *
     * @param v Start vertex of Prim algorithm.
     * @return The spanning tree graph.
     * @throws InvalidGraphException if the supplied vertex is invalid.
     */
    public Pair<Vector<Vertex>, Vector<Edge>>
    findMinimumSpanningTree(Vertex v, Comparator<Edge> comparator)
            throws InvalidGraphException, InvalidVertexException {
        if (comparator == null)
            throw new NullPointerException("Null comparator supplied to Prim algorithm.");
        ec = comparator;
        return findMinimumSpanningTree(v);

    }

    /**
     * Finds minimum spanning tree starting at vertex v. Note that if
     * your graph is not connected, the algorithm falls in an infinite loop
     * it's the caller's task to check connectivity. Default comparator
     * which compares weight parameter of the graph is used.
     *
     * @param v Start vertex of Prim algorithm.
     * @return The spanning tree graph.
     * @throws InvalidGraphException if the supplied vertex is invalid.
     */
    public Pair<Vector<Vertex>, Vector<Edge>>
    findMinimumSpanningTree(Vertex v)
            throws InvalidGraphException, InvalidVertexException {
        graph.checkVertex(v);
        GraphModel gCopy = graph;
//        GraphModel gCopy = graph.copy(gc);
//        gCopy = graph;

        Vector<Vertex> oVertices = new Vector<>();
        Vector<Edge> oEdges = new Vector<>();

        //dispatchEvent(new GraphEvent<Vertex,Edge>(oGraph));

        pq = new PriorityQueue<>(1, ec);

        {    //lovely block
            Iterator<Edge> edgeList = gCopy.edgeIterator();

            while (edgeList.hasNext())
                pq.add(edgeList.next());
        }

        for (Vertex searchV : gCopy)
            if (searchV.getId() == v.getId()) {
                oVertices.add(searchV);
                searchV.setMark(true);
                //dispatchEvent(new VertexEvent<Vertex, Edge>(graph, searchV, VertexEvent.EventType.MARK));
            }

        while (true) {
            Pair<Edge, Vertex> pev = getNewEdgeForSpanningTree(oVertices, oEdges);
            //Pair<Edge,Vertex> pev = getNewEdgeForSpanningTree(oGraph);

            if (pev == null) {
                break;
            } else {
                pev.second.setMark(true);
                //dispatchEvent(new VertexEvent<Vertex, Edge>(graph, pev.second, VertexEvent.EventType.MARK));
                oVertices.add(pev.second);
                oEdges.add(pev.first);
                //dispatchEvent(new EdgeEvent<Vertex, Edge>(graph, pev.first, EdgeEvent.EventType.MARK));
                pev.first.setMark(true);
                EventUtils.algorithmStep(this, "");
            }
        }

        return new Pair<>(oVertices, oEdges);
    }


    private Pair<Edge, Vertex>
    getNewEdgeForSpanningTree(Vector<Vertex> vertices, Vector<Edge> edges) {
        ArrayList<Edge> tempEdgeArray = new ArrayList<>();

        try {
            while (true) {
                Edge edge = pq.poll();
                if (edge == null)
                    return null;

                if (!vertices.contains(edge.target) &&
                        vertices.contains(edge.source))
                    return new Pair<>(edge, edge.target);

                if (!graph.isDirected() &&
                        vertices.contains(edge.target) &&
                        !vertices.contains(edge.source))
                    return new Pair<>(edge, edge.source);

                tempEdgeArray.add(edge);

            }
        } finally {
            pq.addAll(tempEdgeArray);
        }
    }

    public void doAlgorithm() {
//        BaseGraphRequest gr = new BaseGraphRequest();
//        dispatchEvent(gr);
//        BaseGraph<BaseVertex, BaseEdge<BaseVertex>> graph = gr.getGraph();
//
//        dispatchEvent(new MessageEvent("Please choose a vertex for the Prim algorithm.", true, 2000));
//
//        BaseVertexRequest vr = new BaseVertexRequest(graph);
//        dispatchEvent(vr);
//
//        Prim<BaseVertex, BaseEdge<BaseVertex>> prim = new Prim<>(graph, new BaseEdgeVertexCopier());
//        prim.acceptEventDispatcher(getDispatcher());
//
//        //BaseGraph<BaseVertex,BaseEdge<BaseVertex>> output =
//        prim.findMinimumSpanningTree(vr.getVertex());

        //dispatchEvent(new BaseGraphEvent(output));
    }
}
