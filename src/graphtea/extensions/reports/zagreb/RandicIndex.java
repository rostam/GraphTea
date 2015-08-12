// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.zagreb;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.platform.lang.CommandAttitude;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;

/**
 * @author Ali Rostami

 */

@CommandAttitude(name = "randic_index", abbreviation = "_ri")
public class RandicIndex implements GraphReportExtension {
    public String getName() {
        return "Randic Index";
    }

    public String getDescription() {
        return "Randic Index";
    }

    public Object calculate(GraphModel g) {
        ArrayList<String> out = new ArrayList<String>();
        double ret = 0;
        for (Edge e : g.getEdges()) {
            ret+=1./Math.sqrt(g.getDegree(e.source)*g.getDegree(e.target));
        }

        out.add("Randic Index : "+ ret);
        return out;
    }

    @Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "Topological Indices";
	}
}
