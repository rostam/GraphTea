package graphtea.extensions.reports.spectralreports;

import Jama.Matrix;

/**
 * @author Hooman Mohajeri Moghaddam
 */
public class LaplacianOfGraph extends AbstractLaplacianOfGraph {

    @Override
    protected Matrix combine(Matrix d, Matrix a) {
        return d.minus(a);
    }

    @Override
    protected String getMatrixLabel() {
        return "Laplacian Matrix:";
    }

    @Override
    public String getName() {
        return "Spectrum of Laplacian";
    }

    @Override
    public String getDescription() {
        return "The Laplacian matrix associated with the graph";
    }
}
