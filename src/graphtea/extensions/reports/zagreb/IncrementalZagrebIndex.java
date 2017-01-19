// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.zagreb;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.platform.lang.CommandAttitude;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.reports.extension.GraphReportExtension;

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

    public Object calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();
        titles.add("Alpha");
        titles.add("First General Zagreb Index");
        titles.add("Second General Zagreb Index");
        titles.add("First Reformulated Zagreb Index");
        titles.add("Second Reformulated Zagreb Index");
        ret.setTitles(titles);

        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        for(double alpha = start_alpha;alpha <= end_alpha;alpha=alpha+inc) {
            Vector<Object> v = new Vector<>();
            v.add(alpha);
            v.add(zif.getFirstZagreb(alpha));
            v.add(zif.getSecondZagreb(alpha));
            v.add(zif.getFirstReZagreb(alpha));
            v.add(zif.getSecondReZagreb(alpha));
            ret.add(v);
        }
        return ret;
    }

    public String checkParameters() {
        return null;
    }

    @Override
	public String getCategory() {
        return "Topological Indices-Zagreb Indices";
	}
}
