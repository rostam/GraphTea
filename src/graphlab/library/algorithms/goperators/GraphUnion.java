// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphlab.library.algorithms.goperators;

import graphlab.library.BaseEdge;
import graphlab.library.BaseGraph;
import graphlab.library.BaseVertex;
import graphlab.library.algorithms.Algorithm;
import graphlab.library.exceptions.InvalidVertexException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @author Mohammad Ali Rostami
 * @email ma.rostami@yahoo.com
 */

public class GraphUnion
        extends Algorithm {
    public static <VertexType extends BaseVertex
            , EdgeType extends BaseEdge<VertexType>>
    BaseGraph<VertexType, EdgeType> union(BaseGraph<VertexType, EdgeType> g1, BaseGraph<VertexType, EdgeType> g2) {
        BaseGraph<VertexType, EdgeType> g = g1.createEmptyGraph();
        HashMap<VertexType, VertexType> temp = new HashMap<VertexType, VertexType>();
        HashSet<EdgeType> E = new HashSet<EdgeType>();

        for (VertexType v : g1) {
            VertexType vt = (VertexType) v.getCopy();
            temp.put(v, vt);
            g.insertVertex(vt);
        }
        for (VertexType v : g2) {
            VertexType vt = (VertexType) v.getCopy();
            temp.put(v, vt);
            g.insertVertex(vt);
        }

        Iterator<EdgeType> iet = g1.lightEdgeIterator();
        while (iet.hasNext()) {
            EdgeType e = iet.next();
            E.add((EdgeType) e.getCopy(temp.get(e.source), temp.get(e.target)));
            //E.add(iet.next());
        }

        iet = g2.lightEdgeIterator();
        while (iet.hasNext()) {
            EdgeType e = iet.next();
            E.add((EdgeType) e.getCopy(temp.get(e.source), temp.get(e.target)));
        }

        for (EdgeType e : E) {
            try {
                g.insertEdge(e);
            } catch (InvalidVertexException e1) {
                e1.printStackTrace();
            }
        }
        return g;
    }
}
