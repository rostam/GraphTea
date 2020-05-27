// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.extensions.algorithms.shortestpath.algorithms;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.library.BaseEdge;
import graphtea.library.algorithms.Algorithm;
import graphtea.library.genericcloners.BaseEdgeVertexCopier;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

/**
 * This Algorithm computes the lenght of the shortest
 * path between any two arbitrary vertices.
 * This method is usually used for sparse graphs.
 *
 * @author Soroush Sabet
 * @author M. Ali Rostami
 */
public class Johnson extends Algorithm {

    int[] a;
    int[][] d;

    public int[][] ComputePaths(GraphModel g) {

        BaseEdgeVertexCopier gc = new BaseEdgeVertexCopier();
        Vertex u;
        BaseEdge e;

        u = (Vertex) gc.convert(null);
        g.insertVertex(u);
        for (Vertex v : g) {

            if (v != u) {
                e = gc.convert(null, u, v);
                g.insertEdge((Edge) e);
                e.setWeight(0);
            }

        }
        BellmanFord sp = new BellmanFord();

        if (sp.computePaths(g, u) != null) {
            Vector<Vertex> pd = sp.computePaths(g, u);
            for (Vertex v : g) {

                int dd = 0;
                Vertex w = v;
                Edge h;
                while (w != u) {
                    h = getSingleEdge(g, pd.get(v.getId()), w);
                    dd += h.getWeight();
                }
                a[v.getId()] = dd;

            }
            Iterator<Edge> iet = g.edgeIterator();
            Edge h;
            while (iet.hasNext()) {
                h = iet.next();
                int w = h.getWeight() + a[h.source.getId()] - a[h.target.getId()];
                //nw.put(h,w);
                h.setWeight(w);
            }

            for (Vertex v : g) {
                Dijkstra dj = new Dijkstra();
                Vector<Vertex> pdj = dj.getShortestPath(g, v);
                for (Vertex z : g) {
                    int dd = 0;
                    Vertex w = z;
                    Edge f;
                    while (w != v) {
                        f = getSingleEdge(g, pdj.get(z.getId()), w);
                        dd += f.getWeight();
                    }
                    d[v.getId()][z.getId()] = dd + a[z.getId()] - a[v.getId()];

                }
            }

        }


        return new int[0][];
    }

    private Edge getSingleEdge(GraphModel g, Vertex v, Vertex w) {
        Collection<Edge> f;
        Edge h;
        f = g.getEdges(v, w);
        h = f.iterator().next();
        return h;
    }

}
