package graphtea.extensions.reports.others;

import graphtea.extensions.algorithms.shortestpath.algs.FloydWarshall;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.Vector;

@CommandAttitude(name = "Eccentricity", abbreviation = "_eccentricity")
public class Eccentricity implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Eccentricity";
    }

    public String getDescription() {
        return "Eccentricity";
    }

    /**
     * The eccentricity of a graph vertex v in a connected graph G is
     * the maximum graph distance between v and any other vertex u of G.
     * For a disconnected graph, all vertices are defined to have infinite eccentricity
     *
     * @param g    the given graph
     * @param v    the given vertex id
     * @param dist All pair shortest path without considering weights
     * @return the eccentricity value
     */
    public static int eccentricity(GraphModel g, int v, int[][] dist) {
        int max_dist = 0;
        for (int j = 0; j < g.getVerticesCount(); j++) {
            if (max_dist < dist[v][j]) {
                max_dist = dist[v][j];
            }
        }
        return max_dist;
    }

    /**
     * The eccentricity of a graph vertex v in a connected graph G is
     * the maximum graph distance between v and any other vertex u of G.
     * For a disconnected graph, all vertices are defined to have infinite eccentricity
     *
     * @param g    the given graph
     * @param vv   the given vertex
     * @param dist All pair shortest path without considering weights
     * @return the eccentricity value
     */
    public static int eccentricity(GraphModel g, Vertex vv, int[][] dist) {
        int v = vv.getId();
        int max_dist = 0;
        for (int j = 0; j < g.getVerticesCount(); j++) {
            System.out.println(v + " " +max_dist);
            if (max_dist < dist[v][j]) {
                max_dist = dist[v][j];
            }
        }
        return max_dist;
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
		return "General";
    }
}
