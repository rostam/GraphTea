// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.spectralreports;

import graphtea.extensions.AlgorithmUtils;
import graphtea.graph.graph.GraphModel;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

/**
 * @author M. Ali Rostami
 */
@CommandAttitude(name = "distance_energy", abbreviation = "_distener")
public class DistanceEnergy implements GraphReportExtension<Double> {
    public String getName() { return "Distance Energy"; }
    public String getDescription() { return "Distance Energy"; }
    public String getCategory() { return "Spectral- Energies"; }

    public Double calculate(GraphModel g) {
        return AlgorithmUtils.spectralEnergy(AlgorithmUtils.getDistanceAdjacencyMatrix(g), 0);
    }
}
