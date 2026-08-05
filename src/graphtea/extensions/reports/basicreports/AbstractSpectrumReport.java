// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.basicreports;

import Jama.Matrix;
import graphtea.extensions.AlgorithmUtils;
import graphtea.graph.graph.GraphModel;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Template for spectral reports: builds a matrix, then lists its rows,
 * eigenvalues, and eigenvectors. Subclasses supply the matrix and its label.
 */
abstract class AbstractSpectrumReport implements GraphReportExtension<ArrayList<String>> {

    protected abstract Matrix getMatrix(GraphModel g);

    protected abstract String getMatrixLabel();

    @Override
    public final ArrayList<String> calculate(GraphModel g) {
        ArrayList<String> res = new ArrayList<>();
        res.add(getMatrixLabel());
        if (g == null || g.getVerticesCount() == 0) {
            // A zero-by-zero matrix makes Jama's eigenvalue decomposition read index -1.
            // That used to surface as an ArrayIndexOutOfBoundsException thrown on a
            // background thread, which the user never saw at all.
            res.add("This report needs a graph with at least one vertex.");
            return res;
        }
        Matrix a = getMatrix(g);
        for (double[] row : a.getArray()) {
            res.add(Arrays.toString(row));
        }
        res.add("Eigen Values");
        res.addAll(AlgorithmUtils.formatEigenDecomposition(a));
        return res;
    }

    @Override
    public String getCategory() {
        return "Spectral";
    }
}
