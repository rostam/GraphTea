import graphtea.extensions.algorithms.homomorphism.Homomorphism;
import graphtea.extensions.algorithms.shortestpath.algs.FloydWarshall;
import graphtea.extensions.generators.CircleGenerator;
import graphtea.extensions.generators.CompleteGraphGenerator;
import graphtea.extensions.generators.GeneralizedPetersonGenerator;
import graphtea.extensions.reports.HeuristicGreedyColoring;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Vector;

public class TestHomomorphism {
    GraphModel peterson = GeneralizedPetersonGenerator.generateGeneralizedPeterson(5,2);
    GraphModel circle4 = CircleGenerator.generateCircle(4);
    GraphModel circle5 = CircleGenerator.generateCircle(5);
    GraphModel complete4 = CompleteGraphGenerator.generateCompleteGraph(4);
    GraphModel complete5 = CompleteGraphGenerator.generateCompleteGraph(5);

    @Test
    public void testDegreeHomomorphism() {
        FloydWarshall fw = new FloydWarshall();
        int[][] dist = fw.getAllPairsShortestPathWithoutWeight(peterson);
        Vector<Integer> coloring = new HeuristicGreedyColoring().calculate(peterson);
        Homomorphism hm = new Homomorphism(peterson, coloring, Collections.max(coloring));
//        for(Vertex v : )
    }
}
