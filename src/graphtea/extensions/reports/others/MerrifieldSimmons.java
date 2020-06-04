package graphtea.extensions.reports.others;

import graphtea.extensions.reports.basicreports.NumOfIndSets;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.Vector;

@CommandAttitude(name = "Merrifield-Simmons", abbreviation = "_merrifield")
public class MerrifieldSimmons implements GraphReportExtension {
    public String getName() {
        return "Merrifield-Simmons";
    }


    public String getDescription() {
        return " Lanzhou";
    }

    @Override
    public Object calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();
        titles.add("Merrifield");
        ret.setTitles(titles);
        Vector<Object> v = new Vector<>();
        v.add((Integer)new NumOfIndSets().calculate(g));
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "OurWorks-Conjectures";
    }
}
