// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.topological;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.List;

/**
 * Shared template for incremental Zagreb index reports.
 *
 * <p>Iterates alpha from {@link #start_alpha} to {@link #end_alpha} in steps
 * of {@link #inc}, builds a {@link RenderTable} with an "Alpha" column plus
 * the columns provided by {@link #columnTitles()}, and fills each row using
 * {@link #rowValues(ZagrebIndexFunctions, double)}.
 */
abstract class IncrementalZagrebReportBase
        implements GraphReportExtension<RenderTable>, Parametrizable {

    @Parameter(name = "Starting Value of Alpha", description = "")
    public Double startAlpha = -10.0;

    @Parameter(name = "End Value of Alpha", description = "")
    public Double endAlpha = 10.0;

    @Parameter(name = "Incremental Value", description = "")
    public Double inc = 0.1;

    @Override
    public final RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        List<String> titles = new ArrayList<>();
        titles.add("Alpha");
        titles.addAll(columnTitles());
        ret.setTitles(titles);

        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        for (double alpha = startAlpha; alpha <= endAlpha; alpha = alpha + inc) {
            List<Object> v = new ArrayList<>();
            v.add(alpha);
            v.addAll(rowValues(zif, alpha));
            ret.add(v);
        }
        return ret;
    }

    /** Column titles (excluding the leading "Alpha" column). */
    protected abstract List<String> columnTitles();

    /** Per-row values for the given alpha (in the same order as {@link #columnTitles()}). */
    protected abstract List<Object> rowValues(ZagrebIndexFunctions zif, double alpha);

    @Override
    public String getCategory() {
        return "Topological Indices-Zagreb Indices";
    }
}
