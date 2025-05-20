package test;

import graphtea.extensions.generators.CircleGenerator;
import graphtea.extensions.generators.CompleteGraphGenerator;
import graphtea.extensions.generators.GeneralizedPetersonGenerator;
import graphtea.extensions.reports.basicreports.*;
import graphtea.graph.graph.GraphModel;
import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;

public class BasicReportsTest {
    GraphModel peterson = GeneralizedPetersonGenerator.generateGeneralizedPeterson(5, 2);
    GraphModel circle4 = CircleGenerator.generateCircle(4);
    GraphModel circle5 = CircleGenerator.generateCircle(5);
    GraphModel complete4 = CompleteGraphGenerator.generateCompleteGraph(4);
    GraphModel complete5 = CompleteGraphGenerator.generateCompleteGraph(5);

    @Test
    public void testGirthSize() {
        GirthSize girthSize = new GirthSize();
        Assert.assertEquals(5, girthSize.calculate(peterson).intValue());
    }

    @Test
    public void testIsBipartite() {
        IsBipartite isBipartite = new IsBipartite();
        Assert.assertTrue(isBipartite.calculate(circle4));
    }

    @Test
    public void testIsEulerian() {
        IsEulerian isEulerian = new IsEulerian();
        Assert.assertTrue(isEulerian.calculate(circle5));
        Assert.assertTrue(isEulerian.calculate(complete5));
        Assert.assertFalse(isEulerian.calculate(complete4));
    }

    @Test
    public void testMaxAndMinDegree() {
        MaxAndMinDegree maxAndMinDegree = new MaxAndMinDegree();
        ArrayList<Integer> al = maxAndMinDegree.calculate(peterson);
        Assert.assertEquals(3, al.get(0).intValue());
        Assert.assertEquals(3, al.get(1).intValue());
    }

    @Test
    public void testNumOfConnectedComponents() {
        NumOfConnectedComponents numOfConnectedComponents = new NumOfConnectedComponents();
        Assert.assertEquals(1, numOfConnectedComponents.calculate(peterson).intValue());
    }

    @Test
    public void testNumOfEdges() {
        NumOfEdges numOfEdges = new NumOfEdges();
        Assert.assertEquals(15, numOfEdges.calculate(peterson).intValue());
    }

    @Test
    public void testNumOfVertices() {
        NumOfVertices numOfVertices = new NumOfVertices();
        Assert.assertEquals(10, numOfVertices.calculate(peterson).intValue());
    }

    @Test
    public void testNumOfVerticesWithDegK() {
        NumOfVerticesWithDegK numOfVerticesWithDegK = new NumOfVerticesWithDegK();
        NumOfVerticesWithDegK.k = 3;
        Assert.assertEquals(10, numOfVerticesWithDegK.calculate(peterson).intValue());
    }
}