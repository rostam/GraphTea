// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.visualization.corebasics.extension;

import graphlab.graph.atributeset.GraphAttrSet;
import graphlab.graph.graph.GraphModel;
import graphlab.platform.core.BlackBoard;
import graphlab.plugins.visualization.corebasics.animator.GeneralAnimator;
import graphlab.ui.AbstractExtensionAction;

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
        GraphModel g = (GraphModel) blackboard.getData(GraphAttrSet.name);
        ve.setWorkingGraph(g);
        t = new GeneralAnimator(ve.getNewVertexPlaces(), g, blackboard);
        t.start();
    }
}
