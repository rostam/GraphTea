// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.extensions.algorithms;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Mohammad Ali Rostami
 * @email rostamiev@gmail.com
 */
public class EdgeInduced {
    public static GraphModel edgeInduced(GraphModel g, Collection<Edge> S) {
        GraphModel ret = g.createEmptyGraph();
        HashMap<Vertex, Vertex> vv = new HashMap<>();
        for (Vertex v : g) {
            Vertex t = v.getCopy();
            vv.put(v, t);
            ret.insertVertex(t);
        }

        Iterator<Edge> e = g.edgeIterator();
        while (e.hasNext()) {
            Edge t = e.next();
            ret.insertEdge(t.getCopy(vv.get(t.source), vv.get(t.target)));
        }

        for (Edge ee : S) {
            Vertex v1 = ee.source;
            Vertex v2 = ee.target;
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
