// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.commonplugin.help;

import graphlab.platform.core.BlackBoard;
import graphlab.platform.core.exception.ExceptionHandler;
import graphlab.platform.plugin.PluginInterface;
import graphlab.ui.UI;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * @author Reza Mohammadi
 */
public class Init implements PluginInterface {

    public final static String filter = "graphlab/plugins/commonplugin/help/content";

    public void init(BlackBoard blackboard) {
//        Utils.registerHelpPlugin(blackboard, "main", "GraphLab's Main Help", filter);

        UI ui = (UI) blackboard.getData(UI.name);
        try {
            ui.addXML("/graphlab/plugins/commonplugin/help/config.xml", getClass());
        } catch (IOException e) {
            ExceptionHandler.catchException(e);
            System.out.println("xml file was not found , or IO error");
        } catch (SAXException e) {
            ExceptionHandler.catchException(e);
        }
    }
}
