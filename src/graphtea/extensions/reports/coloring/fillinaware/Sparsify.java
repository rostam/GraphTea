package graphtea.extensions.reports.coloring.fillinaware;

import Jama.Matrix;

/**
 * Created by rostam on 30.12.15.
 *
 */
public class Sparsify {
    public static Matrix sparsify(Matrix mm, int k) {
        int rows = mm.getRowDimension();
        int cols = mm.getColumnDimension();

        Matrix newMM = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                newMM.set(i, j, 0);
            }
        }

        for (int d = 0; d < ((rows / k) - 1) * k; d = d + k) {
            for (int ii = 0; ii < k; ii++) {
                for (int jj = 0; jj < k; jj++) {
                    if ((d + ii) < rows && (d + jj) < cols) {
                        if (mm.get(d + ii, d + jj) != 0) {
                            newMM.set(d + ii, d + jj, 1);
                        }
                    }
                }
            }
        }

        int d1 = ((rows / k) - 1) * k;
        for (int cnt1 = d1; cnt1 < rows; cnt1++) {
            for (int cnt2 = d1; cnt2 < cols; cnt2++) {
                if (mm.get(cnt1, cnt2) != 0) {
                    newMM.set(cnt1, cnt2, 1);
                }
            }
        }

        return newMM;
    }
}
