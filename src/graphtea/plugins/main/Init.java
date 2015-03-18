// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.graph.ui.GHTMLPageComponent;
import graphtea.graph.ui.GTabbedGraphPane;
import graphtea.platform.Application;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.Listener;
import graphtea.platform.core.exception.ExceptionOccuredData;
import graphtea.platform.extension.ExtensionLoader;
import graphtea.platform.plugin.PluginInterface;
import graphtea.platform.preferences.lastsettings.StorableOnExit;
import graphtea.plugins.main.extension.GraphActionExtensionHandler;

import java.net.MalformedURLException;
import java.net.URL;


/**
 * @author azin azadi
 */
public class Init implements PluginInterface, StorableOnExit {
    static {
        ExtensionLoader.registerExtensionHandler(new GraphActionExtensionHandler());
        GHTMLPageComponent.registerHyperLinkHandler("PerformExtension", new PerformExtensionLinkHandler());
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


        track("App", "Started");
        blackboard.addListener(ExceptionOccuredData.EVENT_KEY, new Listener() {
            public void keyChanged(String key, Object value) {
                trackError(((ExceptionOccuredData)value).e.getMessage());
            }
        });
    }

    public static void trackError(String stacktrace) {
        System.out.println("errr: " + stacktrace);
        try {
            sendGet( ("http://graphtheorysoftware.com/tik/err?data=" + stacktrace).replaceAll(" ", "-") );
        } catch (Exception e) {
        }

    }
    public static void track(String category, String action) {
        System.out.println(action);
        try {
            sendGet( ("http://graphtheorysoftware.com/tik/run?data=" + category + ":" + action).replaceAll(" ", "-") );
        } catch (Exception e) {
        }
    }

    public static void sendGet(String url) throws Exception {

        //URL obj = new URL(url);
        //HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        //con.setRequestMethod("GET");

        //int responseCode = con.getResponseCode();
//        System.out.println("\nSending 'GET' request to URL : " + url);
//        System.out.println("Response Code : " + responseCode);
    }
}
 