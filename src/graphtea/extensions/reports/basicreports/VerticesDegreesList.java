// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.basicreports;

import graphtea.extensions.AlgorithmUtils;
import graphtea.graph.graph.GraphModel;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.Collections;


/**
 * @author Mohammad Ali Rostami
 */

@CommandAttitude(name = "vertices_degree_list", abbreviation = "_vdl")
public class VerticesDegreesList implements GraphReportExtension<ArrayList<Integer>> {
    public ArrayList<Integer> calculate(GraphModel g) {
        ArrayList<Integer> al = AlgorithmUtils.getDegreesList(g);
        Collections.sort(al);
        return al;
    }

    public String getName() {
        return "Vertices Degrees List";
    }

    public String getDescription() {
        return "vertices degrees list";
    }

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "General";
	}
}
