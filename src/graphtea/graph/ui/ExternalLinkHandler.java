// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.graph.ui;

import graphtea.platform.StaticUtils;
import graphtea.platform.core.BlackBoard;

import java.net.URL;

/**
 * @author Azin Azadi
 */
class ExternalLinkHandler implements HyperlinkHandler {

    public void handle(String url, BlackBoard b, URL currentURL) {
        System.out.println(url);
        StaticUtils.browse(url);
    }
}
