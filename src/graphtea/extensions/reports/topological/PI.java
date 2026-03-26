package graphtea.extensions.reports.topological;

import graphtea.extensions.reports.others.PiIndex;
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

@CommandAttitude(name = "PI", abbreviation = "_PI")
public class PI implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "PI";
    }

    public String getDescription() {
        return " PI ";
    }

    public RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        ret.setTitles(Arrays.asList(" m ", " n ", " PI "));

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        PiIndex PI = new PiIndex();

        List<Object> v = new ArrayList<>();
        v.add(m);
        v.add(n);
        v.add(PI.calculate(g));

        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Checking";
    }
}
