// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.energy;

import Jama.Matrix;
import graphtea.extensions.AlgorithmUtils;
import graphtea.graph.graph.GraphModel;

/**
 * @author Ali Rostami
 */
public class SignlessLaplacianEstrada extends AbstractEstradaReport {

    @Override
    protected Matrix getMatrix(GraphModel g) {
        return AlgorithmUtils.getSignlessLaplacian(g.getWeightedAdjacencyMatrix());
    }

    @Override
    protected String getLabel() {
        return " Signless Laplacian Estrada ";
    }

    @Override
    public String getName() {
        return "Signless Laplacian Estrada";
    }
}
