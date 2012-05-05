// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.commonplugin.reporter;

import graphtea.platform.plugin.PluginMethods;

import java.net.URL;

/**
 * @author azin azadi

 */
public class ReporterPluginMethods implements PluginMethods {

    /**
     * opens a browser for displaing the given url
     * it first tries to open an external browser then internal one
     *
     * @return true if successfull
     */
    public boolean browse(URL url) {
        return Browser.browse(url);
    }

}
