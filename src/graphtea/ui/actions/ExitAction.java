// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui.actions;

import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.preferences.lastsettings.StorableOnExit;
import graphtea.ui.UIUtils;

public class ExitAction extends AbstractAction implements StorableOnExit {

    public static final String event = UIUtils.getUIEventKey("Exit");

    public ExitAction(BlackBoard bb) {
        super(bb);
        this.listen4Event(event);
    }

    public void performAction(String eventName, Object value) {
        // todo: correct it for multi frames

        UIUtils.exit();
    }

}
