// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.connector;

import graphlab.platform.core.BlackBoard;
import graphlab.platform.extension.ExtensionLoader;
import graphlab.platform.plugin.PluginInterface;
import graphlab.plugins.connector.matlab.MatlabExtensionLoader;

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
