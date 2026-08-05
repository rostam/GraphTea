// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.topological;

import graphtea.extensions.reports.DijkstraNonNegative;
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
public class BalabanIndex implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Balaban Index";
    }

    public RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        ret.setTitles(Arrays.asList(" m ", " n ", " Balaban Index "));

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();
        double tmp = m / (m - n + 2.);
        double allSum = 0;

        for (Vertex fV : g) {
            for (Vertex sV : g) {
                if (g.isEdge(fV, sV)) {
                    DijkstraNonNegative.dijkstra(g, sV);
                    double sum1 = 0;
                    for (Vertex temp : g) {
                        sum1 += (Double) temp.getUserDefinedAttribute(DijkstraNonNegative.Dist);
                    }
                    DijkstraNonNegative.dijkstra(g, fV);
                    double sum2 = 0;
                    for (Vertex temp : g) {
                        sum2 += (Double) temp.getUserDefinedAttribute(DijkstraNonNegative.Dist);
                    }
                    allSum += 1 / Math.sqrt(sum1 * sum2);
                }
            }
        }

        List<Object> v = new ArrayList<>();
        v.add(m);
        v.add(n);
        v.add(tmp * allSum);
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Topological Indices-Distance";
    }
}
