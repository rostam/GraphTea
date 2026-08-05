package graphtea.extensions.reports.spectralreports;

import Jama.Matrix;
import graphtea.extensions.AlgorithmUtils;
import graphtea.graph.graph.GraphModel;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Hooman Mohajeri Moghaddam
 */
public class EccentricityMatrixOfGraph implements GraphReportExtension<ArrayList<String>> {

    public String getName() {
        return "Spectrum of Eccentricity Matrix";
    }

    public String getDescription() {
        return "The eccentricity matrix associated with the graph";
    }

    public ArrayList<String> calculate(GraphModel g) {
        try {
            int[][] dist = AlgorithmUtils.getAllPairsDistances(g);
            Matrix a = AlgorithmUtils.eccentricityMatrix(g, dist);

            ArrayList<String> result = new ArrayList<>();
            result.add("Eccentricity Matrix:");
            for (double[] row : a.getArray()) {
                result.add(Arrays.toString(row));
            }
            result.add("Eigen Value Decomposition:");
            result.addAll(AlgorithmUtils.formatEigenDecomposition(a));
            return result;
        } catch (Exception e) {
            ExceptionHandler.catchException(e);
        }
        return null;
    }

    @Override
    public String getCategory() {
        return "Spectral";
    }
}
