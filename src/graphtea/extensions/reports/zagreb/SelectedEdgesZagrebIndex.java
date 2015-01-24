// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.zagreb;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;

/**
 * @author Ali Rostami

 */

@CommandAttitude(name = "zagreb_index_edges", abbreviation = "_zie")
public class SelectedEdgesZagrebIndex implements GraphReportExtension, Parametrizable {
    public String getName() {
        return "Zagreb Indices of Selected Edges";
    }

    @Parameter(name = "Alpha", description = "")
    public Double alpha = 1.0;

    public String getDescription() {
        return "Zagreb Indices of Selected Edges";
    }

    public Object calculate(GraphData gd) {
        ArrayList<String> out = new ArrayList<String>();

        double first_zagreb = 0;
        for (Vertex v : gd.getGraph().vertices()) {
            for (Vertex nv : gd.getGraph().getNeighbors(v))
                if(gd.getGraph().getEdge(v,nv).isSelected()) {
                   first_zagreb += Math.pow(gd.getGraph().getDegree(v), alpha + 1);
                    break;
                }
        }

        double second_zagreb = 0;
        double first_re_zagreb = 0;
        for (Edge e : gd.getGraph().getEdges()) {
            if(e.isSelected()) {
                second_zagreb +=
                        Math.pow(
                                gd.getGraph().getDegree(e.source) *
                                        gd.getGraph().getDegree(e.target), alpha);

                int d = gd.getGraph().getDegree(e.source) +
                        gd.getGraph().getDegree(e.target) - 2;

                first_re_zagreb += Math.pow(d, alpha + 1);
            }
        }

        double second_re_zagreb = 0;
        ArrayList<Edge> eds = new ArrayList<Edge>();
        for(Edge ee : gd.getGraph().getEdges()) {
            eds.add(ee);
        }
       for (Edge e1 : eds) {
            for (Edge e2 : eds) {
                if(e1.isSelected() && e2.isSelected()) {
                    if (edge_adj(e1, e2)) {
                        int d1 = gd.getGraph().getDegree(e1.source) +
                                gd.getGraph().getDegree(e1.target) - 2;

                        int d2 = gd.getGraph().getDegree(e2.source) +
                                gd.getGraph().getDegree(e2.target) - 2;

                        second_re_zagreb += Math.pow(d1 * d2, alpha);
                    }
                }
            }
        }

        second_re_zagreb/=2;
        out.add("First General Zagreb Index : "+ first_zagreb);
        out.add("Second General Zagreb Index : "+ second_zagreb);
        out.add("First Reformulated Zagreb Index : " + first_re_zagreb);
        out.add("Second Reformulated Zagreb Index : " + second_re_zagreb);
        return out;
    }

    private boolean edge_adj(Edge e1,Edge e2) {
        if(e1.source.getId()==e2.source.getId()  &&
                e1.target.getId()==e2.target.getId()) return false;
        else if(e1.target.getId()==e2.source.getId() &&
                e1.source.getId()==e2.target.getId()) return false;
        else if(e1.source.getId() == e2.source.getId()) return true;
        else if(e1.source.getId() == e2.target.getId()) return true;
        else if(e1.target.getId() == e2.source.getId()) return true;
        else if(e1.target.getId() == e2.target.getId()) return true;
        return false;
    }

    public String checkParameters() {
        return null;
    }

    @Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "Topological Indices";
	}
}
