package graphtea.extensions.reports.coloring.fillinaware;

import Jama.Matrix;

/**
 * Created by rostam on 21.01.16.
 *
 */
public class MyMat extends Matrix {
    //init with zero
    public MyMat(int i, int i1) {
        super(i, i1);
        zeroM();
    }

    public MyMat(Matrix m) {
        super(m.getRowDimension(), m.getColumnDimension());
        for (int i = 0; i < m.getRowDimension(); i++) {
            for (int j = 0; j < m.getColumnDimension(); j++) {
                this.set(i, j, m.get(i, j));
            }
        }
    }

    void zeroM() {
        for (int i = 0; i < this.getRowDimension(); i++) {
            for (int j = 0; j < this.getColumnDimension(); j++) {
                this.set(i, j, 0);
            }
        }
    }

    public int nnz() {
        int rows = this.getRowDimension();
        int cols = this.getColumnDimension();
        int nnz = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (this.get(i, j) != 0) nnz++;
            }
        }
        return nnz;
    }

    public MyMat sparsify(int k) {
        int rows = this.getRowDimension();
        int cols = this.getColumnDimension();

        MyMat newMM = new MyMat(rows, cols);
        for (int d = 0; d < ((rows / k) - 1) * k; d = d + k) {
            for (int ii = 0; ii < k; ii++) {
                for (int jj = 0; jj < k; jj++) {
                    if ((d + ii) < rows && (d + jj) < cols) {
                        if (this.get(d + ii, d + jj) != 0) {
                            newMM.set(d + ii, d + jj, 1);
                        }
                    }
                }
            }
        }

        int d1 = ((rows / k) - 1) * k;
        for (int cnt1 = d1; cnt1 < rows; cnt1++) {
            for (int cnt2 = d1; cnt2 < cols; cnt2++) {
                if (this.get(cnt1, cnt2) != 0) {
                    newMM.set(cnt1, cnt2, 1);
                }
            }
        }

        return newMM;
    }
}
