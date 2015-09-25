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

@CommandAttitude(name = "randic_index", abbreviation = "_ri")
public class RandicIndex implements GraphReportExtension {
    public String getName() {
        return "Randic Index";
    }

    public String getDescription() {
        return "Randic Index";
    }


    public Object calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        ArrayList<String> out = new ArrayList<String>();
        out.add("Randic Index : "+zif.getSecondZagreb(-0.5));
        return out;
    }


    @Override
    public String getCategory() {
        return "Topological Indices-Zagreb Indices";
    }
}