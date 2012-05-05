// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.main.help;

import graphtea.graph.ui.GHTMLPageComponent;
import graphtea.graph.ui.GTabbedGraphPane;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.ui.extension.UIActionExtension;

import java.io.File;
import java.net.MalformedURLException;

/**
 * @author Azin Azadi
 */
public class ShowWelcomePage implements UIActionExtension {


    public ShowWelcomePage() {
    }

    public void actionPerformed(BlackBoard blackBoard) {

        GTabbedGraphPane p = GTabbedGraphPane.getCurrentGTabbedGraphPane(blackBoard);
        try {
            ((GHTMLPageComponent) p.jtp.getComponentAt(0)).setPage(new File("doc/").toURL());
            ((GHTMLPageComponent) p.jtp.getComponentAt(0)).setPage(new File("doc/welcome_page.html").toURL());

        } catch (MalformedURLException e) {
            ExceptionHandler.catchException(e);
        }
    }
}
