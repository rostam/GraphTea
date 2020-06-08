package graphtea.extensions.reports.others;

import graphtea.extensions.algorithms.shortestpath.algs.FloydWarshall;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.Vector;

@CommandAttitude(name = "Radius", abbreviation = "_graph_radius")
public class Radius implements GraphReportExtension<RenderTable> {
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
     * @param v the given vertex
     * @param dist All pair shortest path without considering weights
     * @return the eccentricity value
     */
    public int eccentricity(GraphModel g, int v, int[][] dist) {
        int min_dist = 2*g.numOfVertices();
        for (int j = 0; j < g.getVerticesCount(); j++) {
            if (min_dist > dist[v][j]) {
                min_dist = dist[v][j];
            }
        }
        return min_dist;
    }

    @Override
    public RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();
        titles.add("Vertex");
        titles.add("Eccentricity");
        ret.setTitles(titles);

        FloydWarshall fw = new FloydWarshall();
        int[][] dist = fw.getAllPairsShortestPathWithoutWeight(g);
        for (int i = 0; i < g.getVerticesCount(); i++) {
            Vector<Object> v = new Vector<>();
            v.add(i);
            v.add(eccentricity(g, i, dist));
            ret.add(v);
        }
        return ret;
    }

    @Override
    public String getCategory() {
        return "OurWorks-Conjectures";
    }

}