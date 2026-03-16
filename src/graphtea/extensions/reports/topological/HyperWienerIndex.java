// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.topological;

import graphtea.graph.graph.GraphModel;
import graphtea.platform.lang.CommandAttitude;

/**
 * @author Ali Rostami
 */
@CommandAttitude(name = "hyper_wiener_index", abbreviation = "_windex")
public class HyperWienerIndex extends PairwiseDistanceReportBase {

    public String getName() {
        return "Hyper Wiener Index";
    }

    public String getDescription() {
        return "Hyper Wiener Index";
    }

    @Override
    protected double contribution(GraphModel g, int v, int u, int dist) {
        return (dist + (double) dist * dist) / 2.0;
    }

    @Override
    public String getCategory() {
        return "Topological Indices-Wiener Types";
    }
}
