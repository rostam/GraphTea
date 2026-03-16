// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.topological;

import graphtea.extensions.reports.others.Eccentricity;
import graphtea.graph.graph.GraphModel;
import graphtea.platform.lang.CommandAttitude;

/**
 * @author Ali Rostami
 */
@CommandAttitude(name = "eccentric_wiener_index", abbreviation = "_ewindex")
public class EccentricWienerIndex extends WienerIndexBase {
    public String getName() {
        return "Eccentric Wiener Index";
    }

    public String getDescription() {
        return "Eccentric Wiener Index";
    }

    @Override
    protected Integer compute(GraphModel g, int[][] dist) {
        int sum = 0;
        for (int v = 0; v < g.numOfVertices(); v++) {
            for (int u = v + 1; u < g.numOfVertices(); u++) {
                double eu = Eccentricity.eccentricity(g, u, dist);
                double ev = Eccentricity.eccentricity(g, v, dist);
                if (dist[v][u] == Math.min(eu, ev)) {
                    sum += dist[v][u];
                }
            }
        }
        return sum;
    }
}
