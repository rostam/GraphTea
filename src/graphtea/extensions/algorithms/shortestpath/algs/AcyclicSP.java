// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.extensions.algorithms.shortestpath.algs;


import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.library.algorithms.AutomatedAlgorithm;
import graphtea.library.event.PreWorkEvent;
import graphtea.library.exceptions.InvalidVertexException;
import graphtea.library.genericcloners.EdgeVertexCopier;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.algorithmanimator.core.GraphAlgorithm;

import java.util.*;

/**
 * @author Soroush Sabet
 * @author M. Ali Rostami
 */
public class AcyclicSP extends GraphAlgorithm implements AutomatedAlgorithm {


    private EdgeVertexCopier<Vertex, Edge> gc;

    public AcyclicSP(BlackBoard blackBoard) {
        super(blackBoard);
    }

//    public AcyclicSP(EdgeVertexCopier<Vertex, Edge> gc) {
//        this.gc = gc;
//    }

    public Vector<Vertex> acyclicSP(GraphModel g, Vertex v) throws InvalidVertexException {
        GraphModel gcopy = (GraphModel) g.copy(gc);
        final Integer[] dist = new Integer[g.getVerticesCount()];
        Vector<Vertex> prev = new Vector<>();
        Queue<Vertex> Q = new LinkedList<>();
        HashMap<Vertex, Vertex> gcopy2g = new HashMap<>();
        HashMap<Integer, Vertex> t = new HashMap<>();
        for (Vertex type : gcopy)
            t.put(type.getId(), type);
        for (Vertex type : g)
            gcopy2g.put(t.get(type.getId()), type);
        Arrays.fill(dist, Integer.MAX_VALUE);

        dist[v.getId()] = 0;

        for (Vertex u : gcopy) {
            if (gcopy.getInDegree(u) == 0) {
                Q.add(u);
            }
        }

        while (!Q.isEmpty()) {
            Vertex u = Q.poll();
            Iterator<Edge> iet = gcopy.edgeIterator(u);
            while (iet.hasNext()) {
                Edge e = iet.next();
                if ((dist[u.getId()] + e.getWeight()) < dist[e.target.getId()]) {
                    dist[e.target.getId()] = dist[u.getId()] + e.getWeight();
                    prev.add(e.target.getId(), u);
                    dispatchEvent(new PreWorkEvent<>(gcopy2g.get(e.target), gcopy2g.get(u), gcopy));
//                    System.out.println(e.target.getId());
                }
                if (gcopy.getInDegree(e.target) == 1)
                    Q.add(e.target);
            }
            gcopy.removeVertex(u);
        }
        return prev;
    }

    public void doAlgorithm() {
//        GraphRequest<Vertex, Edge> gr = new GraphRequest<>();
//        dispatchEvent(gr);
//        GraphModel g = gr.getGraph();
//        VertexRequest<Vertex, Edge> vr = new VertexRequest<>(g);
//        dispatchEvent(vr);
//        Vertex v = vr.getVertex();
//        acyclicSP(g, v);
    }
}
