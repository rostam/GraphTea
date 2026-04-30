// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

import graphtea.extensions.actions.BarycentricSubdivisionGraph;
import graphtea.extensions.algorithms.homomorphism.Homomorphism;
import graphtea.extensions.generators.CompleteGraphGenerator;
import graphtea.extensions.generators.PathGenerator;
import graphtea.extensions.reports.HeuristicGreedyColoring;
import graphtea.extensions.reports.basicreports.SpectrumOfDistanceAdjMatrix;
import graphtea.extensions.reports.basicreports.SpectrumOfDistanceLaplacianMatrix;
import graphtea.extensions.reports.basicreports.SpectrumOfDistanceSignlessLaplacian;
import graphtea.extensions.reports.basicreports.SpectrumOfMaxDegreeAdjMatrix;
import graphtea.extensions.reports.basicreports.SpectrumOfNormalizedLaplacian;
import graphtea.extensions.reports.basicreports.SpectraofTransmissionMatrix;
import graphtea.extensions.reports.coloring.ColumnIntersectionGraph;
import graphtea.extensions.reports.hamilton.HamiltonianCycle;
import graphtea.extensions.reports.spanningtree.MSTPrim;
import graphtea.extensions.reports.spectralreports.EccentricityMatrixOfGraph;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for classes with &lt;15% coverage: spectrum reports, pure algorithms,
 * BarycentricSubdivisionGraph, Homomorphism extras, and ColumnIntersectionGraph.
 */
public class MoreCoverageTest {

    private final GraphModel k3 = CompleteGraphGenerator.generateCompleteGraph(3);
    private final GraphModel p3 = PathGenerator.generatePath(3);

    @Test
    public void spectrumDistanceAdjK3() {
        ArrayList<String> res = new SpectrumOfDistanceAdjMatrix().calculate(k3);
        assertNotNull(res);
        assertFalse(res.isEmpty());
        assertEquals("Distance Adjacency Matrix", res.get(0));
    }

    @Test
    public void spectrumDistanceLaplacianK3() {
        ArrayList<String> res = new SpectrumOfDistanceLaplacianMatrix().calculate(k3);
        assertNotNull(res);
        assertFalse(res.isEmpty());
    }

    @Test
    public void spectrumDistanceSignlessLaplacianK3() {
        ArrayList<String> res = new SpectrumOfDistanceSignlessLaplacian().calculate(k3);
        assertNotNull(res);
        assertFalse(res.isEmpty());
    }

    @Test
    public void spectrumMaxDegreeAdjK3() {
        ArrayList<String> res = new SpectrumOfMaxDegreeAdjMatrix().calculate(k3);
        assertNotNull(res);
        assertEquals("Max Degree Adjacency Matrix", res.get(0));
    }

    @Test
    public void spectrumNormalizedLaplacianK3() {
        ArrayList<String> res = new SpectrumOfNormalizedLaplacian().calculate(k3);
        assertNotNull(res);
        assertFalse(res.isEmpty());
    }

    @Test
    public void spectraTransmissionMatrixK3() {
        ArrayList<String> res = new SpectraofTransmissionMatrix().calculate(k3);
        assertNotNull(res);
        assertFalse(res.isEmpty());
    }

    @Test
    public void mstPrimK3ReturnsParentArray() {
        int[][] adj = {{0, 1, 1}, {1, 0, 1}, {1, 1, 0}};
        int[] parent = new MSTPrim().prim(adj);
        assertNotNull(parent);
        assertEquals(3, parent.length);
        assertEquals(-1, parent[0]);
    }

    @Test
    public void mstPrimMinimumIdPicker() {
        MSTPrim prim = new MSTPrim();
        int[] id = {5, 2, 8};
        boolean[] mst = {false, false, false};
        assertEquals(1, prim.minimumID(id, mst));
    }

    @Test
    public void mstPrimSkipsAlreadyInMST() {
        MSTPrim prim = new MSTPrim();
        // vertex 1 is in MST; among non-MST vertices 0 (id=5) and 2 (id=3), minimum is vertex 2
        int[] id = {5, 2, 3};
        boolean[] mst = {false, true, false};
        assertEquals(2, prim.minimumID(id, mst));
    }

    @Test
    public void hamiltonianCycleK3FindsCycle() throws Exception {
        HamiltonianCycle hc = new HamiltonianCycle();
        int[][] adj = {{0, 1, 1}, {1, 0, 1}, {1, 1, 0}};
        java.lang.reflect.Method m = HamiltonianCycle.class.getDeclaredMethod("HamiltonCycle", int[][].class);
        m.setAccessible(true);
        int[] cycle = (int[]) m.invoke(hc, (Object) adj);
        assertNotNull(cycle, "K3 has a Hamiltonian cycle");
        assertEquals(3, cycle.length);
    }

    @Test
    public void hamiltonianCycleNoCycleReturnsNull() throws Exception {
        HamiltonianCycle hc = new HamiltonianCycle();
        // P2: no Hamiltonian cycle
        int[][] adj = {{0, 1}, {1, 0}};
        java.lang.reflect.Method m = HamiltonianCycle.class.getDeclaredMethod("HamiltonCycle", int[][].class);
        m.setAccessible(true);
        int[] cycle = (int[]) m.invoke(hc, (Object) adj);
        assertNull(cycle, "P2 has no Hamiltonian cycle");
    }

    @Test
    public void barycentricSubdivisionK1subdividesEdges() {
        GraphModel g = CompleteGraphGenerator.generateCompleteGraph(3);
        // k=1: each of the 3 edges gains 1 intermediate vertex → 6 vertices, 6 edges
        GraphModel sub = BarycentricSubdivisionGraph.createBarycentricGraph(g, 1);
        assertNotNull(sub);
        assertEquals(6, sub.getVerticesCount());
        assertEquals(6, sub.getEdgesCount());
    }

    @Test
    public void barycentricSubdivisionK2subdividesEdges() {
        GraphModel g = CompleteGraphGenerator.generateCompleteGraph(3);
        // k=2: each of the 3 edges gains 2 intermediate vertices → 9 vertices, 9 edges
        GraphModel sub = BarycentricSubdivisionGraph.createBarycentricGraph(g, 2);
        assertEquals(9, sub.getVerticesCount());
        assertEquals(9, sub.getEdgesCount());
    }

    @Test
    public void eccentricityMatrixOfGraphUndirected() {
        ArrayList<String> result = new EccentricityMatrixOfGraph().calculate(k3);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void eccentricityMatrixHelperMethods() {
        EccentricityMatrixOfGraph em = new EccentricityMatrixOfGraph();
        graphtea.extensions.algorithms.shortestpath.algs.FloydWarshall fw =
                new graphtea.extensions.algorithms.shortestpath.algs.FloydWarshall();
        int[][] dist = fw.getAllPairsShortestPathWithoutWeight(k3);
        // K3: all eccentricities = 1
        assertEquals(1, em.eccentricity(k3, 0, dist));
        Jama.Matrix m = em.eccentricityMatrix(k3, dist);
        assertNotNull(m);
        assertEquals(3, m.getRowDimension());
    }

    @Test
    public void homomorphismIsHomomorphismOnto() {
        GraphModel g = CompleteGraphGenerator.generateCompleteGraph(3);
        List<Integer> colors = new HeuristicGreedyColoring().calculate(g);
        int numColors = Collections.max(colors);
        Homomorphism hm = new Homomorphism(g, colors, numColors);
        assertTrue(hm.isHomomorphismOnto());
    }

    @Test
    public void homomorphismGetQuotient() {
        GraphModel g = CompleteGraphGenerator.generateCompleteGraph(3);
        List<Integer> colors = new HeuristicGreedyColoring().calculate(g);
        int numColors = Collections.max(colors);
        Homomorphism hm = new Homomorphism(g, colors, numColors);
        GraphModel quotient = hm.getQuotient();
        assertNotNull(quotient);
        assertEquals(numColors, quotient.getVerticesCount());
    }

    @Test
    public void homomorphismGetDomainAndRange() {
        GraphModel g = CompleteGraphGenerator.generateCompleteGraph(3);
        List<Integer> colors = new HeuristicGreedyColoring().calculate(g);
        int numColors = Collections.max(colors);
        Homomorphism hm = new Homomorphism(g, colors, numColors);
        assertNotNull(hm.getDomain());
        assertNotNull(hm.getRange());
    }

    @Test
    public void homomorphismCompose() {
        GraphModel g = CompleteGraphGenerator.generateCompleteGraph(3);
        List<Integer> colors = new HeuristicGreedyColoring().calculate(g);
        int numColors = Collections.max(colors);
        Homomorphism h1 = new Homomorphism(g, colors, numColors);
        // h2 maps h1's range (K3) back to K3 via its own coloring
        List<Integer> colors2 = new HeuristicGreedyColoring().calculate(h1.getRange());
        int numColors2 = Collections.max(colors2);
        Homomorphism h2 = new Homomorphism(h1.getRange(), colors2, numColors2);
        Homomorphism composed = Homomorphism.compose(h1, h2);
        assertNotNull(composed);
    }

    @Test
    public void columnIntersectionGraphFromK3() {
        GraphModel cig = ColumnIntersectionGraph.from(k3);
        assertNotNull(cig);
        assertEquals(3, cig.getVerticesCount());
    }

    @Test
    public void columnIntersectionGraphSparsify() {
        GraphModel sparse = ColumnIntersectionGraph.sparsify(k3, 1);
        assertNotNull(sparse);
        assertEquals(3, sparse.getVerticesCount());
    }
}
