// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.graphgenerator.core.extension;

import graphlab.graph.graph.GraphModel;
import graphlab.platform.core.BlackBoard;
import graphlab.plugins.graphgenerator.GraphGenerator;
import graphlab.ui.AbstractExtensionAction;

import java.awt.*;

/**
 * @author azin azadi

 */
public class GraphGeneratorExtensionAction extends AbstractExtensionAction<GraphGeneratorExtension> {
    public GraphGeneratorExtensionAction(BlackBoard bb, GraphGeneratorExtension sp) {
        super(bb, sp);
    }

    public String getParentMenuName() {
        return "Generate Graph";
    }

    @Override
    public GraphModel performExtensionInCommandLine() {
        return GraphGenerator.generateGraphInRect(blackboard,getTarget(), new Rectangle(100, 100, 600,600));
    }

    public void performExtension() {
//        GraphModel g = blackboard.get(GraphAttrSet.name);
        GraphGenerator.generateInRectangularBounds(getTarget(), blackboard);
    }

}
