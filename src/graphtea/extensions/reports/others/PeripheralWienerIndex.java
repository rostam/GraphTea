package graphtea.extensions.reports.others;

import graphtea.extensions.algorithms.shortestpath.algs.FloydWarshall;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

@CommandAttitude(name = "PeripheralWienerIndex", abbreviation = "_peripheral_wiener_index")
public class PeripheralWienerIndex implements GraphReportExtension<Integer> {
    public String getName() {
        return "Peripheral wiener index";
    }

    public String getDescription() {
        return "Peripheral wiener index";
    }


    /**
     *  The peripheral Wiener index PW (G)
     * of a graph G is the sum of distances between
     * all pairs of peripheral vertices @{@link PeripheralVertices} of G.
     *
     * @param g the given graph
     * @return the peripheral wiener index of g
     */
    @Override
    public Integer calculate(GraphModel g) {
        int sum = 0;
        FloydWarshall fw = new FloydWarshall();
        int[][] dist = fw.getAllPairsShortestPathWithoutWeight(g);
        for (int v = 0; v < g.numOfVertices(); v++) {
            for (int u = v + 1; u < g.numOfVertices(); u++) {
                sum += dist[v][u];
            }
        }
        return sum;
    }

    @Override
    public String getCategory() {
        return "OurWorks-Conjectures";
    }

}