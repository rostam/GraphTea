// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.spectralreports;

import Jama.Matrix;
import graphtea.extensions.AlgorithmUtils;
import graphtea.graph.graph.GraphModel;
import graphtea.plugins.reports.extension.GraphReportExtension;

/**
 * Template for Kirchhoff-type indices: sum 1/|λ| over non-zero Laplacian eigenvalues,
 * then scale by a graph parameter. Subclasses supply the matrix and the scale factor.
 */
abstract class AbstractKirchhoffIndex implements GraphReportExtension<String> {

    protected abstract Matrix getMatrix(GraphModel g);

    protected abstract double getMultiplier(GraphModel g);

    @Override
    public final String calculate(GraphModel g) {
        try {
            double[] rv = getMatrix(g).eig().getRealEigenvalues();
            double sum = 0;
            for (double value : rv) {
                if (Math.abs(AlgorithmUtils.round(value, 6)) != 0) {
                    sum += 1.0 / Math.abs(value);
                }
            }
            sum *= getMultiplier(g);
            return String.valueOf(AlgorithmUtils.round(sum, 5));
        } catch (Exception ignored) {
        }
        return null;
    }
}
