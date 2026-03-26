package graphtea.extensions.reports.topological;

import graphtea.extensions.reports.basicreports.Diameter;
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

@CommandAttitude(name = "Mere1", abbreviation = "_Mere")
public class Mere1 implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Mere1";
    }

    public String getDescription() {
        return " Mere1 ";
    }

    public RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        ret.setTitles(Arrays.asList(" m ", " n ", " EC(G) ", " EDS(G) ", " EDS-EC ", " ECI/EDS ", " EWI ", "Diameter"));

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        EccentricConnectiveIndex eci = new EccentricConnectiveIndex();
        EccentricDistanceSum EDS = new EccentricDistanceSum();
        EccentricWienerIndex EWI = new EccentricWienerIndex();

        double diameter = (int) new Diameter().calculate(g);

        eci.setA(1);
        double ECI = eci.calculate(g);

        double EDSS = EDS.calculate(g);

        List<Object> v = new ArrayList<>();
        v.add(m);
        v.add(n);
        v.add(ECI);
        v.add(EDSS);
        v.add(EDSS - ECI);
        v.add(ECI / EDSS);
        v.add(EWI.calculate(g));
        v.add(diameter);

        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Checking";
    }
}
