package graphtea.extensions.reports.topological;

import graphtea.extensions.reports.matching.MaxMatchingExtension;
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

@CommandAttitude(name = "SDD", abbreviation = "_SDD")
public class SDD implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "SDD";
    }

    public String getDescription() {
        return " SDD ";
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        RenderTable ret = new RenderTable();
        ret.setTitles(Arrays.asList(" m ", " n ", " M2 ", " M1 ", " M2-M1 ", "Matching"));

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        double M12 = zif.getSecondZagreb(1);
        double M21 = zif.getFirstZagreb(1);

        double maxMatching = new MaxMatchingExtension().numOfMatching(g);

        List<Object> v = new ArrayList<>();
        v.add(m);
        v.add(n);
        v.add(M12);
        v.add(M21);
        v.add(M12 - M21);
        v.add(maxMatching);

        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Degree";
    }
}
