// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.extension;

import graphtea.platform.core.BlackBoard;
import graphtea.plugins.main.GraphData;
import graphtea.ui.extension.AbstractExtensionAction;

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
        return "Operators";
    }

    public void performExtension() {
        ga.action(gd);
    }

}
