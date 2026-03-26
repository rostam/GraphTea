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

@CommandAttitude(name = "PB", abbreviation = "_PB")
public class PB implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "PB";
    }

    public String getDescription() {
        return " PB ";
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        RenderTable ret = new RenderTable();
        ret.setTitles(Arrays.asList(" m ", " n ", " PB "));

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();
        double pb = zif.getPBIndex();

        List<Object> v = new ArrayList<>();
        v.add(m);
        v.add(n);
        v.add(pb);

        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Topological Indices-Zagreb Indices";
    }
}
