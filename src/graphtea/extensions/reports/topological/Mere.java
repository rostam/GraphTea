package graphtea.extensions.reports.topological;

import graphtea.extensions.reports.basicreports.NumOfIndSets;
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

@CommandAttitude(name = "Mere", abbreviation = "_Mere")
public class Mere implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Mere";
    }

    public String getDescription() {
        return " Mere ";
    }

    public RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        ret.setTitles(Arrays.asList(" m ", " n ", " i(G) ", " PW(G) ", " i(G) - PW(G) ", " Wie-Comp "));

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        PeripheralWienerIndex Pw = new PeripheralWienerIndex();
        WienerComplexIndex WCI = new WienerComplexIndex();

        int Wcomp = (int) WCI.calculate(g);

        List<Object> v = new ArrayList<>();
        v.add(m);
        v.add(n);
        v.add(new NumOfIndSets().calculate(g));
        v.add(Pw.calculate(g));

        double i = new NumOfIndSets().calculate(g);
        double PW = Pw.calculate(g);
        v.add(i - PW);

        v.add(Wcomp);

        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Checking";
    }
}
