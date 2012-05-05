// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.reports.extension;

import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.platform.extension.ExtensionHandler;
import graphtea.plugins.reports.ui.ReportsUI;

/**
 * @author azin azadi

 */
public class GraphReportExtensionHandler implements ExtensionHandler {
    protected AbstractAction a = null;
    public static final String REPORTS_UI = "reports ui";
    public static final String REPORTS_SIDEBAR = "reports side_bar";

    /**
     * @return null if ext doesn't implements GraphGeneratorExtension
     */
    public AbstractAction handle(BlackBoard b, Object ext) {
        a = null;
        if (ext instanceof GraphReportExtension) {
            try {
                ReportsUI rsd = b.getData(REPORTS_UI);
                ReportsUI sb = b.getData(REPORTS_SIDEBAR);
                if (rsd == null) {
                    rsd = new ReportsUI(b, true);
                    sb = new ReportsUI(b, false);
                    b.setData(REPORTS_UI, rsd);
                    b.setData(REPORTS_SIDEBAR, sb);
                }
                GraphReportExtension gr = (GraphReportExtension) ext;
                a = new GraphReportExtensionAction(b, gr);
                rsd.addReport(gr);
                sb.addReport(gr);
            } catch (Exception e) {
                ExceptionHandler.catchException(e);
            }
        }
        return a;
    }

}
