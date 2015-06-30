// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.extensions.actions;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.GraphPoint;
import graphtea.graph.graph.Vertex;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.extension.GraphActionExtension;

import java.util.Iterator;


/**
 * Creates a line graph from the current graph and shows it in a new tab
 *
 * @author Mohammad Ali Rostami
 * @author Azin Azadi
 */
public class ParalineGraph implements GraphActionExtension , Parametrizable {
    @Parameter
    public int k = 2;

    public void action(GraphData graphData) {
        GraphModel g1 = graphData.getGraph();
        GraphModel g2 = new GraphModel(false);//

        for(Vertex v : g1.getVertexArray()) {
            Vertex tmp = new Vertex();
            tmp.setLocation(v.getLocation());
            g2.addVertex(tmp);
        }

        for(Edge e : g1.getEdges()) {
            GraphPoint v1 = e.source.getLocation();
            GraphPoint v2 = e.target.getLocation();
            double dis = v1.distance(v2);
            GraphPoint v3 = GraphPoint.sub(v2, v1);
            v3 = GraphPoint.div(v3, k+1);

            Vertex src = g2.getVertex(e.source.getId());
            for (int i = 0; i < k; i++) {
                Vertex tmp = new Vertex();
                GraphPoint v4 = new GraphPoint(v3);
                v4.multiply(i + 1);
                v4.add(v1);
                tmp.setLocation(v4);
                g2.addVertex(tmp);
                g2.addEdge(new Edge(
                        src,
                        tmp
                ));
                src=tmp;
            }

            g2.addEdge(new Edge(
                    src,
                    g2.getVertex(e.target.getId())
            ));
        }
        graphData.core.showGraph(createLineGraph(g2));
    }

    public static GraphModel createLineGraph(GraphModel g1) {
        GraphModel g2 = new GraphModel(false);//

        for (Edge e : g1.getEdges()) {
            Vertex v = new Vertex();
            v.setLabel(e.getLabel());
            GraphPoint loc = new GraphPoint(e.source.getLocation());
            loc.add(e.target.getLocation());
            loc.multiply(0.5);
            loc.add(e.getCurveControlPoint());
            v.setLocation(loc);
            e.getProp().obj = v;
            g2.insertVertex(v);
        }
        for (Vertex v : g1) {
            Iterator<Edge> ie = g1.lightEdgeIterator(v);

            while (ie.hasNext()) {
                Edge e = ie.next();
                Iterator<Edge> ie2 = g1.lightEdgeIterator(v);
                while (ie2.hasNext()) {
                    Edge e2 = ie2.next();
                    if (e != e2) {
                        Edge ne = new Edge((Vertex) e.getProp().obj, (Vertex) e2.getProp().obj);
                        g2.insertEdge(ne);
                    }
                }
            }
        }
        return g2;
    }

    public String getName() {
        return "Paraline Graph";
    }

    public String getDescription() {
        return "Paraline Graph";
    }

    @Override
    public String checkParameters() {
        return null;
    }
}
