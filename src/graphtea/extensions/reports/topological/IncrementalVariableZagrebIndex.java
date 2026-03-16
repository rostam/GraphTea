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
@CommandAttitude(name = "inc_var_zagreb_coindex", abbreviation = "_ivzci")
public class IncrementalVariableZagrebIndex extends IncrementalZagrebReportBase {

    public String getName() {
        return "Incremental Variable Zagreb Indices";
    }

    public String getDescription() {
        return "Incremental Variable Zagreb Indices";
    }

    @Override
    protected List<String> columnTitles() {
        return Arrays.asList(
                "First Variable Zagreb Index",
                "Second Variable Zagreb Index");
    }

    @Override
    protected List<Object> rowValues(ZagrebIndexFunctions zif, double alpha) {
        return Arrays.asList(
                zif.getFirstVariableZagrebIndex(alpha),
                zif.getSecondVariableZagrebIndex(alpha));
    }
}
