package graphtea.extensions.reports.others;

import graphtea.extensions.algorithms.shortestpath.algs.FloydWarshall;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
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

    public int eccentricity(GraphModel g, int v, int[][] dist) {
        int max_dist = 0;
        for (int j = 0; j < g.getVerticesCount(); j++) {
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
        return "OurWorks-Conjectures";
    }

}