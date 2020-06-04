package graphtea.extensions.reports.zagreb;

import graphtea.extensions.algorithms.shortestpath.algs.FloydWarshall;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.Iterator;
import java.util.Vector;

/**
 * @author Ali Rostami
 */

@CommandAttitude(name = "AllEccen", abbreviation = "_AllEccen")
public class AllEccen implements GraphReportExtension<RenderTable>{
    public String getName() {
        return "AllEccen";
    }

    public String getDescription() {
        return " AllEccen ";
    }

    public int eccentricity(GraphModel g, int v, Integer[][] dist) {
        int max_dist = 0;
        for(int j=0;j < g.getVerticesCount();j++) {
            if(max_dist < dist[v][j]) {
                max_dist = dist[v][j];
            }
        }
        return max_dist;
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();
        titles.add(" m ");
        titles.add(" n ");
        titles.add(" total eccentricity ");
        titles.add(" eccentric connectivity index ");
        titles.add(" connective eccentricity index ");
        ret.setTitles(titles);

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        Vector<Object> v = new Vector<>();
        v.add(m);
        v.add(n);

        FloydWarshall fw = new FloydWarshall();
        Integer[][] dist = fw.getAllPairsShortestPathWithoutWeight(g);
        int total_eccentricity = 0;
        for(Vertex ver : g) {
            total_eccentricity += eccentricity(g, ver.getId(), dist);
        }
        v.add(total_eccentricity);

        int eccentric_connectivity_index = 0;
        for(Vertex ver : g) {
            eccentric_connectivity_index += eccentricity(g, ver.getId(), dist)*g.getDegree(ver);
        }
        v.add(eccentric_connectivity_index);

        double connective_eccentric_index = 0;
        for(Vertex ver : g) {
            connective_eccentric_index += (double)g.getDegree(ver)/eccentricity(g, ver.getId(), dist);
        }
        v.add(connective_eccentric_index);
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "OurWorks-Conjectures";
    }
}






