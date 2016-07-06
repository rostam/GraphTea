package graphtea.extensions.reports.coloring.fillinaware;

import Jama.Matrix;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Vector;

/**
 * Created by rostam on 21.01.16.
 *
 */
public class SpMat extends Vector<HashSet<Integer>> {
    private int rows, cols;

    public SpMat(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        for (int i = 0; i < rows; i++) {
            this.add(new HashSet<Integer>());
        }
    }

    public SpMat(Matrix m) {
        this.rows = m.getRowDimension();
        this.cols = m.getColumnDimension();
        for (int i = 0; i < rows; i++) {
            this.add(new HashSet<Integer>());
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (m.get(i, j) != 0) {
                    this.get(i).add(j);
                }
            }
        }
    }

    public int rows() {
        return rows;
    }

    public int cols() {
        return cols;
    }

    public SpMat copy() {
        SpMat cp = new SpMat(rows, cols);
        for (int i = 0; i < rows; i++) {
            cp.add(new HashSet<Integer>());
            for (int j : this.get(i)) {
                cp.get(i).add(j);
            }
        }
        return cp;
    }

    public int nnz() {
        int ret = 0;
        for (int i = 0; i < rows; i++) {
            ret += this.get(i).size();
        }
        return ret;
    }


    public void set(int i, int j) {
        if (!this.get(i).contains(j)) {
            this.get(i).add(j);
        }
    }

    public boolean contains(int i, int j) {
        return this.get(i).contains(j);
    }

    public SpMat sparsify(int k) {
        SpMat newMM = new SpMat(this.rows, this.cols);
        if(rows%k == 0) {
            for (int d = 0; d < ((rows / k) - 2) * k; d = d + k) {
                for (int ii = 0; ii < k; ii++) {
                    for (int jj = 0; jj < k; jj++) {
                        if ((d + ii) < rows && (d + jj) < cols) {
                            if (this.contains(d + ii, d + jj)) {
                                newMM.set(d + ii, d + jj);
                            }
                        }
                    }
                }
            }

            int d1 = ((rows / k) - 2) * k;
            for (int cnt1 = d1; cnt1 < rows; cnt1++) {
                for (int cnt2 = d1; cnt2 < cols; cnt2++) {
                    if (this.contains(cnt1, cnt2)) {
                        newMM.set(cnt1, cnt2);
                    }
                }
            }
        } else {
            for (int d = 0; d < ((rows / k) - 1) * k; d = d + k) {
                for (int ii = 0; ii < k; ii++) {
                    for (int jj = 0; jj < k; jj++) {
                        if ((d + ii) < rows && (d + jj) < cols) {
                            if (this.contains(d + ii, d + jj)) {
                                newMM.set(d + ii, d + jj);
                            }
                        }
                    }
                }
            }

            int d1 = ((rows / k) - 1) * k;
            for (int cnt1 = d1; cnt1 < rows; cnt1++) {
                for (int cnt2 = d1; cnt2 < cols; cnt2++) {
                    if (this.contains(cnt1, cnt2)) {
                        newMM.set(cnt1, cnt2);
                    }
                }
            }
        }

        return newMM;
    }


    public void writeToFile(String fileName) throws IOException {
        FileWriter fw = new FileWriter(fileName);
        for (int i = 0; i < this.size(); i++) {
            for (int j : this.get(i)) {
                fw.write(j + " ");
            }
            fw.write("\n");
        }
        fw.close();
    }
}
