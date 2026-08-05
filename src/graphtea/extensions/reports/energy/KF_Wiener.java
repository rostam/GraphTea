// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.energy;

import graphtea.extensions.reports.RandomMatching;
import graphtea.extensions.reports.basicreports.Diameter;
import graphtea.extensions.reports.basicreports.GirthSize;
import graphtea.extensions.reports.spectralreports.KirchhoffIndex;
import graphtea.extensions.reports.topological.WienerIndex;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ali Rostami
 */
public class KF_Wiener implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "KF_Wiener";
    }

    public RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        ret.setTitles(Arrays.asList("m ", "n ", "KF", "Wiener", "Diameter", " girth ", " matching "));

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();
        double maxMatching = new RandomMatching().calculateMaxMatching(g);

        List<Object> v = new ArrayList<>();
        v.add(m);
        v.add(n);
        v.add(Double.parseDouble(new KirchhoffIndex().calculate(g)));
        v.add(new WienerIndex().calculate(g));
        v.add((int) new Diameter().calculate(g));
        v.add((int) new GirthSize().calculate(g));
        v.add(maxMatching);
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification- Energy";
    }
}
