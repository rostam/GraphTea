// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.graphgenerator;

import graphlab.platform.core.BlackBoard;
import graphlab.platform.core.exception.ExceptionHandler;
import graphlab.platform.extension.ExtensionLoader;
import graphlab.platform.plugin.PluginInterface;
import graphlab.plugins.graphgenerator.core.extension.GraphGeneratorExtensionHandler;
import graphlab.ui.UI;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * @author Reza Mohammadi
 */
public class Init implements PluginInterface {
    static {
        ExtensionLoader.registerExtensionHandler(new GraphGeneratorExtensionHandler());
    }

    public void init(BlackBoard blackboard) {
        UI ui = blackboard.getData(UI.name);
        try {
            ui.addXML("/graphlab/plugins/graphgenerator/GeneratorUI.xml", getClass());
        } catch (IOException e) {
            ExceptionHandler.catchException(e);
            System.err.println("xml file was not found , or IO error");
        } catch (SAXException e) {
            ExceptionHandler.catchException(e);
        }
    }
}
