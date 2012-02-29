// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.main;

import graphlab.platform.core.BlackBoard;
import graphlab.platform.core.exception.ExceptionHandler;
import graphlab.platform.plugin.PluginHandlerInterface;
import graphlab.ui.UI;
import org.xml.sax.SAXException;

import java.io.IOException;

public class HandlerInit implements PluginHandlerInterface {


    /**
     * Load config xml in UI.
     *
     * @see graphlab.platform.plugin.PluginHandlerInterface#init(String,graphlab.platform.core.BlackBoard)
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