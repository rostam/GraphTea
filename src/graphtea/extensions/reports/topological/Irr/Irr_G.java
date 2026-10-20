package graphtea.extensions.reports.topological.Irr;

import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.reports.basicreports.NumOfTriangles;
import graphtea.extensions.reports.topological.ZagrebIndexFunctions;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.graph.graph.Vertex;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ali Rostami
 */

public class Irr_G implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Irr_G";
    }

    public static int deg_e(GraphModel g, Vertex u) {
        int ret = 0;
        List<Vertex> neighbors = new ArrayList<>();
        for (Vertex v : g.directNeighbors(u)) {
            ret += g.getDegree(v);
            neighbors.add(v);
        }

        int conn = 0;
        for (int i = 0; i < neighbors.size(); i++) {
            for (int j = i + 1; j < neighbors.size(); j++) {
                if (g.isEdge(neighbors.get(i), neighbors.get(j))) {
                    conn++;
                }
            }
        }

        return ret - conn;
    }

    public static List<Integer> list_deg_e(GraphModel g) {
        List<Integer> ret = new ArrayList<>();
        for (Vertex v : g) {
            ret.add(deg_e(g, v));
        }
        return ret;
    }

    public static int irr_ev_G(GraphModel graph) {
        int sum = 0;
        for (Vertex i : graph) {
            for (Vertex j : graph.directNeighbors(i)) {
                if (i.getId() > j.getId()) {
                    if (graph.isEdge(i, j)) {
                        sum += Math.abs(deg_e(graph, i) - deg_e(graph, j));
                    }
                }
            }
        }
        return sum;
    }

    public RenderTable calculate(GraphModel g) {
        return irregularityTable(g, " ve-Irr(G) ", irr_ev_G(g));
    }

    /**
     * The table both ev-irregularity reports show: the graph's size, its Zagreb and Ve
     * indices, the irregularity value under {@code title}, and both degree sequences.
     *
     * @param g     the graph
     * @param title column heading for the irregularity value
     * @param irr   the irregularity value
     * @return a one-row table
     */
    static RenderTable irregularityTable(GraphModel g, String title, int irr) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        RenderTable ret = new RenderTable();
        ret.setTitles(Arrays.asList(" m ", " n ", " Zagreb ", " Ve ", " t ", title, " V. Degrees ", "ve Degrees"));

        int t = NumOfTriangles.getNumOfTriangles(g);
        double m21 = zif.getFirstZagreb(1);

        List<Object> v = new ArrayList<>();
        v.add((double) g.getEdgesCount());
        v.add((double) g.getVerticesCount());
        v.add(m21);
        v.add(m21 - (3 * t));
        v.add(t);
        v.add(irr);
        v.add(AlgorithmUtils.getDegreesList(g).toString());
        v.add(list_deg_e(g));

        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Degree";
    }
}
