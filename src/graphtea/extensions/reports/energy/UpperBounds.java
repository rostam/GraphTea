// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.energy;

import graphtea.extensions.reports.spectralreports.LaplacianEnergy;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ali Rostami
 */
public class UpperBounds implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Upper Bounds";
    }

    public RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        List<String> titles = new ArrayList<>();
        titles.add(" Laplacian Energy ");
        titles.add("m ");
        titles.add("n ");
        ret.setTitles(titles);

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        List<Object> v = new ArrayList<>();
        v.add(Double.parseDouble(new LaplacianEnergy().calculate(g)));
        v.add(m);
        v.add(n);
        ret.add(v);

        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification- Energy";
    }
}
