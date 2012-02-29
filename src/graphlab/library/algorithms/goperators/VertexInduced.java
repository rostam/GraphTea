// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphlab.library.algorithms.goperators;

import graphlab.library.BaseEdge;
import graphlab.library.BaseGraph;
import graphlab.library.BaseVertex;
import graphlab.library.exceptions.InvalidVertexException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Mohammad Ali Rostami
 * @email ma.rostami@yahoo.com
 */

public class VertexInduced {
    public static <VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
    BaseGraph<VertexType, EdgeType> induced(BaseGraph<VertexType, EdgeType> g, Collection<VertexType> S) {

        BaseGraph<VertexType, EdgeType> baseGraph = g.createEmptyGraph();
        HashMap<VertexType, VertexType> hm = new HashMap<VertexType, VertexType>();
        for (VertexType v : g) {
            VertexType t = (VertexType) v.getCopy();
            hm.put(v, t);
            baseGraph.insertVertex(t);
        }

        Iterator<EdgeType> i = g.edgeIterator();
        while (i.hasNext()) {
            EdgeType e = i.next();
            baseGraph.insertEdge((EdgeType) e.getCopy(hm.get(e.source), hm.get(e.target)));
        }

        for (VertexType v : g) {
            if (!S.contains(v)) {
                try {
                    baseGraph.removeVertex(hm.get(v));
                } catch (InvalidVertexException e) {
                    e.printStackTrace();
                }
            }
        }
        return baseGraph;
    }
}
