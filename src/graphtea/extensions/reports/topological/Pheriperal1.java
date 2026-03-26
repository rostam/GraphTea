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
@CommandAttitude(name = "Pheriperal1", abbreviation = "_Pheriperal1")
public class Pheriperal1 implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Pheriperal1";
    }

    public String getDescription() {
        return " Pheriperal1 ";
    }

    public RenderTable calculate(GraphModel g) {
        PeripheralWienerIndex pw = new PeripheralWienerIndex();
        GraphModel l1 = AlgorithmUtils.createLineGraph(g);
        GraphModel l2 = AlgorithmUtils.createLineGraph(l1);
        RenderTable ret = new RenderTable();
        ret.setTitles(Arrays.asList(" m ", " n ", " PW(G) ", " PW(L1) ", " PW(L2) "));
        List<Object> v = new ArrayList<>();
        v.add(g.getEdgesCount());
        v.add(g.getVerticesCount());
        v.add(pw.calculate(g));
        v.add(pw.calculate(l1));
        v.add(pw.calculate(l2));
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Checking";
    }
}
