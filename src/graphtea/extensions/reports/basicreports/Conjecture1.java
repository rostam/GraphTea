// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.basicreports;

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

@CommandAttitude(name = "conj", abbreviation = "_conj")
public class Conjecture1 implements GraphReportExtension {
    public String getName() {
        return "Conjecture 1";
    }

    public Double alpha = 1.0;

    public String getDescription() {
        return "Conjecture 1";
    }

    public Object calculate(GraphData gd) {
        ArrayList<String> out = new ArrayList<String>();

        double first_zagreb = 0;
        for (Vertex v : gd.getGraph().vertices()) {
            first_zagreb += Math.pow(gd.getGraph().getDegree(v), 2);
        }

        double second_zagreb = 0;
        for (Edge e : gd.getGraph().getEdges()) {
            second_zagreb +=
                    Math.pow(
                            gd.getGraph().getDegree(e.source) *
                                    gd.getGraph().getDegree(e.target), 1);
        }

        double conj = first_zagreb/gd.getGraph().numOfVertices() -
                second_zagreb/gd.getGraph().getEdgesCount();
        if(conj !=0)
            conj = -conj;
        out.add("The formula (M_2^1/m-M_1^2/n = "+ conj
                + ") is great than zero.");
        return out;
    }

    @Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "Topological Indices";
	}
}
