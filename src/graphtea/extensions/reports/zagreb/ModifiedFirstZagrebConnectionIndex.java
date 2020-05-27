// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.zagreb;

import graphtea.graph.graph.GraphModel;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;

/**
 * @author Ali Rostami

 */

@CommandAttitude(name = "modified_first_zagreb_connection_index", abbreviation = "_mfzci")
public class ModifiedFirstZagrebConnectionIndex implements GraphReportExtension {

    public String getName() {
        return "Modified First Zagreb Connection Index";
    }

    public String getDescription() {
        return "Modified First Zagreb Connection Index";
    }

    public Object calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        ArrayList<String> out = new ArrayList<>();
        out.add("Modified First Zagreb Connection Index: "+ zif.getModifiedFirstZagrebConnection());
        return out;
    }

    @Override
	public String getCategory() {
        return "Topological Indices-Zagreb Indices";
	}
}
