// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.topological;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

/**
 * @author Ali Rostami

 */


@CommandAttitude(name = "TotalIrregularityIndex", abbreviation = "_ttirrindex")
public class TotalIrregularityIndex implements GraphReportExtension<Integer> {
    public String getName() {
        return "TotalIrregularityIndex";
    }

    public String getDescription() {
        return "TotalIrregularityIndex";
    }

    public static int computeAlbertsonIndex(GraphModel graph) {
        int sum = 0;
        for (Vertex i : graph) {
            for (Vertex j : graph) {
                if (i.getId() > j.getId()) {
                    sum += Math.abs(graph.getDegree(i) - graph.getDegree(j));
                }
            }
        }
        return sum;
    }

    public Integer calculate(GraphModel graph) {
        return computeAlbertsonIndex(graph);
    }

    @Override
    public String getCategory() {
        return "Topological Indices-Irregularities";
    }
}