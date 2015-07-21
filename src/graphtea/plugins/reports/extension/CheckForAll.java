package graphtea.plugins.reports.extension;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RendTable;
import graphtea.graph.graph.Vertex;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by rostam on 21.07.15.
 */
public class CheckForAll {
    public static int[] forall(GraphReportExtension mr) {
        int[] res = null;
        JFrame jd = new JFrame();
        JProgressBar pb = new JProgressBar();
        pb = new JProgressBar(0, 261078);
        pb.setValue(0);
        pb.setStringPainted(true);
        jd.add(pb);
        jd.setTitle("All Connected Graphs with 9 Vertices");
        jd.setSize(600,100);
        jd.setLocation(200,200);
        jd.setVisible(true);
        jd.setAlwaysOnTop(true);

        try {
            Scanner sc = new Scanner(new File("g9c.txt"));
            int gcount = 0;

            while(sc.hasNext()) {
                pb.setValue(gcount);
                pb.validate();
                jd.validate();
                GraphModel g = new GraphModel();
                sc.nextLine();
                String order = sc.nextLine();
                order = order.substring(order.lastIndexOf("r")+1,
                        order.lastIndexOf("."));
                order = order.trim();
                int ord = Integer.parseInt(order);
                for(int i=0;i<ord;i++) g.addVertex(new Vertex());
                for(int i=0;i<ord;i++) {
                    String tmp = sc.nextLine();
                    tmp = tmp.substring(tmp.indexOf(":")+1);
                    Scanner sc2 = new Scanner(tmp.trim());
                    while(sc2.hasNext()) {
                        String num = sc2.next();
                        if(num.contains(";")) num=num.substring(0,num.indexOf(";"));
                        int id = Integer.parseInt(num);
                        g.addEdge(new Edge(g.getVertex(i),g.getVertex(id)));
                    }
                }

                RendTable ret = (RendTable) mr.calculate(g);
                gcount++;
                if(ret.get(0).size() <= 2) return null;
                if(res == null) {
                    res = new int[ret.get(0).size()];
                }
                for(int i=1;i < ret.get(0).size();i++) {
                    if((Double)ret.get(1).get(0) < (Double)ret.get(1).get(i)) {
                        res[i]++;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return res;
    }
}
