// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.commonplugin.reporter.actions;

import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.ui.UIUtils;

/**
 * this class is only for testing bug report, it just throws exception
 *
 * @author azin azadi
 */
public class Exceptioner extends AbstractAction {
    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */
    public Exceptioner(BlackBoard bb) {
        super(bb);
        listen4Event(UIUtils.getUIEventKey("exceptioner"));
    }

    public void performAction(String eventName, Object value) {
        throw new RuntimeException("just an Exceptioner! :D");
    }
}
