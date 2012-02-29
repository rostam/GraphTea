// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.ui.extension;

import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.ui.UIUtils;

/**
 * wrap a UIAction in an AbstractAction
 *
 * @author azin azadi

 */
public class UIActionExtensionAction extends AbstractAction {
    private UIActionExtension ac;

    public UIActionExtensionAction(BlackBoard bb, UIActionExtension action) {
        super(bb);
        this.ac = action;
    }

    public void setUIEvent(String actionId) {
        listen4Event(UIUtils.getUIEventKey(actionId));
    }

    public void performAction(String eventName, Object value) {
        ac.actionPerformed(blackboard);
    }
}
