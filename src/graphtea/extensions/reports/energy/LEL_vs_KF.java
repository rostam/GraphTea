// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.energy;

import graphtea.extensions.reports.spectralreports.DegreeKirchhoffIndex;
import graphtea.extensions.reports.spectralreports.KirchhoffIndex;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ali Rostami
 */
public class LEL_vs_KF implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "LEL_vs_KF";
    }

    public RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        ret.setTitles(Arrays.asList("m ", "n ", "KF", "DKF"));

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        List<Object> v = new ArrayList<>();
        v.add(m);
        v.add(n);
        v.add(Double.parseDouble(new KirchhoffIndex().calculate(g)));
        v.add(Double.parseDouble(new DegreeKirchhoffIndex().calculate(g)));
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification- Energy";
    }
}
