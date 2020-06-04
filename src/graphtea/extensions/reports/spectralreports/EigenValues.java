// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.spectralreports;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import graphtea.extensions.AlgorithmUtils;
import graphtea.graph.graph.GraphModel;
import graphtea.library.util.Complex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;

/**
 * @author M. Ali Rostami
 */

@CommandAttitude(name = "eig_values", abbreviation = "_evs")
public class EigenValues implements GraphReportExtension,Parametrizable {

    @Parameter(name = "power:", description = "The power of the eigen values")
    public double power = 2;

    public Object calculate(GraphModel g) {
        ArrayList<String> res = new ArrayList<>();
        Matrix A = g.getWeightedAdjacencyMatrix();
        EigenvalueDecomposition ed = A.eig();
        double[] rv = ed.getRealEigenvalues();
        double[] iv = ed.getImagEigenvalues();
        double maxrv=0;
        double minrv=1000000;
        for(double value : rv) {
            double tval = Math.abs(value);
            if(maxrv < tval) maxrv=tval;
            if(minrv > tval) minrv=tval;
        }
        res.add("Largest Eigen Value");
        res.add(AlgorithmUtils.round(maxrv, 10)+"");
        res.add("Smallest Eigen Value");
        res.add(AlgorithmUtils.round(minrv, 10)+"");

        res.add("Sum of power of Eigen Values");
        double sum = 0;
        double sum_i = 0;
        for (double aRv : rv) sum += Math.pow(Math.abs(aRv), power);
        for (double anIv : iv) sum_i += Math.abs(anIv);

        if (sum_i != 0) {
            sum_i=0;
            Complex num = new Complex(0,0);
            for(int i=0;i < iv.length;i++) {
                Complex tmp = new Complex(rv[i], iv[i]);
                Complex.pow(new Complex(power,0));
                num.plus(tmp);
            }
            res.add("" + AlgorithmUtils.round(num.re(), 10) + " + "
                    + AlgorithmUtils.round(num.im(), 10) + "i");
        } else {
            res.add("" + AlgorithmUtils.round(sum, 10));
        }
        res.add("Eigen Values");
        for (int i = 0; i < rv.length; i++) {
            if (iv[i] != 0)
                res.add("" + AlgorithmUtils.round(rv[i], 10) + " + " + AlgorithmUtils.round(iv[i], 10) + "i");
            else
                res.add("" + AlgorithmUtils.round(rv[i], 10));
        }
        return res;
    }

    public String getName() {
        return "Eigen Values";
    }

    public String getDescription() {
        return "Eigen Values";
    }

	@Override
	public String getCategory() {
		return "Spectral";
	}

    @Override
    public String checkParameters() {
        return null;
    }
}
