package graphtea.extensions.reports.topological.Irr;

import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.reports.basicreports.NumOfTriangles;
import graphtea.extensions.reports.topological.ZagrebIndexFunctions;
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

@CommandAttitude(name = "VeIndex", abbreviation = "_VeIndex")
public class AlbertsonIdnexReport implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "VeIndex";
    }

    public String getDescription() {
        return "VeIndex";
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        RenderTable ret = new RenderTable();
        ret.setTitles(Arrays.asList(" m ", " n ", " Zagreb ", " Ve ", " t ", " VE ", " V. Degrees "));

        ArrayList<Integer> al = AlgorithmUtils.getDegreesList(g);
        int t = NumOfTriangles.getNumOfTriangles(g);
        int Albertson = AlbertsonIndex.computeAlbertsonIndex(g);

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();
        double M21 = zif.getFirstZagreb(1);

        List<Object> v = new ArrayList<>();
        v.add(m);
        v.add(n);
        v.add(M21);
        v.add(M21 - (3 * t));
        v.add(t);
        v.add(Albertson);
        v.add(al.toString());

        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Degree";
    }
}
