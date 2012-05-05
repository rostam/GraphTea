// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.reports;

import graphtea.platform.core.BlackBoard;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.extension.GraphActionExtension;
import graphtea.plugins.reports.extension.GraphReportExtensionHandler;
import graphtea.plugins.reports.ui.ReportsUI;

/**
 * shows the property editor contains all registered report(extensions)
 *
 * @author azin azadi

 */
public class ShowReports implements GraphActionExtension {
    public void action(GraphData gd) {
        BlackBoard blackboard = gd.getBlackboard();
        ReportsUI rui = blackboard.getData(GraphReportExtensionHandler.REPORTS_UI);
        rui.show();
    }

    public String getName() {
        return "show Reports";
    }

    public String getDescription() {
        return "shows the list of all reports and their values";
    }
}
