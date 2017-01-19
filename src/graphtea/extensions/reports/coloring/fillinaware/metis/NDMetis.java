package graphtea.extensions.reports.coloring.fillinaware.metis;

import graphtea.graph.graph.GraphModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

/**
 * Created by rostam on 15.02.16.
 * Write Metis Graph Format
 * Read Order of NDMetis
 */
public class NDMetis {
    String name;
    GraphModel g;

    public NDMetis(String name, GraphModel g) {
        this.name = name;
        this.g=g;
    }

    public Vector<Integer> getOrder() {
        writeGraphForMetis(g);
        ndmetis(name);
        return readOrder(name);
    }

    public void writeGraphForMetis(GraphModel g) {
        try {
            FileWriter fw = new FileWriter("mats/"+name);
            fw.write(g.numOfVertices()+ " " + g.getEdgesCount() + "\n");
            for(int i=0;i<g.numOfVertices();i++) {
                String tmp = "";
                for(int j=0;j<g.numOfVertices();j++) {
                    if(g.isEdge(g.getVertex(i),g.getVertex(j))) {
                        tmp += (j+1) + " ";
                    }
                }
                if(!tmp.equals("")) {
                    fw.write(tmp.substring(0,tmp.length()-1));
                    fw.write("\n");
                } else {
                    fw.write("\n");
                }
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Vector<Integer> readOrder(String name) {
        Vector<Integer> ret = new Vector<>();
        try {
            Scanner sc = new Scanner(new File("mats/"+name + ".iperm"));
            while (sc.hasNext()) {
                ret.add(sc.nextInt());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public ProcessBuilder ndmetis(String file) {
        String cur = null;
        try {
            cur = new java.io.File(".").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ProcessBuilder process=null;

        if(System.getProperty("os.name").contains("Win")) {
            System.out.println("This option works only in linux.");
        } else {
            cur = cur + "/mats/";
            process = new ProcessBuilder("ndmetis", cur + file);
            Process p = null;
            try {
                p = process.start();
                p.waitFor();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return process;
    }
}