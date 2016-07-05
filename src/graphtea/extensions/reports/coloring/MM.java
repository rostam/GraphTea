package graphtea.extensions.reports.coloring;

import Jama.Matrix;
import graphtea.extensions.reports.coloring.fillinaware.SpMat;

import java.io.*;
import java.util.Scanner;

/**
 * Created by rostam on 04.10.15.
 * Load .mtx format file for matrices
 */
public class MM {
    public static void saveMtxFormat(File out, Matrix m) throws IOException {
        String s = "%%MatrixMarket matrix coordinate real";
        boolean isSymmetric = true;
        int nnz = 0;
        int diagNonZero =0;
        for (int i = 0; i < m.getRowDimension(); i++) {
            for (int j = 0; j < m.getColumnDimension(); j++) {
                if (m.get(i, j) != m.get(j, i)) {
                    isSymmetric = false;
                }
                if (m.get(i, j) != 0) {
                    nnz++;
                }
            }
            if(m.get(i,i) != 0) diagNonZero++;
        }

        if (isSymmetric) {
            s += " symmetric";
            nnz=(nnz-diagNonZero)/2;
            nnz+=diagNonZero;
        } else s += " general";

        FileOutputStream fos = new FileOutputStream(out);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        bw.write(s);
        bw.newLine();
        bw.write(m.getRowDimension() + " " + m.getColumnDimension() + " "+nnz);
        bw.newLine();

        if (isSymmetric) {
            for (int i = 0; i < m.getRowDimension(); i++) {
                for (int j = i; j < m.getColumnDimension(); j++) {
                    if (m.get(i, j) != 0) {
                        bw.write((i+1) + " " + (j+1) + " " + m.get(i, j));
                        bw.newLine();
                    }
                }
            }
        } else {
            for (int i = 0; i < m.getRowDimension(); i++) {
                for (int j = 0; j < m.getColumnDimension(); j++) {
                    if (m.get(i, j) != 0) {
                        bw.write((i+1) + " " + (j+1) + " " + m.get(i, j));
                        bw.newLine();
                    }
                }
            }
        }
        bw.close();
    }

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
            mm.set(sc3.nextInt()-1,sc3.nextInt()-1,sc3.nextDouble());
        }

        if(isSymmetric) {
            for(int i=0;i<rows;i++) {
                for(int j=0;j<cols;j++) {
                    if(mm.get(i,j)!= 0) if(mm.get(j,i)==0) mm.set(j,i,mm.get(i,j));
                }
            }
        }
        return mm;
    }



    public static void saveMtxFormat(File out, SpMat m) throws IOException {
        String s = "%%MatrixMarket matrix coordinate real general";
        int nnz = m.nnz();
        FileOutputStream fos = new FileOutputStream(out);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        bw.write(s);
        bw.newLine();
        bw.write(m.rows() + " " + m.rows() + " " + nnz);
        bw.newLine();
        for (int i = 0; i < m.rows(); i++) {
            for(int j : m.get(i)) {
                bw.write((i + 1) + " " + (j + 1) + " " + 1.0);
                bw.newLine();
            }
        }
        bw.close();
    }
}
