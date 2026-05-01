import graphtea.extensions.generators.CircleGenerator;
import graphtea.extensions.generators.CompleteGraphGenerator;
import graphtea.extensions.generators.GeneralizedPetersonGenerator;
import graphtea.extensions.generators.PathGenerator;
import graphtea.extensions.reports.basicreports.*;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BasicReportsTest {
    GraphModel peterson = GeneralizedPetersonGenerator.generateGeneralizedPeterson(5,2);
    GraphModel circle4 = CircleGenerator.generateCircle(4);
    GraphModel circle5 = CircleGenerator.generateCircle(5);
    GraphModel complete4 = CompleteGraphGenerator.generateCompleteGraph(4);
    GraphModel complete5 = CompleteGraphGenerator.generateCompleteGraph(5);
    GraphModel path3 = PathGenerator.generatePath(3);
    GraphModel path4 = PathGenerator.generatePath(4);

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

    @Test
    public void testNumOfTriangles() {
        NumOfTriangles t = new NumOfTriangles();
        assertEquals(4, (int) t.calculate(complete4));   // K4 has C(4,3)=4 triangles
        assertEquals(0, (int) t.calculate(circle4));     // C4 is triangle-free
        assertEquals(0, (int) t.calculate(peterson));    // Petersen graph is triangle-free
    }

    @Test
    public void testNumOfQuadrangle() {
        NumOfQuadrangle q = new NumOfQuadrangle();
        assertEquals(1, (int) q.calculate(circle4));     // C4 itself is one quadrangle
        assertEquals(3, (int) q.calculate(complete4));   // K4 has 3 distinct C4 subgraphs
    }

    @Test
    public void testVerticesDegreesList() {
        VerticesDegreesList vdl = new VerticesDegreesList();
        List<Integer> degs = vdl.calculate(complete4);
        assertEquals(4, degs.size());
        assertEquals(3, (int) degs.get(0));    // sorted: all degrees = 3
        assertEquals(3, (int) degs.get(3));

        List<Integer> pathDegs = vdl.calculate(path3);
        assertEquals(3, pathDegs.size());
        assertEquals(1, (int) pathDegs.get(0));   // sorted: [1, 1, 2]
        assertEquals(2, (int) pathDegs.get(2));
    }

    @Test
    public void testPathsOfLengthTwo() {
        PathsofLengthTwo p = new PathsofLengthTwo();
        // L(P3) has 2 vertices (one per edge) sharing vertex 1 → 1 edge
        assertEquals(1, (int) p.calculate(path3));
        // L(K4) has 6 vertices each of degree 4 → 12 edges
        assertEquals(12, (int) p.calculate(complete4));
    }

    @Test
    public void testAllPairShortestPathsWithoutWeight() {
        AllPairShortestPathsWithoutWeight apsp = new AllPairShortestPathsWithoutWeight();
        int[][] dist = apsp.getAllPairsShortestPathWithoutWeight(path4);
        assertEquals(0, dist[0][0]);
        assertEquals(1, dist[0][1]);
        assertEquals(2, dist[0][2]);
        assertEquals(3, dist[0][3]);  // diameter of P4 is 3
    }

    @Test
    public void testNumOfStars() {
        NumOfStars s = new NumOfStars();
        s.k = 2;
        // In K4 each vertex has degree 3; C(3,2)=3 stars K1,2 per vertex → 4*3=12
        assertEquals(12, (int) s.calculate(complete4));

        s.k = 1;
        // In C4 each vertex degree=2; C(2,1)=2 per vertex → 4*2=8 (each edge counted twice)
        assertEquals(8, (int) s.calculate(circle4));
    }

    @Test
    public void testTotalNumOfStars() {
        TotalNumOfStars t = new TotalNumOfStars();
        List<String> result = t.calculate(path3);
        // P3: 2 edges (K1,1) and 1 path-of-length-2 (K1,2)
        assertEquals("NumOf(K1,1) = 2", result.get(0));
        assertEquals("NumOf(K1,2) = 1", result.get(1));
    }

    @Test
    public void testMaxOfIndSets() {
        MaxOfIndSets m = new MaxOfIndSets();
        assertEquals(1, (int) m.calculate(complete4));   // K4: only singletons are independent
        assertEquals(2, (int) m.calculate(circle4));     // C4: any antipodal pair
    }

    @Test
    public void testNumOfIndSets() {
        NumOfIndSets n = new NumOfIndSets();
        int k4Result = n.calculate(complete4);
        int c4Result = n.calculate(circle4);
        assertTrue(k4Result > 0);
        // C4 has more independent sets than K4 (K4 only allows singletons)
        assertTrue(c4Result > k4Result);
    }

    @Test
    public void testSubTreeCounting() {
        SubTreeCounting s = new SubTreeCounting();
        RenderTable result = s.calculate(complete4);
        assertNotNull(result);
        assertTrue(result.size() > 0);
    }

    @Test
    public void testPruferSequence() {
        PruferSequence ps = new PruferSequence();
        // P4 (0-1-2-3): removes leaf 0 (neighbor 1), then leaf 1 (neighbor 2)
        assertEquals("1, 2", ps.calculate(path4));
        // P3 (0-1-2): removes leaf 0 (neighbor 1) → single-element sequence
        assertEquals("1", ps.calculate(path3));
    }

//    @Test public void testDominationNumber() {
//        DominationNumber dn = new DominationNumber();
//        Assertions.assertEquals(
//                dn.calculate(GeneralizedPetersonGenerator.generateGeneralizedPeterson(5,2)), 3);
//    }
}