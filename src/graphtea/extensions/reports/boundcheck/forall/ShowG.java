package graphtea.extensions.reports.boundcheck.forall;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

import java.io.*;
import java.util.Scanner;

/**
 * Created by rostam on 30.09.15.
 *
 */
public class ShowG {
    public static FileWriter outG(String file) throws IOException {
        String cur = null;
        try {
            cur = new java.io.File(".").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(System.getProperty("os.name").contains("Win")) {
            cur =cur + "\\graphs\\";
        } else {
            cur = cur + "/graphs/";
        }

        return new FileWriter(cur+file+".g6");
    }

    public static Scanner inG(String file) throws IOException {
        String cur = null;
        try {
            cur = new java.io.File(".").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(System.getProperty("os.name").contains("Win")) {
            cur =cur + "\\graphs\\";
        } else {
            cur = cur + "/graphs/";
        }

        return new Scanner(new File(cur+file+".g6"));
    }

    public static ProcessBuilder getShowGProcess(String file, int from, int to) {
        String cur = null;
        try {
            cur = new java.io.File(".").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ProcessBuilder process;
        String parameter1="-p"+from+":"+to;
        if(System.getProperty("os.name").contains("Win")) {
            cur =cur + "\\graphs\\";
            String parameter2 = cur+file+".g6";
            process = new ProcessBuilder(cur + "showg_win32.exe", parameter1,parameter2.trim());
        } else {
            cur = cur + "/graphs/";
            String parameter2 = cur+file+".g6";
            process = new ProcessBuilder(cur + "showg.out", parameter1,parameter2.trim());
        }

        return process;
    }

    public static ProcessBuilder getShowGProcess(String file) {
        String cur = null;
        try {
            cur = new java.io.File(".").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ProcessBuilder process;

        if(System.getProperty("os.name").contains("Win")) {
            cur =cur + "\\graphs\\";
            process = new ProcessBuilder(cur + "showg_win32.exe", cur + file+".g6");
        } else {
            cur = cur + "/graphs/";
            process = new ProcessBuilder(cur + "showg.out", cur + file + ".g6");
        }

        return process;
    }

    public static BufferedReader showG(String file, int from, int to) {
        try {
            ProcessBuilder process = getShowGProcess(file, from,to);
            Process p = process.start();
            BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
            return bri;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static GraphModel parseGraph(Scanner sc) {
        GraphModel g = new GraphModel();
        g.setDirected(false);
        //sc.nextLine();
        String order = sc.nextLine();
        order = order.substring(order.lastIndexOf("r") + 1,
                order.lastIndexOf("."));
        order = order.trim();

        int ord = Integer.parseInt(order);
        for (int i = 0; i < ord; i++) g.addVertex(new Vertex());
        for (int i = 0; i < ord; i++) {
            String tmp = sc.nextLine();
            int first = Integer.parseInt(tmp.substring(0,tmp.indexOf(":")-1).trim());
            tmp = tmp.substring(tmp.indexOf(":") + 1);
            Scanner sc2 = new Scanner(tmp.trim());
            while (sc2.hasNext()) {
                String num = sc2.next();
                if (num.contains(";")) num = num.substring(0, num.indexOf(";"));
                int id = Integer.parseInt(num.trim());
                if (!g.isEdge(g.getVertex(first), g.getVertex(id))) {
                    g.addEdge(new Edge(g.getVertex(first), g.getVertex(id)));
                }
            }
        }

        return g;
    }
}
