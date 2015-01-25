// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.zagreb;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.RendTable;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.reports.extension.GraphReportExtension;
import graphtea.ui.components.gpropertyeditor.utils.ObjectViewer;

import java.util.ArrayList;
import java.util.Vector;

/**
 * @author Ali Rostami

 */

@CommandAttitude(name = "inc_zagreb_index", abbreviation = "_izi")
public class IncrementalZagrebIndex implements GraphReportExtension, Parametrizable {
    public String getName() {
        return "Incremental Zagreb Indices";
    }

    @Parameter(name = "Starting Value of Alpha", description = "")
    public Double start_alpha = -10.0;

    @Parameter(name = "End Value of Alpha", description = "")
    public Double end_alpha = 10.0;

    @Parameter(name = "Incremental Value", description = "")
    public Double inc = 0.1;

    public String getDescription() {
        return "Incremental Zagreb Indices";
    }

    public Object calculate(GraphData gd) {
        ArrayList<String> out = new ArrayList<String>();
        RendTable ret = new RendTable();
        ret.add(new Vector<Object>());
        ret.get(0).add("Alpha");
        ret.get(0).add("First General Zagreb Index");
        ret.get(0).add("Second General Zagreb Index");
        ret.get(0).add("First Reformulated Zagreb Index");
        ret.get(0).add("Second Reformulated Zagreb Index");


        int ind = 0;
        for(double alpha = start_alpha;alpha <= end_alpha;alpha=alpha+inc) {
            ind++;
            ret.add(new Vector<Object>());
            double first_zagreb = 0;
            for (Vertex v : gd.getGraph().vertices()) {
                first_zagreb += Math.pow(gd.getGraph().getDegree(v), alpha + 1);
            }

            double second_zagreb = 0;
            double first_re_zagreb = 0;
            for (Edge e : gd.getGraph().getEdges()) {
                second_zagreb +=
                        Math.pow(
                                gd.getGraph().getDegree(e.source) *
                                        gd.getGraph().getDegree(e.target), alpha);

                int d = gd.getGraph().getDegree(e.source) +
                        gd.getGraph().getDegree(e.target) - 2;

                first_re_zagreb += Math.pow(d, alpha + 1);

            }

            double second_re_zagreb = 0;
            ArrayList<Edge> eds = new ArrayList<Edge>();
            for (Edge ee : gd.getGraph().getEdges()) {
                eds.add(ee);
            }
            for (Edge e1 : eds) {
                for (Edge e2 : eds) {
                    if (edge_adj(e1, e2)) {
                        int d1 = gd.getGraph().getDegree(e1.source) +
                                gd.getGraph().getDegree(e1.target) - 2;

                        int d2 = gd.getGraph().getDegree(e2.source) +
                                gd.getGraph().getDegree(e2.target) - 2;

                        second_re_zagreb += Math.pow(d1 * d2, alpha);
                    }
                }
            }
            second_re_zagreb/=2;
            ret.get(ind).add(alpha);
            ret.get(ind).add(first_zagreb);
            ret.get(ind).add(second_zagreb);
            ret.get(ind).add(first_re_zagreb);
            ret.get(ind).add(second_re_zagreb);
        }
        return ret;
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
