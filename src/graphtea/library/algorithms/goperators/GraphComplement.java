// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.library.algorithms.goperators;

import graphtea.library.BaseEdge;
import graphtea.library.BaseGraph;
import graphtea.library.BaseVertex;

import java.util.HashMap;

/**
 * @author Mohammad Ai Rostami
 * @email rostamiev@gmail.com
 */

public class GraphComplement {
    public static <VertexType extends BaseVertex,
            EdgeType extends BaseEdge<VertexType>>
    BaseGraph<VertexType, EdgeType>
    complement(BaseGraph<VertexType, EdgeType> g1) {
        {
            BaseGraph<VertexType, EdgeType> g = g1.createEmptyGraph();
            HashMap<VertexType, VertexType> hm = new HashMap<>();

            for (VertexType v : g1) {
                VertexType t = (VertexType) v.getCopy();
                hm.put(v, t);
                g.insertVertex(t);
            }

            for (VertexType v : g1)
                for (VertexType u : g1) {
                    if (!g1.isEdge(v, u)) {
                        EdgeType e = (EdgeType) g1.edgeIterator().next()
                                .getCopy(hm.get(v), hm.get(u));
                        g.insertEdge(e);
                    }
                }
            return g;
        }
    }
}
