// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.basicreports;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import graphtea.extensions.AlgorithmUtils;
import graphtea.graph.graph.GraphModel;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Mohammad Ali Rostami
 */

@CommandAttitude(name = "eig_values", abbreviation = "_evs")
public class AdjacencyMatrix implements GraphReportExtension<ArrayList<String>> {

    public ArrayList<String> calculate(GraphModel g) {
        ArrayList<String> res = new ArrayList<>();
        res.add("Adjacency Matrix");
        Matrix A = g.getWeightedAdjacencyMatrix();
        for(double[] a: A.getArray())
            res.add(Arrays.toString(a));
        res.add("Eigen Values");
        EigenvalueDecomposition ed = A.eig();
        double[] rv = ed.getRealEigenvalues();
        double[] iv = ed.getImagEigenvalues();


        for (int i = 0; i < rv.length; i++)
            if (iv[i] != 0)
                res.add("" + AlgorithmUtils.round(rv[i], 5) + " + " + AlgorithmUtils.round(iv[i], 5) + "i");
            else
                res.add("" + AlgorithmUtils.round(rv[i], 5));
        res.add("Eigen Vectors:\n");
        double[][] eigenVectors = ed.getV().getArray();
        for (double[] eigenVector : eigenVectors) res.add(Arrays.toString(AlgorithmUtils.round(eigenVector, 5)));
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
		return "Spectral";
	}
}
