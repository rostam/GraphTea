// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.visualization.corebasics.extension;

import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.graph.GraphModel;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.visualization.corebasics.animator.GeneralAnimator;
import graphtea.ui.extension.AbstractExtensionAction;

/**
 * @author Rouzbeh Ebrahimi
 * Email: ruzbehus@gmail.com
 */
public class VisualizationExtensionAction extends AbstractExtensionAction<VisualizationExtension> {
    public VisualizationExtensionAction(BlackBoard bb, VisualizationExtension sp) {
        super(bb, sp);
    }

    public String getParentMenuName() {
        return "Visualization";
    }

    public void performExtension() {
        GeneralAnimator t;
        VisualizationExtension ve = getTarget();
        GraphModel g = blackboard.getData(GraphAttrSet.name);
        ve.setWorkingGraph(g);
        t = new GeneralAnimator(ve.getNewVertexPlaces(), g, blackboard);
        t.start();
    }
}
