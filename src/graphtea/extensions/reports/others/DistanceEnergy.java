package graphtea.extensions.reports.others;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import graphtea.extensions.AlgorithmUtils;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.Vector;

@CommandAttitude(name = "DistanceEnergy", abbreviation = "_distance_energy")
public class DistanceEnergy implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Distance Energy";
    }

    public String getDescription() {
        return "Distance Energy based on" +
                "Gopalapillai Indulal,a Ivan Gutmanb and Vijayakumarc\n" +
                "      ON DISTANCE ENERGY OF GRAPHS\n" +
                "      MATCH Commun. Math. Comput. Chem. 60 (2008) 461-472. ";
    }

    @Override
    public RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();
        titles.add("m ");
        titles.add("n ");
        titles.add("Distance Energy");
        titles.add("Eigen Values");
        ret.setTitles(titles);

        Matrix m = AlgorithmUtils.getDistanceAdjacencyMatrix(g);
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