// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.reports;

import graphlab.platform.core.BlackBoard;
import graphlab.plugins.main.GraphData;
import graphlab.plugins.main.extension.GraphActionExtension;
import graphlab.plugins.reports.extension.GraphReportExtensionHandler;
import graphlab.plugins.reports.ui.ReportsUI;

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
