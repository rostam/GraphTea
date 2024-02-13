// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.reports.extension;

import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.platform.extension.ExtensionHandler;

/**
 * @author azin azadi

 */
public class GraphReportExtensionHandler implements ExtensionHandler {
    protected GraphReportExtensionAction a = null;
    public static final String REPORTS_UI = "reports ui";
    public static final String REPORTS_SIDEBAR = "reports side_bar";

    public AbstractAction handle(BlackBoard b, Object ext) {
        a = null;
        if (ext instanceof GraphReportExtension) {
            try {
                GraphReportExtension gr = (GraphReportExtension) ext;
                a = new GraphReportExtensionAction(b, gr);
            } catch (Exception e) {
                ExceptionHandler.catchException(e);
            }
        }
        return a;
    }

}
