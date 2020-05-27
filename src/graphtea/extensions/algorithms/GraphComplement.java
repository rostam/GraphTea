// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.extensions.algorithms;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

import java.util.HashMap;

/**
 * @author Mohammad Ai Rostami
 * @email rostamiev@gmail.com
 */

public class GraphComplement {
    public static GraphModel complement(GraphModel g1) {
        {
            GraphModel g = g1.createEmptyGraph();
            HashMap<Vertex, Vertex> hm = new HashMap<>();

            for (Vertex v : g1) {
                Vertex t = v.getCopy();
                hm.put(v, t);
                g.insertVertex(t);
            }

            for (Vertex v : g1)
                for (Vertex u : g1) {
                    if (!g1.isEdge(v, u)) {
                        Edge e = g1.edgeIterator().next()
                                .getCopy(hm.get(v), hm.get(u));
                        g.insertEdge(e);
                    }
                }
            return g;
        }
    }
}
