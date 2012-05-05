// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.commonplugin.reporter.actions;

import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.ui.UIUtils;

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
