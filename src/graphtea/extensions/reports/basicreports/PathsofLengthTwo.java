// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.basicreports;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.GraphPoint;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.Iterator;

/**
 * @author Ali Rostami
 */


@CommandAttitude(name = "paths_length2", abbreviation = "_ps2")
public class PathsofLengthTwo implements GraphReportExtension<Integer> {
    public String getName() {
        return "Number of Paths of Length 2";
    }

    public String getDescription() {
        return "Number of Paths of Length 2";
    }

    public Integer calculate(GraphModel g) {
        GraphModel g1 = g;
        GraphModel g2 = new GraphModel(false);//
        Vertex vg1[] = g1.getVertexArray();

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


        return g2.getEdgesCount();
    }

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "General";
	}
}
