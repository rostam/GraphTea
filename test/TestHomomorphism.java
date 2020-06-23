import graphtea.extensions.AllSetPartitions;
import graphtea.extensions.algorithms.homomorphism.Homomorphism;
import graphtea.extensions.algorithms.shortestpath.algs.FloydWarshall;
import graphtea.extensions.generators.CircleGenerator;
import graphtea.extensions.generators.CompleteGraphGenerator;
import graphtea.extensions.generators.GeneralizedPetersonGenerator;
import graphtea.extensions.reports.HeuristicGreedyColoring;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

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
        HashMap<Vertex, Vertex> hmFunc = hm.getHomomorphism();
        int[][] distPeterson = fw.getAllPairsShortestPathWithoutWeight(peterson);
        int[][] distRange = fw.getAllPairsShortestPathWithoutWeight(hm.getRange());
        Assertions.assertTrue(hm.isValid());

        boolean isCorrect = false;
        // Collary 1.2 from Pavol Hell Book
        for(Vertex v : peterson) {
            for(Vertex u : peterson) {
                int d_peterson = distPeterson[v.getId()][u.getId()];
                int d_range = distRange[hmFunc.get(v).getId()][hmFunc.get(u).getId()];
                isCorrect = (d_range <= d_peterson);
                if(!isCorrect)
                    break;
            }
        }
        Assertions.assertTrue(isCorrect);
    }

    @Test
    public void testPartitioner() {
        GraphModel g = CircleGenerator.generateCircle(4);
        List<List<List<Vertex>>> allpartitions = AllSetPartitions.partitions(Arrays.asList(g.getVertexArray()));
        for (List<List<Vertex>> partitions : allpartitions) {
            for(List<Vertex> l : partitions) {
                for(Vertex v : l) {
                    System.out.print(v.getId() + ",");
                }
                System.out.print("-");
            }
            System.out.println();
        }
        System.out.println(allpartitions.size());
    }
}
//
//class MySubSetListener implements SubSetListener {
//
//    @Override
//    public boolean subsetFound(int t, ArrayDeque<Vertex> complement, ArrayDeque<Vertex> set) {
//        System.out.println(set.size());
//        return false;
//    }
//}
