// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main;

import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.VertexModel;
import graphlab.graph.ui.GHTMLPageComponent;
import graphlab.graph.ui.GTabbedGraphPane;
import graphlab.platform.core.BlackBoard;
import graphlab.platform.core.exception.ExceptionHandler;
import graphlab.platform.extension.ExtensionLoader;
import graphlab.platform.plugin.PluginInterface;
import graphlab.platform.preferences.lastsettings.StorableOnExit;
import graphlab.plugins.main.extension.GraphActionExtensionHandler;

import java.io.File;
import java.net.MalformedURLException;

/**
 * @author azin azadi
 */
public class Init implements PluginInterface, StorableOnExit {
    static {
        ExtensionLoader.registerExtensionHandler(new GraphActionExtensionHandler());
    }

    public void init(BlackBoard blackboard) {
        new graphlab.plugins.main.resources.Init().init(blackboard);
        new graphlab.plugins.main.core.Init().init(blackboard);
        new graphlab.plugins.main.select.Init().init(blackboard);
        new graphlab.plugins.main.saveload.Init().init(blackboard);
        //init the setting
        EdgeModel em = new EdgeModel(new VertexModel(), new VertexModel());
        SETTINGS.registerSetting(em, "Graph Drawings");
        GTabbedGraphPane gtgp = blackboard.getData(GTabbedGraphPane.NAME);
        GHTMLPageComponent pc = new GHTMLPageComponent(blackboard);
        try {
            pc.setPage(new File("doc/welcome_page.html").toURL());
            gtgp.jtp.addTab("Welcome!", pc);
        } catch (MalformedURLException e) {
            ExceptionHandler.catchException(e);
        }
        gtgp.addGraph(new GraphModel(false));
        gtgp.jtp.setSelectedIndex(0);
    }
}
 