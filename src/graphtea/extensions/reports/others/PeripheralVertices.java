package graphtea.extensions.reports.others;

import graphtea.extensions.algorithms.shortestpath.algs.FloydWarshall;
import graphtea.extensions.reports.basicreports.Diameter;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;

@CommandAttitude(name = "PeripheralVertices", abbreviation = "_peripheral_veritces")
public class PeripheralVertices implements GraphReportExtension<ArrayList<Vertex>> {
    public String getName() {
        return "Peripheral vertices";
    }

    public String getDescription() {
        return "Peripheral vertices";
    }

    /**
     * A vertex with maximum
     * eccentricity is called a peripheral vertex.
     *
     * @param g the given graph
     * @return all peripheral vertices
     */
    @Override
    public ArrayList<Vertex> calculate(GraphModel g) {
        FloydWarshall fw = new FloydWarshall();
        int[][] dist = fw.getAllPairsShortestPathWithoutWeight(g);
        ArrayList<Vertex> perpheralVertices = new ArrayList<>();
        int diameter = new Diameter().calculate(g);
        for(Vertex v : g) {
            if(Eccentricity.eccentricity(g, v.getId(), dist) == diameter)
                perpheralVertices.add(v);
        }
        return perpheralVertices;
    }

    @Override
    public String getCategory() {
        return "OurWorks-Conjectures";
    }

}