// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.topological;

import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.reports.others.PeripheralWienerIndex;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.List;

/**
 * Compares the peripheral Wiener index of a graph with that of its iterated line graphs
 * L1 = L(G), L2 = L(L1), and so on, up to a fixed depth.
 *
 * <p>Replaces three copies of the same report that differed only in how many line graphs they
 * took.
 *
 * @author Ali Rostami
 */
abstract class PeripheralWienerLineGraphsBase implements GraphReportExtension<RenderTable> {
    private final int depth;

    /**
     * @param depth how many times to take the line graph
     */
    PeripheralWienerLineGraphsBase(int depth) {
        this.depth = depth;
    }

    public RenderTable calculate(GraphModel g) {
        PeripheralWienerIndex pw = new PeripheralWienerIndex();
        List<String> titles = new ArrayList<>(List.of(" m ", " n ", " PW(G) "));
        List<Object> row = new ArrayList<>(List.of(g.getEdgesCount(), g.getVerticesCount(), pw.calculate(g)));
        GraphModel line = g;
        for (int i = 1; i <= depth; i++) {
            line = AlgorithmUtils.createLineGraph(line);
            titles.add(" PW(L" + i + ") ");
            row.add(pw.calculate(line));
        }
        RenderTable ret = new RenderTable();
        ret.setTitles(titles);
        ret.add(row);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Checking";
    }
}
