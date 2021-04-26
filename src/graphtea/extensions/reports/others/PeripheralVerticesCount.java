package graphtea.extensions.reports.others;

import graphtea.graph.graph.GraphModel;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

@CommandAttitude(name = "PeripheralVerticesCount", abbreviation = "_peripheral_vertices_count")
public class PeripheralVerticesCount implements GraphReportExtension<Integer> {
    public String getName() {
        return "Peripheral vertices count";
    }

    public String getDescription() {
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
