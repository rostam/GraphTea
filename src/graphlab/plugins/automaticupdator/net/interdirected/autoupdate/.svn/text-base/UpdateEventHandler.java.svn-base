// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.automaticupdator.net.interdirected.autoupdate;

import org.tmatesoft.svn.core.SVNCancelException;
import org.tmatesoft.svn.core.wc.ISVNEventHandler;
import org.tmatesoft.svn.core.wc.SVNEvent;
import org.tmatesoft.svn.core.wc.SVNEventAction;

public class UpdateEventHandler implements ISVNEventHandler {

    private GuiStatusScreen gss = null;

    public void setGuiStatusScreen(GuiStatusScreen GuiScreen) {
        gss = GuiScreen;
    }

    public void handleEvent(SVNEvent event, double progress) {
        SVNEventAction action = event.getAction();
        String pathChangeType = "";
        if (action == SVNEventAction.UPDATE_ADD) {
            pathChangeType = "A";
        } else if (action == SVNEventAction.UPDATE_DELETE) {
            pathChangeType = "D";
        } else if (action == SVNEventAction.UPDATE_UPDATE) {
            pathChangeType = "C";
        }
        if (gss != null) {
            if (pathChangeType.length() > 0) gss.appendText(pathChangeType + ": " + event.getPath());
        } else {
            if (pathChangeType.length() > 0) System.out.println(pathChangeType + ": " + event.getPath());
        }
    }

    public void checkCancelled() throws SVNCancelException {
    }
}
