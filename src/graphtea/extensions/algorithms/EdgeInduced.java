// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.extensions.algorithms;

import graphtea.library.BaseEdge;
import graphtea.library.BaseGraph;
import graphtea.library.BaseVertex;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Mohammad Ali Rostami
 * @email rostamiev@gmail.com
 */
public class EdgeInduced {
    public static <VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
    BaseGraph<VertexType, EdgeType> edgeInduced(BaseGraph<VertexType, EdgeType> g, Collection<EdgeType> S) {
        BaseGraph<VertexType, EdgeType> ret = g.createEmptyGraph();
        HashMap<VertexType, VertexType> vv = new HashMap<>();
        for (VertexType v : g) {
            VertexType t = (VertexType) v.getCopy();
            vv.put(v, t);
            ret.insertVertex(t);
        }

        Iterator<EdgeType> e = g.edgeIterator();
        while (e.hasNext()) {
            EdgeType t = e.next();
            ret.insertEdge((EdgeType) t.getCopy(vv.get(t.source), vv.get(t.target)));
        }

        for (EdgeType ee : S) {
            VertexType v1 = ee.source;
            VertexType v2 = ee.target;
            if (ret.getInDegree(v1) + ret.getOutDegree(v1) == 0)
                ret.removeVertex(v1);
            else if (ret.getInDegree(v2) + ret.getOutDegree(v2) == 0)
                ret.removeVertex(v2);
            else
                ret.removeEdge(ret.getEdges(vv.get(ee.source), vv.get(ee.target)).get(0));
        }
        return ret;
    }
}
