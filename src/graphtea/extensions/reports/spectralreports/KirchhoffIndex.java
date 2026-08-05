// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.spectralreports;

import Jama.Matrix;
import graphtea.extensions.AlgorithmUtils;
import graphtea.graph.graph.GraphModel;

/**
 * @author M. Ali Rostami
 */
public class KirchhoffIndex extends AbstractKirchhoffIndex {

    @Override
    protected Matrix getMatrix(GraphModel g) {
        return AlgorithmUtils.getLaplacian(g.getWeightedAdjacencyMatrix());
    }

    @Override
    protected double getMultiplier(GraphModel g) {
        return g.numOfVertices();
    }

    @Override
    public String getName() {
        return "Kirchhoff Index";
    }

    @Override
    public String getCategory() {
        return "Spectral- Energies";
    }
}
