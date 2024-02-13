// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.extensions.algorithms;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.library.exceptions.InvalidVertexException;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.algorithmanimator.core.GraphAlgorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @author Mohammad Ali Rostami
 * @email rostamiev@gmail.com
 */

public class GraphJoin extends GraphAlgorithm {
    public GraphJoin(BlackBoard blackBoard) {
        super(blackBoard);
    }
    public static GraphModel join(GraphModel g1, GraphModel g2) {
        GraphModel g = g1.createEmptyGraph();
        HashMap<Vertex, Vertex> temp = new HashMap<>();
        HashSet<Edge> E = new HashSet<>();

        for (Vertex v : g1) {
            Vertex vt = v.getCopy();
            temp.put(v, vt);
            g.insertVertex(vt);
        }
        for (Vertex v : g2) {
            Vertex vt = v.getCopy();
            temp.put(v, vt);
            g.insertVertex(vt);
        }

        Iterator<Edge> iet = g1.lightEdgeIterator();
        while (iet.hasNext()) {
            Edge e = iet.next();
            E.add(e.getCopy(temp.get(e.source), temp.get(e.target)));
        }

        iet = g2.lightEdgeIterator();
        while (iet.hasNext()) {
            Edge e = iet.next();
            E.add(e.getCopy(temp.get(e.source), temp.get(e.target)));
        }

        for(Vertex v1 : g1) {
            for(Vertex v2 : g2) {
                E.add(new Edge(temp.get(v1),temp.get(v2)));
            }
        }

        for (Edge e : E) {
            try {
                g.insertEdge(e);
            } catch (InvalidVertexException e1) {
                e1.printStackTrace();
            }
        }
        return g;
    }
}
