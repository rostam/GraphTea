// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.topological;

import graphtea.platform.lang.CommandAttitude;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ali Rostami
 */
@CommandAttitude(name = "inc_zagreb_index_edges", abbreviation = "_izie")
public class IncrementalZagrebIndexSelectedEdges extends IncrementalZagrebReportBase {

    public String getName() {
        return "Incremental Zagreb Indices of Selected Edges";
    }

    public String getDescription() {
        return "Incremental Zagreb Indices of Selected Edges";
    }

    @Override
    protected List<String> columnTitles() {
        return Arrays.asList(
                "First General Zagreb Index",
                "Second General Zagreb Index",
                "First Reformulated Zagreb Index",
                "Second Reformulated Zagreb Index");
    }

    @Override
    protected List<Object> rowValues(ZagrebIndexFunctions zif, double alpha) {
        return Arrays.asList(
                zif.getFirstZagrebSelectedEdges(alpha),
                zif.getSecondZagrebSelectedEdges(alpha),
                zif.getFirstReZagrebSelectedEdges(alpha),
                zif.getSecondReZagrebSelectedEdges(alpha));
    }
}
