package graphtea.extensions.reports.others;

import graphtea.extensions.algorithms.shortestpath.algs.FloydWarshall;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;

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
        PeripheralVertices pv = new PeripheralVertices();
        ArrayList<Vertex> al = pv.calculate(g);
        for(Vertex vertex1 : al) {
            for (Vertex vertex2 : al) {
                int v = vertex1.getId();
                int u = vertex2.getId();
                if (v < u) {
                    sum += dist[v][u];
                }
            }
        }
        return sum;
    }

    @Override
    public String getCategory() {
        return "OurWorks-Conjectures";
    }

}