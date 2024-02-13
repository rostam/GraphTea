// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.extensions.algorithms;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.algorithmanimator.core.GraphAlgorithm;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Mohammad Ali Rostami
 * @email rostamiev@gmail.com
 */

public class VertexCorona extends GraphAlgorithm {
    public VertexCorona(BlackBoard blackBoard) {
        super(blackBoard);
    }

    public static GraphModel corona(GraphModel g1, GraphModel g2) {
        GraphModel g = g1.createEmptyGraph();
        HashMap<Vertex, Vertex> temp1 = new HashMap<>();

        for (Vertex v : g1) {
            Vertex vt = v.getCopy();
            temp1.put(v, vt);
            g.insertVertex(vt);
        }

        Iterator<Edge> iet = g1.lightEdgeIterator();
        while (iet.hasNext()) {
            Edge e = iet.next();
            g.insertEdge(e.getCopy(temp1.get(e.source), temp1.get(e.target)));
        }

        for (Vertex v1 : g1) {
            HashMap<Vertex, Vertex> temp2 = new HashMap<>();
            Edge ee = g1.getEdges().iterator().next();
            for (Vertex v : g2) {
                Vertex nvt = v.getCopy();
                temp2.put(v, nvt);
                g.insertVertex(nvt);
                g.addEdge(ee.getCopy(
                        temp1.get(v1), nvt));

            }

            iet = g2.lightEdgeIterator();
            while (iet.hasNext()) {
                Edge e = iet.next();
                g.addEdge(e.getCopy(temp2.get(e.source), temp2.get(e.target)));
            }

        }
        g.setDirected(g1.isDirected());

        return g;
    }

    public void setPositions(GraphModel g1, GraphModel g2,GraphModel g) {
        Vertex[] varr=g.getVertexArray();
        int k =0;
        for(int i=g1.getVerticesCount();i< g.getVerticesCount();
            i=i+g2.getVerticesCount(),k++) {
            varr[k].setLabel(g1.getLabel().substring(1)+ "_"+varr[k].getLabel());
            for(int j=0;j<g2.getVerticesCount();j++) {
                int index=g1.getVerticesCount()+g2.getVerticesCount()*k + j;
                varr[index].setLabel(g2.getLabel().substring(1)+"_"+varr[index].getLabel()+"_"+k);
                GPoint gp1 = varr[index].getLocation();
                GPoint gp2 = varr[k].getLocation();
                GPoint gp3 = GPoint.sub(gp2,gp1);
                gp3= GPoint.div(gp3,2);
                gp3.add(gp1);
                g.getVertex(varr[index].getId()).setLocation(gp3);
            }
        }
    }
}
