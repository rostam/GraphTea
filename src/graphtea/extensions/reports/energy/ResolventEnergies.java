// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.energy;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ali Rostami
 */
public class ResolventEnergies implements GraphReportExtension<RenderTable> {

    public String getName() {
        return "ResolventEnergies";
    }

    public String getDescription() {
        return "Resolvent-Energies";
    }

    public RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        List<String> titles = new ArrayList<>();
        titles.add("m");
        titles.add("n");
        titles.add("RE");
        titles.add("RLE");
        titles.add("RSLE");
        titles.add("NLRE");
        ret.setTitles(titles);

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();
        double RE = Double.parseDouble(new ResolventEnergy().calculate(g));
        double RLE = Double.parseDouble(new ResolventLaplacianEnergy().calculate(g));
        double RSLE = Double.parseDouble(new ResolventSignlessLaplacianEnergy().calculate(g));
        double NLRE = Double.parseDouble(new NormalizedLaplacianResolventEnergy().calculate(g));

        List<Object> v = new ArrayList<>();
        v.add(m);
        v.add(n);
        v.add(RE);
        v.add(RLE);
        v.add(RSLE);
        v.add(NLRE);
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification- Energy";
    }
}
