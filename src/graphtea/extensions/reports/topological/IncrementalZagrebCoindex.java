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
@CommandAttitude(name = "inc_zagreb_coindex", abbreviation = "_izci")
public class IncrementalZagrebCoindex extends IncrementalZagrebReportBase {

    public String getName() {
        return "Incremental Zagreb Coindices";
    }

    public String getDescription() {
        return "Incremental Zagreb Coindices";
    }

    @Override
    protected List<String> columnTitles() {
        return Arrays.asList(
                "First Zagreb Coindex",
                "Second Zagreb Coindex",
                "First Zagreb Reformulated Coindex",
                "Second Zagreb Reformulated Coindex");
    }

    @Override
    protected List<Object> rowValues(ZagrebIndexFunctions zif, double alpha) {
        return Arrays.asList(
                zif.getFirstZagrebCoindex(alpha),
                zif.getSecondZagrebCoindex(alpha),
                zif.getFirstReZagrebCoindex(alpha),
                zif.getSecondReZagrebCoindex(alpha));
    }
}
