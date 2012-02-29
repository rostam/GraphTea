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
import graphlab.library.event.PreWorkEvent;
import graphlab.library.event.VertexRequest;
import graphlab.library.exceptions.InvalidVertexException;
import graphlab.library.genericcloners.EdgeVertexCopier;

import java.util.*;

/**
 * @author Soroush Sabet
 */
public class AcyclicSP<VertexType extends BaseVertex,
        EdgeType extends BaseEdge<VertexType>,
        GraphType extends BaseGraph<VertexType, EdgeType>>
        extends Algorithm implements AutomatedAlgorithm {


    private EdgeVertexCopier<VertexType, EdgeType> gc;

    public AcyclicSP(EdgeVertexCopier<VertexType, EdgeType> gc) {
        this.gc = gc;
    }

    public Vector<VertexType> acyclicSP(BaseGraph<VertexType, EdgeType> g, VertexType v) throws InvalidVertexException {
        BaseGraph<VertexType, EdgeType> gcopy = g.copy(gc);
        final Integer dist[] = new Integer[g.getVerticesCount()];
        Vector<VertexType> prev = new Vector<VertexType>();
        Queue<VertexType> Q = new LinkedList<VertexType>();
        HashMap<VertexType, VertexType> gcopy2g = new HashMap<VertexType, VertexType>();
        HashMap<Integer, VertexType> t = new HashMap<Integer, VertexType>();
        for (VertexType _ : gcopy)
            t.put(_.getId(), _);
        for (VertexType _ : g)
            gcopy2g.put(t.get(_.getId()), _);
        for (int i = 0; i < dist.length; i++)
            dist[i] = Integer.MAX_VALUE;

        dist[v.getId()] = 0;

        for (VertexType u : gcopy) {
//                System.out.println(u.getId()+":"+g.getInDegree(u));
            if (gcopy.getInDegree(u) == 0) {
//                System.out.println(u.getId());
                Q.add(u);
            }
        }

        while (!Q.isEmpty()) {
            VertexType u = Q.poll();
            Iterator<EdgeType> iet = gcopy.edgeIterator(u);
            while (iet.hasNext()) {
                EdgeType e = iet.next();
                if ((dist[u.getId()] + e.getWeight()) < dist[e.target.getId()]) {
                    dist[e.target.getId()] = dist[u.getId()] + e.getWeight();
                    prev.add(e.target.getId(), u);
                    dispatchEvent(new PreWorkEvent<VertexType, EdgeType>(gcopy2g.get(e.target), gcopy2g.get(u), gcopy));
//                    System.out.println(e.target.getId());
                }
                if (gcopy.getInDegree((VertexType) e.target) == 1)
                    Q.add((VertexType) e.target);
            }
            gcopy.removeVertex(u);
        }
        return prev;
    }

    public void doAlgorithm() {
        GraphRequest<VertexType, EdgeType> gr = new GraphRequest<VertexType, EdgeType>();
        dispatchEvent(gr);
        BaseGraph<VertexType, EdgeType> g = gr.getGraph();
        VertexRequest<VertexType, EdgeType> vr = new VertexRequest<VertexType, EdgeType>(g);
        dispatchEvent(vr);
        VertexType v = vr.getVertex();
        acyclicSP(g, v);
    }
}
