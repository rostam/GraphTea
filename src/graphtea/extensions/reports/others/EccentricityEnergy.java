package graphtea.extensions.reports.others;

import Jama.Matrix;
import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.reports.basicreports.Diameter;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.List;

public class EccentricityEnergy implements GraphReportExtension<RenderTable> {

    public String getName() {
        return "Eccentricity Energy";
    }

    @Override
    public RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        List<String> titles = new ArrayList<>();
        titles.add("m");
        titles.add("n");
        titles.add("Eccentricity Energy");
        titles.add("Eigen Values");
        titles.add("Diameter");
        ret.setTitles(titles);

        int[][] dist = AlgorithmUtils.getAllPairsDistances(g);
        Matrix m = AlgorithmUtils.eccentricityMatrix(g, dist);
        double[] rv = m.eig().getRealEigenvalues();
        double sum = 0;
        for (double v : rv) {
            sum += AlgorithmUtils.round(Math.abs(v), 5);
        }

        List<Object> v = new ArrayList<>();
        v.add(g.getEdgesCount());
        v.add(g.getVerticesCount());
        v.add(sum);
        v.add(AlgorithmUtils.getEigenValues(m));
        v.add((int) new Diameter().calculate(g));
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Checking";
    }
}
