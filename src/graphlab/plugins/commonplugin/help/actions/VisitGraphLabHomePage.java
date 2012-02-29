// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.commonplugin.help.actions;

import graphlab.plugins.commonplugin.reporter.ReporterPluginMethods;
import graphlab.plugins.main.GraphData;
import graphlab.plugins.main.extension.GraphActionExtension;
import graphlab.platform.core.exception.ExceptionHandler;

import java.net.MalformedURLException;
import java.net.URL;

public class VisitGraphLabHomePage implements GraphActionExtension {
    public void action(GraphData gd) {
        try {
            new ReporterPluginMethods().browse(new URL("http://graphlab.sharif.ir"));
        } catch (MalformedURLException e) {
            ExceptionHandler.catchException(e);
        }
    }

    public String getName() {
        return "GraphLab Homepage";

    }

    public String getDescription() {
        return "Visits GraphLab homepage";
    }
}
