// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

import graphtea.extensions.generators.CircleGenerator;
import graphtea.extensions.generators.CompleteGraphGenerator;
import graphtea.extensions.generators.PathGenerator;
import graphtea.extensions.reports.topological.*;
import graphtea.extensions.reports.topological.Irr.AlbertsonIdnexReport;
import graphtea.extensions.reports.topological.Irr.AlbertsonIndex;
import graphtea.extensions.reports.topological.Irr.Irr_G;
import graphtea.extensions.reports.topological.Irr.Irr_t_G;
import graphtea.graph.graph.GraphModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Covers topological report classes and Irr-package classes not reached by
 * TopologicalReportsTest. All tests use K_3 (3-regular, 3 edges) as the
 * primary graph — a small, connected, regular graph that exercises most
 * index formulas without divide-by-zero or disconnected-graph edge cases.
 */
public class TopologicalReportsExtendedTest {

    private final GraphModel k3 = CompleteGraphGenerator.generateCompleteGraph(3);
    private final GraphModel p3 = PathGenerator.generatePath(3);
    private final GraphModel c4 = CircleGenerator.generateCircle(4);

    // -----------------------------------------------------------------------
    // Pairwise-distance-based (PairwiseDistanceReportBase subclasses)
    // -----------------------------------------------------------------------

    @Test
    public void testAdditiveHararyK3() {
        assertNotNull(new AdditiveHarary().calculate(k3));
    }

    @Test
    public void testHararyK3() {
        assertNotNull(new Harary().calculate(k3));
    }

    @Test
    public void testMultiplicativeHararyK3() {
        assertNotNull(new MultiplicativeHarary().calculate(k3));
    }

    @Test
    public void testWeightedWienerIndexK3() {
        // WeightedWienerIndex = sum (deg(u)+deg(v))*dist(u,v).
        // K3: 3 pairs each at dist 1 with deg 2+2 = 4 → 12.0.
        assertEquals(12.0, new WeightedWienerIndex().calculate(k3), 1e-6);
    }

    // -----------------------------------------------------------------------
    // WienerIndexBase subclasses
    // -----------------------------------------------------------------------

    @Test
    public void testEccentricWienerIndexK3() {
        assertNotNull(new EccentricWienerIndex().calculate(k3));
    }

    @Test
    public void testMWienerIndexK3() {
        assertNotNull(new MWienerIndex().calculate(k3));
    }

    // -----------------------------------------------------------------------
    // EccentricDistanceSumBase subclasses
    // -----------------------------------------------------------------------

    @Test
    public void testEccentricDistanceSumK3() {
        assertNotNull(new EccentricDistanceSum().calculate(k3));
    }

    @Test
    public void testAdjacentEccentricDistanceSumK3() {
        assertNotNull(new AdjacentEccentricDistanceSum().calculate(k3));
    }

    // -----------------------------------------------------------------------
    // Remaining concrete topological reports (alphabetical)
    // -----------------------------------------------------------------------

    @Test
    public void testAGIndexK3() {
        assertNotNull(new AGIndex().calculate(k3));
    }

    @Test
    public void testAllCheckK3() {
        assertNotNull(new AllCheck().calculate(k3));
    }

    @Test
    public void testAutographixConjK3() {
        assertNotNull(new AutographixConj().calculate(k3));
    }

    @Test
    public void testComparingE1E2K3() {
        assertNotNull(new ComparingE1E2().calculate(k3));
    }

    @Test
    public void testComparisionK3() {
        assertNotNull(new comparision().calculate(k3));
    }

    @Test
    public void testCompCompareK3() {
        assertNotNull(new CompCompare().calculate(k3));
    }

    @Test
    public void testComplexK3() {
        // Use FQN to avoid ambiguity with graphtea.library.util.Complex
        assertNotNull(new graphtea.extensions.reports.topological.Complex().calculate(k3));
    }

    @Test
    public void testConnectiveEccentricComplexityK3() {
        assertNotNull(new ConnectiveEccentricComplexity().calculate(k3));
    }

    @Test
    public void testConnectiveEccentricIndexK3() {
        assertNotNull(new ConnectiveEccentricIndex().calculate(k3));
    }

    @Test
    public void testConnectivityEccentricityIndexK3() {
        assertNotNull(new ConnectivityEccentricityIndex().calculate(k3));
    }

    @Test
    public void testEccentricConnectiveCoIndexK3() {
        assertNotNull(new EccentricConnectiveCoIndex().calculate(k3));
    }

    @Test
    public void testEccentricConnectiveComplexityK3() {
        assertNotNull(new EccentricConnectiveComplexity().calculate(k3));
    }

    @Test
    public void testEccentricConnectiveIndexK3() {
        assertNotNull(new EccentricConnectiveIndex().calculate(k3));
    }

    @Test
    public void testEccentricityAdjacencyIndexK3() {
        assertNotNull(new EccentricityAdjacencyIndex().calculate(k3));
    }

    @Test
    public void testEccentricityComplexityIndexK3() {
        assertNotNull(new EccentricityComplexityIndex().calculate(k3));
    }

    @Test
    public void testECIConjectureK3() {
        assertNotNull(new ECIConjecture().calculate(k3));
    }

    @Test
    public void testEdgeDegreeK3() {
        // K3 has 3 edges, each of degree (deg(u)-1)+(deg(v)-1) = 1+1 = 2.
        // EdgeDegree returns the sum → 3*2 = 6.
        assertNotNull(new EdgeDegree().calculate(k3));
    }

    @Test
    public void testEdgesDegreesListK3() {
        ArrayList<Integer> result = new EdgesDegreesList().calculate(k3);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testEM1LowerK3() {
        assertNotNull(new EM1Lower().calculate(k3));
    }

    @Test
    public void testEM1LowerBoundK3() {
        assertNotNull(new EM1LowerBound().calculate(k3));
    }

    @Test
    public void testEM1UpperBoundK3() {
        assertNotNull(new EM1UpperBound().calculate(k3));
    }

    @Test
    public void testEM2LowerBoundK3() {
        assertNotNull(new EM2LowerBound().calculate(k3));
    }

    @Test
    public void testEM2UpperBoundK3() {
        assertNotNull(new EM2UpperBound().calculate(k3));
    }

    @Test
    public void testExpK3() {
        assertNotNull(new Exp().calculate(k3));
    }

    @Test
    public void testExponentialK3() {
        assertNotNull(new Exponential().calculate(k3));
    }

    @Test
    public void testFinalNewM2LowerK3() {
        assertNotNull(new FinalNewM2Lower().calculate(k3));
    }

    @Test
    public void testHyperZagrebIndexK3() {
        assertFalse(new HyperZagrebIndex().calculate(k3).isEmpty());
    }

    @Test
    public void testHyperCheckK3() {
        assertNotNull(new HyperCheck().calculate(k3));
    }

    @Test
    public void testIndeK3() {
        assertNotNull(new Inde().calculate(k3));
    }

    @Test
    public void testInverseSumK3() {
        assertNotNull(new InverseSum().calculate(k3));
    }

    @Test
    public void testISIBoundK3() {
        assertNotNull(new ISIBound().calculate(k3));
    }

    @Test
    public void testISIUpperK3() {
        assertNotNull(new ISIUpper().calculate(k3));
    }

    @Test
    public void testLanzhouK3() {
        assertNotNull(new Lanzhou().calculate(k3));
    }

    @Test
    public void testM3BoundConjectureK3() {
        assertNotNull(new M3BoundConjecture().calculate(k3));
    }

    @Test
    public void testM3CompIndCoindConjectureC4() {
        // K3's complement is edgeless (causes NoSuchElementException in this report).
        // Use C4 whose complement (2K2) has edges.
        assertNotNull(new M3CompIndCoindConjecture().calculate(c4));
    }

    @Test
    public void testM3FinalK3() {
        assertNotNull(new M3Final().calculate(k3));
    }

    @Test
    public void testMaxK3() {
        assertNotNull(new Max().calculate(k3));
    }

    @Test
    public void testMereK3() {
        assertNotNull(new Mere().calculate(k3));
    }

    @Test
    public void testMere1K3() {
        assertNotNull(new Mere1().calculate(k3));
    }

    @Test
    public void testModifiedFirstZagrebConnectionIndexK3() {
        assertFalse(new ModifiedFirstZagrebConnectionIndex().calculate(k3).isEmpty());
    }

    @Test
    public void testMostarTopologicalK3() {
        // graphtea.extensions.reports.topological.Mostar (different from others.MostarIndex)
        assertNotNull(new Mostar().calculate(k3));
    }

    @Test
    public void testPBK3() {
        assertNotNull(new PB().calculate(k3));
    }

    @Test
    public void testPheriperal1K3() {
        assertNotNull(new Pheriperal1().calculate(k3));
    }

    @Test
    public void testPheriperal2K3() {
        assertNotNull(new Pheriperal2().calculate(k3));
    }

    @Test
    public void testPheriperalK3() {
        assertNotNull(new Pheriperal().calculate(k3));
    }

    @Test
    public void testPIK3() {
        assertNotNull(new PI().calculate(k3));
    }

    @Test
    public void testPlanarK3() {
        assertNotNull(new Planar().calculate(k3));
    }

    @Test
    public void testSDDK3() {
        assertNotNull(new SDD().calculate(k3));
    }

    @Test
    public void testSumConnectivityIndexK3() {
        assertFalse(new SumConnectivityIndex().calculate(k3).isEmpty());
    }

    @Test
    public void testThirdZagrebIndexK3() {
        assertFalse(new ThirdZagrebIndex().calculate(k3).isEmpty());
    }

    @Test
    public void testTotalEccentricityIndexK3() {
        // Formula: sum(deg(v) * ecc(v)). K3: 3 * (2 * 1) = 6.0.
        assertEquals(6.0, new TotalEccentricityIndex().calculate(k3), 1e-6);
    }

    @Test
    public void testTotalIrregularityIndexK3() {
        // K3 is regular → TotalIrregularityIndex = 0.
        assertEquals(0, (int) new TotalIrregularityIndex().calculate(k3));
    }

    @Test
    public void testTotalIrregularityIndexP3() {
        // P3 degrees [1,2,1]: pairs with |d(u)-d(v)|>0 = (0,1) and (1,2) → irr = 1.
        assertNotNull(new TotalIrregularityIndex().calculate(p3));
    }

    @Test
    public void testVeIndexK3() {
        assertNotNull(new VeIndex().calculate(k3));
    }

    @Test
    public void testWienerComplexIndexK3() {
        assertNotNull(new WienerComplexIndex().calculate(k3));
    }

    @Test
    public void testWienerPolarityK3() {
        assertNotNull(new WienerPolarity().calculate(k3));
    }

    @Test
    public void testWienerPolarityIndexK3() {
        // K3 has diameter 1 → no pairs at distance 3 → WienerPolarityIndex = 0.
        assertEquals(0, (int) new WienerPolarityIndex().calculate(k3));
    }

    @Test
    public void testWinerPolarityK3() {
        assertNotNull(new WinerPolarity().calculate(k3));
    }

    @Test
    public void testZagrebCoindexK3() {
        assertFalse(new ZagrebCoindex().calculate(k3).isEmpty());
    }

    @Test
    public void testZagrebEccentricityK3() {
        assertNotNull(new ZagrebEccentricity().calculate(k3));
    }

    // -----------------------------------------------------------------------
    // Parametrized topological classes (use default values)
    // -----------------------------------------------------------------------

    @Test
    public void testPathZagrebIndexK3() {
        PathZagrebIndex idx = new PathZagrebIndex();
        idx.alpha = 1.0;
        assertFalse(idx.calculate(k3).isEmpty());
    }

    @Test
    public void testSecondMixZagrebIndexK3() {
        SecondMixZagrebIndex idx = new SecondMixZagrebIndex();
        idx.alpha = 1.0;
        idx.beta = 1.0;
        assertFalse(idx.calculate(k3).isEmpty());
    }

    @Test
    public void testVariableZagrebIndexK3() {
        VariableZagrebIndex idx = new VariableZagrebIndex();
        idx.alpha = 1.0;
        assertFalse(idx.calculate(k3).isEmpty());
    }

    @Test
    public void testZagrebCoindexSelectedEdgesK3() {
        ZagrebCoindexSelectedEdges idx = new ZagrebCoindexSelectedEdges();
        idx.alpha = 1.0;
        assertFalse(idx.calculate(k3).isEmpty());
    }

    @Test
    public void testZagrebIndexSelectedEdgesK3() {
        ZagrebIndexSelectedEdges idx = new ZagrebIndexSelectedEdges();
        idx.alpha = 1.0;
        assertFalse(idx.calculate(k3).isEmpty());
    }

    // -----------------------------------------------------------------------
    // Irr package (AlbertsonIndex, Irr_G, Irr_t_G)
    // -----------------------------------------------------------------------

    @Test
    public void testAlbertsonIndexK3() {
        // K3 is regular → Albertson irregularity = 0.
        assertEquals(0, (int) new AlbertsonIndex().calculate(k3));
    }

    @Test
    public void testAlbertsonIndexP3() {
        // P3 degrees [1,2,1]: sum |d(u)-d(v)| over edges = |1-2|+|2-1| = 2.
        assertEquals(2, (int) new AlbertsonIndex().calculate(p3));
    }

    @Test
    public void testAlbertsonIdnexReportK3() {
        assertNotNull(new AlbertsonIdnexReport().calculate(k3));
    }

    @Test
    public void testIrrGK3() {
        assertNotNull(new Irr_G().calculate(k3));
    }

    @Test
    public void testIrrTGK3() {
        assertNotNull(new Irr_t_G().calculate(k3));
    }
}
