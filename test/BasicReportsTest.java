import graphtea.extensions.generators.CircleGenerator;
import graphtea.extensions.generators.CompleteGraphGenerator;
import graphtea.extensions.generators.GeneralizedPetersonGenerator;
import graphtea.extensions.reports.basicreports.*;
import graphtea.graph.graph.GraphModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class BasicReportsTest {
    GraphModel peterson = GeneralizedPetersonGenerator.generateGeneralizedPeterson(5,2);
    GraphModel circle4 = CircleGenerator.generateCircle(4);
    GraphModel circle5 = CircleGenerator.generateCircle(5);
    GraphModel complete4 = CompleteGraphGenerator.generateCompleteGraph(4);
    GraphModel complete5 = CompleteGraphGenerator.generateCompleteGraph(5);

    @Test
    public void testGirthSize() {
        GirthSize girthSize = new GirthSize();
        Assertions.assertEquals(girthSize.calculate(peterson),5);
    }

    @Test
    public void testIsBipartite() {
        IsBipartite isBipartite = new IsBipartite();
        Assertions.assertTrue(isBipartite.calculate(circle4));
    }

    @Test
    public void testIsEulerian() {
        IsEulerian isEulerian = new IsEulerian();
        Assertions.assertTrue(isEulerian.calculate(circle5));
        Assertions.assertTrue(isEulerian.calculate(complete5));
        Assertions.assertFalse(isEulerian.calculate(complete4));
    }

    @Test
    public void testMaxAndMinDegree() {
        MaxAndMinDegree maxAndMinDegree = new MaxAndMinDegree();
        ArrayList<Integer> al = maxAndMinDegree.calculate(peterson);
        Assertions.assertEquals(al.get(0),3);
        Assertions.assertEquals(al.get(1),3);
    }

    @Test
    public void testNumOfConnectedComponents() {
        NumOfConnectedComponents numOfConnectedComponents = new NumOfConnectedComponents();
        Assertions.assertEquals(numOfConnectedComponents.calculate(peterson),1);
    }

    @Test
    public void testNumOfEdges() {
        NumOfEdges numOfEdges = new NumOfEdges();
        Assertions.assertEquals(numOfEdges.calculate(peterson),15);
    }

    @Test
    public void testNumOfVertices() {
        NumOfVertices numOfVertices = new NumOfVertices();
        Assertions.assertEquals(numOfVertices.calculate(peterson),10);
    }

    @Test
    public void testNumOfVerticesWithDegK() {
        NumOfVerticesWithDegK numOfVerticesWithDegK = new NumOfVerticesWithDegK();
        NumOfVerticesWithDegK.k = 3;
        Assertions.assertEquals(numOfVerticesWithDegK.calculate(peterson),10);
    }



//    @Test public void testDominationNumber() {
//        DominationNumber dn = new DominationNumber();
//        Assertions.assertEquals(
//                dn.calculate(GeneralizedPetersonGenerator.generateGeneralizedPeterson(5,2)), 3);
//    }
}