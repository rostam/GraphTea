// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.commonplugin.reporter;

import graphlab.platform.plugin.PluginMethods;
import graphlab.plugins.commonplugin.reporter.actions.Report;

import java.net.URL;

/**
 * @author azin azadi

 */
public class ReporterPluginMethods implements PluginMethods {

//    /**
//     * opens a browser for displaing the given htmltext
//     * it first tries to open an external browser then internal one
//     *
//     * @return true if successfull
//     */
//    public boolean browse(String htmlText) {
//        return Browser.browse(htmlText);
//    }

    /**
     * opens a browser for displaing the given url
     * it first tries to open an external browser then internal one
     *
     * @return true if successfull
     */
    public boolean browse(URL url) {
        return Browser.browse(url);
    }

    /**
     * opens a browser window to report a bug to server
     * ## it is also possible to have a more control on the opening page, the operation are similar to ExceptionReport
     */
    public void showBugBuddy() {
        Report.showBugBuddy();
    }
}
