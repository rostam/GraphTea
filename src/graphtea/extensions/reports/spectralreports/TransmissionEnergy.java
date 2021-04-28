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

@CommandAttitude(name = "transmission_energy", abbreviation = "_transener")
public class TransmissionEnergy implements GraphReportExtension<Double> {
    public Double calculate(GraphModel g) {
        Matrix m = AlgorithmUtils.getDiagonalTransMatrix(g);
        EigenvalueDecomposition ed = m.eig();
        double[] rv = ed.getRealEigenvalues();
        double sum = 0;

        //positiv RV
        Double[] prv = new Double[rv.length];
        for (int i = 0; i < rv.length; i++) {
			  prv[i] = Math.abs(rv[i]);
          //  prv[i] = (double)Math.round(prv[i] * 100000d) / 100000d;
            sum += prv[i];
        }
        return sum;
    }

    public String getName() {
        return "Transmission Energy";
    }

    public String getDescription() {
        return "Transmission Energy";
    }

    @Override
    public String getCategory() {
        return "Spectral- Energies";
    }
}
