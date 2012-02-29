// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.preview;

import graphlab.platform.plugin.PluginMethods;

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
