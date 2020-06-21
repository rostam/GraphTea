package graphtea.extensions.reports.others;

import graphtea.extensions.algorithms.shortestpath.algs.FloydWarshall;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

@CommandAttitude(name = "Radius", abbreviation = "_graph_radius")
public class Radius implements GraphReportExtension<Integer> {
    public String getName() {
        return "Radius";
    }

    public String getDescription() {
        return "Radius";
    }

    /**
     * The radius of a graph is the minimum graph eccentricity @{@link Eccentricity}
     * of any graph vertex in a graph.
     * A disconnected graph therefore has infinite radius (West 2000, p. 71).
     *
     * @param g the given graph
     * @return the eccentricity value
     */

    @Override
    public Integer calculate(GraphModel g) {
        FloydWarshall fw = new FloydWarshall();
        int[][] dist = fw.getAllPairsShortestPathWithoutWeight(g);
        int min = 2 * g.numOfVertices();
        for (Vertex v : g) {
            int ecc = Eccentricity.eccentricity(g, v.getId(), dist);
            if (min > ecc) {
                min = ecc;
            }
        }
        return min;
    }

    @Override
    public String getCategory() {
        return "OurWorks-Conjectures";
    }
}