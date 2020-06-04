package graphtea.extensions.reports.basicreports;

import graphtea.extensions.algorithms.shortestpath.algs.FloydWarshall;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.Vector;

public class AllPairShortestPathsWithoutWeight implements GraphReportExtension<RenderTable> {

    /**
     * @param g The given graph
     * @return All shortest paths
     */
    public Integer[][] getAllPairsShortestPathWithoutWeight(final GraphModel g) {
        FloydWarshall fw = new FloydWarshall();
        return fw.getAllPairsShortestPathWithoutWeight(g);
    }

    @Override
    public RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();
        titles.add("Vertex 1");
        titles.add("Vertex 2");
        titles.add("Distance");
        ret.setTitles(titles);

        final Integer[][] dist = getAllPairsShortestPathWithoutWeight(g);
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
