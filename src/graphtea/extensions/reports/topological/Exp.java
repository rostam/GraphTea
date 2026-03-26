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

@CommandAttitude(name = "Exp", abbreviation = "_Exp")
public class Exp implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Exp";
    }

    public String getDescription() {
        return " Exp ";
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        RenderTable ret = new RenderTable();
        ret.setTitles(Arrays.asList(" Exp-ABC ", " m ", " n "));

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        double expABC = zif.getexpABCindex();

        List<Object> v = new ArrayList<>();
        v.add(expABC);
        v.add(m);
        v.add(n);

        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Checking";
    }
}
