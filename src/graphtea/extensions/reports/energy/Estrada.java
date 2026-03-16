// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.energy;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ali Rostami
 */
@CommandAttitude(name = "newInvs", abbreviation = "_newInv")
public class Estrada implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Estrada";
    }

    public String getDescription() {
        return "Estrada";
    }

    public RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        List<String> titles = new ArrayList<>();
        titles.add(" m ");
        titles.add(" n ");
        titles.add(" Estarda ");
        titles.add(" check1 ");
        titles.add(" check2 ");
        ret.setTitles(titles);

        Matrix A = g.getWeightedAdjacencyMatrix();
        EigenvalueDecomposition ed = A.eig();
        double[] rv = ed.getRealEigenvalues();
        double estra = 0;
        double es = 0;

        for (int i = 0; i < rv.length; i++) {
            rv[i] = (double) Math.round(rv[i] * 10000000000d) / 10000000000d;
            estra += Math.exp(rv[i]);
            es += Math.exp(2 * rv[i]);
        }

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        List<Object> v = new ArrayList<>();
        v.add(m);
        v.add(n);
        v.add(estra);
        v.add(Math.sqrt(n * es));
        v.add(Math.pow(n * (Math.exp(rv[0]) - Math.exp(rv[rv.length - 1])), 2) / 4);
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification- Energy";
    }
}
