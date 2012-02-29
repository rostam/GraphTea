// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.select;

import graphlab.platform.core.BlackBoard;
import graphlab.platform.core.exception.ExceptionHandler;
import graphlab.ui.UI;

import java.io.IOException;

/**
 * @author Reza Mohammadi
 */
public class Init implements graphlab.platform.plugin.PluginInterface {

    public void init(BlackBoard blackboard) {
        UI ui = (UI) blackboard.getData(UI.name);
        try {
            ui.addXML("/graphlab/plugins/main/select/SelectUI.xml", getClass());

        } catch (IOException e) {
            ExceptionHandler.catchException(e);
            System.out.println("xml file was not found , or IO error");
        } catch (Exception e) {
            ExceptionHandler.catchException(e);
        }
    }
}
