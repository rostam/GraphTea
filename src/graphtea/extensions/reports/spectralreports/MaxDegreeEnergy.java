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

@CommandAttitude(name = "max_deg_energy", abbreviation = "_max_deg_energy")
public class MaxDegreeEnergy implements GraphReportExtension<Double> {
    public Double calculate(GraphModel g) {
        Matrix m = AlgorithmUtils.getMaxDegreeAdjacencyMatrix(g);
        return AlgorithmUtils.sumOfEigenValues(m);
    }

    public String getName() {
        return "Maximum Degree Energy";
    }

    public String getDescription() {
        return "Maximum Degree Energy";
    }

    @Override
    public String getCategory() {
        return "Spectral- Energies";
    }
}
