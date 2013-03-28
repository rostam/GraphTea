// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.core.actions;

import com.sun.jndi.toolkit.url.UrlUtil;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.ui.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;


/**
 * displays the about dialog of graphtea
 *
 * @author azin azadi
 */
public class ShowAboutDialog extends graphtea.platform.core.AbstractAction {
    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */
    public ShowAboutDialog(BlackBoard bb) {
        super(bb);
        listen4Event(UIUtils.getUIEventKey("show about"));
    }


    public void performAction(String eventName, Object value) {
        try {
            Desktop.getDesktop().browse(new URI("http://graphtheorysoftware.com"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
