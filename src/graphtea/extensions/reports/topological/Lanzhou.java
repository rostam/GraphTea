package graphtea.extensions.reports.topological;

import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.reports.basicreports.Diameter;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Ali Rostami
 */

@CommandAttitude(name = "Lanzhou", abbreviation = "_Lanzhou")
public class Lanzhou implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Lanzhou";
    }

    public String getDescription() {
        return " Lanzhou";
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        RenderTable ret = new RenderTable();
        ret.setTitles(Arrays.asList(" m ", " Max Planar ", " n ", " Min ", " M2-M1 ", "Diameter", " V. Degrees "));

        double minDeg = Integer.MAX_VALUE;

        ArrayList<Integer> al = AlgorithmUtils.getDegreesList(g);
        Collections.sort(al);
        minDeg = al.get(0);

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        double M12 = zif.getSecondZagreb(1);
        double M21 = zif.getFirstZagreb(1);

        int diameter = (int) new Diameter().calculate(g);

        List<Object> v = new ArrayList<>();
        v.add(m);
        v.add((3 * n) - 6);
        v.add(n);
        v.add(minDeg);
        v.add(M12 - M21);
        v.add(diameter);
        v.add(al.toString());

        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Degree";
    }
}
