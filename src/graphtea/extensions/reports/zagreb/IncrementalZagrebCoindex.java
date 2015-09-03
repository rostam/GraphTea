// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.zagreb;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RendTable;
import graphtea.platform.lang.CommandAttitude;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.Vector;

/**
 * @author Ali Rostami

 */

@CommandAttitude(name = "inc_zagreb_coindex", abbreviation = "_izci")
public class IncrementalZagrebCoindex implements GraphReportExtension, Parametrizable {
    public String getName() {
        return "Incremental Zagreb Coindices";
    }

    @Parameter(name = "Starting Value of Alpha", description = "")
    public Double start_alpha = -10.0;

    @Parameter(name = "End Value of Alpha", description = "")
    public Double end_alpha = 10.0;

    @Parameter(name = "Incremental Value", description = "")
    public Double inc = 0.1;

    public String getDescription() {
        return "Incremental Zagreb Coindices";
    }

    public Object calculate(GraphModel g) {
        ArrayList<String> out = new ArrayList<String>();
        RendTable ret = new RendTable();
        ret.add(new Vector<Object>());
        ret.get(0).add("Alpha");
        ret.get(0).add("First Zagreb Coindex");
        ret.get(0).add("Second Zagreb Coindex");
        ret.get(0).add("First Zagreb Reformulated Coindex");
        ret.get(0).add("Second Zagreb Reformulated Coindex");

        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        int ind = 0;
        for(double alpha = start_alpha;alpha <= end_alpha;alpha=alpha+inc) {
            ind++;
            ret.add(new Vector<Object>());
            ret.get(ind).add(alpha);
            ret.get(ind).add(zif.getFirstZagrebCoindex(alpha));
            ret.get(ind).add(zif.getSecondZagrebCoindex(alpha));
            ret.get(ind).add(zif.getFirstReZagrebCoindex(alpha));
            ret.get(ind).add(zif.getSecondReZagrebCoindex(alpha));
        }
        return ret;
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
