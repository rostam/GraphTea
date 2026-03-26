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

@CommandAttitude(name = "ZagrebEccentricity", abbreviation = "_ComparingE1E2")
public class ComparingE1E2 implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Comparing E1 E2";
    }

    public String getDescription() {
        return " Comparing E1 E2 ";
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        RenderTable ret = new RenderTable();
        ret.setTitles(Arrays.asList(" m ", " n ", " EDS-E1", " EDS-E2"));

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        double E1 = zif.getFirstZagrebEccentricity(g);
        double E2 = zif.getSecondZagrebEccentricity(g);

        EccentricDistanceSum EDS = new EccentricDistanceSum();
        double EDSS = EDS.calculate(g);

        List<Object> v = new ArrayList<>();
        v.add(m);
        v.add(n);
        v.add(EDSS - E1);
        v.add(EDSS - E2);

        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Checking";
    }
}
