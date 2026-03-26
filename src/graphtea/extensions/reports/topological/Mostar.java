package graphtea.extensions.reports.topological;

import graphtea.extensions.reports.others.MostarIndex;
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

@CommandAttitude(name = "Mostar", abbreviation = "_Mostar")
public class Mostar implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Mostar";
    }

    public String getDescription() {
        return " Mostar ";
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        RenderTable ret = new RenderTable();
        ret.setTitles(Arrays.asList(" m ", " n ", "Mo-irregular", "Mo-Albertson"));

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        double Albertson = zif.getAlbertson();
        double irregular = zif.getirregularity();

        MostarIndex mi = new MostarIndex();
        int mostar = (int) mi.calculate(g);

        List<Object> v = new ArrayList<>();
        v.add(m);
        v.add(n);
        v.add(mostar - irregular);
        v.add(mostar - Albertson);

        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Degree";
    }
}
