package graphtea.extensions.reports.others;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.Iterator;
import java.util.Vector;

@CommandAttitude(name = "Eccentricity", abbreviation = "_eccentricity")
public class Eccentricity implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Eccentricity";
    }

    public String getDescription() {
        return "Eccentricity";
    }

    public int eccentricity(GraphModel g, int v, Integer[][] dist) {
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

        Integer[][] dist = getAllPairsShortestPathWithoutWeight(g);


        for (int i = 0; i < g.getVerticesCount(); i++) {
            Vector<Object> v = new Vector<>();
            v.add(i);
            v.add(eccentricity(g, i, dist));
            ret.add(v);
        }
        return ret;
    }

    public Integer[][] getAllPairsShortestPathWithoutWeight(final GraphModel g) {
        final Integer[][] dist = new Integer[g.numOfVertices()][g.numOfVertices()];
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

    @Override
    public String getCategory() {
        return "OurWorks-Conjectures";
    }

}