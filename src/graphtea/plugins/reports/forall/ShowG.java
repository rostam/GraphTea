package graphtea.plugins.reports.forall;

import graphtea.graph.graph.GraphModel;
import graphtea.plugins.main.saveload.matrix.Matrix;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by rostam on 30.09.15.
 */
public class ShowG {
    public static BufferedReader showG(String file) {
        try {
            String cur = new java.io.File(".").getCanonicalPath();
            ProcessBuilder process = new ProcessBuilder(cur + "/showg", cur + "/"+file);
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
        try {
            FileWriter fw = new FileWriter("tmpF");
            fw.write(line);
            fw.close();
            String cur = new java.io.File(".").getCanonicalPath();
            ProcessBuilder process = new ProcessBuilder(cur + "/showg", cur + "/tmpF");
            Process p = process.start();
            p.waitFor();
            BufferedReader bri = new BufferedReader
                    (new InputStreamReader(p.getInputStream()));
            p.waitFor();
            bri.readLine();
            int cnt =0;
            String g="";
            while ((line = bri.readLine()) != null) {
                if(!line.equals("")) {
                    g+=line+"\n";
                } else {
                    if(!g.equals("")) {
                        cnt++;
                        GraphModel tmp = new GraphModel();
                        Matrix.Matrix2Graph(Matrix.String2Matrix(g), tmp);
                        return tmp;
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
