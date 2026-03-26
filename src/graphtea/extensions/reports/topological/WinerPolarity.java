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

@CommandAttitude(name = "WinerPolarity", abbreviation = "_WinerPolarity")
public class WinerPolarity implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "WinerPolarity";
    }

    public String getDescription() {
        return " WinerPolarity ";
    }

    public RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        ret.setTitles(Arrays.asList(" m ", " n ", " W_p "));

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        WienerPolarityIndex wp = new WienerPolarityIndex();

        List<Object> v = new ArrayList<>();
        v.add(m);
        v.add(n);
        v.add(wp.calculate(g));

        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Degree";
    }
}
