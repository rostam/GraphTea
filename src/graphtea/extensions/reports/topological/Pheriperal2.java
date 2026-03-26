package graphtea.extensions.reports.topological;

import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.reports.others.PeripheralWienerIndex;
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
@CommandAttitude(name = "Pheriperal2", abbreviation = "_Pheriperal2")
public class Pheriperal2 implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Pheriperal2";
    }

    public String getDescription() {
        return " Pheriperal2 ";
    }

    public RenderTable calculate(GraphModel g) {
        PeripheralWienerIndex pw = new PeripheralWienerIndex();
        GraphModel l1 = AlgorithmUtils.createLineGraph(g);
        GraphModel l2 = AlgorithmUtils.createLineGraph(l1);
        GraphModel l3 = AlgorithmUtils.createLineGraph(l2);
        RenderTable ret = new RenderTable();
        ret.setTitles(Arrays.asList(" m ", " n ", " PW(G) ", " PW(L1) ", " PW(L2) ", " PW(L3) "));
        List<Object> v = new ArrayList<>();
        v.add(g.getEdgesCount());
        v.add(g.getVerticesCount());
        v.add(pw.calculate(g));
        v.add(pw.calculate(l1));
        v.add(pw.calculate(l2));
        v.add(pw.calculate(l3));
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Checking";
    }
}
