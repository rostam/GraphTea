package graphtea.extensions.reports.basicreports;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.graph.graph.Vertex;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.Iterator;
import java.util.Vector;

public class AllPairShortestPathsWithoutWeight implements GraphReportExtension {

    /**
     * @param g The given graph
     * @return All shortest paths
     */
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

    @Override
    public Object calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();
        titles.add("Vertex 1");
        titles.add("Vertex 2");
        titles.add("Distance");
        ret.setTitles(titles);

        final Integer dist[][] = getAllPairsShortestPathWithoutWeight(g);
        for (int i = 0; i < dist.length; i++) {
            for (int j = i + 1; j < dist[i].length; j++) {
                Vector<Object> v = new Vector<>();
                v.add(i);
                v.add(j);
                v.add(dist[i][j]);
                ret.add(v);
            }
        }
        return ret;
    }


    @Override
    public String getCategory() {
        return "General";
    }

    @Override
    public String getName() {
        return "All pair shortest paths without weight";
    }

    @Override
    public String getDescription() {
        return "All pair shortest paths without weight";
    }
}
