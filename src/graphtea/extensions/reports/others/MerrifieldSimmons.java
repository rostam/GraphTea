package graphtea.extensions.reports.others;

import graphtea.extensions.reports.basicreports.NumOfIndSets;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.List;

@CommandAttitude(name = "Merrifield_Simmons", abbreviation = "_merrifield")
public class MerrifieldSimmons implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Merrifield-Simmons";
    }


    public String getDescription() {
        return " Lanzhou";
    }

    @Override
    public RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        List<String> titles = new ArrayList<>();
        titles.add("Merrifield");
        ret.setTitles(titles);
        List<Object> v = new ArrayList<>();
        v.add(new NumOfIndSets().calculate(g));
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
		return "Topological Indices-Independent Set";
    }
}
