// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

import Jama.Matrix;
import graphtea.extensions.RandomTree;
import graphtea.extensions.algorithms.IndSetProductColoring;
import graphtea.extensions.generators.CompleteGraphGenerator;
import graphtea.extensions.generators.PathGenerator;
import graphtea.extensions.generators.TreeGenerator;
import graphtea.extensions.reports.coloring.SpMat;
import graphtea.extensions.reports.energy.AllEnergies1;
import graphtea.extensions.reports.energy.Linear;
import graphtea.extensions.reports.energy.ResolventEnergies;
import graphtea.extensions.reports.spectralreports.maxflowmincut.GomoryHuTree;
import graphtea.extensions.reports.spectralreports.maxflowmincut.MaximumFlow;
import graphtea.extensions.reports.spectralreports.maxflowmincut.MinimumCut;
import graphtea.extensions.reports.spectralreports.maxflowmincut.one;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.graph.graph.Vertex;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for energy reports, SpMat, RandomTree, TreeGenerator, IndSetProductColoring,
 * flow-cut reports (GomoryHuTree, MinimumCut, MaximumFlow), and the JAMA one demo.
 */
public class YetMoreCoverageTest {

    private final GraphModel k3 = CompleteGraphGenerator.generateCompleteGraph(3);
    private final GraphModel p3 = PathGenerator.generatePath(3);

    // ── AllEnergies1 ──────────────────────────────────────────────────────────

    @Test
    public void allEnergies1CalculateK3() {
        RenderTable rt = new AllEnergies1().calculate(k3);
        assertNotNull(rt);
        assertFalse(rt.isEmpty());
    }

    @Test
    public void allEnergies1EnergyHelper() {
        assertNotNull(new AllEnergies1().Energy(k3));
    }

    @Test
    public void allEnergies1LaplacianEnergyHelper() {
        assertNotNull(new AllEnergies1().LaplacianEnergy(k3));
    }

    @Test
    public void allEnergies1SignlessLaplacianEnergyHelper() {
        assertNotNull(new AllEnergies1().SignlessLaplacianEnergy(k3));
    }

    @Test
    public void allEnergies1ResolventEnergyHelper() {
        assertNotNull(new AllEnergies1().ResolventEnergy(k3));
    }

    // ── ResolventEnergies ─────────────────────────────────────────────────────

    @Test
    public void resolventEnergiesCalculateK3() {
        RenderTable rt = new ResolventEnergies().calculate(k3);
        assertNotNull(rt);
        assertFalse(rt.isEmpty());
    }

    @Test
    public void resolventEnergiesEnergyHelper() {
        assertNotNull(new ResolventEnergies().Energy(k3));
    }

    @Test
    public void resolventEnergiesLaplacianEnergyHelper() {
        assertNotNull(new ResolventEnergies().LaplacianEnergy(k3));
    }

    @Test
    public void resolventEnergiesSignlessLaplacianHelper() {
        assertNotNull(new ResolventEnergies().SignlessLaplacianEnergy(k3));
    }

    @Test
    public void resolventEnergiesResolventEnergyHelper() {
        assertNotNull(new ResolventEnergies().ResolventEnergy(k3));
    }

    // ── Linear ────────────────────────────────────────────────────────────────

    @Test
    public void linearCalculateK3() {
        RenderTable rt = new Linear().calculate(k3);
        assertNotNull(rt);
        assertFalse(rt.isEmpty());
    }

    @Test
    public void linearEstradaHelper() {
        assertNotNull(new Linear().Estrada(k3));
    }

    @Test
    public void linearDEnergyHelper() {
        assertNotNull(new Linear().DEnergy(k3));
    }

    @Test
    public void linearEnergyHelper() {
        assertNotNull(new Linear().Energy(k3));
    }

    @Test
    public void linearLaplacianEnergyHelper() {
        assertNotNull(new Linear().LaplacianEnergy(k3));
    }

    @Test
    public void linearSignlessLaplacianHelper() {
        assertNotNull(new Linear().SignlessLaplacianEnergy(k3));
    }

    @Test
    public void linearGaussEstradaHelper() {
        assertNotNull(new Linear().GaussEstrada(k3));
    }

    @Test
    public void linearResolventEnergyHelper() {
        assertNotNull(new Linear().ResolventEnergy(k3));
    }

    // ── SpMat ─────────────────────────────────────────────────────────────────

    @Test
    public void spMatRowColConstructor() {
        SpMat sp = new SpMat(3, 4);
        assertEquals(3, sp.rows());
        assertEquals(4, sp.cols());
        assertEquals(0, sp.nnz());
    }

    @Test
    public void spMatMatrixConstructor() {
        Matrix m = new Matrix(new double[][]{{1, 0}, {0, 1}});
        SpMat sp = new SpMat(m);
        assertEquals(2, sp.rows());
        assertEquals(2, sp.nnz());
    }

    @Test
    public void spMatSetAndContains() {
        SpMat sp = new SpMat(3, 3);
        assertFalse(sp.contains(0, 1));
        sp.set(0, 1);
        assertTrue(sp.contains(0, 1));
        assertEquals(1, sp.nnz());
    }

    @Test
    public void spMatCopy() {
        SpMat sp = new SpMat(3, 3);
        sp.set(0, 1);
        sp.set(2, 2);
        SpMat cp = sp.copy();
        assertEquals(sp.nnz(), cp.nnz());
        assertTrue(cp.contains(0, 1));
        assertTrue(cp.contains(2, 2));
    }

    @Test
    public void spMatSparsifyDivisibleK() {
        // rows=3, k=1: 3 % 1 == 0 → first branch
        SpMat sp = new SpMat(3, 3);
        sp.set(0, 0);
        sp.set(1, 1);
        sp.set(2, 2);
        SpMat result = sp.sparsify(1);
        assertNotNull(result);
        assertEquals(3, result.rows());
    }

    @Test
    public void spMatSparsifyNonDivisibleK() {
        // rows=3, k=2: 3 % 2 != 0 → second branch
        SpMat sp = new SpMat(3, 3);
        sp.set(0, 0);
        sp.set(1, 1);
        sp.set(2, 2);
        SpMat result = sp.sparsify(2);
        assertNotNull(result);
        assertEquals(3, result.rows());
    }

    @Test
    public void spMatWriteToFile(@TempDir Path tmp) throws Exception {
        SpMat sp = new SpMat(3, 3);
        sp.set(0, 1);
        sp.set(2, 2);
        File f = tmp.resolve("spmat.txt").toFile();
        sp.writeToFile(f.getAbsolutePath());
        assertTrue(f.exists());
        assertTrue(f.length() > 0);
    }

    // ── RandomTree ────────────────────────────────────────────────────────────

    @Test
    public void randomTreeEdgeListSize3() {
        int[][] edges = new RandomTree(3).getEdgeList();
        assertNotNull(edges);
        assertEquals(2, edges.length);
        assertEquals(2, edges[0].length);
    }

    @Test
    public void randomTreeEdgeListSize5() {
        int[][] edges = new RandomTree(5).getEdgeList();
        assertNotNull(edges);
        // A tree on 5 vertices has 4 edges
        assertEquals(4, edges[0].length);
    }

    // ── TreeGenerator ─────────────────────────────────────────────────────────

    @Test
    public void treeGeneratorBinaryDepth2() {
        // Complete binary tree: depth=2, degree=2 → n = (2^3-1)/(2-1) = 7 vertices
        GraphModel t = TreeGenerator.generateTree(2, 2);
        assertNotNull(t);
        assertEquals(7, t.getVerticesCount());
        assertEquals(6, t.getEdgesCount());
    }

    @Test
    public void treeGeneratorTernaryDepth1() {
        // depth=1, degree=3 → n = (3^2-1)/(3-1) = 4 vertices
        GraphModel t = TreeGenerator.generateTree(1, 3);
        assertNotNull(t);
        assertEquals(4, t.getVerticesCount());
        assertEquals(3, t.getEdgesCount());
    }

    // ── IndSetProductColoring ─────────────────────────────────────────────────

    @Test
    public void getAllIndependentSetsK3() {
        // K3: only singletons are independent sets
        List<java.util.ArrayDeque<Vertex>> sets =
                IndSetProductColoring.getAllIndependentSets(k3);
        assertNotNull(sets);
        assertFalse(sets.isEmpty());
    }

    @Test
    public void getAllIndependentSetsPath() {
        // P3 has two-vertex independent sets {v0,v2}
        List<java.util.ArrayDeque<Vertex>> sets =
                IndSetProductColoring.getAllIndependentSets(p3);
        assertNotNull(sets);
        assertTrue(sets.size() >= 1);
    }

    // ── GomoryHuTree, MinimumCut, MaximumFlow ─────────────────────────────────

    @Test
    public void gomoryHuTreeUndirectedReturnsNull() {
        assertNull(new GomoryHuTree().calculate(k3));
    }

    @Test
    public void minimumCutCalculateReturnsNull() {
        assertNull(new MinimumCut().calculate(k3));
    }

    @Test
    public void maximumFlowCalculateReturnsNull() {
        assertNull(new MaximumFlow().calculate(k3));
    }

    // ── one (JAMA demo) ───────────────────────────────────────────────────────

    @Test
    public void oneMagicOddOrder() {
        Matrix m = one.magic(3);
        assertNotNull(m);
        assertEquals(3, m.getRowDimension());
        assertEquals(15.0, m.trace(), 1e-9);
    }

    @Test
    public void oneMagicDoublyEvenOrder() {
        Matrix m = one.magic(4);
        assertNotNull(m);
        assertEquals(4, m.getRowDimension());
    }

    @Test
    public void oneMagicSinglyEvenOrder() {
        Matrix m = one.magic(6);
        assertNotNull(m);
        assertEquals(6, m.getRowDimension());
    }

    @Test
    public void oneFixedWidthDouble() {
        String s = one.fixedWidthDoubletoString(3.14, 8, 2);
        assertNotNull(s);
        assertTrue(s.length() >= 8);
        assertTrue(s.contains("3.14") || s.contains("3,14"));
    }

    @Test
    public void oneFixedWidthInteger() {
        String s = one.fixedWidthIntegertoString(42, 6);
        assertNotNull(s);
        assertTrue(s.length() >= 6);
        assertTrue(s.contains("42"));
    }
}
