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
            e.printStackTrace();
        }


        track("App", "Started");
        blackboard.addListener(ExceptionOccuredData.EVENT_KEY, new Listener() {
            public void keyChanged(String key, Object value) {
                trackError(getLatestExceptionStackStrace(blackboard));
            }
        });

        //tracks
        new Thread(new Runnable() {
            public void run() {
                while (true) { try {
                    Thread.sleep(100);
                    if (tracks.isEmpty()) continue;

                    sendEvent(tracks.removeFirst());

                } catch (Exception e) { addExceptionLog(e); } }
            }
        }).start();
        try { uid = getExternalIP(); } catch (Exception e) { e.printStackTrace();}

        blackboard.addListener("ATrack", new Listener<AEvent>(){
            public void keyChanged(String key, AEvent event){
                System.out.println(event);
                tracks.add(event);
            }
        });

    }

    public static String getLatestExceptionStackStrace(BlackBoard blackboard) {
        String s = "";
        ExceptionOccuredData exceptionData = blackboard.getData(ExceptionOccuredData.EVENT_KEY);
        if (exceptionData != null) {
            StackTraceElement[] ee = exceptionData.e.getStackTrace();
            s = exceptionData.e.toString() + "\n";
            for (StackTraceElement element : ee) {
                s += "\tat " + element.toString() + "\n";
            }
        }
        return s;
    }

    public static String getExternalIP(){
        String ip;
        try{
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));

            ip = in.readLine(); //you get the IP as a String
        } catch (Exception e) { ip = "couldnt find out the ip :(!";}

        return ip;
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
    public static String encode(String in){
        try{
            return URLEncoder.encode(in, "UTF-8").replace("+", "%20");
        } catch (Exception e) {e.printStackTrace(); return in;}
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
            // return;
/*
            params = params.replace(" ", "-");
            // params = URLEncoder.encode(params, "UTF-8");
            System.out.println(params);
            URL obj = new URL(params);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept-Charset", "UTF-8");
            // con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("charset", "UTF-8");
    
            // DataOutputStream wr = new DataOutputStream(con.getOutputStream());

            // wr.writeBytes(params);
            // wr.flush();
            // wr.close();

            int responseCode = con.getResponseCode();
           System.out.println("Response Code : " + responseCode);

           BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
        
*/
        }
        catch (Exception ex) {
            System.out.println("Err "+ ex);
        }
    }
public static void sendGet(String host, String payload) {

        String url = host + "?" + payload;

        System.out.println("*"+url+"*");

        URL myURL = null;
        try {
            myURL = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        HttpURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;
        try {
            urlConnection = (HttpURLConnection) myURL.openConnection();
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:19.0) Gecko/20100101 Firefox/19.0");
            urlConnection.setDoOutput(false);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("GET");

            bufferedReader = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()));
            while (bufferedReader.readLine() != null) {
                /*
                 * Reading returned stuff just to ensure that http connection is going to be closed - Java SE bug...
                 *
                 */
            }
            int code = urlConnection.getResponseCode();
            if (code != 200) {
                throw new RuntimeException("The request wasn't successful - please revisit payload for url: "
                        + url);
            }
            bufferedReader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }


}
    public static void sendPost(String host, String payload) {
        System.out.println(host + payload);
        URL myURL = null;
        try {
            myURL = new URL(host);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        HttpURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;
        DataOutputStream wr = null;
        try {
            urlConnection = (HttpURLConnection) myURL.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");

            String urlParameters = payload;

            // Send post request
            urlConnection.setDoOutput(true);
            wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            bufferedReader = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()));
            while (bufferedReader.readLine() != null) {
                /*
                 * Reading returned stuff just to ensure that http connection is going to be closed - Java SE bug...
                 *
                 */
            }
            int code = urlConnection.getResponseCode();
            if (code != 200) {
                throw new RuntimeException("The request wasn't successful - please revisit payload for payload: "
                        + payload);
            }
            bufferedReader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (wr != null) {
                    wr.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
