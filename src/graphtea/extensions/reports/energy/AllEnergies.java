// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.energy;

import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.reports.spectralreports.DistanceEnergy;
import graphtea.extensions.reports.spectralreports.DistanceLaplacianEnergy;
import graphtea.extensions.reports.spectralreports.DistanceSignlessLaplacianEnergy;
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
public class AllEnergies implements GraphReportExtension<RenderTable> {

    public String getName() {
        return "AllEnergies";
    }

    public String getDescription() {
        return "All-Energies";
    }

    public RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        List<String> titles = new ArrayList<>();
        titles.add("m");
        titles.add("n");
        titles.add("E");
        titles.add("LE");
        titles.add("SLE");
        titles.add("LE-Bar");
        titles.add("SLE-Bar");
        titles.add("DE");
        titles.add("DLE");
        titles.add("DLSE");
        titles.add("DLE-Bar");
        titles.add("DLSE-Bar");
        titles.add("Bipartite");
        ret.setTitles(titles);

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        LaplacianEnergy le = new LaplacianEnergy();
        SignlessLaplacianEnergy sle = new SignlessLaplacianEnergy();
        GraphModel complement = AlgorithmUtils.createComplementGraph(g);

        double e = Double.parseDouble(new Energy().calculate(g));
        double LE = Double.parseDouble(le.calculate(g));
        double SLE = Double.parseDouble(sle.calculate(g));
        double LEC = Double.parseDouble(le.calculate(complement));
        double SLEC = Double.parseDouble(sle.calculate(complement));
        double DE = new DistanceEnergy().calculate(g);
        double DLE = new DistanceLaplacianEnergy().calculate(g);
        double DSLE = new DistanceSignlessLaplacianEnergy().calculate(g);
        double DLEC = new DistanceLaplacianEnergy().calculate(complement);
        double DSLEC = new DistanceSignlessLaplacianEnergy().calculate(complement);
        boolean bipartite = BipartiteChecker.isBipartite(g);

        List<Object> v = new ArrayList<>();
        v.add(m);
        v.add(n);
        v.add(e);
        v.add(LE);
        v.add(SLE);
        v.add(LE + LEC);
        v.add(SLE + SLEC);
        v.add(DE);
        v.add(DLE);
        v.add(DSLE);
        v.add(DLE + DLEC);
        v.add(DSLE + DSLEC);
        v.add(bipartite);
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification- Energy";
    }
}
