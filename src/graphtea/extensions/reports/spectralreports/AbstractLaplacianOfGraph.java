package graphtea.extensions.reports.spectralreports;

import Jama.Matrix;
import graphtea.extensions.AlgorithmUtils;
import graphtea.graph.graph.GraphModel;
import graphtea.plugins.reports.extension.GraphReportExtension;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Template for Laplacian-family spectrum reports that show the matrix and its
 * eigendecomposition. Subclasses define how to combine the degree matrix D and
 * adjacency matrix A (e.g. D−A for Laplacian, D+A for signless Laplacian).
 */
abstract class AbstractLaplacianOfGraph implements GraphReportExtension<ArrayList<String>> {

    private boolean inDegree;

    /** Combine degree matrix {@code d} and adjacency matrix {@code a} into the target matrix. */
    protected abstract Matrix combine(Matrix d, Matrix a);

    /** Label printed above the matrix rows (e.g. "Laplacian Matrix:"). */
    protected abstract String getMatrixLabel();

    private Matrix buildDegreeMatrix(Matrix a) {
        int n = a.getArray().length;
        double[][] at = a.getArray();
        Matrix d = new Matrix(n, n);
        double[][] dt = d.getArray();
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = 0; j < n; j++) {
                sum += inDegree ? at[j][i] : at[i][j];
            }
            dt[i][i] = sum;
        }
        return d;
    }

    private Matrix buildMatrix(Matrix a) {
        return combine(buildDegreeMatrix(a), a);
    }

    private ArrayList<String> showMatrix(Matrix a) {
        ArrayList<String> result = new ArrayList<>();
        result.add(getMatrixLabel());
        for (double[] row : buildMatrix(a).getArray()) {
            result.add(Arrays.toString(row));
        }
        return result;
    }

    private ArrayList<String> eigenDecomposition(Matrix a) {
        ArrayList<String> result = new ArrayList<>();
        result.add("Eigen Value Decomposition:");
        result.addAll(AlgorithmUtils.formatEigenDecomposition(buildMatrix(a)));
        return result;
    }

    @Override
    public final ArrayList<String> calculate(GraphModel g) {
        try {
            if (g.isDirected()) {
                int choice = JOptionPane.showOptionDialog(null,
                        "Do you want to use in or out degrees for calculation of the Laplacian Matrix?",
                        "Laplacian", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, new String[]{"In Degrees", "Out Degrees"}, "In Degrees");
                if (choice == -1) {
                    return null;
                }
                inDegree = (choice == 0);
            }
            Matrix a = g.getWeightedAdjacencyMatrix();
            ArrayList<String> result = new ArrayList<>(showMatrix(a));
            result.addAll(eigenDecomposition(a));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getCategory() {
        return "Spectral";
    }
}
