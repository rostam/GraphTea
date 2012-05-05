// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.commonplugin.help.actions;

import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.ui.UIUtils;

public class Help extends AbstractAction {

    public static final String EVENT_KEY = UIUtils.getUIEventKey("Help");

    public Help(BlackBoard bb) {
        super(bb);
        listen4Event(EVENT_KEY);
    }

    @Override
    public void performAction(String eventName, Object value) {
        new Thread() {
            public void run() {
                showHelpWindow();
            }
        }.start();
    }

    public static void showHelpWindow() {
        new graphtea.plugins.commonplugin.help.actions.HelpWindow().setVisible(true);
    }

}
