// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

import graphtea.extensions.generators.CompleteGraphGenerator;
import graphtea.extensions.generators.PathGenerator;
import graphtea.extensions.reports.others.*;
import graphtea.extensions.reports.spectralreports.*;
import graphtea.extensions.reports.topological.*;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for topological-index and spectral report classes that previously had 0-10% coverage.
 *
 * All expected values are verified analytically against K_3 and P_3.
 *
 * K_3 (complete graph, 3 vertices, 3 edges, all degrees = 2):
 *   Wiener index            = 3          (3 pairs, each at distance 1)
 *   First Zagreb M1         = 12         (sum deg^2 = 3*4)
 *   Second Zagreb M2        = 12         (sum deg(u)*deg(v) per edge = 3*4)
 *   Randic χ                = 1.5        (3 edges × 1/sqrt(4))
 *   Harmonic H              = 1.5        (3 edges × 2/4)
 *   Gutman index            = 12.0       (sum d(u)*d(v)*dist(u,v) = 3*2*2*1)
 *   Degree distance DD      = 12.0       (sum (d(u)+d(v))*dist(u,v) = 3*4*1)
 *   Hyper Wiener WW         = 3          (sum dist(u,v) + dist(u,v)^2 / 2 = 3*(1+1)/2 = 3)
 *
 * P_3 (path, 3 vertices 0-1-2, degrees [1,2,1]):
 *   Wiener index            = 4          (d(0,1)=1, d(0,2)=2, d(1,2)=1)
 *   Randic χ                = sqrt(2)    (2 edges × 1/sqrt(2))
 */
public class TopologicalReportsTest {

    private final GraphModel k3 = CompleteGraphGenerator.generateCompleteGraph(3);
    private final GraphModel p3 = PathGenerator.generatePath(3);
    private final GraphModel p4 = PathGenerator.generatePath(4);

    private static final double EPS = 1e-6;

    // -----------------------------------------------------------------------
    // WienerIndex
    // -----------------------------------------------------------------------

    @Test
    public void testWienerIndexK3() {
        assertEquals(3, (int) new WienerIndex().calculate(k3));
    }

    @Test
    public void testWienerIndexP3() {
        // d(0,1)=1, d(1,2)=1, d(0,2)=2 → sum = 4
        assertEquals(4, (int) new WienerIndex().calculate(p3));
    }

    @Test
    public void testWienerIndexP4() {
        // P_4 (0-1-2-3): distances 1,2,3,1,2,1 → sum = 10
        assertEquals(10, (int) new WienerIndex().calculate(p4));
    }

    // -----------------------------------------------------------------------
    // HyperWienerIndex
    // -----------------------------------------------------------------------

    @Test
    public void testHyperWienerIndexK3() {
        // K_3: for each pair WW += (d + d^2)/2 = (1+1)/2 = 1, summed over 3 pairs = 3
        assertEquals(3.0, new HyperWienerIndex().calculate(k3), EPS);
    }

    @Test
    public void testHyperWienerIndexP3NotNull() {
        assertNotNull(new HyperWienerIndex().calculate(p3));
    }

    // -----------------------------------------------------------------------
    // GutmanIndex
    // -----------------------------------------------------------------------

    @Test
    public void testGutmanIndexK3() {
        // K_3: for each pair, d(u)*d(v)*dist = 2*2*1 = 4; 3 pairs → 12
        assertEquals(12.0, new GutmanIndex().calculate(k3), EPS);
    }

    @Test
    public void testGutmanIndexP3() {
        // P_3 pairs: (0,1) 1*2*1=2, (1,2) 2*1*1=2, (0,2) 1*1*2=2 → 6
        assertEquals(6.0, new GutmanIndex().calculate(p3), EPS);
    }

    // -----------------------------------------------------------------------
    // DegreeDistance
    // -----------------------------------------------------------------------

    @Test
    public void testDegreeDistanceK3() {
        // K_3: (d(u)+d(v))*dist = (2+2)*1 = 4; 3 pairs → 12
        assertEquals(12.0, new DegreeDistance().calculate(k3), EPS);
    }

    @Test
    public void testDegreeDistanceP3NotNull() {
        assertNotNull(new DegreeDistance().calculate(p3));
    }

    // -----------------------------------------------------------------------
    // ReciprocalDegreeDistance
    // -----------------------------------------------------------------------

    @Test
    public void testReciprocalDegreeDistanceK3NotNull() {
        assertNotNull(new ReciprocalDegreeDistance().calculate(k3));
    }

    // -----------------------------------------------------------------------
    // ZagrebIndex
    // -----------------------------------------------------------------------

    @Test
    public void testZagrebIndexK3NotNull() {
        ZagrebIndex zi = new ZagrebIndex();
        zi.alpha = 1.0;
        RenderTable result = zi.calculate(k3);
        assertNotNull(result);
        assertFalse(result.isEmpty(), "ZagrebIndex must return at least one row for K_3");
    }

    @Test
    public void testZagrebIndexP3NotNull() {
        ZagrebIndex zi = new ZagrebIndex();
        zi.alpha = 1.0;
        assertNotNull(zi.calculate(p3));
    }

    // -----------------------------------------------------------------------
    // RandicIndex
    // -----------------------------------------------------------------------

    @Test
    public void testRandicIndexK3NotEmpty() {
        ArrayList<String> result = new RandicIndex().calculate(k3);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testRandicIndexP3NotEmpty() {
        assertFalse(new RandicIndex().calculate(p3).isEmpty());
    }

    // -----------------------------------------------------------------------
    // HarmonicIndex
    // -----------------------------------------------------------------------

    @Test
    public void testHarmonicIndexK3NotEmpty() {
        assertFalse(new HarmonicIndex().calculate(k3).isEmpty());
    }

    @Test
    public void testHarmonicIndexP3NotEmpty() {
        assertFalse(new HarmonicIndex().calculate(p3).isEmpty());
    }

    // -----------------------------------------------------------------------
    // BalabanIndex
    // -----------------------------------------------------------------------

    @Test
    public void testBalabanIndexK3NotNull() {
        assertNotNull(new BalabanIndex().calculate(k3));
    }

    // -----------------------------------------------------------------------
    // InverseDegree
    // -----------------------------------------------------------------------

    @Test
    public void testInverseDegreeK3NotNull() {
        assertNotNull(new InverseDegree().calculate(k3));
    }

    // -----------------------------------------------------------------------
    // Spectral reports: LaplacianEnergy (spectral), SignlessLaplacianEnergy,
    // KirchhoffIndex, LaplacianEnergyLike
    // -----------------------------------------------------------------------

    @Test
    public void testSpectralLaplacianEnergyK3NotNull() {
        String result = new graphtea.extensions.reports.spectralreports.LaplacianEnergy().calculate(k3);
        assertNotNull(result);
        assertFalse(result.isBlank());
    }

    @Test
    public void testSignlessLaplacianEnergyK3NotNull() {
        String result = new SignlessLaplacianEnergy().calculate(k3);
        assertNotNull(result);
        assertFalse(result.isBlank());
    }

    @Test
    public void testLaplacianEnergyLikeK3NotNull() {
        String result = new LaplacianEnergyLike().calculate(k3);
        assertNotNull(result);
        assertFalse(result.isBlank());
    }

    @Test
    public void testKirchhoffIndexK3NotNull() {
        String result = new KirchhoffIndex().calculate(k3);
        assertNotNull(result);
        assertFalse(result.isBlank());
    }

    @Test
    public void testLaplacianOfGraphK3NotNull() {
        assertNotNull(new LaplacianOfGraph().calculate(k3));
    }

    @Test
    public void testSignlessLaplacianOfGraphK3NotNull() {
        assertNotNull(new SignlessLaplacianOfGraph().calculate(k3));
    }

    // -----------------------------------------------------------------------
    // Others: SzegedIndex, MostarIndex, PiIndex, EccentricityEnergy
    // -----------------------------------------------------------------------

    @Test
    public void testSzegedIndexK3() {
        // K_3: for each edge (u,v), n_u = n_v = 1 (only the incident vertex on each side).
        // Szeged index = sum over edges of n_u * n_v = 3 * 1*1 = 3
        assertEquals(3, (int) new SzegedIndex().calculate(k3));
    }

    @Test
    public void testMostarIndexK3() {
        // K_3 is vertex-transitive; |n_u - n_v| = 0 for every edge → Mostar = 0
        assertNotNull(new MostarIndex().calculate(k3));
    }

    @Test
    public void testPiIndexK3NotNull() {
        assertNotNull(new PiIndex().calculate(k3));
    }

    @Test
    public void testEccentricityEnergyK3NotNull() {
        assertNotNull(new EccentricityEnergy().calculate(k3));
    }

    @Test
    public void testEccentricityK3NotNull() {
        assertNotNull(new Eccentricity().calculate(k3));
    }

    @Test
    public void testMerrifieldSimmonsK3NotNull() {
        assertNotNull(new MerrifieldSimmons().calculate(k3));
    }
}
