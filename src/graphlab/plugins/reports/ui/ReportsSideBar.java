// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.reports.ui;

import graphlab.platform.core.BlackBoard;
import graphlab.plugins.reports.extension.GraphReportExtensionHandler;
import graphlab.ui.components.GComponentInterface;

import java.awt.*;

public class ReportsSideBar implements GComponentInterface {
    public Component getComponent(BlackBoard b) {

        ReportsUI reportsUI = ((ReportsUI) b.getData(GraphReportExtensionHandler.REPORTS_SIDEBAR));
//        reportsUI.reCalculateReports();
        return reportsUI.initWrapper();

    }
}
