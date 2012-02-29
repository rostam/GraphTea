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

@CommandAttitude(name = "is_eulerian", abbreviation = "_ie")
public class IsEulerian implements GraphReportExtension {
    public Object calculate(GraphData gd) {
        GraphModel graph = gd.getGraph();
        return isEulerian(graph);
    }

    /**
     * @return true if given graph is Eulerian
     */
    public static boolean isEulerian(GraphModel graph) {
        int cc = graph.getVerticesCount();
        if (cc == 0) return false;
        if (cc < 2)
            return true;
        if (!AlgorithmUtils.isConnected(graph))
            return false;
        ArrayList<Integer> degrees = AlgorithmUtils.getDegreesList(graph);
        for (int d : degrees) {
            if (d % 2 != 1) return false;
        }
        return true;
    }

    public String getName() {
        return "Is Eulerian";
    }

    public String getDescription() {
        return "Is Eulerian";
    }

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "Property";
	}
}
