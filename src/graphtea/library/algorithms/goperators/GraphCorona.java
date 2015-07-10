// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.library.algorithms.goperators;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.GraphPoint;
import graphtea.graph.graph.Vertex;
import graphtea.library.BaseEdge;
import graphtea.library.BaseGraph;
import graphtea.library.BaseVertex;
import graphtea.library.algorithms.Algorithm;
import graphtea.library.exceptions.InvalidVertexException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @author Mohammad Ali Rostami
 * @email ma.rostami@yahoo.com
 */

public class GraphCorona
        extends Algorithm {
    public static <VertexType extends BaseVertex
            , EdgeType extends BaseEdge<VertexType>>
    BaseGraph<VertexType, EdgeType> corona(BaseGraph<VertexType, EdgeType> g1, BaseGraph<VertexType, EdgeType> g2) {
        BaseGraph<VertexType, EdgeType> g = g1.createEmptyGraph();
        HashMap<VertexType, VertexType> temp1 = new HashMap<VertexType, VertexType>();
        HashSet<EdgeType> E = new HashSet<EdgeType>();

        for (VertexType v : g1) {
            VertexType vt = (VertexType) v.getCopy();
            temp1.put(v, vt);
            g.insertVertex(vt);
        }

        Iterator<EdgeType> iet = g1.lightEdgeIterator();
        while (iet.hasNext()) {
            EdgeType e = iet.next();
            g.insertEdge((EdgeType) e.getCopy(temp1.get(e.source), temp1.get(e.target)));
        }

        for (VertexType v1 : g1) {
            HashMap<VertexType, VertexType> temp2 = new HashMap<VertexType, VertexType>();
            EdgeType ee = g1.getEdges().iterator().next();
            for (VertexType v : g2) {
                VertexType nvt = (VertexType) v.getCopy();
                temp2.put(v, nvt);
                g.insertVertex(nvt);
                g.addEdge((EdgeType) ee.getCopy(
                        temp1.get(v1), nvt));

            }

            iet = g2.lightEdgeIterator();
            while (iet.hasNext()) {
                EdgeType e = iet.next();
                g.addEdge((EdgeType) e.getCopy(temp2.get(e.source), temp2.get(e.target)));
            }

        }
        System.out. println("corona 4");
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
                GraphPoint gp1 = varr[index].getLocation();
                GraphPoint gp2 = varr[k].getLocation();
                GraphPoint gp3 = GraphPoint.sub(gp2,gp1);
                gp3= GraphPoint.div(gp3,2);
                gp3.add(gp1);
                g.getVertex(varr[index].getId()).setLocation(gp3);
            }
        }
    }
}
