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

@CommandAttitude(name = "Exponential", abbreviation = "_Exponential")
public class Exponential implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Exponential";
    }

    public String getDescription() {
        return " Exponential ";
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        RenderTable ret = new RenderTable();
        ret.setTitles(Arrays.asList(" m ", " n ", " Exp-M2 ", " Exp-AZI "));

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        double expM12 = zif.getexpSecondZagreb(1);
        double expAZI = zif.getexpAugumentedZagrebIndex();

        List<Object> v = new ArrayList<>();
        v.add(m);
        v.add(n);
        v.add(expM12);
        v.add(expAZI);

        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Checking";
    }
}
