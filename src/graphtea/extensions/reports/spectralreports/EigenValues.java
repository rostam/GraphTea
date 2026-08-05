// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.spectralreports;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import graphtea.extensions.AlgorithmUtils;
import graphtea.graph.graph.GraphModel;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;

/**
 * @author M. Ali Rostami
 */
public class EigenValues implements GraphReportExtension<ArrayList<String>>, Parametrizable {

    @Parameter(name = "power:", description = "The power of the eigen values")
    public double power = 2;

    public ArrayList<String> calculate(GraphModel g) {
        ArrayList<String> res = new ArrayList<>();
        Matrix a = g.getWeightedAdjacencyMatrix();
        EigenvalueDecomposition ed = a.eig();
        double[] rv = ed.getRealEigenvalues();
        double[] iv = ed.getImagEigenvalues();

        double maxMod = 0;
        double minMod = Double.MAX_VALUE;
        for (int i = 0; i < rv.length; i++) {
            double mod = Math.sqrt(rv[i] * rv[i] + iv[i] * iv[i]);
            if (mod > maxMod) { maxMod = mod; }
            if (mod < minMod) { minMod = mod; }
        }
        res.add("Largest Eigen Value");
        res.add(AlgorithmUtils.round(maxMod, 10) + "");
        res.add("Smallest Eigen Value");
        res.add(AlgorithmUtils.round(minMod, 10) + "");

        res.add("Sum of power of Eigen Values");
        double sum = 0;
        for (int i = 0; i < rv.length; i++) {
            double mod = Math.sqrt(rv[i] * rv[i] + iv[i] * iv[i]);
            sum += Math.pow(mod, power);
        }
        res.add("" + AlgorithmUtils.round(sum, 10));

        res.add("Eigen Values");
        for (int i = 0; i < rv.length; i++) {
            if (iv[i] != 0) {
                res.add("" + AlgorithmUtils.round(rv[i], 10) + " + " + AlgorithmUtils.round(iv[i], 10) + "i");
            } else {
                res.add("" + AlgorithmUtils.round(rv[i], 10));
            }
        }
        return res;
    }

    public String getName() {
        return "Eigen Values";
    }

    @Override
    public String getCategory() {
        return "Spectral- Energies";
    }
}
