// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.topological;

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

@CommandAttitude(name = "zagreb_index", abbreviation = "_zi")
public class ZagrebIndex implements GraphReportExtension<RenderTable>, Parametrizable {
    public String getName() {
        return "All Zagreb Indices";
    }

    @Parameter(name = "Alpha", description = "The alpha value")
    public Double alpha = 1.0;

    public String getDescription() {
        return "All Zagreb Indices";
    }

    public RenderTable calculate(GraphModel g) {
        RenderTable renderTable = new RenderTable();
        Vector<String> titles = new Vector<>();
        titles.add("First General Zagreb Index");
        titles.add("Second General Zagreb Index");
        titles.add("First Reformulated Zagreb Index");
        titles.add("First Reformulated Zagreb Index");
        renderTable.setTitles(titles);

        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        Vector<Object> values = new Vector<>();
        values.add(zif.getFirstZagreb(alpha));
        values.add(zif.getSecondZagreb(alpha));
        values.add(zif.getFirstReZagreb(alpha));
        values.add(zif.getSecondReZagreb(alpha));
        renderTable.add(values);
        return renderTable;
    }

    public String checkParameters() {
        return null;
    }

    @Override
	public String getCategory() {
		return "Topological Indices-Zagreb Indices";
	}
}
