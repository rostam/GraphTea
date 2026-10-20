// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.reports.ui;

import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.ui.UIUtils;

/**
 * Opens the Conjecture Checker from Reports ▸ Conjecture Checker….
 *
 * @author M. Ali Rostami
 */
public class ConjectureCheckerAction extends AbstractAction {

    /**
     * @param bb the blackboard of the action
     */
    public ConjectureCheckerAction(BlackBoard bb) {
        super(bb);
        listen4Event(UIUtils.getUIEventKey("conjecture checker"));
    }

    @Override
    public void performAction(String eventName, Object value) {
        ConjectureCheckerDialog.open(blackboard);
    }
}
