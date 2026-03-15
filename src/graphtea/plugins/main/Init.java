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
import graphtea.platform.Application;
import graphtea.platform.core.AEvent;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.Listener;
import graphtea.platform.core.exception.ExceptionOccuredData;
import graphtea.platform.extension.ExtensionLoader;
import graphtea.platform.plugin.PluginInterface;
import graphtea.platform.preferences.lastsettings.StorableOnExit;
import graphtea.plugins.main.extension.GraphActionExtensionHandler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
        try {
            GTabbedGraphPane.getCurrentGHTMLPageComponent(blackboard).setPage(new URL(Application.WELCOME_URL));
        } catch (MalformedURLException e) {
            ExceptionHandler.catchException(e);
        }


        track("App", "Started");
        blackboard.addListener(ExceptionOccuredData.EVENT_KEY, (key, value) -> trackError(getLatestExceptionStackStrace(blackboard)));

        //tracks
        new Thread(() -> {
            while (true) { try {
                Thread.sleep(100);
                if (tracks.isEmpty()) continue;

                sendEvent(tracks.removeFirst());

            } catch (Exception e) { addExceptionLog(e); } }
        }).start();
        try { uid = getExternalIP(); } catch (Exception e) { ExceptionHandler.catchException(e);}

        blackboard.addListener("ATrack", (Listener<AEvent>) (key, event) -> {
//            System.out.println(event);
            tracks.add(event);
        });

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
