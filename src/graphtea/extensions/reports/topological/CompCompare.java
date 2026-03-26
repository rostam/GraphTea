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

@CommandAttitude(name = "Complexity_Compare", abbreviation = "_Complexity_Compare")
public class CompCompare implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Complexity-Compare";
    }

    public String getDescription() {
        return "Complexity-Compare";
    }

    public RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        ret.setTitles(Arrays.asList(" m ", " n ", " E-Comp ", " Wie-Comp "));

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        EccentricityComplexityIndex ecompi = new EccentricityComplexityIndex();
        WienerComplexIndex WCI = new WienerComplexIndex();

        int Ecomp = (int) ecompi.calculate(g);
        int Wcomp = (int) WCI.calculate(g);

        List<Object> v = new ArrayList<>();
        v.add(m);
        v.add(n);
        v.add(Ecomp);
        v.add(Wcomp);

        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Checking";
    }
}
