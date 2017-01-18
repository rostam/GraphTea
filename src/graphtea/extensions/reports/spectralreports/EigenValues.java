// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.spectralreports;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
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

    double round(double value, int decimalPlace) {
        double power_of_ten = 1;
        while (decimalPlace-- > 0)
            power_of_ten *= 10.0;
        return Math.round(value * power_of_ten)
                / power_of_ten;
    }

    public Object calculate(GraphModel g) {
        ArrayList<String> res = new ArrayList<>();
        Matrix A = g.getWeightedAdjacencyMatrix();
        EigenvalueDecomposition ed = A.eig();
        double rv[] = ed.getRealEigenvalues();
        double iv[] = ed.getImagEigenvalues();
        double maxrv=0;
        double minrv=1000000;
        for(double value : rv) {
            double tval = Math.abs(value);
            if(maxrv < tval) maxrv=tval;
            if(minrv > tval) minrv=tval;
        }
        res.add("Largest Eigen Value");
        res.add(round(maxrv, 3)+"");
        res.add("Smallest Eigen Value");
        res.add(round(minrv, 3)+"");

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
                tmp.pow(new Complex(power,0));
                num.plus(tmp);
            }
            res.add("" + round(num.re(), 3) + " + "
                    + round(num.im(), 3) + "i");
        } else {
            res.add("" + round(sum, 3));
        }
        res.add("Eigen Values");
        for (int i = 0; i < rv.length; i++) {
            if (iv[i] != 0)
                res.add("" + round(rv[i], 3) + " + " + round(iv[i], 3) + "i");
            else
                res.add("" + round(rv[i], 3));
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
