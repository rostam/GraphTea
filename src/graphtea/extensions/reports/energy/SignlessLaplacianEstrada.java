// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.energy;

import Jama.Matrix;
import graphtea.extensions.AlgorithmUtils;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ali Rostami
 */
@CommandAttitude(name = "newInvs", abbreviation = "_newInv")
public class SignlessLaplacianEstrada implements GraphReportExtension<RenderTable> {

    public String getName() {
        return "Signless Laplacian Estrada";
    }

    public String getDescription() {
        return "Signless Laplacian Estrada";
    }

    public RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        List<String> titles = new ArrayList<>();
        titles.add(" m ");
        titles.add(" n ");
        titles.add(" Signless Laplacian Estarda ");
        ret.setTitles(titles);

        Matrix a = AlgorithmUtils.getSignlessLaplacian(g.getWeightedAdjacencyMatrix());
        double[] rv = a.eig().getRealEigenvalues();
        double signLapEstrada = 0;
        for (double eigenvalue : rv) {
            signLapEstrada += Math.exp(eigenvalue);
        }

        List<Object> v = new ArrayList<>();
        v.add((double) g.getEdgesCount());
        v.add((double) g.getVerticesCount());
        v.add(signLapEstrada);
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification- Energy";
    }
}
