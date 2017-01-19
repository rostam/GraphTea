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
import graphtea.plugins.main.saveload.SaveLoadPluginMethods;
import graphtea.plugins.main.saveload.core.GraphIOException;
import graphtea.plugins.main.saveload.core.extension.GraphReaderExtension;
import graphtea.ui.UIUtils;
import graphtea.ui.components.gpropertyeditor.GCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
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
                if (line.contains("region")) {
                    i++;
                    String region = extract_value_from_line(line);
                    if (region.contains(",")) region = region.substring(0, region.indexOf(","));
                    String str_lat = extract_value_from_line(sc.nextLine());
                    String str_lng = extract_value_from_line(sc.nextLine());
                    sc.nextLine();
                    double lat = 0, lng = 0;
                    line = sc.nextLine();
                    String id = extract_value_from_line(line);
                    if (!str_lat.contains("nul")) {
                        lat = Double.parseDouble(str_lat);
                        lng = Double.parseDouble(str_lng);
                    } else {
                        sttlWithoutCoordinates.add(id);
                        Vector<Object> vs = new Vector<>();
                        vs.add(id);
                        vs.add((double) i);
                        vs.add("No");
                        rt.add(vs);

                    }
                    labelVertex.put(id, i);
                    Vertex v = new Vertex();
                    v.setLabel(id);
                    v.setLocation(convertLatLonToXY(lat, lng));
                    g.addVertex(v);
                    if (regionVertices.keySet().contains(region)) {
                        regionVertices.get(region).add(i);
                    } else {
                        regionVertices.put(region, new Vector<>());
                        regionVertices.get(region).add(i);
                    }
                    verticesRegion.put(i, region);
                    if (!regions.contains(region)) regions.add(region);
                    g.getVertex(i).setColor(regions.indexOf(region) + 2);

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
//                        Edge e = new Edge(g.getVertex(src), g.getVertex(tgt));
//                        e.setWeight(len);
//                        g.addEdge(e);
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
            Button bt = new Button("Show settlements without coordinates");
            bt.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    int i = 0;
                    for(Vertex v : g) {
                        if(sttlWithoutCoordinates.contains(v.getLabel())) {
//                            v.setColor(3);
                            //v.setLocation(new GPoint(200,200 + i*20));
                            i++;
                        } else {
                            v.setLocation(new GPoint(100,100));
                            v.setColor(0);
                        }
                    }
                }
            });
            bt.setSize(new Dimension(200,50));
            jd.add(bt, BorderLayout.SOUTH);
            jd.pack();
            jd.setVisible(true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//
        for(String s : sttlWithoutCoordinates) {
            GPoint gp = new GPoint(0, 0);
            int num = 0;
            for (int ver : regionVertices.get(verticesRegion.get(g.getVertex(labelVertex.get(s)).getId()))) {
                if (g.getVertex(ver).getLocation().getX() == 100) continue;
                num++;
                gp.add(g.getVertex(ver).getLocation());
            }
            gp.multiply(1.0 / num);
//            System.out.println(s + "  " + gp);
            g.getVertex(labelVertex.get(s)).setLocation(gp);
//            g.getVertex(labelVertex.get(s)).setColor(50);
        }
//
//        for(String s : sttlWithoutCoordinates) {
//            Vector<GPoint> vs = new Vector<>();
//            for(Vertex n : g.getNeighbors(g.getVertex(labelVertex.get(s)))) {
//                if(!sttlWithoutCoordinates.contains(n.getLabel())) {
//                    vs.add(n.getLocation());
//                }
//            }
//            if(vs.size()>2) {
//                vs.get(0).add(vs.get(1));
//                vs.get(0).multiply(0.5);
//                g.getVertex(labelVertex.get(s)).setLocation(vs.get(0));
//            }
//        }

        g.removeVertex(g.getVertex(0));
        g.setDrawVertexLabels(false);
        return g;
    }

    GPoint convertLatLonToXY(double lat, double lon) {
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
        return new GPoint(x, y);
    }

    String extract_value_from_line(String line) {
        if(line.charAt(line.length()-1) == ',') line = line.substring(0,line.length()-1);
        line = line.substring(line.indexOf(":")+1).trim();
        line = line.substring(1,line.length()-1).trim();
        return line;
    }

    public String getDescription() {
        return "Special JSON File Format";
    }
}
