// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.connector;

import graphtea.platform.core.BlackBoard;
import graphtea.platform.extension.ExtensionLoader;
import graphtea.platform.plugin.PluginInterface;
import graphtea.plugins.connector.matlab.MatlabExtensionLoader;

/**
 * @author azin
 */
public class Init implements PluginInterface {
    static {
        try {
            System.loadLibrary("jmatlink");
            ExtensionLoader.registerUnknownExtensionLoader(new MatlabExtensionLoader());
        }
        catch (UnsatisfiedLinkError e) {
            System.err.println("Could't load JMATLINK");
            //System.err.println("Could't load JMATLINK");
//            ExceptionHandler.catchException(e);
        }
//        ExtensionLoader.registerExtensionHandler(new MatlabReportHandler());
    }

    public void init(BlackBoard blackboard) {
    }
}
