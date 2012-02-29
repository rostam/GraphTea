// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.extension;

import graphlab.platform.core.BlackBoard;
import graphlab.plugins.main.GraphData;
import graphlab.ui.AbstractExtensionAction;

/**
 * @author azin azadi

 */
public class GraphActionExtensionAction extends AbstractExtensionAction {
    private GraphActionExtension ga;
    private GraphData gd;

    public GraphActionExtensionAction(BlackBoard bb, GraphActionExtension ga) {
        super(bb, ga);
        this.ga = ga;
        gd = new GraphData(blackboard);
    }

    public String getParentMenuName() {
        return "Actions";
    }

    public void performExtension() {
        ga.action(gd);
    }

}
