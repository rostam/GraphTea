// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.commonplugin.actiongrouping;

import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;

/**
 * this class is used whenever you have more than one action
 * and you want them to be enabled and desabled alltogether,
 * so they can be thinked as a single action.
 *
 * @author azin azadi
 */
public abstract class ActionGrouper extends AbstractAction {

    public ActionGrouper(BlackBoard bb) {
        super(bb);
    }

    public abstract AbstractAction[] getActions();

    public void performAction(String eventName, Object value) {
        enableActions();
    }

    //enables all of the actions in the group
    public void enableActions() {
        for (AbstractAction _ : getActions())
            _.enable();
    }

    public void disable() {
        super.disable();
        disableActions();
    }

    //disables all actions in group
    public void disableActions() {
        for (AbstractAction _ : getActions())
            _.disable();
    }
}
