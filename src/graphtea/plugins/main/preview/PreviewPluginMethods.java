// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.preview;

import graphtea.platform.plugin.PluginMethods;

/**
 * @author azin azadi

 */
public class PreviewPluginMethods implements PluginMethods {
    /**
     * @param fileName
     */
    public void showPreview(String fileName) {
        ShowPreview.show(fileName);
    }
}
