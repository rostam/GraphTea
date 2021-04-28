package graphtea.extensions.reports.topological;


import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.reports.ChromaticNumber;
import graphtea.extensions.reports.RandomMatching;
import graphtea.extensions.reports.basicreports.Diameter;
import graphtea.extensions.reports.basicreports.GirthSize;
import graphtea.extensions.reports.basicreports.NumOfVerticesWithDegK;
import graphtea.extensions.reports.clique.MaxCliqueSize;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

/**
 * @author Ali Rostami
 */

@CommandAttitude(name = "Max", abbreviation = "_Max")
public class Max implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Max";
    }

    public String getDescription() {
        return " Max ";
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();
        titles.add(" m ");
        titles.add(" n ");
        titles.add(" Max ");

        ret.setTitles(titles);

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        double M12 = zif.getSecondZagreb(1);
        double M21 = zif.getFirstZagreb(1);

        Vector<Object> v = new Vector<>();
        v.add(m);
        v.add(n);

        v.add(M12);

        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Checking";
    }
}



