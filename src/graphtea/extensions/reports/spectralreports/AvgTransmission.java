// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.spectralreports;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import graphtea.extensions.AlgorithmUtils;
import graphtea.graph.graph.GraphModel;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

/**
 * @author M. Ali Rostami
 */

@CommandAttitude(name = "distance_energy", abbreviation = "_distener")
public class AvgTransmission implements GraphReportExtension<Double> {
    public Double calculate(GraphModel g) {
        Matrix m = AlgorithmUtils.getDiagonalTransMatrix(g);
        EigenvalueDecomposition ed = m.eig();
		double n = g.getVerticesCount();
        double[] rv = ed.getRealEigenvalues();
        double sum = 0;
		        double sum1= 0;

        //positiv RV
        Double[] prv = new Double[rv.length];
        for (int i = 0; i < rv.length; i++) {
            prv[i] = Math.abs(rv[i]);
            sum += prv[i];
        }
		sum1 = (sum/n);
        return sum1;
    }

    public String getName() {
        return "t(G)";
    }

    public String getDescription() {
        return "t(G)";
    }

    @Override
    public String getCategory() {
        return "Spectral- Energies";
    }
}
