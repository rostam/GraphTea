// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.energy;

import graphtea.extensions.AlgorithmUtils;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Ali Rostami
 */
public class Conjecture implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Conjecture";
    }

    public RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        List<String> titles = new ArrayList<>();
        titles.add(" m ");
        titles.add(" n ");
        titles.add(" E(G) ");
        titles.add(" R.H.S ");
        titles.add(" Eigenvalues ");
        ret.setTitles(titles);

        double energy = Double.parseDouble(new Energy().calculate(g));

        ArrayList<Integer> al = AlgorithmUtils.getDegreesList(g);
        Collections.sort(al);
        double maxDeg = al.get(al.size() - 1);
        double minDeg = al.get(0);

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        List<Object> v = new ArrayList<>();
        v.add(m);
        v.add(n);
        v.add(energy);
        v.add(maxDeg + minDeg);
        v.add(AlgorithmUtils.getEigenValues(g));

        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification- Energy";
    }
}
