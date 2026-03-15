package graphtea.extensions.reports.others;

import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.reports.basicreports.NumOfIndSets;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.List;

@CommandAttitude(name = "HosoyaIndex", abbreviation = "_hosoya")
public class Hosoya implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Hosoya Index";
    }


    public String getDescription() {
        return "Hosoya Index";
    }

    @Override
    public RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        List<String> titles = new ArrayList<>();
        titles.add("Hosoya");
        ret.setTitles(titles);
        List<Object> v = new ArrayList<>();
        v.add(new NumOfIndSets().calculate(AlgorithmUtils.createLineGraph(g)));
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
		return "Topological Indices-Independent Set";
    }
}
