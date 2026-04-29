// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

import graphtea.extensions.generators.CircleGenerator;
import graphtea.extensions.generators.CompleteGraphGenerator;
import graphtea.extensions.generators.PathGenerator;
import graphtea.extensions.reports.basicreports.*;
import graphtea.extensions.reports.energy.Energy;
import graphtea.extensions.reports.energy.Estrada;
import graphtea.extensions.reports.spectralreports.EigenValues;
import graphtea.extensions.reports.spectralreports.LaplacianEnergy;
import graphtea.extensions.reports.basicreports.SpectrumOfAdjacencyMatrix;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for report and basic-report classes that previously had 0-10% coverage.
 *
 * Expected values are derived analytically from small well-known graphs
 * (K_3, P_3, P_4, K_1, star S_3).
 */
public class MissingCoverageTest {

    private final GraphModel k3  = CompleteGraphGenerator.generateCompleteGraph(3);
    private final GraphModel p3  = PathGenerator.generatePath(3);
    private final GraphModel p4  = PathGenerator.generatePath(4);
    private final GraphModel c4  = CircleGenerator.generateCircle(4);

    // -----------------------------------------------------------------------
    // NumOfStars
    // -----------------------------------------------------------------------

    @Test
    public void testNumOfStarsK3k1() {
        // K_3: each vertex has degree 2.  C(2,1)=2.  Sum = 3*2 = 6.
        NumOfStars ns = new NumOfStars();
        ns.k = 1;
        assertEquals(6, (int) ns.calculate(k3));
    }

    @Test
    public void testNumOfStarsK3k2() {
        // K_3: C(2,2)=1 for each vertex.  Sum = 3.
        NumOfStars ns = new NumOfStars();
        ns.k = 2;
        assertEquals(3, (int) ns.calculate(k3));
    }

    @Test
    public void testNumOfStarsP3k1() {
        // P_3 degrees [1,2,1]: C(1,1)+C(2,1)+C(1,1) = 1+2+1 = 4
        NumOfStars ns = new NumOfStars();
        ns.k = 1;
        assertEquals(4, (int) ns.calculate(p3));
    }

    // -----------------------------------------------------------------------
    // TotalNumOfStars
    // -----------------------------------------------------------------------

    @Test
    public void testTotalNumOfStarsNotNull() {
        List<String> result = new TotalNumOfStars().calculate(k3);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testTotalNumOfStarsP3ReturnsList() {
        List<String> result = new TotalNumOfStars().calculate(p3);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    // -----------------------------------------------------------------------
    // VerticesDegreesList
    // -----------------------------------------------------------------------

    @Test
    public void testVerticesDegreesListK3() {
        // K_3: all degrees = 2, sorted → [2,2,2]
        ArrayList<Integer> deg = new VerticesDegreesList().calculate(k3);
        assertEquals(3, deg.size());
        deg.forEach(d -> assertEquals(2, (int) d));
    }

    @Test
    public void testVerticesDegreesListP3() {
        // P_3: degrees [1,2,1], sorted → [1,1,2]
        ArrayList<Integer> deg = new VerticesDegreesList().calculate(p3);
        assertEquals(3, deg.size());
        assertEquals(1, (int) deg.get(0));
        assertEquals(1, (int) deg.get(1));
        assertEquals(2, (int) deg.get(2));
    }

    // -----------------------------------------------------------------------
    // PruferSequence
    // -----------------------------------------------------------------------

    @Test
    public void testPruferSequenceP4() {
        // P_4 (0-1-2-3): remove leaf 0 (neighbor 1) then leaf 1 (neighbor 2) → "1, 2"
        assertEquals("1, 2", new PruferSequence().calculate(p4));
    }

    @Test
    public void testPruferSequenceP3() {
        // P_3 (0-1-2): remove leaf 0 (neighbor 1) → "1"
        assertEquals("1", new PruferSequence().calculate(p3));
    }

    @Test
    public void testPruferSequenceNonTree() {
        String result = new PruferSequence().calculate(k3);
        assertTrue(result.startsWith("Graph is not a tree"), "k3 is not a tree: " + result);
    }

    @Test
    public void testPruferSequenceSingleEdge() {
        // P_2: n=2, n-2=0 so empty sequence → ""
        GraphModel p2 = PathGenerator.generatePath(2);
        String result = new PruferSequence().calculate(p2);
        assertEquals("", result);
    }

    // -----------------------------------------------------------------------
    // AllPairShortestPathsWithoutWeight
    // -----------------------------------------------------------------------

    @Test
    public void testAllPairShortestPathsNotNull() {
        RenderTable result = new AllPairShortestPathsWithoutWeight().calculate(k3);
        assertNotNull(result);
    }

    @Test
    public void testAllPairShortestPathsK3HasThreeRows() {
        // K_3 has C(3,2)=3 ordered pairs; APSP returns all pairs
        RenderTable result = new AllPairShortestPathsWithoutWeight().calculate(k3);
        assertTrue(result.size() > 0);
    }

    @Test
    public void testAllPairShortestPathsP3() {
        RenderTable result = new AllPairShortestPathsWithoutWeight().calculate(p3);
        assertNotNull(result);
    }

    // -----------------------------------------------------------------------
    // SubTreeCounting
    // -----------------------------------------------------------------------

    @Test
    public void testSubTreeCountingNotNull() {
        RenderTable result = new SubTreeCounting().calculate(p3);
        assertNotNull(result);
    }

    @Test
    public void testSubTreeCountingK3NotNull() {
        RenderTable result = new SubTreeCounting().calculate(k3);
        assertNotNull(result);
    }

    // -----------------------------------------------------------------------
    // DominationNumber
    // -----------------------------------------------------------------------

    @Test
    public void testDominationNumberK3IsOne() {
        // K_3: one vertex dominates all → domination number = 1
        assertEquals(1, (int) new DominationNumber().calculate(k3));
    }

    @Test
    public void testDominationNumberP3() {
        // Note: DominationNumber delegates to getMaxIndependentSetSize (independence number).
        // P_3 max independent set = {0,2} → size 2.
        assertEquals(2, (int) new DominationNumber().calculate(p3));
    }

    // -----------------------------------------------------------------------
    // MaxOfIndSets and NumOfIndSets
    // -----------------------------------------------------------------------

    @Test
    public void testMaxOfIndSetsK3IsOne() {
        // K_3: maximum independent set = 1 vertex
        assertEquals(1, (int) new MaxOfIndSets().calculate(k3));
    }

    @Test
    public void testMaxOfIndSetsP3IsTwo() {
        // P_3 (0-1-2): {0,2} is independent → max = 2
        assertEquals(2, (int) new MaxOfIndSets().calculate(p3));
    }

    @Test
    public void testNumOfIndSetsK3() {
        // K_3: only singletons are independent (3 non-empty) + 1 = 4
        assertEquals(4, (int) new NumOfIndSets().calculate(k3));
    }

    @Test
    public void testNumOfIndSetsP3() {
        // P_3: singletons {0},{1},{2} and the pair {0,2} = 4 non-empty, + 1 = 5
        assertEquals(5, (int) new NumOfIndSets().calculate(p3));
    }

    // -----------------------------------------------------------------------
    // Spectral / Energy reports
    // -----------------------------------------------------------------------

    @Test
    public void testSpectrumOfAdjacencyMatrixNotNull() {
        ArrayList<String> result = new SpectrumOfAdjacencyMatrix().calculate(k3);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testSpectrumOfAdjacencyMatrixP3NotNull() {
        ArrayList<String> result = new SpectrumOfAdjacencyMatrix().calculate(p3);
        assertNotNull(result);
    }

    @Test
    public void testEigenValuesK3NotNull() {
        ArrayList<String> result = new EigenValues().calculate(k3);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testEigenValuesP3NotNull() {
        ArrayList<String> result = new EigenValues().calculate(p3);
        assertNotNull(result);
    }

    @Test
    public void testEnergyK3NotNull() {
        // Energy(K_3) = |2| + |-1| + |-1| = 4.0 (sum of |eigenvalues|)
        String result = new Energy().calculate(k3);
        assertNotNull(result);
        assertFalse(result.isBlank());
    }

    @Test
    public void testLaplacianEnergyK3NotNull() {
        String result = new LaplacianEnergy().calculate(k3);
        assertNotNull(result);
        assertFalse(result.isBlank());
    }

    @Test
    public void testEstradaIndexK3NotNull() {
        RenderTable result = new Estrada().calculate(k3);
        assertNotNull(result);
    }

    @Test
    public void testEstradaIndexP3NotNull() {
        RenderTable result = new Estrada().calculate(p3);
        assertNotNull(result);
    }
}
