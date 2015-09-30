package graphtea.plugins.reports.forall;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by rostam on 30.09.15.
 */
public class ShowG {
    public static BufferedReader showG(String file) {
        try {
            String cur = new java.io.File(".").getCanonicalPath();
            ProcessBuilder process;
            if(System.getProperty("os.name").contains("Win")) {
                process = new ProcessBuilder(cur + "\\showg_win32.exe", cur + "\\" + file);
            } else {
                process = new ProcessBuilder(cur + "/showg", cur + "/" + file);
            }
            Process p = process.start();
            p.waitFor();
            BufferedReader bri = new BufferedReader
                    (new InputStreamReader(p.getInputStream()));
            p.waitFor();
            return bri;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static GraphModel showOneG(String line) {
        String g="";

        try {
            FileWriter fw = new FileWriter("tmpF.g6");
            fw.write(line);
            fw.write(System.lineSeparator());
            fw.close();
            String cur = new java.io.File(".").getCanonicalPath();
            ProcessBuilder process;
            if(System.getProperty("os.name").contains("Win")) {
                process = new ProcessBuilder(cur + "\\showg_win32.exe", cur + "\\tmpF.g6");
            } else {
                process = new ProcessBuilder(cur + "/showg", cur + "/tmpF.g6");
            }
            Process p = process.start();
            p.waitFor();
            BufferedReader bri = new BufferedReader
                    (new InputStreamReader(p.getInputStream()));
            p.waitFor();
            Scanner sc = new Scanner(bri);
            return parseGraph(sc);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static GraphModel parseGraph(Scanner sc) {
        GraphModel g = new GraphModel();
        g.setDirected(false);
        sc.nextLine();
        String order = sc.nextLine();

        order = order.substring(order.lastIndexOf("r") + 1,
                order.lastIndexOf("."));
        order = order.trim();
        int ord = Integer.parseInt(order);
        for (int i = 0; i < ord; i++) g.addVertex(new Vertex());
        for (int i = 0; i < ord; i++) {
            String tmp = sc.nextLine();
            tmp = tmp.substring(tmp.indexOf(":") + 1);
            Scanner sc2 = new Scanner(tmp.trim());
            while (sc2.hasNext()) {
                String num = sc2.next();
                if (num.contains(";")) num = num.substring(0, num.indexOf(";"));
                int id = Integer.parseInt(num);
                if (!g.isEdge(g.getVertex(i), g.getVertex(id)))
                    g.addEdge(new Edge(g.getVertex(i), g.getVertex(id)));
            }
        }

        return g;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(System.getProperty("os.name"));
        BufferedReader br = ShowG.showG("tmpF.g6");
        System.out.println(br.readLine());
        System.out.println(br.readLine());
        System.out.println(br.readLine());
        System.out.println(br.readLine());
    }
}
