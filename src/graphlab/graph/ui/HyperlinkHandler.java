// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.graph.ui;

import graphlab.platform.core.BlackBoard;

import java.net.URL;

/**
 * @author Azin Azadi
 * @see graphlab.graph.ui.GHTMLPageComponent
 */
public interface HyperlinkHandler {
    public void handle(String command, BlackBoard b, URL currentURL);
}
