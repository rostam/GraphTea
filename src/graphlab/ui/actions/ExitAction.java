// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.ui.actions;

import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.platform.preferences.lastsettings.StorableOnExit;
import graphlab.ui.UIUtils;

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
