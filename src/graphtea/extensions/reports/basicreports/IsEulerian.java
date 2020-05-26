// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.basicreports;

import graphtea.graph.graph.GraphModel;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.main.core.AlgorithmUtils;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;

/**
 * @author Mohammad Ali Rostami
 */

@CommandAttitude(name = "is_eulerian", abbreviation = "_ie")
public class IsEulerian implements GraphReportExtension<Boolean> {
    public Boolean calculate(GraphModel g) {
        return isEulerian(g);
    }

    /**
     * @return true if given graph is Eulerian
     */
    private static boolean isEulerian(GraphModel graph) {
        int cc = graph.getVerticesCount();
        if (cc == 0) return false;
        if (cc < 2)
            return true;
        if (!AlgorithmUtils.isConnected(graph))
            return false;
        ArrayList<Integer> degrees = AlgorithmUtils.getDegreesList(graph);
        for (int d : degrees) {
            if (d % 2 != 0) return false;
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
		return "General";
	}
}
