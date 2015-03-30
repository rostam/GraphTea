// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.zagreb;

import graphtea.graph.graph.RendTable;
import graphtea.platform.lang.CommandAttitude;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.Vector;

/**
 * @author Ali Rostami

 */

@CommandAttitude(name = "inc_zagreb_index_edges", abbreviation = "_izie")
public class IncrementalZagrebCoindexSelectedEdges implements GraphReportExtension, Parametrizable {
    public String getName() {
        return "Incremental Zagreb Coindices of Selected Edges";
    }

    @Parameter(name = "Starting Value of Alpha", description = "")
    public Double start_alpha = -10.0;

    @Parameter(name = "End Value of Alpha", description = "")
    public Double end_alpha = 10.0;

    @Parameter(name = "Incremental Value", description = "")
    public Double inc = 0.1;

    public String getDescription() {
        return "Incremental Zagreb Coindices of Selected Edges";
    }

    public Object calculate(GraphData gd) {
        RendTable ret = new RendTable();
        ret.add(new Vector<Object>());
        ret.get(0).add("Alpha");
        ret.get(0).add("First Zagreb Coindex");
        ret.get(0).add("Second Zagreb Coindex");


        int ind = 0;
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(gd.getGraph());
        for(double alpha = start_alpha;alpha <= end_alpha;alpha=alpha+inc) {
            ind++;
            ret.add(new Vector<Object>());
            ret.get(ind).add(alpha);
            ret.get(ind).add(zif.getFirstZagrebCoindexSelectedEdges(alpha));
            ret.get(ind).add(zif.getSecondZagrebCoindexSelectedEdges(alpha));
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
