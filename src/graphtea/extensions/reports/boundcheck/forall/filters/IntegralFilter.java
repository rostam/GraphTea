package graphtea.extensions.reports.boundcheck.forall.filters;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import graphtea.extensions.reports.boundcheck.forall.GraphFilter;
import graphtea.extensions.reports.boundcheck.forall.Utils;
import graphtea.graph.graph.GraphModel;

/**
 * Created by rostam on 30.09.15.
 */
public class IntegralFilter implements GraphFilter {
    public boolean isIntegral(GraphModel g) {
        Matrix A = g.getWeightedAdjacencyMatrix();
        EigenvalueDecomposition ed = A.eig();
        double rrv[] = ed.getRealEigenvalues();
        double rv[] = Utils.round(rrv, 3);
        for (int i = 0; i < rv.length; i++) {
            if (Math.floor(rv[i]) != rv[i]) return false;
        }

        return true;
    }

    @Override
    public boolean filter(GraphModel g) {
        return isIntegral(g);
    }

    @Override
    public String getName() {
        return "int";
    }
}
