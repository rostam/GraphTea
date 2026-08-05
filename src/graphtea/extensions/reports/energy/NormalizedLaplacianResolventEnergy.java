// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.energy;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import graphtea.extensions.AlgorithmUtils;
import graphtea.graph.graph.GraphModel;
import graphtea.plugins.reports.extension.GraphReportExtension;

/**
 * @author M. Ali Rostami
 */
public class NormalizedLaplacianResolventEnergy implements GraphReportExtension<String> {

    public String calculate(GraphModel g) {
        try {
            Matrix a = AlgorithmUtils.getNormalizedLaplacian(g);
            EigenvalueDecomposition ed = a.eig();
            double[] rv = ed.getRealEigenvalues();
            double[] iv = ed.getImagEigenvalues();
            double sumIm = 0;
            for (double v : iv) {
                sumIm += Math.abs(v);
            }
            if (sumIm != 0) {
                return "N/A (imaginary eigenvalues)";
            }
            double sum = 0;
            for (double value : rv) {
                if (Math.abs(AlgorithmUtils.round(value, 6)) != 0) {
                    sum += 1.0 / (3 - Math.abs(value));
                }
            }
            sum *= 2 * g.getEdgesCount();
            return String.valueOf(AlgorithmUtils.round(sum, 5));
        } catch (Exception ignored) {
        }
        return null;
    }

    public String getName() {
        return "Normalized Laplacian Resolvent Energy";
    }

    @Override
    public String getCategory() {
        return "Spectral- Energies";
    }
}
