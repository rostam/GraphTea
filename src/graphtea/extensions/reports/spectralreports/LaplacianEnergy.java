// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.spectralreports;

import Jama.Matrix;
import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.reports.energy.AbstractEnergyReport;
import graphtea.graph.graph.GraphModel;

/**
 * @author M. Ali Rostami
 */
public class LaplacianEnergy extends AbstractEnergyReport {

    @Override
    protected Matrix getMatrix(GraphModel g) {
        return AlgorithmUtils.getLaplacian(g.getWeightedAdjacencyMatrix());
    }

    @Override
    protected double transform(double eigenvalue, double n, double m) {
        return Math.abs(eigenvalue - (2 * m) / n);
    }

    @Override
    public String getName() {
        return "Laplacian Energy";
    }
}
