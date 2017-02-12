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

@CommandAttitude(name = "zagreb_coindex", abbreviation = "_zci")
public class ZagrebCoindex implements GraphReportExtension, Parametrizable {
    public String getName() {
        return "All Zagreb Coindices";
    }

    @Parameter(name = "Alpha", description = "")
    public Double alpha = 1.0;

    public String getDescription() {
        return "All Zagreb Coindices";
    }

    public Object calculate(GraphModel g) {
        ArrayList<String> out = new ArrayList<>();
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        out.add("First Zagreb Coindex : "+ zif.getFirstZagrebCoindex(alpha));
        out.add("Second Zagreb Coindex : "+ zif.getSecondZagrebCoindex(alpha));
        out.add("First Zagreb Reformulated Coindex : " + zif.getFirstReZagrebCoindex(alpha));
        out.add("Second Zagreb Reformulated Coindex : " + zif.getSecondReZagrebCoindex(alpha));

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
		return "Topological Indices-Zagreb Indices";
	}
}
