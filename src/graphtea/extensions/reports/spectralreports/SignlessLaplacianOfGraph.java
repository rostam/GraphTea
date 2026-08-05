package graphtea.extensions.reports.spectralreports;

import Jama.Matrix;

/**
 * @author Hooman Mohajeri Moghaddam
 */
public class SignlessLaplacianOfGraph extends AbstractLaplacianOfGraph {

    @Override
    protected Matrix combine(Matrix d, Matrix a) {
        return d.plus(a);
    }

    @Override
    protected String getMatrixLabel() {
        return "Signless Laplacian Matrix:";
    }

    @Override
    public String getName() {
        return "Spectrum of Signless Laplacian";
    }

    @Override
    public String getDescription() {
        return "The Signless Laplacian matrix associated with the graph";
    }
}
