// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.commandline;

import graphlab.graph.ui.HyperlinkHandler;
import graphlab.platform.core.BlackBoard;

import java.net.URL;

/**
 * @author Azin Azadi
 * @see graphlab.graph.ui.GHTMLPageComponent
 */
public class ShellHyperlinkHandler implements HyperlinkHandler {
    public ShellHyperlinkHandler(Shell shell) {
        this.shell = shell;
    }

    Shell shell;

    public void handle(String command, BlackBoard b, URL currentURL) {
        shell.evaluate(command);
    }
}
