package graphtea.extensions.reports;

import graphtea.extensions.reports.basicreports.Diameter;
import graphtea.extensions.reports.topological.WienerIndex;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.Vector;

/**
 * Created by rostami on 26.04.17.
 *
 */
public class WienerDiameterReport implements GraphReportExtension<RenderTable> {
    @Override
    public RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();
        titles.add(" Wiener ");
        titles.add(" Diagonal ");

        WienerIndex wi = new WienerIndex();
        Diameter d = new Diameter();
        Vector<Object> v = new Vector<>();
        v.add(wi.calculate(g));
        v.add(d.calculate(g));
        ret.setTitles(titles);
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Checking";
    }

    @Override
    public String getName() {
        return "Wiener  Diagonal";
    }

    @Override
    public String getDescription() {
        return "Wiener  Diagonal";
    }
}
