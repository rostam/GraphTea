// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.topological;

import graphtea.extensions.algorithms.shortestpath.algs.FloydWarshall;
import graphtea.graph.graph.GraphModel;
import graphtea.plugins.reports.extension.GraphReportExtension;

/**
 * Shared base for Wiener-type topological indices.
 * Subclasses implement {@link #compute(GraphModel, int[][])} with the
 * all-pairs shortest-path matrix already computed.
 */
abstract class WienerIndexBase implements GraphReportExtension<Integer> {

    @Override
    public final Integer calculate(GraphModel g) {
        FloydWarshall fw = new FloydWarshall();
        int[][] dist = fw.getAllPairsShortestPathWithoutWeight(g);
        return compute(g, dist);
    }

    /** Compute the index value given the graph and its all-pairs distance matrix. */
    protected abstract Integer compute(GraphModel g, int[][] dist);

    @Override
    public String getCategory() {
        return "Topological Indices-Wiener Types";
    }
}
