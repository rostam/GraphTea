// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.library.algorithms.shortestpath;

import graphtea.library.BaseEdge;
import graphtea.library.BaseGraph;
import graphtea.library.BaseVertex;
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
 */
public class Johnson
        <VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>, GraphType extends BaseGraph<VertexType, EdgeType>>
        extends Algorithm {

    int[] a;
    int[][] d;

    //HashMap nw = new HashMap<EdgeType,Integer>();
    Johnson() {

    }

    public int[][] ComputePaths(GraphType g) {

        BaseEdgeVertexCopier gc = new BaseEdgeVertexCopier();
        VertexType u;
        BaseEdge e;

        u = (VertexType) gc.convert(null);
        g.insertVertex(u);
        for (VertexType v : g) {

            if (v != u) {
                e = gc.convert(null, u, v);
                g.insertEdge((EdgeType) e);
                e.setWeight(0);
            }

        }
        BellmanFord sp = new BellmanFord();

        if (sp.computePaths(g, u) != null) {
            Vector<VertexType> pd = sp.computePaths(g, u);
            for (VertexType v : g) {

                int dd = 0;
                VertexType w = v;
                EdgeType h;
                while (w != u) {
                    h = getSingleEdge(g, pd.get(v.getId()), w);
                    dd += h.getWeight();
                }
                a[v.getId()] = dd;

            }
            Iterator<EdgeType> iet = g.edgeIterator();
            EdgeType h;
            while (iet.hasNext()) {
                h = iet.next();
                int w = h.getWeight() + a[h.source.getId()] - a[h.target.getId()];
                //nw.put(h,w);
                h.setWeight(w);
            }

            for (VertexType v : g) {
                Dijkstra dj = new Dijkstra();
                Vector<VertexType> pdj = dj.getShortestPath(g, v);
                for (VertexType z : g) {
                    int dd = 0;
                    VertexType w = z;
                    EdgeType f;
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

    private EdgeType getSingleEdge(GraphType g, VertexType v, VertexType w) {
        Collection<EdgeType> f;
        EdgeType h;
        f = g.getEdges(v, w);
        h = f.iterator().next();
        return h;
    }

}
