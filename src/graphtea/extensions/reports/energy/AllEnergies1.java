// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.energy;

import graphtea.extensions.reports.spectralreports.LaplacianEnergy;
import graphtea.extensions.reports.spectralreports.SignlessLaplacianEnergy;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.library.algorithms.util.BipartiteChecker;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ali Rostami
 */
public class AllEnergies1 implements GraphReportExtension<RenderTable> {

    public String getName() {
        return "All Energies check";
    }

    public String getDescription() {
        return "All-Energies-check";
    }

    public RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        List<String> titles = new ArrayList<>();
        titles.add("m");
        titles.add("n");
        titles.add("E");
        titles.add("SLE-LE");
        titles.add("Bipartite");
        ret.setTitles(titles);

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();
        double e = Double.parseDouble(new Energy().calculate(g));
        double LE = Double.parseDouble(new LaplacianEnergy().calculate(g));
        double SLE = Double.parseDouble(new SignlessLaplacianEnergy().calculate(g));
        boolean bipartite = BipartiteChecker.isBipartite(g);

        List<Object> v = new ArrayList<>();
        v.add(m);
        v.add(n);
        v.add(e);
        v.add(Math.abs(SLE - LE));
        v.add(bipartite);
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification- Energy";
    }
}
