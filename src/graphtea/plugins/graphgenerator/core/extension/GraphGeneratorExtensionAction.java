// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.graphgenerator.core.extension;

import graphtea.graph.graph.GraphModel;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.graphgenerator.GraphGenerator;
import graphtea.plugins.graphgenerator.core.SimpleGeneratorInterface;
import graphtea.ui.extension.AbstractExtensionAction;

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

    @Override
    public void performExtension() {
//        GraphModel g = blackboard.get(GraphAttrSet.name);
        GraphGenerator.generateInRectangularBounds(getTarget(), blackboard);
    }

    @Override
    public GraphModel generateGraph() {
        return GraphGenerator.getGraph(false, (SimpleGeneratorInterface) getTarget());
    }

}
