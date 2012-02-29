// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.visualization;

import graphlab.platform.core.BlackBoard;
import graphlab.platform.core.exception.ExceptionHandler;
import graphlab.platform.extension.ExtensionLoader;
import graphlab.platform.plugin.PluginInterface;
import graphlab.plugins.visualization.corebasics.extension.VisualizationExtensionHandler;
import graphlab.ui.UI;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * @author Rouzbeh
 */
public class Init implements PluginInterface {
    static {
        ExtensionLoader.registerExtensionHandler(new VisualizationExtensionHandler());

    }

    public void init(BlackBoard blackboard) {
        UI ui = (UI) blackboard.getData(UI.name);
        try {
            ui.addXML("/graphlab/plugins/visualization/VisualizationUI.xml", getClass());
        } catch (IOException e) {
            ExceptionHandler.catchException(e);
            System.out.println("xml file was not found , or IO error");

        } catch (SAXException e) {
            ExceptionHandler.catchException(e);
        }
    }
}
