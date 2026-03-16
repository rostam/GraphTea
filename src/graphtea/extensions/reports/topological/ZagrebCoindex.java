// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.topological;

import java.util.List;
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
public class ZagrebCoindex implements GraphReportExtension<ArrayList<String>>, Parametrizable {
    public String getName() {
        return "All Zagreb Coindices";
    }

    @Parameter(name = "Alpha", description = "")
    public Double alpha = 1.0;

    public String getDescription() {
        return "All Zagreb Coindices";
    }

    public ArrayList<String> calculate(GraphModel g) {
        ArrayList<String> out = new ArrayList<>();
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        out.add("First Zagreb Coindex : "+ zif.getFirstZagrebCoindex(alpha));
        out.add("Second Zagreb Coindex : "+ zif.getSecondZagrebCoindex(alpha));
        out.add("First Zagreb Reformulated Coindex : " + zif.getFirstReZagrebCoindex(alpha));
        out.add("Second Zagreb Reformulated Coindex : " + zif.getSecondReZagrebCoindex(alpha));

        return out;
    }



    @Override
	public String getCategory() {
		return "Topological Indices-Zagreb Indices";
	}
}
