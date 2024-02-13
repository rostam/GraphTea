package graphtea.extensions.reports.others;

import Jama.Matrix;
import graphtea.extensions.AlgorithmUtils;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.Vector;

@CommandAttitude(name = "MaximumDegreeEnergyCompare", abbreviation = "_max_deg_energy_compare")
public class MaxDegreeEnergyCompare implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Maximum Degree Energy Compare";
    }

    public String getDescription() {
        return "Maximum Degree Energy based on" +
                "C. Adiga, M. Smitha\n" +
                "      On maximum degree energy of a graph\n" +
                "      Int. Journal of Contemp. Math. Sciences, Vol. 4, 2009, no. 5-8, 385-396. ";
    }

    @Override
    public RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();
        titles.add("m ");
        titles.add("n ");
        titles.add("Maximum Degree Energy");
        titles.add("Estrada Version");
        titles.add("Eigen Values");
        ret.setTitles(titles);
        Matrix m = AlgorithmUtils.getMaxDegreeAdjacencyMatrix(g);
        double sum = AlgorithmUtils.sumOfEigenValues(m);
        double estrada = AlgorithmUtils.sumOfExpOfEigenValues(m);
        Vector<Object> v = new Vector<>();
        v.add(g.getVerticesCount());
        v.add(g.getEdgesCount());
        v.add(sum);
        v.add(estrada);
        v.add(AlgorithmUtils.getEigenValues(m));
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Checking";
    }
}
