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

@CommandAttitude(name = "Complex", abbreviation = "_Complex")
public class Complex implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Complex";
    }

    public String getDescription() {
        return " Complex ";
    }

    public RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        ret.setTitles(Arrays.asList(" vertices ", " edges "));

        List<Object> v = new ArrayList<>();
        v.add(g.getVerticesCount());
        v.add(g.getEdgesCount());
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Checking";
    }
}
