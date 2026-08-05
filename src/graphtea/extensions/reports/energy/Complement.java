// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.energy;

import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.reports.topological.ZagrebIndexFunctions;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ali Rostami
 */
public class Complement implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Complement Energy";
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zifL = new ZagrebIndexFunctions(AlgorithmUtils.createLineGraph(g));
        ZagrebIndexFunctions zifC = new ZagrebIndexFunctions(AlgorithmUtils.createComplementGraph(g));
        ZagrebIndexFunctions zifCL = new ZagrebIndexFunctions(
                AlgorithmUtils.createComplementGraph(AlgorithmUtils.createLineGraph(g)));

        RenderTable ret = new RenderTable();
        List<String> titles = new ArrayList<>();
        titles.add(" m ");
        titles.add(" n ");
        titles.add(" E(G) ");
        titles.add(" CE ");
        titles.add(" LE ");
        titles.add(" CLE ");
        ret.setTitles(titles);

        double sum = Double.parseDouble(new Energy().calculate(g));
        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        List<Object> v = new ArrayList<>();
        v.add(m);
        v.add(n);
        v.add(sum);
        v.add(zifC.getEnegry());
        v.add(zifL.getEnegry());
        v.add(zifCL.getEnegry());

        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification- Energy";
    }
}
