// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphlab.extensions.actions;

import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.GraphPoint;
import graphlab.graph.graph.VertexModel;
import graphlab.plugins.main.GraphData;
import graphlab.plugins.main.extension.GraphActionExtension;

import java.util.Iterator;


/**
 * Creates a line graph from the current graph and shows it in a new tab
 *
 * @author Mohammad Ali Rostami
 * @author Azin Azadi
 */
public class LineGraph implements GraphActionExtension {

    public void action(GraphData graphData) {

        GraphModel g1 = graphData.getGraph();
        GraphModel g2 = new GraphModel(false);//
        VertexModel vg1[] = g1.getVertexArray();

        for (EdgeModel e : g1.getEdges()) {
            VertexModel v = new VertexModel();
            v.setLabel(e.getLabel());
            GraphPoint loc = new GraphPoint(e.source.getLocation());
            loc.add(e.target.getLocation());
            loc.multiply(0.5);
            loc.add(e.getCurveControlPoint());
            v.setLocation(loc);
            e.getProp().obj = v;
            g2.insertVertex(v);
        }
        for (VertexModel v : g1) {
            Iterator<EdgeModel> ie = g1.lightEdgeIterator(v);

            while (ie.hasNext()) {
                EdgeModel e = ie.next();
                Iterator<EdgeModel> ie2 = g1.lightEdgeIterator(v);
                while (ie2.hasNext()) {
                    EdgeModel e2 = ie2.next();
                    if (e != e2) {
                        EdgeModel ne = new EdgeModel((VertexModel) e.getProp().obj, (VertexModel) e2.getProp().obj);
                        g2.insertEdge(ne);
                    }
                }
            }
        }
        graphData.core.showGraph(g2);
    }

    public String getName() {
        return "LineGraph";
    }

    public String getDescription() {
        return "Makes a graph including the edges of original graph as vertices ";
    }

}
