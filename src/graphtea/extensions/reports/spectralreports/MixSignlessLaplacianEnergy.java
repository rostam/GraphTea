package graphtea.extensions.reports.spectralreports;


import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import graphtea.extensions.reports.Utils;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.library.util.Complex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.Vector;

@CommandAttitude(name = "mix_eig_values", abbreviation = "_mevs")
public class MixSignlessLaplacianEnergy implements GraphReportExtension {
    double round(double value, int decimalPlace) {
        double power_of_ten = 1;
        while (decimalPlace-- > 0)
            power_of_ten *= 10.0;
        return Math.round(value * power_of_ten)
                / power_of_ten;
    }


    String signlessLaplacianEnergy(GraphModel g) {
        double power = 1;
        try {
            Matrix B = g.getWeightedAdjacencyMatrix();
            Matrix A = Utils.getSignlessLaplacian(B);
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
                sum += Math.pow(Math.abs(rv[i]),power);
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
                return "" + round(num.re(), 5) + " + "
                        + round(num.im(), 5) + "i";
            } else {
                return "" + round(sum, 5);
            }
        } catch (Exception e) {
        }
        return null;
    }

    String laplacianEnergy(GraphModel g) {
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
                sum += Math.pow(Math.abs(rv[i]),power);
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
                return "" + round(num.re(), 5) + " + "
                        + round(num.im(), 5) + "i";
            } else {
                return "" + round(sum, 5);
            }
        } catch (Exception e) {
        }
        return null;
    }


    @Override
    public Object calculate(GraphModel g) {
        RenderTable rt = new RenderTable();
        Vector<String> titles = new Vector<>();
        titles.add("Laplacian Energy");
        titles.add("Signless Laplacian Energy");
        rt.setTitles(titles);
        Vector<String> results = new Vector<>();
        results.add(laplacianEnergy(g));
        results.add(signlessLaplacianEnergy(g));
        return results;
    }

    @Override
    public String getCategory() {
        return "Spectral";
    }

    @Override
    public String getName() {
        return "Mix Signless Laplacian Energy";
    }

    @Override
    public String getDescription() {
        return "Mix Signless Laplacian Energy";
    }
}
