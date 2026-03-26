package graphtea.extensions.reports.topological;

import graphtea.extensions.AlgorithmUtils;
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

@CommandAttitude(name = "AG", abbreviation = "_AGIndex")
public class AGIndex implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "AG";
    }

    public String getDescription() {
        return "AG";
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        RenderTable ret = new RenderTable();
        ret.setTitles(Arrays.asList(" m ", " n ", " R ", " GA ", " AG ", " SDD ", " V. Degrees "));

        ArrayList<Integer> al = AlgorithmUtils.getDegreesList(g);
        Collections.sort(al);

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        double R = zif.getSecondZagreb(-0.5);
        double GA = zif.getGAindex();
        double AG = zif.getAGIndex();
        double SDD = zif.getSDDIndex();

        List<Object> v = new ArrayList<>();
        v.add(m);
        v.add(n);
        v.add(R);
        v.add(GA);
        v.add(AG);
        v.add(SDD);
        v.add(al.toString());

        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Degree";
    }
}
