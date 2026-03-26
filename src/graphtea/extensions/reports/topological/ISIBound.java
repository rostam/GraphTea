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

@CommandAttitude(name = "ISIBound", abbreviation = "_ISIBound")
public class ISIBound implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "ISIBound";
    }

    public String getDescription() {
        return " ISIBound ";
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        RenderTable ret = new RenderTable();
        ret.setTitles(Arrays.asList(" m ", " n ", " SDD-co ", " LHS1 ", " alb "));

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        double M12 = zif.getSecondZagreb(1);
        double M21 = zif.getFirstZagreb(1);
        double SDDcoindex = zif.getSDDCoindex();
        double Albcoindex = zif.getAlbCoindex();

        List<Object> v = new ArrayList<>();
        v.add(m);
        v.add(n);
        v.add(SDDcoindex);
        v.add(((4 * m * m) - M21 - (2 * M12)) * (SDDcoindex - (n * (n - 1)) + (2 * m)));
        v.add(2 * Albcoindex * Albcoindex);

        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Degree";
    }
}
