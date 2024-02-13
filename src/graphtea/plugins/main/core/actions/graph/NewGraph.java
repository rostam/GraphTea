// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.core.actions.graph;

import graphtea.platform.Application;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.ui.UIUtils;

public class NewGraph extends AbstractAction {

    public static final String event = UIUtils.getUIEventKey("New");

    public NewGraph(BlackBoard bb) {
        super(bb);
        this.listen4Event(event);
    }

    public void performAction(String eventName, Object value) {
        new Thread(() -> doJob(blackboard)).start();
    }

    public static BlackBoard doJob(BlackBoard b) {
        Application g = b.getData(Application.APPLICATION_INSTANCE);
        return g.init();
    }
}
