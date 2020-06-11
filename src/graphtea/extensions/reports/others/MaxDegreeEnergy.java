package graphtea.extensions.reports.others;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.algorithms.shortestpath.algs.FloydWarshall;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.Vector;

@CommandAttitude(name = "MaximumDegreeEnergy", abbreviation = "_max_deg_energy")
public class MaxDegreeEnergy implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Maximum Degree Energy";
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
        titles.add("Eigen Values");
        ret.setTitles(titles);

        Matrix m = AlgorithmUtils.getMaxDegreeAdjacencyMatrix(g);
        EigenvalueDecomposition ed = m.eig();
        double[] rv = ed.getRealEigenvalues();
        double sum = 0;

        //positiv RV
        Double[] prv = new Double[rv.length];
        for (int i = 0; i < rv.length; i++) {
            prv[i] = Math.abs(rv[i]);
            prv[i] = (double)Math.round(prv[i] * 100000d) / 100000d;
            sum += prv[i];
        }
        Vector<Object> v = new Vector<>();
        v.add(g.getVerticesCount());
        v.add(g.getEdgesCount());
        v.add(sum);
        v.add(AlgorithmUtils.getEigenValues(m));
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "OurWorks-Conjectures";
    }
}