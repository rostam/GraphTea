// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.energy;

import Jama.Matrix;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.List;

abstract class AbstractEstradaReport implements GraphReportExtension<RenderTable> {

    protected abstract Matrix getMatrix(GraphModel g);

    protected abstract String getLabel();

    @Override
    public final RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        List<String> titles = new ArrayList<>();
        titles.add(" m ");
        titles.add(" n ");
        titles.add(getLabel());
        ret.setTitles(titles);

        double[] rv = getMatrix(g).eig().getRealEigenvalues();
        double estrada = 0;
        for (double eigenvalue : rv) {
            estrada += Math.exp(eigenvalue);
        }

        List<Object> v = new ArrayList<>();
        v.add((double) g.getEdgesCount());
        v.add((double) g.getVerticesCount());
        v.add(estrada);
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification- Energy";
    }
}
