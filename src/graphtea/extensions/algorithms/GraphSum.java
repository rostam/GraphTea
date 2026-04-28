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

public class GraphSum extends GraphAlgorithm {
    public GraphSum(BlackBoard blackBoard) {
        super(blackBoard);
    }

    public static GraphModel sum(GraphModel g1, GraphModel g2) {
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

        for (Vertex v1 : g1) {
            for (Vertex v2 : g2) {
                E.add(new Edge(temp.get(v1), temp.get(v2)));
            }
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

    public static void setUnionLabel(GraphModel g1, GraphModel g2, GraphModel graphModel) {
        for(Vertex v : graphModel) {
            //    v.getSize().multiply(1.5);
        }
        for(int i=0;i < g1.getVerticesCount();i++) {
            Vertex v = graphModel.getVertex(i);
            String gname = g1.getLabel();
            graphModel.getVertex(i).setLabel(gname.substring(1) + "_" + v.getLabel());
        }
        for(int i=0;i < g2.getVerticesCount();i++) {
            int ind = i + g1.getVerticesCount();
            Vertex v = graphModel.getVertex(ind);
            String gname = g2.getLabel();
            graphModel.getVertex(ind).setLabel(gname.substring(1) + "_" + v.getLabel());
        }
    }
}
