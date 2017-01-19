// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/
package graphtea.extensions.actions;
import graphtea.extensions.Utils;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.extension.GraphActionExtension;

/**
 * Creates a line graph from the current graph and shows it in a new tab
 *
 * @author Mohammad Ali Rostami
 * @author Azin Azadi
 */
public class LineGraph implements GraphActionExtension {

    public void action(GraphData graphData) {
        graphData.core.showGraph(Utils.createLineGraph(graphData.getGraph()));
    }

    public String getName() {
        return "Line Graph";
    }

    public String getDescription() {
        return "Makes a graph including the edges of original graph as vertices ";
    }

    @Override
    public String getCategory() {
        return "Transformations";
    }
}
