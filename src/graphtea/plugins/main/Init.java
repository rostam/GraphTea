// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main;
import java.util.List;
import graphtea.platform.core.exception.ExceptionHandler;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.graph.ui.GHTMLPageComponent;
import graphtea.graph.ui.GTabbedGraphPane;
import graphtea.platform.core.AEvent;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.Listener;
import graphtea.platform.core.exception.ExceptionOccuredData;
import graphtea.platform.extension.ExtensionLoader;
import graphtea.platform.plugin.PluginInterface;
import graphtea.platform.preferences.lastsettings.StorableOnExit;
import graphtea.plugins.main.core.actions.ShowWelcomeDialog;
import graphtea.plugins.main.extension.GraphActionExtensionHandler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

import static graphtea.platform.StaticUtils.addExceptionLog;

/**
 * @author azin azadi
 */
public class Init implements PluginInterface, StorableOnExit {
    static {
        ExtensionLoader.registerExtensionHandler(new GraphActionExtensionHandler());
        GHTMLPageComponent.registerHyperLinkHandler("PerformExtension", new PerformExtensionLinkHandler());
    }
    static String uid = "";
    
    public void init(BlackBoard blackboard) {
        new graphtea.plugins.main.core.Init().init(blackboard);
        new graphtea.plugins.main.select.Init().init(blackboard);
        new graphtea.plugins.main.saveload.Init().init(blackboard);
        //init the setting
        Edge em = new Edge(new Vertex(), new Vertex());
        SETTINGS.registerSetting(em, "Graph Drawings");
        GTabbedGraphPane gtgp = GTabbedGraphPane.getCurrentGTabbedGraphPane(blackboard);

        gtgp.addGraph(new GraphModel(false));
        gtgp.jtp.setSelectedIndex(0);
        showWelcomePage(blackboard);

        startTelemetry(blackboard);
    }

    /**
     * Greets a new user.
     *
     * <p>The getting-started page ships inside the jar and opens in its own window. It was
     * previously fetched from {@code http://graphtheorysoftware.com/v/<codename>} into the tab's
     * helper strip &mdash; a URL that redirects to HTTPS, which {@link javax.swing.JEditorPane}
     * will not follow, pointing at a page that no longer exists, rendered into a pane the layout
     * fixes at 75 pixels high. Between the three, new users saw nothing whatsoever.
     *
     * @param blackboard the main blackboard
     */
    private void showWelcomePage(BlackBoard blackboard) {
        GHTMLPageComponent strip = GTabbedGraphPane.getCurrentGHTMLPageComponent(blackboard);
        if (strip != null) {
            // The strip is a status bar. Give it something useful to say at rest.
            GTabbedGraphPane.setMessage(
                    "Click the canvas to add a vertex &nbsp;·&nbsp; drag between vertices to connect them"
                            + " &nbsp;·&nbsp; press <b>Ctrl+K</b> to search every command",
                    blackboard, true);
        }
        if (ShowWelcomeDialog.showOnStartup()) {
            new ShowWelcomeDialog(blackboard).show();
        }
    }

    /**
     * Starts usage reporting, but only with the user's agreement.
     *
     * <p>GraphTea transmits usage events and exception stack traces to its authors. That was
     * previously switched on silently, and the startup path also blocked on a network call to
     * look up the machine's public IP address. Both now happen only after an explicit yes, and
     * off the startup path.
     *
     * @param blackboard the main blackboard
     */
    private void startTelemetry(BlackBoard blackboard) {
        if (!Telemetry.isEnabled()) {
            return;
        }
        track("App", "Started");
        blackboard.addListener(ExceptionOccuredData.EVENT_KEY,
                (key, value) -> trackError(getLatestExceptionStackStrace(blackboard)));

        Thread sender = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100);
                    if (tracks.isEmpty()) {
                        continue;
                    }
                    sendEvent(tracks.removeFirst());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                } catch (Exception e) {
                    addExceptionLog(e);
                }
            }
        }, "graphtea-telemetry");
        sender.setDaemon(true);
        sender.start();

        // Off the startup path: this is a blocking network call and used to delay the
        // main window by however long the lookup took.
        Thread ip = new Thread(() -> uid = getExternalIP(), "graphtea-telemetry-id");
        ip.setDaemon(true);
        ip.start();

        blackboard.addListener("ATrack", (Listener<AEvent>) (key, event) -> tracks.add(event));
    }

    public static String getLatestExceptionStackStrace(BlackBoard blackboard) {
        ExceptionOccuredData exceptionData = blackboard.getData(ExceptionOccuredData.EVENT_KEY);
        if (exceptionData == null) return "";
        StringBuilder sb = new StringBuilder(exceptionData.e.toString()).append("\n");
        for (StackTraceElement element : exceptionData.e.getStackTrace()) {
            sb.append("\tat ").append(element).append("\n");
        }
        return sb.toString();
    }

    public static String getExternalIP() {
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            try (BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()))) {
                return in.readLine();
            }
        } catch (Exception e) {
            return "couldnt find out the ip :(!";
        }
    }

    public static void track(String category, String action) {
        AEvent e = new AEvent();
        e.category = category;
        e.action = action;
        tracks.addLast(e);
    }
    public static void trackError(String stacktrace) {
        System.out.println("errr: " + stacktrace);
        AEvent e = new AEvent();
        e.category = "Error";
        e.action = "Exception";
        e.label = stacktrace;
        tracks.addLast(e);
    }

    static LinkedList<AEvent> tracks = new LinkedList<>();
    public static String encode(String in) {
        return URLEncoder.encode(in, StandardCharsets.UTF_8).replace("+", "%20");
    }
    public static void sendEvent(AEvent e) {
        try {
            String params = "v=1&t=event&tid=UA-6755911-3&cid="+uid+
                "&ec="+encode(e.category)+
                "&ea="+encode(e.action)+
                "&el="+encode(e.label)+
                "&ev="+e.value;
            // String encode = URLEncoder.encode(params, "UTF-8");
            // encode = encode.replace("+", "%20");
            sendGet("https://www.google-analytics.com/collect", params);
        }
        catch (Exception ex) {
            System.out.println("Err "+ ex);
        }
    }
    public static void sendGet(String host, String payload) {
        String url = host + "?" + payload;
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL(url).openConnection();
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:19.0) Gecko/20100101 Firefox/19.0");
            urlConnection.setDoOutput(false);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("GET");
            try (BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
                while (br.readLine() != null) { /* drain to allow connection reuse */ }
            }
            int code = urlConnection.getResponseCode();
            if (code != 200) {
                throw new RuntimeException("The request wasn't successful - please revisit payload for url: " + url);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
        }
    }
    public static void sendPost(String host, String payload) {
        System.out.println(host + payload);
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL(host).openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream())) {
                wr.writeBytes(payload);
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
                while (br.readLine() != null) { /* drain to allow connection reuse */ }
            }
            int code = urlConnection.getResponseCode();
            if (code != 200) {
                throw new RuntimeException("The request wasn't successful - please revisit payload for payload: " + payload);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
        }
    }
}
