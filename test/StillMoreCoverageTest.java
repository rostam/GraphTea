// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

import Jama.Matrix;
import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.algorithms.BiconnectedComponents;
import graphtea.extensions.generators.CompleteGraphGenerator;
import graphtea.extensions.generators.PathGenerator;
import graphtea.extensions.reports.energy.AllEnergies;
import graphtea.extensions.reports.energy.NewLowerBounds;
import graphtea.extensions.reports.topological.ZagrebIndexFunctions;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.graph.graph.Vertex;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for ZagrebIndexFunctions, AllEnergies, NewLowerBounds,
 * BiconnectedComponents, and AlgorithmUtils static methods.
 */
public class StillMoreCoverageTest {

    private final GraphModel k3 = CompleteGraphGenerator.generateCompleteGraph(3);
    private final GraphModel p3 = PathGenerator.generatePath(3);

    // ── AllEnergies ───────────────────────────────────────────────────────────

    @Test
    public void allEnergiesCalculateK3() {
        RenderTable rt = new AllEnergies().calculate(k3);
        assertNotNull(rt);
        assertFalse(rt.isEmpty());
    }

    @Test
    public void allEnergiesEnergyHelper() {
        assertNotNull(new AllEnergies().Energy(k3));
    }

    @Test
    public void allEnergiesLaplacianEnergyHelper() {
        assertNotNull(new AllEnergies().LaplacianEnergy(k3));
    }

    @Test
    public void allEnergiesSignlessLaplacianEnergyHelper() {
        assertNotNull(new AllEnergies().SignlessLaplacianEnergy(k3));
    }

    @Test
    public void allEnergiesResolventEnergyHelper() {
        assertNotNull(new AllEnergies().ResolventEnergy(k3));
    }

    // ── NewLowerBounds ────────────────────────────────────────────────────────

    @Test
    public void newLowerBoundsCalculateK3() {
        Object rt = new NewLowerBounds().calculate(k3);
        assertNotNull(rt);
    }

    @Test
    public void newLowerBoundsEnergyHelper() {
        assertNotNull(new NewLowerBounds().Energy(k3));
    }

    @Test
    public void newLowerBoundsSignlessLaplacianEnergyHelper() {
        assertNotNull(new NewLowerBounds().SignlessLaplacianEnergy(k3));
    }

    @Test
    public void newLowerBoundsLaplacianEnergyHelper() {
        assertNotNull(new NewLowerBounds().LaplacianEnergy(k3));
    }

    // ── BiconnectedComponents ─────────────────────────────────────────────────

    @Test
    public void biconnectedComponentsK3ReturnsComponents() {
        BiconnectedComponents bc = new BiconnectedComponents();
        Vertex start = k3.getVertex(0);
        List<HashSet<Vertex>> comps = bc.biconnected_components(k3, start, k3.getVerticesCount());
        assertNotNull(comps);
        assertFalse(comps.isEmpty());
    }

    @Test
    public void biconnectedComponentsP3ReturnsComponents() {
        BiconnectedComponents bc = new BiconnectedComponents();
        Vertex start = p3.getVertex(0);
        List<HashSet<Vertex>> comps = bc.biconnected_components(p3, start, p3.getVerticesCount());
        assertNotNull(comps);
        assertEquals(2, comps.size());
    }

    // ── ZagrebIndexFunctions ─────────────────────────────────────────────────

    @Test
    public void zagrebEnergyK3() {
        ZagrebIndexFunctions z = new ZagrebIndexFunctions(k3);
        double e = z.getEnegry();
        assertTrue(e >= 0);
    }

    @Test
    public void zagrebInverseSumIndegIndex() {
        ZagrebIndexFunctions z = new ZagrebIndexFunctions(k3);
        assertTrue(z.getInverseSumIndegIndex() > 0);
    }

    @Test
    public void zagrebAlbCoindex() {
        ZagrebIndexFunctions z = new ZagrebIndexFunctions(k3);
        // K3 has no non-edges, coindex = 0
        assertEquals(0.0, z.getAlbCoindex(), 1e-9);
    }

    @Test
    public void zagrebSigmaIndex() {
        ZagrebIndexFunctions z = new ZagrebIndexFunctions(k3);
        // K3: all degrees equal → sigma = 0
        assertEquals(0.0, z.getSigmaindex(), 1e-9);
    }

    @Test
    public void zagrebAlbertson() {
        ZagrebIndexFunctions z = new ZagrebIndexFunctions(k3);
        assertEquals(0.0, z.getAlbertson(), 1e-9);
    }

    @Test
    public void zagrebIrregularity() {
        ZagrebIndexFunctions z = new ZagrebIndexFunctions(k3);
        assertEquals(0.0, z.getirregularity(), 1e-9);
    }

    @Test
    public void zagrebAugumentedZagrebIndex() {
        ZagrebIndexFunctions z = new ZagrebIndexFunctions(k3);
        assertTrue(z.getAugumentedZagrebIndex() > 0);
    }

    @Test
    public void zagrebSDDIndex() {
        ZagrebIndexFunctions z = new ZagrebIndexFunctions(k3);
        assertTrue(z.getSDDIndex() > 0);
    }

    @Test
    public void zagrebAGIndex() {
        ZagrebIndexFunctions z = new ZagrebIndexFunctions(k3);
        assertTrue(z.getAGIndex() > 0);
    }

    @Test
    public void zagrebPBIndex() {
        ZagrebIndexFunctions z = new ZagrebIndexFunctions(k3);
        assertTrue(Double.isFinite(z.getPBIndex()));
    }

    @Test
    public void zagrebABCindex() {
        ZagrebIndexFunctions z = new ZagrebIndexFunctions(k3);
        assertTrue(z.getABCindex() > 0);
    }

    @Test
    public void zagrebCheck() {
        ZagrebIndexFunctions z = new ZagrebIndexFunctions(k3);
        assertTrue(Double.isFinite(z.getCheck()));
    }

    @Test
    public void zagrebVariationRandicIndex() {
        ZagrebIndexFunctions z = new ZagrebIndexFunctions(k3);
        assertTrue(Double.isFinite(z.getVariationRandicIndex()));
    }

    @Test
    public void zagrebEdgeDegree() {
        ZagrebIndexFunctions z = new ZagrebIndexFunctions(k3);
        assertTrue(Double.isFinite(z.getEdgeDegree(2.0)));
    }

    @Test
    public void zagrebHyperZagrebIndex() {
        ZagrebIndexFunctions z = new ZagrebIndexFunctions(k3);
        assertTrue(z.getHyperZagrebIndex() > 0);
    }

    @Test
    public void zagrebFirstZagreb() {
        ZagrebIndexFunctions z = new ZagrebIndexFunctions(k3);
        // K3: all degrees 2, M1(alpha=1) = sum_v deg(v)^2 = 3*4 = 12
        assertEquals(12.0, z.getFirstZagreb(1.0), 1e-9);
    }

    @Test
    public void zagrebMultiplicativeFirstZagreb() {
        ZagrebIndexFunctions z = new ZagrebIndexFunctions(k3);
        assertTrue(z.getMultiplicativeFirstZagreb(1.0) > 0);
    }

    @Test
    public void zagrebExpHarmonicIndex() {
        ZagrebIndexFunctions z = new ZagrebIndexFunctions(k3);
        assertTrue(Double.isFinite(z.getexpHarmonicIndex()));
    }

    @Test
    public void zagrebExpGAindex() {
        ZagrebIndexFunctions z = new ZagrebIndexFunctions(k3);
        assertTrue(Double.isFinite(z.getexpGAindex()));
    }

    @Test
    public void zagrebExpSDDIndex() {
        ZagrebIndexFunctions z = new ZagrebIndexFunctions(k3);
        assertTrue(Double.isFinite(z.getexpSDDIndex()));
    }

    @Test
    public void zagrebExpABCindex() {
        ZagrebIndexFunctions z = new ZagrebIndexFunctions(k3);
        assertTrue(Double.isFinite(z.getexpABCindex()));
    }

    @Test
    public void zagrebExpAugumentedZagrebIndex() {
        ZagrebIndexFunctions z = new ZagrebIndexFunctions(k3);
        assertTrue(Double.isFinite(z.getexpAugumentedZagrebIndex()));
    }

    @Test
    public void zagrebConnectionNumber() {
        ZagrebIndexFunctions z = new ZagrebIndexFunctions(k3);
        // For any vertex in K3, connection number = vertices at distance 2 = 0 (all are neighbors)
        Vertex v = k3.getVertex(0);
        assertTrue(z.getConnectionNumber(v) >= 0);
    }

    @Test
    public void zagrebModifiedFirstZagrebConnection() {
        ZagrebIndexFunctions z = new ZagrebIndexFunctions(k3);
        assertTrue(Double.isFinite(z.getModifiedFirstZagrebConnection()));
    }

    @Test
    public void zagrebThirdZagreb() {
        ZagrebIndexFunctions z = new ZagrebIndexFunctions(k3);
        assertTrue(Double.isFinite(z.getThirdZagreb()));
    }

    @Test
    public void zagrebExpSecondZagreb() {
        ZagrebIndexFunctions z = new ZagrebIndexFunctions(k3);
        assertTrue(Double.isFinite(z.getexpSecondZagreb(1.0)));
    }

    @Test
    public void zagrebExpFirstZagreb() {
        ZagrebIndexFunctions z = new ZagrebIndexFunctions(k3);
        assertTrue(Double.isFinite(z.getexpFirstZagreb(1.0)));
    }

    @Test
    public void zagrebSecondZagreb() {
        ZagrebIndexFunctions z = new ZagrebIndexFunctions(k3);
        // K3: all edges (2,2) → M2(alpha=1) = 3 * 2*2 = 12
        assertEquals(12.0, z.getSecondZagreb(1.0), 1e-9);
    }

    @Test
    public void zagrebGAindex() {
        ZagrebIndexFunctions z = new ZagrebIndexFunctions(k3);
        assertTrue(z.getGAindex() > 0);
    }

    @Test
    public void zagrebHarmonicIndex() {
        ZagrebIndexFunctions z = new ZagrebIndexFunctions(k3);
        assertTrue(z.getHarmonicIndex() > 0);
    }

    @Test
    public void zagrebRandicEnergy() {
        ZagrebIndexFunctions z = new ZagrebIndexFunctions(k3);
        assertTrue(z.getRandicEnergy(k3) >= 0);
    }

    @Test
    public void zagrebFirstZagrebEccentricity() {
        ZagrebIndexFunctions z = new ZagrebIndexFunctions(k3);
        assertTrue(z.getFirstZagrebEccentricity(k3) > 0);
    }

    @Test
    public void zagrebSecondZagrebEccentricity() {
        ZagrebIndexFunctions z = new ZagrebIndexFunctions(k3);
        assertTrue(z.getSecondZagrebEccentricity(k3) > 0);
    }

    // ── AlgorithmUtils ────────────────────────────────────────────────────────

    @Test
    public void algorithmUtilsResetVertexColors() {
        GraphModel g = CompleteGraphGenerator.generateCompleteGraph(3);
        g.getVertex(0).setColor(5);
        AlgorithmUtils.resetVertexColors(g);
        for (Vertex v : g) {
            assertEquals(0, v.getColor());
        }
    }

    @Test
    public void algorithmUtilsResetVertexMarks() {
        GraphModel g = CompleteGraphGenerator.generateCompleteGraph(3);
        g.getVertex(0).setMark(true);
        AlgorithmUtils.resetVertexMarks(g);
        for (Vertex v : g) {
            assertFalse(v.getMark());
        }
    }

    @Test
    public void algorithmUtilsIsConnectedK3() {
        assertTrue(AlgorithmUtils.isConnected(k3));
    }

    @Test
    public void algorithmUtilsGetAllPairsDistancesK3() {
        int[][] dist = AlgorithmUtils.getAllPairsDistances(k3);
        assertNotNull(dist);
        assertEquals(3, dist.length);
        assertEquals(0, dist[0][0]);
        assertEquals(1, dist[0][1]);
    }

    @Test
    public void algorithmUtilsSpectralEnergy() {
        Matrix A = k3.getWeightedAdjacencyMatrix();
        double e = AlgorithmUtils.spectralEnergy(A, 0.0);
        assertTrue(e > 0);
    }

    @Test
    public void algorithmUtilsGetMinNonPendentDegreeK3() {
        // K3: all degrees = 2 (no pendant vertices)
        double d = AlgorithmUtils.getMinNonPendentDegree(k3);
        assertEquals(2.0, d, 1e-9);
    }

    @Test
    public void algorithmUtilsGetMinNonPendentDegreeP3() {
        // P3: degrees [1,2,1] → min non-pendent = 2
        double d = AlgorithmUtils.getMinNonPendentDegree(p3);
        assertEquals(2.0, d, 1e-9);
    }

    @Test
    public void algorithmUtilsGetDegreeSumOfVertex() {
        // K3, alpha=1: each vertex has 2 neighbors with degree 2 → sum = 4
        Vertex v = k3.getVertex(0);
        double s = AlgorithmUtils.getDegreeSumOfVertex(k3, 1.0, v);
        assertEquals(4.0, s, 1e-9);
    }

    @Test
    public void algorithmUtilsGetDegreeSum() {
        // K3, alpha=1: 3 * 4 = 12
        double s = AlgorithmUtils.getDegreeSum(k3, 1.0);
        assertEquals(12.0, s, 1e-9);
    }

    @Test
    public void algorithmUtilsChoose() {
        // C(5,2) = 10
        assertEquals(BigInteger.TEN, AlgorithmUtils.choose(5, 2));
        assertEquals(BigInteger.ONE, AlgorithmUtils.choose(5, 0));
        assertEquals(BigInteger.ZERO, AlgorithmUtils.choose(5, 6));
    }

    @Test
    public void algorithmUtilsGetMaxDegreeK3() {
        assertEquals(2, AlgorithmUtils.getMaxDegree(k3));
    }

    @Test
    public void algorithmUtilsGetEigenValuesGraph() {
        String ev = AlgorithmUtils.getEigenValues(k3);
        assertNotNull(ev);
        assertFalse(ev.isEmpty());
    }

    @Test
    public void algorithmUtilsGetEigenValuesMatrix() {
        Matrix A = k3.getWeightedAdjacencyMatrix();
        String ev = AlgorithmUtils.getEigenValues(A);
        assertNotNull(ev);
        assertFalse(ev.isEmpty());
    }

    @Test
    public void algorithmUtilsSumOfExpOfEigenValues() {
        Matrix A = k3.getWeightedAdjacencyMatrix();
        double s = AlgorithmUtils.sumOfExpOfEigenValues(A);
        assertTrue(s > 0);
    }

    @Test
    public void algorithmUtilsSumOfEigenValues() {
        Matrix A = k3.getWeightedAdjacencyMatrix();
        double s = AlgorithmUtils.sumOfEigenValues(A);
        // K3 eigenvalues: 2, -1, -1 → sum of |eigenvalues| = 4
        assertEquals(4.0, s, 1e-6);
    }

    @Test
    public void algorithmUtilsGetMaxDegreeAdjacencyMatrix() {
        Matrix m = AlgorithmUtils.getMaxDegreeAdjacencyMatrix(k3);
        assertNotNull(m);
        assertEquals(3, m.getRowDimension());
    }

    @Test
    public void algorithmUtilsGetDistanceAdjacencyMatrix() {
        Matrix m = AlgorithmUtils.getDistanceAdjacencyMatrix(k3);
        assertNotNull(m);
        assertEquals(3, m.getRowDimension());
    }

    @Test
    public void algorithmUtilsGetLaplacianFromMatrix() {
        Matrix A = k3.getWeightedAdjacencyMatrix();
        Matrix L = AlgorithmUtils.getLaplacian(A);
        assertNotNull(L);
        // L = D - A; trace(L) = sum of degrees = 6 for K3
        assertEquals(6.0, L.trace(), 1e-6);
    }

    @Test
    public void algorithmUtilsGetNormalizedLaplacian() {
        Matrix L = AlgorithmUtils.getNormalizedLaplacian(k3);
        assertNotNull(L);
        assertEquals(3, L.getRowDimension());
    }

    @Test
    public void algorithmUtilsGetLaplacianFromGraph() {
        Matrix L = AlgorithmUtils.getLaplacian(k3);
        assertNotNull(L);
        assertEquals(3, L.getRowDimension());
    }

    @Test
    public void algorithmUtilsGetSignlessLaplacian() {
        Matrix A = k3.getWeightedAdjacencyMatrix();
        Matrix Q = AlgorithmUtils.getSignlessLaplacian(A);
        assertNotNull(Q);
        assertEquals(6.0, Q.trace(), 1e-6);
    }

    @Test
    public void algorithmUtilsGetDistanceLaplacianMatrix() {
        Matrix DL = AlgorithmUtils.getDistanceLaplacianMatrix(k3);
        assertNotNull(DL);
        assertEquals(3, DL.getRowDimension());
    }

    @Test
    public void algorithmUtilsGetDistanceSignlessLaplacianMatrix() {
        Matrix DQ = AlgorithmUtils.getDistanceSignlessLaplacianMatrix(k3);
        assertNotNull(DQ);
        assertEquals(3, DQ.getRowDimension());
    }

    @Test
    public void algorithmUtilsGetEccentricities() {
        int[] ecc = AlgorithmUtils.getEccentricities(k3);
        assertNotNull(ecc);
        assertEquals(3, ecc.length);
        for (int e : ecc) {
            assertEquals(1, e);
        }
    }

    @Test
    public void algorithmUtilsRound() {
        assertEquals(3.14, AlgorithmUtils.round(3.14159, 2), 1e-9);
    }

    @Test
    public void algorithmUtilsRoundArray() {
        double[] arr = {1.111, 2.222};
        double[] result = AlgorithmUtils.round(arr, 2);
        assertEquals(1.11, result[0], 1e-9);
        assertEquals(2.22, result[1], 1e-9);
    }

    @Test
    public void algorithmUtilsCreateLineGraph() {
        GraphModel line = AlgorithmUtils.createLineGraph(k3);
        assertNotNull(line);
        // Line graph of K3 is K3 itself: 3 edges → 3 vertices, 3 edges
        assertEquals(3, line.getVerticesCount());
    }

    @Test
    public void algorithmUtilsCreateComplementGraph() {
        GraphModel comp = AlgorithmUtils.createComplementGraph(k3);
        assertNotNull(comp);
        // Complement of K3 has 3 vertices, 0 edges
        assertEquals(3, comp.getVerticesCount());
        assertEquals(0, comp.getEdgesCount());
    }

    @Test
    public void algorithmUtilsGetBinaryPattern() {
        double[][] mat = {{1.5, 0.0}, {0.0, 2.0}};
        int[][] bp = AlgorithmUtils.getBinaryPattern(mat, 2);
        assertNotNull(bp);
        assertEquals(2, bp.length);
        assertEquals(1, bp[0][0]);
        assertEquals(0, bp[0][1]);
    }

    @Test
    public void algorithmUtilsGetDiagonalTransMatrix() {
        Matrix T = AlgorithmUtils.getDiagonalTransMatrix(k3);
        assertNotNull(T);
        assertEquals(3, T.getRowDimension());
    }

    @Test
    public void algorithmUtilsGetAverageTransMatrix() {
        Matrix T = AlgorithmUtils.getAverageTransMatrix(k3);
        assertNotNull(T);
        assertEquals(3, T.getRowDimension());
    }
}
