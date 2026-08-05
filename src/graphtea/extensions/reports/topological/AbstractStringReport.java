// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.topological;

import graphtea.graph.graph.GraphModel;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.Arrays;

abstract class AbstractStringReport implements GraphReportExtension<ArrayList<String>> {

    protected abstract String[] computeLines(ZagrebIndexFunctions zif);

    @Override
    public final ArrayList<String> calculate(GraphModel g) {
        return new ArrayList<>(Arrays.asList(computeLines(new ZagrebIndexFunctions(g))));
    }
}
