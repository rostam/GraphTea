package graphtea.extensions.reports.topological.Irr;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.graph.graph.Vertex;
import graphtea.plugins.reports.extension.GraphReportExtension;

/**
 * @author Ali Rostami
 */

public class Irr_t_G implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Irr_t_G";
    }

    public static int irr_t_ev_G(GraphModel graph) {
        int sum = 0;
        for (Vertex i : graph) {
            for (Vertex j : graph) {
                if (i.getId() > j.getId()) {
                    sum += Math.abs(Irr_G.deg_e(graph, i) - Irr_G.deg_e(graph, j));
                }
            }
        }
        return sum;
    }

    public RenderTable calculate(GraphModel g) {
        return Irr_G.irregularityTable(g, " Total-ev-Irr(G) ", irr_t_ev_G(g));
    }

    @Override
    public String getCategory() {
        return "Verification-Degree";
    }
}
