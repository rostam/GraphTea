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

@CommandAttitude(name = "ECI_Conjecture", abbreviation = "_Eci_Conjecture")
public class ECIConjecture implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "ECI-Conjecture";
    }

    public String getDescription() {
        return " ECI-Conjecture ";
    }

    public RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        ret.setTitles(Arrays.asList(" m ", " n ", " EC(-5) ", " EC(-4) ", " EC(-3) ", " EC(-2) ", " EC(-1) ", " EC(1) "));

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        EccentricConnectiveIndex eci = new EccentricConnectiveIndex();

        List<Object> v = new ArrayList<>();
        v.add(m);
        v.add(n);

        eci.setA(-5);
        v.add(eci.calculate(g));

        eci.setA(-4);
        v.add(eci.calculate(g));

        eci.setA(-3);
        v.add(eci.calculate(g));

        eci.setA(-2);
        v.add(eci.calculate(g));

        eci.setA(-1);
        v.add(eci.calculate(g));

        eci.setA(1);
        v.add(eci.calculate(g));

        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Degree";
    }
}
