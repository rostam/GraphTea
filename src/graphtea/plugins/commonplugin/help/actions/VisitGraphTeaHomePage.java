// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.commonplugin.help.actions;

import graphtea.plugins.commonplugin.reporter.ReporterPluginMethods;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.extension.GraphActionExtension;
import graphtea.platform.core.exception.ExceptionHandler;

import java.net.MalformedURLException;
import java.net.URL;

public class VisitGraphTeaHomePage implements GraphActionExtension {
    public void action(GraphData gd) {
        try {
            new ReporterPluginMethods().browse(new URL("http://GraphTheorySoftware.com"));
        } catch (MalformedURLException e) {
            ExceptionHandler.catchException(e);
        }
    }

    public String getName() {
        return "GraphTea Homepage";

    }

    public String getDescription() {
        return "Visits GraphTea homepage";
    }
}
