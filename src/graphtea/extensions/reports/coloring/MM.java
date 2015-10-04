package graphtea.extensions.reports.coloring;

import Jama.Matrix;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by rostam on 04.10.15.
 */
public class MM {
    public static Matrix loadMatrixFromSPARSE(File inputFile) throws IOException {
        Scanner sc = new Scanner(inputFile);
        String line;
        do {
            line = sc.nextLine();
        }while(line.contains("%"));
        Scanner sc2 = new Scanner(line);
        int rows = sc2.nextInt();
        int cols = sc2.nextInt();
        Matrix mm = new Matrix(rows, cols);
        while(sc.hasNextLine()) {
            line = sc.nextLine();
            Scanner sc3 = new Scanner(line);
            mm.set(sc3.nextInt()-1,sc3.nextInt()-1,1);
        }
        return mm;
    }
}
