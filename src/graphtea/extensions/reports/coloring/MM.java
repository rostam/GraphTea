package graphtea.extensions.reports.coloring;

import Jama.Matrix;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by rostam on 04.10.15.
 * Load .mtx format file for matrices
 */
public class MM {
    public static Matrix loadMatrixFromSPARSE(File inputFile) throws IOException {
        Scanner sc = new Scanner(inputFile);
        String line;
        boolean isSymmetric = false;
        do {
            line = sc.nextLine();
            if(line.contains("symmetric")) isSymmetric=true;
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

        if(isSymmetric) {
            for(int i=0;i<rows;i++) {
                for(int j=0;j<cols;j++) {
                    if(mm.get(i,j)== 1) if(mm.get(j,i)!=1) mm.set(j,i,1);
                }
            }
        }
        return mm;
    }

    public static int NNZ(Matrix mm) {
        int rows = mm.getRowDimension();
        int cols = mm.getColumnDimension();
        int nnz=0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(mm.get(i,j)!=0) nnz++;
            }
        }
        return nnz;
    }
}
