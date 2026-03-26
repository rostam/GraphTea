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

@CommandAttitude(name = "InverseSum", abbreviation = "_InverseSum")
public class InverseSum implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "InverseSum";
    }

    public String getDescription() {
        return " InverseSum ";
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        RenderTable ret = new RenderTable();
        ret.setTitles(Arrays.asList(" m ", " n ", " In Sum indeg ", " In Deg ", " IED "));

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        double IED = zif.getEdgeDegree(-1);
        double ID = zif.getFirstZagreb(-2);
        double ISI = zif.getInverseSumIndegIndex();

        List<Object> v = new ArrayList<>();
        v.add(m);
        v.add(n);
        v.add(ISI);
        v.add(ID);
        v.add(IED);

        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Degree";
    }
}
