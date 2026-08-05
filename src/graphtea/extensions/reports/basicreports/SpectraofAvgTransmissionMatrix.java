// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.basicreports;

import Jama.Matrix;
import graphtea.extensions.AlgorithmUtils;
import graphtea.graph.graph.GraphModel;

/**
 * @author Mohammad Ali Rostami
 */
public class SpectraofAvgTransmissionMatrix extends AbstractSpectrumReport {

    @Override
    protected Matrix getMatrix(GraphModel g) {
        return AlgorithmUtils.getAverageTransMatrix(g);
    }

    @Override
    protected String getMatrixLabel() {
        return "Average Transmission Matrix";
    }

    @Override
    public String getName() {
        return "Spectrum of t(G) Matrix";
    }

    @Override
    public String getCategory() {
        return "Spectral";
    }
}
