// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.topological;

import graphtea.graph.graph.GraphModel;
import graphtea.platform.lang.CommandAttitude;

/**
 * In chemical graph theory, the Wiener index (also Wiener number) introduced by Harry Wiener,
 * is a topological index defined as the sum of the lengths of the shortest paths between all
 * pairs of vertices in the graph.
 *
 * @author Ali Rostami
 */
@CommandAttitude(name = "wiener_index", abbreviation = "_windex")
public class WienerIndex extends WienerIndexBase {
    public String getName() {
        return "Wiener Index";
    }

    public String getDescription() {
        return "Wiener Index";
    }

    @Override
    protected Integer compute(GraphModel g, int[][] dist) {
        int sum = 0;
        for (int v = 0; v < g.numOfVertices(); v++) {
            for (int u = v + 1; u < g.numOfVertices(); u++) {
                if (dist[v][u] < g.numOfVertices()) {
                    sum += dist[u][v];
                }
            }
        }
        return sum;
    }
}
