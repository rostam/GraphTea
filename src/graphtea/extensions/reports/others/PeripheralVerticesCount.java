package graphtea.extensions.reports.others;

import graphtea.graph.graph.GraphModel;
import graphtea.plugins.reports.extension.GraphReportExtension;

public class PeripheralVerticesCount implements GraphReportExtension<Integer> {
    public String getName() {
        return "Peripheral vertices count";
    }

    /**
     * A vertex with maximum
     * eccentricity is called a peripheral vertex.
     *
     * @param g the given graph
     * @return all peripheral vertices
     */
    @Override
    public Integer calculate(GraphModel g) {
        PeripheralVertices pv = new PeripheralVertices();
        return pv.calculate(g).size();
    }

    @Override
    public String getCategory() {
		return "Topological Indices-Wiener Types";
    }
}
