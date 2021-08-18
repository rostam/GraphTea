// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.io;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.plugins.main.saveload.SaveLoadPluginMethods;
import graphtea.plugins.main.saveload.core.GraphIOException;
import graphtea.plugins.main.saveload.core.extension.GraphReaderExtension;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Ali ROstami
 */
public class LoadSpecialGML implements GraphReaderExtension {

    public boolean accepts(File file) {
        return SaveLoadPluginMethods.getExtension(file).equals(getExtension());
    }

    public String getName() {
        return "GraphML format";
    }

    public String getExtension() {
        return "graphml";
    }

    public GraphModel read(File file) throws GraphIOException {
        GraphModel g = new GraphModel();
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                String line = sc.nextLine();
                if(line.contains("<node")) {
                    String subline = sc.nextLine();
                    Vertex v = new Vertex();
                    while (!subline.contains("</node")) {
                        String key = subline.substring(subline.indexOf("\"")+1,subline.lastIndexOf("\""));
                        String value = subline.substring(subline.indexOf(">")+1, subline.indexOf("</"));
                        v.setUserDefinedAttribute(key, value);
                        subline = sc.nextLine();
                    }
                    v.setLabel(v.getUserDefinedAttribute("name"));
                    g.addVertex(v);
                }
                if(line.contains("<edge")) {
                    String subline = sc.nextLine();
                    String relation = subline.substring(subline.indexOf(">")+1, subline.indexOf("</"));
                    String next = sc.nextLine();
                    String type = "no";
                    if(next.contains("type")) {
                        type = next.substring(subline.indexOf(">")+1, subline.indexOf("</"));
                    }
                    String[] splitted = line.trim().split(" ");
                    String src, tgt;
                    boolean directed = true;
                    if(splitted[1].contains("directed")) {
                        directed = false;
                        src = splitted[2].substring(splitted[2].indexOf("\"") + 1, splitted[2].lastIndexOf("\""));
                        tgt = splitted[3].substring(splitted[3].indexOf("\"") + 1, splitted[3].lastIndexOf("\""));
                    } else {
                        src = splitted[1].substring(splitted[1].indexOf("\"") + 1, splitted[1].lastIndexOf("\""));
                        tgt = splitted[2].substring(splitted[2].indexOf("\"") + 1, splitted[2].lastIndexOf("\""));
                    }
                    Edge e = new Edge(g.getVertex(Integer.parseInt(src)),g.getVertex(Integer.parseInt(tgt)));
                    e.setUserDefinedAttribute("directed", directed);
                    e.setUserDefinedAttribute("type", type);
                    g.addEdge(e);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return g;
    }

    public String getDescription() {
        return "GraphTea File Format";
    }

    public static void main(String[] args) {
        try {
            new LoadSpecialGML()
                    .read(new File("/home/rostam/kara/GD2018/got-graph.graphml"));
        } catch (GraphIOException e) {
            e.printStackTrace();
        }
    }
}
