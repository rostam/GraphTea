// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.zagreb;

import graphtea.graph.graph.GraphModel;
import graphtea.platform.lang.CommandAttitude;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;

/**
 * @author Ali Rostami

 */

@CommandAttitude(name = "second_mix_zagreb_index", abbreviation = "_smzi")
public class SecondMixZagrebIndex implements GraphReportExtension<ArrayList<String>>, Parametrizable {

    @Parameter(name = "Alpha", description = "")
    public Double alpha = 1.0;
    @Parameter(name = "Beta", description = "")
    public Double beta = 1.0;

    public String getName() {
        return "Second Mix Zagreb Index";
    }

    public String getDescription() {
        return "Second Mix Zagreb Index";
    }

    public ArrayList<String> calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        ArrayList<String> out = new ArrayList<>();
        out.add("Second Mix Zagreb Index : "+ zif.getSecondMixZagrebIndex(alpha,beta));
        return out;
    }

    @Override
	public String getCategory() {
        return "Topological Indices-Zagreb Indices";
	}

    @Override
    public String checkParameters() {
        return null;
    }
}
