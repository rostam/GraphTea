// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.io.g6format;

import graphtea.extensions.G6Format;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.plugins.graphgenerator.core.PositionGenerators;
import graphtea.plugins.main.saveload.SaveLoadPluginMethods;
import graphtea.plugins.main.saveload.core.GraphIOException;
import graphtea.plugins.main.saveload.core.extension.GraphReaderExtension;

import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class LoadGraph6Format implements GraphReaderExtension {

    @Override
    public boolean accepts(File file) {
        return SaveLoadPluginMethods.getExtension(file).equals(getExtension());
    }

    public String getName() {
        return "Graph6 Format";
    }

    public String getExtension() {
        return "g6";
    }

    @Override
    public GraphModel read(File file) throws GraphIOException {
        String g6 = "";
        try {
            Scanner sc = new Scanner(file);
            g6 = sc.nextLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        GraphModel g = G6Format.stringToGraphModel(g6);
        Point pp[] = PositionGenerators.circle(200, 400, 250, g.numOfVertices());

        int tmpcnt = 0;
        for (Vertex v : g) {
            v.setLocation(pp[tmpcnt]);
            tmpcnt++;
        }
        return g;
    }

    public GraphModel next(BufferedReader bri) {
        String g = "";
        try {
            bri.readLine();
            g=bri.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String tmp = g.substring(g.indexOf("order"));
        tmp = tmp.substring(tmp.indexOf(" "),tmp.length()-1);
        int numOfVertices = Integer.parseInt(tmp.trim());
        g+= "\n";
        for (int i = 0; i < numOfVertices; i++) {
            try {
                g += bri.readLine() + "\n";
            } catch (IOException e) {
                e.printStackTrace();
                return new GraphModel();
            }
        }
        return parseGraph(new Scanner(g));
    }

    public String getDescription() {
        return "Graph6 File Format";
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
            process = new ProcessBuilder(cur + "showg_win32.exe", file);
        } else {
            cur = cur + "/graphs/";
            process = new ProcessBuilder(cur + "showg.out",file);
        }

        return process;
    }

    public static BufferedReader showG(File file) {
        try {
            ProcessBuilder process = getShowGProcess(file.getAbsolutePath());
            Process p = process.start();
            return new BufferedReader(new InputStreamReader(p.getInputStream()));
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