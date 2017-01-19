package graphtea.extensions.reports.boundcheck.forall.filters;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import graphtea.extensions.reports.boundcheck.forall.GraphFilter;
import graphtea.extensions.reports.boundcheck.forall.Utils;
import graphtea.graph.graph.GraphModel;

/**
 * Created by rostam on 30.09.15.
 * @author M. Ali Rostami
 */
public class LaplacianIntegralFilter implements GraphFilter {
    public boolean isLaplacianIntegral(GraphModel g) {
        Matrix B = g.getWeightedAdjacencyMatrix();
        Matrix A = getLaplacian(B);
        EigenvalueDecomposition ed = A.eig();
        double rrv[] = ed.getRealEigenvalues();
        double rv[] = Utils.round(rrv, 3);
        for (double aRv : rv) {
            if (Math.floor(aRv) != aRv) return false;
        }

        return true;
    }

    /**
     * Undirected Laplacian.
     *
     * @param A the Adjacency matrix of the graph
     * @return Laplacian of the graph
     */
    private Matrix getLaplacian(Matrix A) {
        //double[][] res=new double[g.numOfVertices()][g.numOfVertices()];


        int n = A.getArray().length;
        double[][] ATemp = A.getArray();

        Matrix D = new Matrix(n, n);
        double[][] DTemp = D.getArray();
        int sum;
        for (int i = 0; i < n; i++) {
            sum = 0;
            for (int j = 0; j < n; j++) {
                sum += ATemp[j][i];
            }
            DTemp[i][i] = sum;
        }

        return D.minus(A);
    }

    @Override
    public boolean filter(GraphModel g) {
        return isLaplacianIntegral(g);
    }

    @Override
    public String getName() {
        return "lint";
    }
}
