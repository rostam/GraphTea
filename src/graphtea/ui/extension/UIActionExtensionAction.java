// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.ui.extension;

import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.ui.UIUtils;

/**
 * wrap a UIAction in an AbstractAction
 *
 * @author azin azadi

 */
public class UIActionExtensionAction extends AbstractAction {
    private final UIActionExtension ac;

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
