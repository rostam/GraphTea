// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.basicreports;

import graphtea.graph.graph.GraphModel;
import graphtea.library.algorithms.shortestpath.FloydWarshall;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.main.core.AlgorithmUtils;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohammad Ali Rostami
 */

@CommandAttitude(name = "graph_diameter", abbreviation = "_gd")
public class Diameter implements GraphReportExtension {

    public Object calculate(GraphModel g) {
        FloydWarshall floydWarshall = new FloydWarshall();
        // that should be called two times
        floydWarshall.getAllPairsShortestPathWithoutWeight(g);
        Integer[][] ret = floydWarshall.getAllPairsShortestPathWithoutWeight(g);
        int max =0;
        for (int i = 0;i<ret.length;i++)
            for (int j = 0;j<ret[i].length;j++) {
                if(ret[i][j] > max) max =ret[i][j];
            }

        return max;
    }

    public String getName() {
        return "Graph Diameter";
    }

    public String getDescription() {
        return "Graph Diameter";
    }


	@Override
	public String getCategory() {
		return "General";
	}
}
