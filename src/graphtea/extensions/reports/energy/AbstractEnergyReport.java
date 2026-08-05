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
 * Template for single-valued energy reports: get a matrix, decompose it,
 * apply a per-eigenvalue transform, and sum the results.
 * Subclasses supply the matrix and the transform.
 */
public abstract class AbstractEnergyReport implements GraphReportExtension<String> {

    protected abstract Matrix getMatrix(GraphModel g);

    /** Maps one real eigenvalue and graph parameters n (vertices), m (edges) to a summand. */
    protected abstract double transform(double eigenvalue, double n, double m);

    @Override
    public final String calculate(GraphModel g) {
        try {
            double n = g.getVerticesCount();
            double m = g.getEdgesCount();
            EigenvalueDecomposition ed = getMatrix(g).eig();
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
            for (double v : rv) {
                sum += transform(v, n, m);
            }
            return String.valueOf(AlgorithmUtils.round(sum, 5));
        } catch (Exception ignored) {
        }
        return null;
    }

    @Override
    public String getCategory() {
        return "Spectral- Energies";
    }
}
