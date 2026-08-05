// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.energy;

import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.reports.spectralreports.LaplacianEnergy;
import graphtea.extensions.reports.spectralreports.SignlessLaplacianEnergy;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ali Rostami
 */
public class NewLowerBounds implements GraphReportExtension {

    public String getName() {
        return "Lower Bounds";
    }

    public Object calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        List<String> titles = new ArrayList<>();
        titles.add("m");
        titles.add("n");
        titles.add("Energy");
        titles.add("Eigenvalues");
        ret.setTitles(titles);

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        List<Object> v = new ArrayList<>();
        v.add(m);
        v.add(n);
        v.add(Energy(g));
        v.add(AlgorithmUtils.getEigenValues(g));
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification- Energy";
    }

    public Object Energy(GraphModel g) {
        return new Energy().calculate(g);
    }

    public Object SignlessLaplacianEnergy(GraphModel g) {
        return new SignlessLaplacianEnergy().calculate(g);
    }

    public Object LaplacianEnergy(GraphModel g) {
        return new LaplacianEnergy().calculate(g);
    }
}
