package graphtea.extensions.reports.topological;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ali Rostami
 */

@CommandAttitude(name = "comparision", abbreviation = "_comparision")
public class comparision implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "comparision";
    }

    public String getDescription() {
        return " comparision ";
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        RenderTable ret = new RenderTable();
        ret.setTitles(Arrays.asList(" m ", " n ", " R ", " VR ", " H ", " GA ", " chi ", " SDD ", "ISI..", " Hyper "));

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        double R = zif.getSecondZagreb(-0.5);
        double VR = zif.getVariationRandicIndex();
        double H = zif.getHarmonicIndex();
        double GA = zif.getGAindex();
        double chi = zif.getGeneralSumConnectivityIndex(-0.5);
        double SDD = zif.getSDDIndex();
        double ISI = zif.getInverseSumIndegIndex();
        double chi2 = zif.getGeneralSumConnectivityIndex(2);

        List<Object> v = new ArrayList<>();
        v.add(m);
        v.add(n);
        v.add(R);
        v.add(VR);
        v.add(H);
        v.add(GA);
        v.add(chi);
        v.add(SDD);
        v.add(ISI);
        v.add(chi2);

        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Degree";
    }
}
