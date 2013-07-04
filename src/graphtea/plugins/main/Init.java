// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main;

import com.firebase.client.Firebase;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.graph.ui.GHTMLPageComponent;
import graphtea.graph.ui.GTabbedGraphPane;
import graphtea.platform.Application;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.platform.extension.ExtensionLoader;
import graphtea.platform.plugin.PluginInterface;
import graphtea.platform.preferences.lastsettings.StorableOnExit;
import graphtea.plugins.main.extension.GraphActionExtensionHandler;

import com.dmurph.tracking.AnalyticsConfigData;
import com.dmurph.tracking.JGoogleAnalyticsTracker;
import com.dmurph.tracking.JGoogleAnalyticsTracker.GoogleAnalyticsVersion;
import com.dmurph.tracking.system.AWTSystemPopulator;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author azin azadi
 */
public class Init implements PluginInterface, StorableOnExit {
    static {
        ExtensionLoader.registerExtensionHandler(new GraphActionExtensionHandler());
    }

    public void init(BlackBoard blackboard) {
        new graphtea.plugins.main.core.Init().init(blackboard);
        new graphtea.plugins.main.select.Init().init(blackboard);
        new graphtea.plugins.main.saveload.Init().init(blackboard);
        //init the setting
        Edge em = new Edge(new Vertex(), new Vertex());
        SETTINGS.registerSetting(em, "Graph Drawings");
        GTabbedGraphPane gtgp = GTabbedGraphPane.getCurrentGTabbedGraphPane(blackboard);
//        GHTMLPageComponent pc = new GHTMLPageComponent(blackboard);
//        try {
//            pc.setPage(new File("doc/welcome_page.html").toURL());
//            gtgp.jtp.addTab("Welcome!", pc);
//        } catch (MalformedURLException e) {
//            ExceptionHandler.catchException(e);
//        }
        gtgp.addGraph(new GraphModel(false));
        gtgp.jtp.setSelectedIndex(0);
        try {
            GTabbedGraphPane.getCurrentGHTMLPageComponent(blackboard).setPage(new URL(Application.WELCOME_URL));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        //setup google analytics so that we know which features the users use more and need to get improved
   
        track("App", "Started");
    }

    public static JGoogleAnalyticsTracker tracker;
    public static Firebase firebase;
    static {

        AnalyticsConfigData config = new AnalyticsConfigData("UA-6755911-5");
        config.setFlashVersion("9.0 r24");
        tracker = new JGoogleAnalyticsTracker(config, JGoogleAnalyticsTracker.GoogleAnalyticsVersion.V_4_7_2);

        // Create a reference to a Firebase location
        firebase = new Firebase("https://graphtea.firebaseio.com/").child(Application.INSTANCE_ID);

// Write data to Firebase
        firebase.setValue("Do you have data? You'll love Firebase.");

    }
    public static void track(String category, String action) {
        System.out.println(action);
        tracker.trackEvent(category, action, "Version: " + Application.VERSION);
        firebase.push().setValue(category + ":" + action);
    }

}
 