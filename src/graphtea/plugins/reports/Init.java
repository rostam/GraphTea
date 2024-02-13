// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.reports;

import graphtea.platform.Application;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.platform.extension.ExtensionLoader;
import graphtea.platform.plugin.PluginInterface;
import graphtea.plugins.reports.extension.GraphReportExtensionHandler;
import graphtea.plugins.reports.ui.ReportsUI;
import graphtea.ui.UI;

/**
 * @author azin
 */
public class Init implements PluginInterface {
    static {
        ExtensionLoader.registerExtensionHandler(new GraphReportExtensionHandler());
    }

    private BlackBoard blackboard;

    public void init(BlackBoard blackboard) {
        this.blackboard = blackboard;

        UI ui = blackboard.getData(UI.name);
        try {
            ui.addXML("/graphtea/plugins/reports/config.xml", getClass());
        } catch (Exception e) {
            ExceptionHandler.catchException(e);
            System.out.println("xml file was not found , or IO error");
        }
        blackboard.addListener(Application.POST_INIT_EVENT, (key, value) -> postInit());
    }

    private void postInit() {
        UI ui = blackboard.getData(UI.name);
        ReportsUI rui = new ReportsUI(blackboard);

        ui.getGFrame().getSidebar().addButton(this.getClass().getResource("/graphtea/plugins/reports/ui/sbicon.GIF"), rui.sidebar, "Graph Reports");

        rui.initTable();
    }
}
