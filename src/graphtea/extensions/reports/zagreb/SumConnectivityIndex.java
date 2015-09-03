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

@CommandAttitude(name = "sumconn_index", abbreviation = "_sci")
public class SumConnectivityIndex implements GraphReportExtension {
    public String getName() {
        return "Sum Connectivity Index";
    }

    public String getDescription() {
        return "Sum Connectivity Index";
    }

    public Object calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        ArrayList<String> out = new ArrayList<String>();
        out.add("Sum Connectivity Index : "+zif.getGeneralSumConnectivityIndex(-0.5));
        return out;
    }



    @Override
    public String getCategory() {
        // TODO Auto-generated method stub
        return "Topological Indices";
    }
}