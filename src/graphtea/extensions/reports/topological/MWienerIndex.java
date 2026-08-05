// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.topological;

import graphtea.graph.graph.GraphModel;

/**
 * @author Ali Rostami
 */
public class MWienerIndex extends WienerIndexBase {
    public String getName() {
        return "Multiplicative Wiener Index";
    }

    @Override
    protected Integer compute(GraphModel g, int[][] dist) {
        int product = 1;
        for (int v = 0; v < g.numOfVertices(); v++) {
            for (int u = v + 1; u < g.numOfVertices(); u++) {
                if (dist[v][u] < g.numOfVertices() + 1 && dist[u][v] > 0) {
                    product *= dist[u][v];
                }
            }
        }
        return product;
    }
}
