// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.zagreb;

import graphtea.graph.graph.GraphModel;
import graphtea.library.algorithms.shortestpath.FloydWarshall;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.io.StringReader;

/**
 * @author azin azadi

 */


@CommandAttitude(name = "wiener_index", abbreviation = "_windex")
public class WienerIndex implements GraphReportExtension<Object> {
    public String getName() {
        return "Wiener Index";
    }

    public String getDescription() {
        return "Wiener Index";
    }

    public Object calculate(GraphModel g) {
        FloydWarshall floydWarshall = new FloydWarshall();
        // that should be called two times
        floydWarshall.getAllPairsShortestPathWithoutWeight(g);
        Integer[][] ret = floydWarshall.getAllPairsShortestPathWithoutWeight(g);
        int sum =0;
        for (int i = 0;i<ret.length;i++)
            for (int j = 0;j<ret[i].length;j++) {
                sum += ret[i][j];
            }

        return sum/2;
    }

	@Override
	public String getCategory() {
		return "Topological Indices";
	}
}
