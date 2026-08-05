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
import java.util.function.DoubleUnaryOperator;

/**
 * @author Ali Rostami
 */
public class Linear implements GraphReportExtension<RenderTable> {

    public String getName() {
        return "Linear";
    }

    public RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        List<String> titles = new ArrayList<>();
        titles.add("m");
        titles.add("n");
        titles.add("Estrada");
        titles.add("DEnergy");
        titles.add("Check");
        titles.add("Check 2");
        titles.add("Check 3");
        ret.setTitles(titles);

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        double[] rv = g.getWeightedAdjacencyMatrix().eig().getRealEigenvalues();
        AlgorithmUtils.round(rv, 10);

        List<Object> v = new ArrayList<>();
        v.add(m);
        v.add(n);
        v.add(Estrada(g));
        v.add(DEnergy(g));
        // Polya inequality check
        v.add((2.0 / (Math.exp(rv[0]) + Math.exp(rv[rv.length - 1])))
                * Math.sqrt(n * (Math.exp(rv[0]) * Math.exp(rv[rv.length - 1]))));
        // Diaz-Metcalf check
        v.add((n * Math.exp(rv[0] + rv[rv.length - 1])) / (Math.exp(rv[0]) + Math.exp(rv[rv.length - 1])));
        v.add(1.0 / (Math.exp(rv[0]) + Math.exp(rv[rv.length - 1])));
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification- Energy";
    }

    /** Sums transform(λ) over all real adjacency eigenvalues; returns null on error. */
    private Object eigenSum(GraphModel g, DoubleUnaryOperator transform) {
        try {
            double[] rv = g.getWeightedAdjacencyMatrix().eig().getRealEigenvalues();
            double sum = 0;
            for (double ev : rv) {
                sum += transform.applyAsDouble(ev);
            }
            return AlgorithmUtils.round(sum, 12);
        } catch (Exception ignored) {
        }
        return null;
    }

    public Object Estrada(GraphModel g) {
        return eigenSum(g, Math::exp);
    }

    public Object DEnergy(GraphModel g) {
        return eigenSum(g, ev -> Math.exp(2 * ev));
    }

    public Object GaussEstrada(GraphModel g) {
        return eigenSum(g, ev -> Math.exp(-(ev * ev)));
    }

    /** Returns sum of exp(λ) (same as Estrada index). */
    public Object Energy(GraphModel g) {
        return eigenSum(g, Math::exp);
    }

    public Object LaplacianEnergy(GraphModel g) {
        return new LaplacianEnergy().calculate(g);
    }

    public Object SignlessLaplacianEnergy(GraphModel g) {
        return new SignlessLaplacianEnergy().calculate(g);
    }

    public Object ResolventEnergy(GraphModel g) {
        return new ResolventEnergy().calculate(g);
    }
}
