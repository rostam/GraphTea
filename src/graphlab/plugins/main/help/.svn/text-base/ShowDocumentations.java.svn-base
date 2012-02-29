// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.main.help;

import graphlab.graph.ui.GTabbedGraphPane;
import graphlab.platform.core.BlackBoard;
import graphlab.platform.core.exception.ExceptionHandler;
import graphlab.ui.extension.UIActionExtension;

import java.io.File;
import java.net.MalformedURLException;

/**
 * @author Azin Azadi
 */
public class ShowDocumentations implements UIActionExtension {


    public void actionPerformed(BlackBoard blackBoard) {
        try {
            GTabbedGraphPane.getCurrentGHTGhtmlPageComponent(blackBoard).setPage(new File("graphlab/plugins/main/ui/welcome_page.html").toURL());
        } catch (MalformedURLException e) {
            ExceptionHandler.catchException(e);
        }
    }
}

