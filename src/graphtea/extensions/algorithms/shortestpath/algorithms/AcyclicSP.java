// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.extensions.algorithms.shortestpath.algorithms;


import graphtea.library.BaseEdge;
import graphtea.library.BaseGraph;
import graphtea.library.BaseVertex;
import graphtea.library.algorithms.Algorithm;
import graphtea.library.algorithms.AutomatedAlgorithm;
import graphtea.library.event.GraphRequest;
import graphtea.library.event.PreWorkEvent;
import graphtea.library.event.VertexRequest;
import graphtea.library.exceptions.InvalidVertexException;
import graphtea.library.genericcloners.EdgeVertexCopier;

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
        Vector<VertexType> prev = new Vector<>();
        Queue<VertexType> Q = new LinkedList<>();
        HashMap<VertexType, VertexType> gcopy2g = new HashMap<>();
        HashMap<Integer, VertexType> t = new HashMap<>();
        for (VertexType type : gcopy)
            t.put(type.getId(), type);
        for (VertexType type : g)
            gcopy2g.put(t.get(type.getId()), type);
        for (int i = 0; i < dist.length; i++)
            dist[i] = Integer.MAX_VALUE;

        dist[v.getId()] = 0;

        for (VertexType u : gcopy) {
            if (gcopy.getInDegree(u) == 0) {
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
                    dispatchEvent(new PreWorkEvent<>(gcopy2g.get(e.target), gcopy2g.get(u), gcopy));
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
        GraphRequest<VertexType, EdgeType> gr = new GraphRequest<>();
        dispatchEvent(gr);
        BaseGraph<VertexType, EdgeType> g = gr.getGraph();
        VertexRequest<VertexType, EdgeType> vr = new VertexRequest<>(g);
        dispatchEvent(vr);
        VertexType v = vr.getVertex();
        acyclicSP(g, v);
    }
}
