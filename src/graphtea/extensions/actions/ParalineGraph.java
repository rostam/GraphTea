// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.extensions.actions;

import graphtea.extensions.Utils;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.extension.GraphActionExtension;


/**
 * Creates a line graph from the current graph and shows it in a new tab
 *
 * @author Mohammad Ali Rostami
 * @author Azin Azadi
 */
public class ParalineGraph implements GraphActionExtension , Parametrizable {
    @Parameter
    public int k = 2;

    public void action(GraphData graphData) {
        graphData.core.showGraph(Utils.createLineGraph(BarycentricSubdivisionGraph.createBarycentricGraph(graphData.getGraph(),k)));
    }

    public String getName() {
        return "Paraline Graph";
    }

    public String getDescription() {
        return "Paraline Graph";
    }

    @Override
    public String checkParameters() {
        return null;
    }

    @Override
    public String getCategory() {
        return "Transformations";
    }
}
