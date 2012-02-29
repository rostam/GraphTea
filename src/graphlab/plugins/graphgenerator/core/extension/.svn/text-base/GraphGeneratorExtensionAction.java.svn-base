// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.graphgenerator.core.extension;

import graphlab.graph.graph.GraphModel;
import graphlab.platform.core.BlackBoard;
import graphlab.plugins.graphgenerator.GraphGenerator;
import graphlab.ui.AbstractExtensionAction;

/**
 * @author azin azadi

 */
public class GraphGeneratorExtensionAction extends AbstractExtensionAction<GraphGeneratorExtension> {
    public GraphGeneratorExtensionAction(BlackBoard bb, GraphGeneratorExtension sp) {
        super(bb, sp);
    }

    public String getParentMenuName() {
        return "Graph.Generate";
    }

    @Override
    public GraphModel performExtensionInCommandLine() {
        return target.generateGraph();
    }

    public void performExtension() {
//        GraphModel g = blackboard.get(GraphAttrSet.name);
        GraphGenerator.generateInRectangularBounds(getTarget(), blackboard);
    }

}
