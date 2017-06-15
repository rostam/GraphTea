// GraphTea Project:bvb   http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.energy;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import graphtea.extensions.reports.spectralreports.KirchhoffIndex;
import graphtea.extensions.reports.spectralreports.LaplacianEnergyLike;
import graphtea.extensions.reports.zagreb.ZagrebIndexFunctions;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

/**
 * @author Ali Rostami

 */

@CommandAttitude(name = "newInvs", abbreviation = "_newInv")
public class LEL_vs_KF implements GraphReportExtension {
    public String getName() {
        return "LEL_vs_KF";
    }

    public String getDescription() {
        return "LEL_vs_KF";
    }

    public Object calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();
        titles.add(" LEL ");
        titles.add("KF");
        titles.add("m ");
        titles.add("n ");
        ret.setTitles(titles);

        Matrix A = g.getWeightedAdjacencyMatrix();
        EigenvalueDecomposition ed = A.eig();
        double rv[] = ed.getRealEigenvalues();
        double sum = 0;

        //positiv RV
        Double[] prv = new Double[rv.length];
        for (int i = 0; i < rv.length; i++) {
            prv[i] = Math.abs(rv[i]);
            sum += prv[i];
        }

        Arrays.sort(prv, Collections.reverseOrder());

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        Vector<Object> v = new Vector<>();
        LaplacianEnergyLike lel = new LaplacianEnergyLike();
        KirchhoffIndex kf = new KirchhoffIndex();
        v.add(Double.parseDouble(lel.calculate(g).toString()));
        v.add(Double.parseDouble(kf.calculate(g).toString()));
        v.add(m);
        v.add(n);
        ret.add(v);

        return ret;
    }

    double round(double value, int decimalPlace) {
        double power_of_ten = 1;
        while (decimalPlace-- > 0)
            power_of_ten *= 10.0;
        return Math.round(value * power_of_ten)
                / power_of_ten;
    }

    @Override
    public String getCategory() {
        return "OurWorks-Graph Energy";
    }
}
