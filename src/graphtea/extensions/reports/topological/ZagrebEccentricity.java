package graphtea.extensions.reports.topological;

import graphtea.extensions.reports.clique.MaxCliqueSize;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ali Rostami
 */
public class ZagrebEccentricity implements GraphReportExtension<RenderTable> {

    public String getName() {
        return "Zagreb Eccentricities";
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        RenderTable ret = new RenderTable();
        ret.setTitles(Arrays.asList(" m ", " n ", " E1 ", " Thm 1.6 "));

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        List<Object> v = new ArrayList<>();
        v.add(m);
        v.add(n);
        v.add(zif.getFirstZagrebEccentricity(g));
        v.add(MaxCliqueSize.maxCliqueSize(g));

        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Checking";
    }
}
