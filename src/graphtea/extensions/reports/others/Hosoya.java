package graphtea.extensions.reports.others;

import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.reports.basicreports.NumOfIndSets;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.Vector;

@CommandAttitude(name = "HosoyaIndex", abbreviation = "_hosoya")
public class Hosoya implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Hosaya Index";
    }


    public String getDescription() {
        return "Hosaya Index";
    }

    @Override
    public RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();
        titles.add("Hosoya");
        ret.setTitles(titles);
        Vector<Object> v = new Vector<>();
        v.add(new NumOfIndSets().calculate(AlgorithmUtils.createLineGraph(g)));
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
		return "Topological Indices-Independent Set";
    }
}
