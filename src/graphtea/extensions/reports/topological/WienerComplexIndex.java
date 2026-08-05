// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.topological;

import graphtea.extensions.AlgorithmUtils;
import graphtea.graph.graph.GraphModel;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.HashSet;
import java.util.Set;

/**
 * @author azin azadi
 */
public class WienerComplexIndex implements GraphReportExtension<Integer> {

    public String getName() {
        return "Wiener Complexity";
    }

    public Integer calculate(GraphModel g) {
        int[][] dist = AlgorithmUtils.getAllPairsDistances(g);
        Set<Integer> uniqueRowSums = new HashSet<>();
        for (int[] row : dist) {
            int sum = 0;
            for (int d : row) {
                sum += d;
            }
            uniqueRowSums.add(sum);
        }
        return uniqueRowSums.size();
    }

    @Override
    public String getCategory() {
        return "Topological Indices-Distance";
    }
}
