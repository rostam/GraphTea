// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.spectralreports;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.reports.Utils;
import graphtea.graph.graph.GraphModel;
import graphtea.library.util.Complex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

/**
 * @author M. Ali Rostami
 */

@CommandAttitude(name = "eig_values", abbreviation = "_evs")
public class SignlessLaplacianEnergy implements GraphReportExtension<String> {

    public String calculate(GraphModel g) {
        double power = 1;
        try {
            Matrix B = g.getWeightedAdjacencyMatrix();
            Matrix A = Utils.getSignlessLaplacian(B);
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
            double sum = 0;
            double sum_i = 0;
            for (double value : rv) sum += Math.pow(Math.abs(value), power);
            for (double v : iv) sum_i += Math.abs(v);

            if (sum_i != 0) {
                //here is completely false
                System.out.println("imaginary part is available. So this function does not work.");
                sum_i=0;
                Complex num = new Complex(0,0);
//                for(int i=0;i < iv.length;i++) {
//                    Complex tmp = new Complex(rv[i], iv[i]);
//                    System.out.println(tmp);
//                    tmp.pow(new Complex(power,0));
//                    System.out.println(power);
//                    System.out.println(tmp);
//                    num.plus(tmp);
//                }
                return "" + AlgorithmUtils.round(num.re(), 5) + " + "
                        + AlgorithmUtils.round(num.im(), 5) + "i";
            } else {
                return "" + AlgorithmUtils.round(sum, 5);
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    public String getName() {
        return "Signless Laplacian Energy";
    }

    public String getDescription() {
        return "Signless Laplacian Energy";
    }

    @Override
    public String getCategory() {
        return "Spectral";
    }
}
