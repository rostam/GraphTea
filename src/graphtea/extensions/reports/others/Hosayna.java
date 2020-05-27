package graphtea.extensions.reports.others;

import graphtea.extensions.reports.Utils;
import graphtea.extensions.reports.basicreports.NumOfIndSets;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.Vector;

@CommandAttitude(name = "Hosayna Index", abbreviation = "_hosayna")
public class Hosayna implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Hosayna Index";
    }


    public String getDescription() {
        return "Hosayna Index";
    }

    @Override
    public RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();
        titles.add("Hosayna");
        ret.setTitles(titles);
        Vector<Object> v = new Vector<>();
        v.add(new NumOfIndSets().calculate(Utils.createLineGraph(g)));
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "OurWorks-Conjectures";
    }
}
