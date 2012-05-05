// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.commandline;

import graphtea.graph.ui.HyperlinkHandler;
import graphtea.platform.core.BlackBoard;

import java.net.URL;

/**
 * @author Azin Azadi
 * @see graphtea.graph.ui.GHTMLPageComponent
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
