// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.spectralreports;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import graphtea.extensions.Utils;
import graphtea.graph.graph.GraphModel;
import graphtea.library.util.Complex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

/**
 * @author M. Ali Rostami
 */

@CommandAttitude(name = "eig_values", abbreviation = "_evs")
public class KirchhoffIndex implements GraphReportExtension {

    double round(double value, int decimalPlace) {
        double power_of_ten = 1;
        while (decimalPlace-- > 0)
            power_of_ten *= 10.0;
        return Math.round(value * power_of_ten)
                / power_of_ten;
    }

    public Object calculate(GraphModel g) {
        double power = 1;
        try {
            Matrix B = g.getWeightedAdjacencyMatrix();
            Matrix A = Utils.getLaplacian(B);
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
            double sum = 0;
            double sum_i = 0;
            for(int i=0;i < rv.length;i++)
                if(Math.abs(round(rv[i],6)) != 0) {
                    sum += 1 / Math.abs(rv[i]);
                }
            sum *= g.numOfVertices();
            for(int i=0;i < iv.length;i++)
                sum_i +=  Math.abs(iv[i]);

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
                return "" + round(num.re(), 3) + " + "
                        + round(num.im(), 3) + "i";
            } else {
                return "" + round(sum, 3);
            }
        } catch (Exception e) {
        }
        return null;
    }

    public String getName() {
        return "Kirchhoff Index";
    }

    public String getDescription() {
        return "Kirchhoff Index";
    }

    @Override
    public String getCategory() {
        return "Spectral";
    }
}
