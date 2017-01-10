// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.io.specialjson;

import graphtea.graph.graph.*;
import graphtea.graph.old.GShape;
import graphtea.plugins.main.saveload.SaveLoadPluginMethods;
import graphtea.plugins.main.saveload.core.GraphIOException;
import graphtea.plugins.main.saveload.core.extension.GraphReaderExtension;
import graphtea.ui.UIUtils;
import graphtea.ui.components.gpropertyeditor.GCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringJoiner;
import java.util.Vector;

import static graphtea.platform.Application.blackboard;

public class LoadSpecialjson implements GraphReaderExtension {

    @Override
    public boolean accepts(File file) {
        return SaveLoadPluginMethods.getExtension(file).equals(getExtension());
    }

    public String getName() {
        return "SpecJSON Format";
    }

    public String getExtension() {
        return "json";
    }

    String between(String s, char c1, char c2) {
        return s.substring(s.indexOf(c1) + 1, s.lastIndexOf(c2)).trim();
    }

    @Override
    public GraphModel read(File file) throws GraphIOException {
        GraphModel g = new GraphModel(false);
        //2793
        Vector<String> regions = new Vector<>();
        Vector<String> sttlWithoutCoordinates = new Vector<>();
        HashMap<String,Integer> labelVertex = new HashMap<>();
        HashMap<String,Vector<Integer>> regionVertices = new HashMap<>();
        HashMap<Integer, String> verticesRegion = new HashMap<>();
        try {
            int i = 0;
            final double QUARTERPI = Math.PI / 4.0;
            Scanner sc = new Scanner(file);
            FastRenderer.defaultVertexRadius = 12;
            RenderTable rt = new RenderTable();
            Vector<String> titles = new Vector<>();
            titles.add("Settlements");
            titles.add("Vertex");
            titles.add("processed");
            rt.setTitles(titles);
            g.addVertex(new Vertex());
            while (sc.hasNext()) {
                String line = sc.nextLine();
                if (line.contains("status")) {
                    i++;
                    g.addVertex(new Vertex());
                    if(i==12) System.out.println(line);
                    if (line.contains("old")) {
                        line = sc.nextLine();
                        if(i==12) System.out.println(line);
                        if (!line.contains("lat")) continue;
                        line = between(line, ':', ',');
                        line = between(line, '\"', '\"');
                        double lat = Double.parseDouble(line);
                        line = sc.nextLine();
                        line = between(line, ':', ',');
                        line = between(line, '\"', '\"');
                        double lon = Double.parseDouble(line);
                        line = sc.nextLine();
                        String id = line.substring(line.indexOf(":") + 3, line.length() - 2).trim();
                        //g.getVertex(i).setLabel(id.substring(0, id.indexOf("_")));
                        if(i==12) System.out.println("what " + id);
                        g.getVertex(i).setLabel(id);
                        // convert to radian
                        GraphPoint gp = convertLatLonToXY(lat, lon);
                        //gp.multiply(10);
                        //gp.add(400,2000);
                        g.getVertex(i).setLocation(gp);

                        line = sc.nextLine();
                        line = line.substring(line.indexOf(":") + 1);
                        String region = line.substring(1, line.length() - 1);
                        if(regionVertices.keySet().contains(region)) {
                            regionVertices.get(region).add(i);
                        } else {
                            regionVertices.put(region, new Vector<>());
                            regionVertices.get(region).add(i);
                        }
                        verticesRegion.put(i,region);
                        if (!regions.contains(region)) regions.add(region);
                        g.getVertex(i).setColor(regions.indexOf(region));
                    } else {
                        sc.nextLine();
                        sc.nextLine();
                        line = sc.nextLine();
                        String id = line.substring(line.indexOf(":") + 3, line.length() - 2).trim();
                        sttlWithoutCoordinates.add(id);
                        labelVertex.put(id,i);
                        g.getVertex(i).setLabel(id);
                        //System.out.println(id);
                        Vector<Object> vs = new Vector<>();
                        vs.add(id);
                        vs.add((double) i);
                        vs.add("No");
                        rt.add(vs);
                        line = sc.nextLine();
                        line = line.substring(line.indexOf(":") + 1);
                        String region = line.substring(1, line.length() - 1);
                        if(region.contains(",")) region=region.substring(0,region.indexOf(","));
                        if(regionVertices.keySet().contains(region)) {
                            regionVertices.get(region).add(i);
                        } else {
                            regionVertices.put(region, new Vector<>());
                            regionVertices.get(region).add(i);
                        }
                        verticesRegion.put(i,region);
                        if (!regions.contains(region)) regions.add(region);
                        g.getVertex(i).setColor(regions.indexOf(region));
                    }
                }
            }

            Scanner sc2 = new Scanner(file);
            while (sc2.hasNext()) {
                String line = sc2.nextLine();
                if (line.contains("source")) {
                    int src = Integer.parseInt(between(line, ':', ','));
                    line = sc2.nextLine();
                    int len = -1;
                    if (!line.contains("nul"))
                        len = (int) Math.floor(Double.parseDouble(between(line, ':', ',')));
                    line = sc2.nextLine();
                    int tgt = Integer.parseInt(line.substring(line.indexOf(":") + 1).trim());
                    if (len == -1) continue;
                    if (sttlWithoutCoordinates.contains(g.getVertex(src).getLabel()) ||
                            sttlWithoutCoordinates.contains(g.getVertex(tgt).getLabel())) {
                        Edge e = new Edge(g.getVertex(src), g.getVertex(tgt));
                        e.setWeight(len);
                        g.addEdge(e);
                    }
                }
            }
            JDialog jd = new JDialog(UIUtils.getGFrame(blackboard));
            jd.setVisible(true);
            jd.setTitle("Settlements without coordinates");
            jd.setLayout(new BorderLayout(3, 3));
            //jd.add(new JLabel(mr.getDescription()), BorderLayout.NORTH);
            Component rendererComponent = GCellRenderer.getRendererFor(rt);
            rendererComponent.setEnabled(true);
            jd.add(rendererComponent, BorderLayout.CENTER);
            jd.pack();
            jd.setVisible(true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for(String s : sttlWithoutCoordinates) {
            GraphPoint gp = new GraphPoint(0,0);
            int num = 0;
            for(int ver : regionVertices.get(verticesRegion.get(g.getVertex(labelVertex.get(s)).getId()))) {
                num++;
                gp.add(g.getVertex(ver).getLocation());
            }
            gp.multiply(1.0/num);
            //System.out.println(s + "  " + gp);
            g.getVertex(labelVertex.get(s)).setLocation(gp);
            g.getVertex(labelVertex.get(s)).setColor(50);
        }

//        for(Vertex v: g) {
//            System.out.println(v.getLabel() + " " + v.getId());
//        }

//        for(String s : sttlWithoutCoordinates) {
//            Vector<GraphPoint> vs = new Vector<>();
//            for(Vertex n : g.getNeighbors(g.getVertex(labelVertex.get(s)))) {
//                if(!sttlWithoutCoordinates.contains(n.getLabel())) {
//                    vs.add(n.getLocation());
//                }
//            }
//            if(vs.size()>2) {
//                System.out.println(vs.get(0) + " " + vs.get(1));
//                vs.get(0).add(vs.get(1));
//                System.out.println(vs.get(0));
//                vs.get(0).multiply(0.5);
//                System.out.println(vs.get(0));
//                System.out.println();
//                g.getVertex(labelVertex.get(s)).setLocation(vs.get(0));
//                g.getVertex(labelVertex.get(s)).setShape(GShape.NICESTAR);
//                g.getVertex(labelVertex.get(s)).setSize(new GraphPoint(20,20));
//            }
//        }

        g.removeVertex(g.getVertex(0));
        g.setDrawVertexLabels(false);
        return g;
    }

    GraphPoint convertLatLonToXY(double lat, double lon) {
//        lat    = 41.145556; // (φ)
//        lon   = -73.995;   // (λ)
        double mapWidth = 2000;
        double mapHeight = 1000;

// get x value
        double x = (lon + 180) * (mapWidth / 360);

// convert from degrees to radians
        double latRad = lat * Math.PI / 180;

// get y value
        double mercN = Math.log(Math.tan((Math.PI / 4) + (latRad / 2)));
        double y = (mapHeight / 2) - (mapWidth * mercN / (2 * Math.PI));
        return new GraphPoint(x, y);
    }

    public String getDescription() {
        return "Special JSON File Format";
    }
}
