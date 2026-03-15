// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.extensions.algorithms;
import graphtea.platform.core.exception.ExceptionHandler;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.library.exceptions.InvalidVertexException;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.algorithmanimator.core.GraphAlgorithm;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Mohammad Ali Rostami
 * @email rostamiev@gmail.com
 */

public class GraphUnion extends GraphAlgorithm {
    public GraphUnion(BlackBoard blackBoard) {
        super(blackBoard);
    }
    public static GraphModel union(GraphModel g1, GraphModel g2) {
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

        for (Edge e : g1.getEdges()) {
            E.add(e.getCopy(temp.get(e.source), temp.get(e.target)));
            //E.add(e);
        }

        for (Edge e : g2.getEdges()) {
            E.add(e.getCopy(temp.get(e.source), temp.get(e.target)));
        }

        for (Edge e : E) {
            try {
                g.insertEdge(e);
            } catch (InvalidVertexException e1) {
                ExceptionHandler.catchException(e1);
            }
        }
        return g;
    }
}
