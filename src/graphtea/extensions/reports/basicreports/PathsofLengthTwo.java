// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.basicreports;

import graphtea.extensions.reports.Utils;
import graphtea.graph.graph.GraphModel;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

/**
 * @author Ali Rostami
 */


@CommandAttitude(name = "paths_length2", abbreviation = "_ps2")
public class PathsofLengthTwo implements GraphReportExtension<Integer> {
    public String getName() {
        return "Number of Paths of Length 2";
    }

    public String getDescription() {
        return "Number of Paths of Length 2";
    }

    public Integer calculate(GraphModel g) {
        return Utils.createLineGraph(g).getEdgesCount();
    }

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "General";
	}
}
