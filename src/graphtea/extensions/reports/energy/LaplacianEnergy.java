// GraphTea Project:bvb   http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.energy;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.reports.zagreb.ZagrebIndexFunctions;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.graph.graph.Vertex;
import graphtea.library.util.Complex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

import static graphtea.extensions.reports.Utils.getLaplacian;

/**
 * @author Ali Rostami

 */

@CommandAttitude(name = "newInvs", abbreviation = "_newInv")
public class LaplacianEnergy implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Laplacian Energy";
    }

    public String getDescription() {
        return "Laplacian Energy";
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();

        titles.add("m ");
        titles.add("n ");
        titles.add(" Laplacian Energy ");
        ret.setTitles(titles);

        Matrix A = g.getWeightedAdjacencyMatrix();
        EigenvalueDecomposition ed = A.eig();
        double rv[] = ed.getRealEigenvalues();
        double sum = 0;

        //positiv RV
        Double[] prv = new Double[rv.length];
        for (int i = 0; i < rv.length; i++) {
            prv[i] = Math.abs(rv[i]);
            prv[i] = (double) Math.round(prv[i] * 100000d) / 100000d;
            sum += prv[i];
        }

        Arrays.sort(prv, Collections.reverseOrder());

        double maxDeg = 0;
        double maxDeg2 = 0;
        double minDeg = Integer.MAX_VALUE;

        ArrayList<Integer> al = AlgorithmUtils.getDegreesList(g);
        Collections.sort(al);
        maxDeg = al.get(al.size() - 1);
        if (al.size() - 2 >= 0) maxDeg2 = al.get(al.size() - 2);
        else maxDeg2 = maxDeg;
        minDeg = al.get(0);

        if (maxDeg2 == 0) maxDeg2 = maxDeg;

        double a = 0;
        double b = 0;

        for (Vertex v : g) {
            if (g.getDegree(v) == maxDeg) a++;
            if (g.getDegree(v) == minDeg) b++;
        }
        if (maxDeg == minDeg) b = 0;

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        double M12 = zif.getSecondZagreb(1);
        double M21 = zif.getFirstZagreb(1);
        double M22 = zif.getSecondZagreb(2);
        double Mm11 = zif.getFirstZagreb(-2);

        Vector<Object> v = new Vector<>();
        String tmp = calc(g).toString();

        v.add(m);
        v.add(n);
        v.add(Double.parseDouble(tmp));
        //1
        // v.add(Math.sqrt(2 * m * n));
        //2
        // v.add(prv[0]
        //          + Math.sqrt((n - 1) * (2 * m - Math.pow(prv[0], 2))));
        //3
        //  v.add(n * Math.sqrt(M21 / (2 * m)));
        //4
        //  double up = (n - 1) * Math.sqrt((M21 - maxDeg * maxDeg) * (2 * m - prv[0] * prv[0]));
        //   double down = 2 * m - maxDeg;
        //   v.add(prv[0] + up / down);
        //5
        //   double tmp = Math.sqrt((n - 1) * (2 * m - Math.pow(prv[0], 2))
        //            + (Math.pow(n - 2, 2) / (n - 1)) * Math.pow(prv[1] - prv[prv.length - 1], 2));

        //    v.add(prv[0] + tmp);
        //6
        //    tmp = Math.sqrt((n - 1) * (2 * m - Math.pow(prv[0], 2))
        //             + (((n - 2) * 2) / (n - 1)) * Math.pow(prv[1] - prv[prv.length - 1], 2));
//
        //     v.add(prv[0] + tmp);

        //Laplacian Energy

        //   v.add(sum);
        //   v.add((2*m/n) + ((2/(n-1))*(Math.sqrt(((n-1)*((2*m) + M21)) - (4*m*m) ) )) );

        //   v.add(Math.sqrt((2/(n-1))*(((n-1)*((2*m) + M21)) - (4*m*m) ) )  );

        // v.add((2*m/n) + ((2/(n-1))*(Math.sqrt( (2*m*((n*(n-1) - (2*m)) )) / n ) )) );
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
        // TODO Auto-generated method stub
        return "OurWorks-Graph Energy";
    }

    public Object calc(GraphModel g) {
        double m = g.getEdgesCount();
        double n = g.getVerticesCount();
        double power = 1;
        try {
            Matrix A = g.getWeightedAdjacencyMatrix();
            A = getLaplacian(A);
            EigenvalueDecomposition ed = A.eig();
            double rv[] = ed.getRealEigenvalues();
            double iv[] = ed.getImagEigenvalues();
            double maxrv = 0;
            double minrv = 1000000;
            for (double value : rv) {
                double tval = Math.abs(value);
                if (maxrv < tval) maxrv = tval;
                if (minrv > tval) minrv = tval;
            }
            double sum = 0;
            double sum_i = 0;
            for (int i = 0; i < rv.length; i++)
                sum += Math.pow(Math.abs(rv[i] - (2 * m / n)), power);
            for (int i = 0; i < iv.length; i++)
                sum_i += Math.abs(iv[i]);

            if (sum_i != 0) {
                sum_i = 0;
                Complex num = new Complex(0, 0);
                for (int i = 0; i < iv.length; i++) {
                    Complex tmp = new Complex(rv[i], iv[i]);
                    tmp.pow(new Complex(power, 0));
                    num.plus(tmp);
                }
                return "" + round(num.re(), 5) + " + "
                        + round(num.im(), 5) + "i";
            } else {
                return "" + round(sum, 5);
            }
        } catch (Exception e) {
        }
        return null;
    }
}
