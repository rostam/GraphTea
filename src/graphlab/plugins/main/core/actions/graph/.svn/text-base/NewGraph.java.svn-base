// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.core.actions.graph;

import graphlab.platform.Application;
import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.ui.UIUtils;

public class NewGraph extends AbstractAction {

    public static final String event = UIUtils.getUIEventKey("New");

    public NewGraph(BlackBoard bb) {
        super(bb);
        this.listen4Event(event);
    }

    public void performAction(String eventName, Object value) {
        new Thread() {
            public void run() {
                doJob(blackboard);
            }
        }.start();
    }

    public static BlackBoard doJob(BlackBoard b) {
        Application g = b.getData(Application.APPLICATION_INSTANCE);
        return g.init();
    }
}
