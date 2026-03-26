package graphtea.extensions.reports.topological;

import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.reports.basicreports.Diameter;
import graphtea.extensions.reports.basicreports.NumOfVerticesWithDegK;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Ali Rostami
 */

@CommandAttitude(name = "AllCheck", abbreviation = "_AllCheck")
public class AllCheck implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "AllCheck";
    }

    public String getDescription() {
        return " AllCheck ";
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        RenderTable ret = new RenderTable();
        ret.setTitles(Arrays.asList(" m ", " n ", " R ", " H ", " VR ", " GA ", " chi ", "ID", " SDD ", " ISI ", " check ", " Diameter "));

        double maxDeg = 0;
        double minDeg2 = AlgorithmUtils.getMinNonPendentDegree(g);

        ArrayList<Integer> al = AlgorithmUtils.getDegreesList(g);
        Collections.sort(al);
        maxDeg = al.get(al.size() - 1);

        int p = NumOfVerticesWithDegK.numOfVerticesWithDegK(g, 1);

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        double R = zif.getSecondZagreb(-0.5);
        double H = zif.getHarmonicIndex();
        double GA = zif.getGAindex();
        double chi = zif.getGeneralSumConnectivityIndex(-0.5);
        double ID = zif.getFirstZagreb(-2);
        double SDD = zif.getSDDIndex();
        double ISI = zif.getInverseSumIndegIndex();
        double VR = zif.getVariationRandicIndex();

        int diameter = new Diameter().calculate(g);

        List<Object> v = new ArrayList<>();
        v.add(m);
        v.add(n);
        v.add(R);
        v.add(H);
        v.add(VR);
        v.add(GA);
        v.add(chi);
        v.add(ID);
        v.add(SDD);
        v.add(ISI);
        v.add(((p * minDeg2) / (maxDeg + 1)) + ((m - p) * (Math.sqrt((2 * maxDeg * Math.pow(minDeg2, 6)) / ((Math.pow(maxDeg, 6)) + (2 * Math.pow(minDeg2, 5)) + (4 * Math.pow(minDeg2, 2) * Math.pow(maxDeg, 3)))))));
        v.add(diameter);

        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Degree";
    }
}
