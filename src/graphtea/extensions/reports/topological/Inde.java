package graphtea.extensions.reports.topological;

import graphtea.extensions.reports.basicreports.NumOfIndSets;
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

@CommandAttitude(name = "iG", abbreviation = "_iG")
public class Inde implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Inde-Wiener";
    }

    public String getDescription() {
        return " Inde-Wiener ";
    }

    public RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        ret.setTitles(Arrays.asList("i(G)", " W(G) "));

        WienerIndex wi = new WienerIndex();

        List<Object> v = new ArrayList<>();
        v.add(new NumOfIndSets().calculate(g));
        v.add(wi.calculate(g));

        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Checking";
    }
}
