// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

import graphtea.extensions.algorithms.DAG;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import org.junit.jupiter.api.Test;

import java.util.AbstractList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for DAG static utility methods:
 *   doSort, findLongestPath, getTraversableSubGraph, findACycle,
 *   findAllPaths, findAllAncestors.
 */
public class DAGTest {

    // -----------------------------------------------------------------------
    // Helpers
    // -----------------------------------------------------------------------

    /** Builds a directed chain v0→v1→v2→…→v(n-1). */
    private GraphModel chain(int n) {
        GraphModel g = new GraphModel(true);
        Vertex[] v = new Vertex[n];
        for (int i = 0; i < n; i++) { v[i] = new Vertex(); g.insertVertex(v[i]); }
        for (int i = 0; i < n - 1; i++) g.insertEdge(new Edge(v[i], v[i + 1]));
        return g;
    }

    /** Builds a directed 3-cycle v0→v1→v2→v0. */
    private GraphModel cycle3() {
        GraphModel g = new GraphModel(true);
        Vertex v0 = new Vertex(), v1 = new Vertex(), v2 = new Vertex();
        g.insertVertex(v0); g.insertVertex(v1); g.insertVertex(v2);
        g.insertEdge(new Edge(v0, v1));
        g.insertEdge(new Edge(v1, v2));
        g.insertEdge(new Edge(v2, v0));
        return g;
    }

    // -----------------------------------------------------------------------
    // doSort
    // -----------------------------------------------------------------------

    @Test
    public void testDoSortLinearChain() {
        // v0→v1→v2 — topological order must have v0 before v1 before v2
        GraphModel g = chain(3);
        AbstractList<Vertex> order = DAG.doSort(g);
        assertNotNull(order, "Chain is a DAG; sort must not return null");
        assertEquals(3, order.size());
        assertTrue(order.indexOf(g.getVertex(0)) < order.indexOf(g.getVertex(1)));
        assertTrue(order.indexOf(g.getVertex(1)) < order.indexOf(g.getVertex(2)));
    }

    @Test
    public void testDoSortSingleVertex() {
        GraphModel g = new GraphModel(true);
        Vertex v = new Vertex();
        g.insertVertex(v);
        AbstractList<Vertex> order = DAG.doSort(g);
        assertNotNull(order);
        assertEquals(1, order.size());
        assertEquals(v, order.get(0));
    }

    @Test
    public void testDoSortReturnsNullForCyclicGraph() {
        assertNull(DAG.doSort(cycle3()), "Cyclic graph must return null from doSort");
    }

    @Test
    public void testDoSortDiamondDAG() {
        // Diamond: v0→v1, v0→v2, v1→v3, v2→v3
        GraphModel g = new GraphModel(true);
        Vertex v0 = new Vertex(), v1 = new Vertex(), v2 = new Vertex(), v3 = new Vertex();
        g.insertVertex(v0); g.insertVertex(v1); g.insertVertex(v2); g.insertVertex(v3);
        g.insertEdge(new Edge(v0, v1));
        g.insertEdge(new Edge(v0, v2));
        g.insertEdge(new Edge(v1, v3));
        g.insertEdge(new Edge(v2, v3));
        AbstractList<Vertex> order = DAG.doSort(g);
        assertNotNull(order);
        assertEquals(4, order.size());
        assertTrue(order.indexOf(v0) < order.indexOf(v1));
        assertTrue(order.indexOf(v0) < order.indexOf(v2));
        assertTrue(order.indexOf(v1) < order.indexOf(v3));
        assertTrue(order.indexOf(v2) < order.indexOf(v3));
    }

    // -----------------------------------------------------------------------
    // findLongestPath
    // -----------------------------------------------------------------------

    @Test
    public void testFindLongestPathChain4() {
        // v0→v1→v2→v3: longest path from any vertex has length 3 (v3 has maxPath=3)
        GraphModel g = chain(4);
        AbstractList<?> result = DAG.findLongestPath(g);
        assertNotNull(result);
        assertEquals(4, result.size());
    }

    @Test
    public void testFindLongestPathReturnsNullForCycle() {
        assertNull(DAG.findLongestPath(cycle3()));
    }

    @Test
    public void testFindLongestPathSingleVertex() {
        GraphModel g = new GraphModel(true);
        Vertex v = new Vertex();
        g.insertVertex(v);
        AbstractList<?> result = DAG.findLongestPath(g);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    // -----------------------------------------------------------------------
    // getTraversableSubGraph
    // -----------------------------------------------------------------------

    @Test
    public void testGetTraversableSubGraphFromSource() {
        // chain v0→v1→v2: from v0 we can reach all 3
        GraphModel g = chain(3);
        Vertex root = g.getVertex(0);
        List<Vertex> reachable = DAG.getTraversableSubGraph(g, root);
        assertEquals(3, reachable.size());
    }

    @Test
    public void testGetTraversableSubGraphFromMiddle() {
        // chain v0→v1→v2: from v1 we reach v1 and v2
        GraphModel g = chain(3);
        Vertex mid = g.getVertex(1);
        List<Vertex> reachable = DAG.getTraversableSubGraph(g, mid);
        assertEquals(2, reachable.size());
        assertTrue(reachable.contains(mid));
        assertTrue(reachable.contains(g.getVertex(2)));
    }

    @Test
    public void testGetTraversableSubGraphFromLeaf() {
        // chain v0→v1→v2: from v2 (sink) only v2 is reachable
        GraphModel g = chain(3);
        Vertex leaf = g.getVertex(2);
        List<Vertex> reachable = DAG.getTraversableSubGraph(g, leaf);
        assertEquals(1, reachable.size());
        assertEquals(leaf, reachable.get(0));
    }

    // -----------------------------------------------------------------------
    // findACycle
    // -----------------------------------------------------------------------

    @Test
    public void testFindACycleOnCyclicGraph() {
        assertNotNull(DAG.findACycle(cycle3()), "Cycle must be detected");
    }

    @Test
    public void testFindACycleOnDAG() {
        assertNull(DAG.findACycle(chain(4)), "Chain DAG has no cycle");
    }

    @Test
    public void testFindACycleSingleVertex() {
        GraphModel g = new GraphModel(true);
        g.insertVertex(new Vertex());
        assertNull(DAG.findACycle(g));
    }

    // -----------------------------------------------------------------------
    // findAllPaths
    // -----------------------------------------------------------------------

    @Test
    public void testFindAllPathsLinearTwoRoutes() {
        // DAG: v0→v1→v3, v0→v2→v3  (two paths from v0 to v3)
        GraphModel g = new GraphModel(true);
        Vertex v0 = new Vertex(), v1 = new Vertex(), v2 = new Vertex(), v3 = new Vertex();
        g.insertVertex(v0); g.insertVertex(v1); g.insertVertex(v2); g.insertVertex(v3);
        g.insertEdge(new Edge(v0, v1));
        g.insertEdge(new Edge(v1, v3));
        g.insertEdge(new Edge(v0, v2));
        g.insertEdge(new Edge(v2, v3));
        List<?> paths = DAG.findAllPaths(g, v0, v3);
        assertEquals(2, paths.size(), "Must find exactly 2 paths from v0 to v3");
    }

    @Test
    public void testFindAllPathsSinglePath() {
        GraphModel g = chain(4);
        Vertex src = g.getVertex(0);
        Vertex trg = g.getVertex(3);
        List<?> paths = DAG.findAllPaths(g, src, trg);
        assertEquals(1, paths.size());
    }

    @Test
    public void testFindAllPathsSrcEqualsTarget() {
        GraphModel g = chain(3);
        Vertex v = g.getVertex(1);
        List<?> paths = DAG.findAllPaths(g, v, v);
        assertEquals(1, paths.size(), "Trivial path from v to itself");
    }

    // -----------------------------------------------------------------------
    // findAllAncestors
    // -----------------------------------------------------------------------

    @Test
    public void testFindAllAncestorsChain() {
        // chain v0→v1→v2: ancestors of v2 are [v1, v0]
        GraphModel g = chain(3);
        Vertex sink = g.getVertex(2);
        List<Vertex> anc = DAG.findAllAncestors(g, sink);
        assertEquals(2, anc.size(), "v2 has two ancestors: v1 and v0");
        assertTrue(anc.contains(g.getVertex(0)));
        assertTrue(anc.contains(g.getVertex(1)));
    }

    @Test
    public void testFindAllAncestorsSourceHasNone() {
        GraphModel g = chain(3);
        Vertex source = g.getVertex(0);
        List<Vertex> anc = DAG.findAllAncestors(g, source);
        assertTrue(anc.isEmpty(), "Source vertex has no ancestors");
    }
}
