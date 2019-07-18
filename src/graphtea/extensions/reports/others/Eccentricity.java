package graphtea.extensions.reports.others;

import graphtea.extensions.reports.Utils;
import graphtea.extensions.reports.basicreports.NumOfIndSets;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.graph.graph.Vertex;
import graphtea.library.algorithms.shortestpath.Dijkstra;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.Vector;

@CommandAttitude(name = "Eccentricity", abbreviation = "_eccentricity")
public class Eccentricity implements GraphReportExtension {
    public String getName() {
        return "Eccentricity";
    }


    public String getDescription() {
        return "Eccentricity";
    }

    @Override
    public Object calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();
        titles.add("Eccentricity");
        ret.setTitles(titles);

        new Dijkstra<Vertex, Edge>()
        Vector<Object> v = new Vector<>();
        v.add((Integer)new NumOfIndSets().calculate(Utils.createLineGraph(g)));
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "OurWorks-Conjectures";
    }
}
