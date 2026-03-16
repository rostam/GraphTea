// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.energy;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import graphtea.extensions.AlgorithmUtils;
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
public class LaplacianEstrada implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Laplacian Estrada";
    }

    public String getDescription() {
        return "Laplacian Estrada";
    }

    public RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        List<String> titles = new ArrayList<>();
        titles.add(" m ");
        titles.add(" n ");
        titles.add(" L.Estrada ");
        ret.setTitles(titles);

        Matrix B = g.getWeightedAdjacencyMatrix();
        Matrix A = AlgorithmUtils.getLaplacian(B);
        EigenvalueDecomposition ed = A.eig();
        double[] rv = ed.getRealEigenvalues();
        double lapestra = 0;

        for (int i = 0; i < rv.length; i++) {
            rv[i] = (double) Math.round(rv[i] * 100000d) / 100000d;
            lapestra += Math.exp(rv[i]);
        }

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        List<Object> v = new ArrayList<>();
        v.add(m);
        v.add(n);
        v.add(lapestra);
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification- Energy";
    }
}
