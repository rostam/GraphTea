package graphtea.extensions.algorithms.homomorphism;

import graphtea.extensions.AllSetPartitions;
import graphtea.extensions.generators.CircleGenerator;
import graphtea.extensions.generators.GeneralizedPetersonGenerator;
import graphtea.extensions.io.g6format.SaveGraph6Format;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.plugins.main.saveload.core.GraphIOException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class FindingHomomorphism {

    Homomorphism findAHomomorphism(GraphModel G, GraphModel H) throws GraphIOException {
        SaveGraph6Format saveGraph6Format = new SaveGraph6Format();
        SaveGraph6Format.isAppend = false;
        saveGraph6Format.write(new File("G.g6"), G);
        saveGraph6Format.write(new File("H.g6"), H);
        HashMap<Vertex, Vertex> homomorphism = new HashMap<>();
        ProcessBuilder process=null;
        if(System.getProperty("os.name").contains("Win")) {
            System.out.println("This option works only in linux.");
        } else {
            process = new ProcessBuilder("python", "FindHomomorphisms.py");
            Process p = null;
            try {
                p = process.start();
                int exitCode = p.waitFor();
                p.getInputStream().close();
                p.getOutputStream().close();
                p.getErrorStream().close();
//                while(exitCode !=0){
//                    p.exitValue();
//                }
                if(!p.isAlive()){
                    Scanner sc = new Scanner(new File("homomorphism_G_H"));
                    String line = sc.nextLine();
                    if(line.contains("not found")) {
                        System.out.println("No homomorphism not found");
                        return null;
                    }
                    String[] parts = line.split(" ");
                    for(int j = 0; j < parts.length;j++) {
                        String partition = parts[j];
                        for (int i = 0; i < partition.length();i++) {
                            int GV1 = partition.charAt(i) - 97;
                            homomorphism.put(G.getVertex(GV1), H.getVertex(j));
                        }
                    }
                    Homomorphism h = new Homomorphism(G, H, homomorphism);
                    System.out.println(h.isValid());
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        GraphModel peterson = GeneralizedPetersonGenerator.generateGeneralizedPeterson(5,2);
        GraphModel circle3 = CircleGenerator.generateCircle(3);
        try {
            new FindingHomomorphism().findAHomomorphism(peterson, circle3);
        } catch (GraphIOException e) {
            e.printStackTrace();
        }
//
//        try {
//            new FindingHomomorphism().findAHomomorphism(circle3, peterson);
//        } catch (GraphIOException e) {
//            e.printStackTrace();
//        }
    }
}
