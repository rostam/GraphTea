// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.reports.basicreports;

import graphlab.graph.graph.GraphModel;
import graphlab.platform.lang.CommandAttitude;
import graphlab.plugins.main.GraphData;
import graphlab.plugins.main.core.AlgorithmUtils;
import graphlab.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;

/**
 * @author Mohammad Ali Rostami
 */

@CommandAttitude(name = "max_and_min_degree", abbreviation = "_mmd")
public class MaxAndMinDegree implements GraphReportExtension {
    public Object calculate(GraphData gd) {
        GraphModel graph = gd.getGraph();
        return maxAndMinDegree(graph);
    }

    /**
     * @return maximum and minimum degree of the given graph in the first and second index of an arraylist
     */
    private ArrayList<Integer> maxAndMinDegree(GraphModel graph) {
        int maxDegree = 0, minDegree = AlgorithmUtils.Max_Int;
        for (int d : AlgorithmUtils.getDegreesList(graph)) {
            if (d > maxDegree) maxDegree = d;
            if (d < minDegree) minDegree = d;
        }
        if (minDegree == AlgorithmUtils.Max_Int) minDegree = 0;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        ret.add(maxDegree);
        ret.add(minDegree);
        return ret;
    }

    public String getName() {
        return "Max and Min Degree";
    }

    public String getDescription() {
        return "max and min degree of graph";
    }

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "Property";
	}
}
