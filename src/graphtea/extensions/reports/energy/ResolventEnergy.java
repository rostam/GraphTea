// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.energy;

import Jama.Matrix;
import graphtea.graph.graph.GraphModel;

/**
 * @author M. Ali Rostami
 */
public class ResolventEnergy extends AbstractEnergyReport {

    @Override
    protected Matrix getMatrix(GraphModel g) {
        return g.getWeightedAdjacencyMatrix();
    }

    @Override
    protected double transform(double eigenvalue, double n, double m) {
        return 1.0 / (n - eigenvalue);
    }

    @Override
    public String getName() {
        return "Resolvent Energy";
    }
}
