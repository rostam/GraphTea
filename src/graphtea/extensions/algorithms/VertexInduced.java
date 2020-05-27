// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.extensions.algorithms;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.library.exceptions.InvalidVertexException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Mohammad Ali Rostami
 * @email rostamiev@gmail.com
 */

public class VertexInduced {
    public static GraphModel induced(GraphModel g, Collection<Vertex> S) {

        GraphModel baseGraph = g.createEmptyGraph();
        HashMap<Vertex, Vertex> hm = new HashMap<>();
        for (Vertex v : g) {
            Vertex t = (Vertex) v.getCopy();
            hm.put(v, t);
            baseGraph.insertVertex(t);
        }

        Iterator<Edge> i = g.edgeIterator();
        while (i.hasNext()) {
            Edge e = i.next();
            baseGraph.insertEdge((Edge) e.getCopy(hm.get(e.source), hm.get(e.target)));
        }

        for (Vertex v : g) {
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
