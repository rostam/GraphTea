// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.main;

import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.platform.plugin.PluginHandlerInterface;
import graphtea.ui.UI;
import org.xml.sax.SAXException;

import java.io.IOException;

public class HandlerInit implements PluginHandlerInterface {


    /**
     * Load config xml in UI.
     *
     * @see graphtea.platform.plugin.PluginHandlerInterface#init(String,graphtea.platform.core.BlackBoard)
     */
    public void init(String path, BlackBoard blackboard) {
        UI ui = blackboard.getData(UI.name);
        try {
            System.err.println(path);
            ui.addXML(path, getClass());
        } catch (IOException e) {
            ExceptionHandler.catchException(e);
            System.err.println("xml file was not found , or IO error");
        } catch (SAXException e) {
            ExceptionHandler.catchException(e);
        }
    }

}