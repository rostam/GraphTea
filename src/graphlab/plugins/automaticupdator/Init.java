// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.automaticupdator;

import graphlab.platform.core.BlackBoard;
import graphlab.platform.core.exception.ExceptionHandler;
import graphlab.platform.plugin.PluginInterface;
import graphlab.ui.UI;
import graphlab.ui.UIUtils;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * This plug in is under developement
 *
 * @author Mohammad Ali Rostami
 */

public class Init implements PluginInterface {

    public void init(BlackBoard blackboard) {
        UI ui = UIUtils.getUI(blackboard);
        try {
            ui.addXML("/graphlab/plugins/automaticupdator/config.xml", getClass());
        } catch (IOException e) {
            ExceptionHandler.catchException(e);
            System.out.println("xml file was not found , or IO error");

        } catch (SAXException e) {
            ExceptionHandler.catchException(e);
        }
    }
}
