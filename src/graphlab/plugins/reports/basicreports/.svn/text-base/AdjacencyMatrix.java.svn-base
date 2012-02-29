// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.reports.basicreports;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import graphlab.platform.lang.CommandAttitude;
import graphlab.plugins.main.GraphData;
import graphlab.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Mohammad Ali Rostami
 */

@CommandAttitude(name = "eig_values", abbreviation = "_evs")
public class AdjacencyMatrix implements GraphReportExtension {

    double round(double value, int decimalPlace) {
        double power_of_ten = 1;
        while (decimalPlace-- > 0)
            power_of_ten *= 10.0;
        return Math.round(value * power_of_ten)
                / power_of_ten;
    }

    public Object calculate(GraphData gd) {
        try {
            ArrayList<String> res = new ArrayList<String>();
            res.add("Adjacency Matrix");
            Matrix A = gd.getGraph().getWeightedAdjacencyMatrix();
            for(double[] a: A.getArray())
            	res.add(Arrays.toString(a));
            res.add("Eigen Values");
            EigenvalueDecomposition ed = A.eig();
            double rv[] = ed.getRealEigenvalues();
            double iv[] = ed.getImagEigenvalues();

            
            for (int i = 0; i < rv.length; i++)
                if (iv[i] != 0)
                	res.add("" + round(rv[i], 3) + " + " + round(iv[i], 3) + "i");
                else
                	res.add("" + round(rv[i], 3));
            res.add("Eigen Vectors:\n");
            double[][] eigenVectors = ed.getV().getArray();
            for (int k = 0; k < eigenVectors.length; k++)
            		res.add(Arrays.toString(round(eigenVectors[k], 3)));
            return res;
        } catch (Exception e) {
        }
        return "";
    }
    

private double[] round (double[] array, int prec)
{
	double[] res=array;
	for(int i=0;i<array.length;i++)
		res[i]=round(res[i],prec);
	return res;
	
}

    public String getName() {
        return "Spectrum of Adjacency";
    }

    public String getDescription() {
        return "Adjacency Matrix";
    }

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "Spectral";
	}
}
