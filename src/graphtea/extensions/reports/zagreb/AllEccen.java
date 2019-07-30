package graphtea.extensions.reports.zagreb;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.main.core.AlgorithmUtils;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

/**
 * @author Ali Rostami
 */

@CommandAttitude(name = "AllEccen", abbreviation = "_AllEccen")
public class AllEccen implements GraphReportExtension{
    public String getName() {
        return "AllEccen";
    }

    public String getDescription() {
        return " AllEccen ";
    }

    public Integer[][] getAllPairsShortestPathWithoutWeight(final GraphModel g) {
        final Integer dist[][] = new Integer[g.numOfVertices()][g.numOfVertices()];
        Iterator<Edge> iet = g.edgeIterator();
        for (int i = 0; i < g.getVerticesCount(); i++)
            for (int j = 0; j < g.getVerticesCount(); j++)
                dist[i][j] = g.numOfVertices();

        for (Vertex v : g)
            dist[v.getId()][v.getId()] = 0;

        while (iet.hasNext()) {
            Edge edge = iet.next();
            dist[edge.target.getId()][edge.source.getId()] = 1;
            dist[edge.source.getId()][edge.target.getId()] = 1;
        }

        for (Vertex v : g)
            for (Vertex u : g)
                for (Vertex w : g) {
                    if ((dist[v.getId()][w.getId()] + dist[w.getId()][u.getId()]) < dist[v.getId()][u.getId()])
                        dist[v.getId()][u.getId()] = dist[v.getId()][w.getId()] + dist[w.getId()][u.getId()];
                }

        return dist;
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

    public Object calculate(GraphModel g) {
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

        Integer[][] dist = getAllPairsShortestPathWithoutWeight(g);
        int total_eccentricity = 0;
        for(Vertex ver : g) {
            total_eccentricity += eccentricity(g, ver.getId(), getAllPairsShortestPathWithoutWeight(g));
        }
        v.add(total_eccentricity);

        int eccentric_connectivity_index = 0;
        for(Vertex ver : g) {
            eccentric_connectivity_index += eccentricity(g, ver.getId(), getAllPairsShortestPathWithoutWeight(g))*g.getDegree(ver);
        }
        v.add(eccentric_connectivity_index);

        double connective_eccentric_index = 0;
        for(Vertex ver : g) {
            connective_eccentric_index += (double)g.getDegree(ver)/eccentricity(g, ver.getId(), getAllPairsShortestPathWithoutWeight(g));
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






