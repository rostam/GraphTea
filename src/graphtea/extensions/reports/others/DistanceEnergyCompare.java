package graphtea.extensions.reports.others;

import Jama.Matrix;
import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.reports.spectralreports.DistanceEnergy;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.Vector;

@CommandAttitude(name = "DistanceEnergyCompare", abbreviation = "_distance_energy_compare")
public class DistanceEnergyCompare implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Distance Energy Compare";
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
        Vector<Object> v = new Vector<>();
        v.add(g.getVerticesCount());
        v.add(g.getEdgesCount());
        v.add(new DistanceEnergy().calculate(g));
        v.add(AlgorithmUtils.getEigenValues(m));
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Checking";
    }
}
