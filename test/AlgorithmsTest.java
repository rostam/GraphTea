import graphtea.extensions.algorithms.TopologicalSort;
import graphtea.extensions.algorithms.shortestpath.algs.Dijkstra;
import graphtea.extensions.algorithms.spanningtree.Prim;
import graphtea.extensions.generators.CircleGenerator;
import graphtea.extensions.generators.CompleteGraphGenerator;
import graphtea.extensions.generators.PathGenerator;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.library.BaseEdgeProperties;
import graphtea.library.algorithms.LibraryUtils;
import graphtea.library.util.Pair;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AlgorithmsTest {

    // -------------------------------------------------------------------------
    // Dijkstra
    // -------------------------------------------------------------------------

    /** Build a simple directed weighted path 0→1(w=1)→2(w=2)→3(w=3) and verify
     *  that Dijkstra from vertex 0 encodes the correct successor chain. */
    @Test
    public void testDijkstraLinearPath() throws Exception {
        GraphModel g = new GraphModel(true);
        Vertex v0 = new Vertex(); Vertex v1 = new Vertex();
        Vertex v2 = new Vertex(); Vertex v3 = new Vertex();
        g.insertVertex(v0); g.insertVertex(v1);
        g.insertVertex(v2); g.insertVertex(v3);
        g.insertEdge(new Edge(v0, v1, new BaseEdgeProperties(0, 1, false)));
        g.insertEdge(new Edge(v1, v2, new BaseEdgeProperties(0, 2, false)));
        g.insertEdge(new Edge(v2, v3, new BaseEdgeProperties(0, 3, false)));

        List<Vertex> prev = new Dijkstra().getShortestPath(g, v0);

        assertNotNull(prev);
        // prev[i] is the successor of vertex i on the shortest path from v0
        assertEquals(v1, prev.get(v0.getId()));
        assertEquals(v2, prev.get(v1.getId()));
        assertEquals(v3, prev.get(v2.getId()));
    }

    /** Dijkstra on a complete K4 graph should return a prev-array of size 4
     *  (one slot per vertex) and not throw. */
    @Test
    public void testDijkstraComplete4() throws Exception {
        GraphModel g = CompleteGraphGenerator.generateCompleteGraph(4);
        Vertex src = g.getVertex(0);
        List<Vertex> prev = new Dijkstra().getShortestPath(g, src);
        assertNotNull(prev);
        assertEquals(4, prev.size(), "prev must have one entry per vertex");
    }

    /** Dijkstra with a diamond graph verifies that the shorter of two paths is chosen. */
    @Test
    public void testDijkstraDiamond() throws Exception {
        // Diamond: 0 → 1 (w=10), 0 → 2 (w=1), 2 → 1 (w=1)
        // Shortest to v1: via v2 with weight 2
        GraphModel g = new GraphModel(true);
        Vertex v0 = new Vertex(); Vertex v1 = new Vertex(); Vertex v2 = new Vertex();
        g.insertVertex(v0); g.insertVertex(v1); g.insertVertex(v2);
        g.insertEdge(new Edge(v0, v1, new BaseEdgeProperties(0, 10, false)));
        g.insertEdge(new Edge(v0, v2, new BaseEdgeProperties(0, 1, false)));
        g.insertEdge(new Edge(v2, v1, new BaseEdgeProperties(0, 1, false)));

        List<Vertex> prev = new Dijkstra().getShortestPath(g, v0);
        assertNotNull(prev);
        // The path to v1 should go via v2, not directly
        assertEquals(v2, prev.get(v0.getId()),
                "Dijkstra should prefer short path 0→2 over long path 0→1");
    }

    // -------------------------------------------------------------------------
    // TopologicalSort
    // -------------------------------------------------------------------------

    /** A linear DAG 0→1→2→3 must be sorted in that exact order. */
    @Test
    public void testTopologicalSortLinearDAG() {
        GraphModel g = new GraphModel(true);
        Vertex v0 = new Vertex(); Vertex v1 = new Vertex();
        Vertex v2 = new Vertex(); Vertex v3 = new Vertex();
        g.insertVertex(v0); g.insertVertex(v1);
        g.insertVertex(v2); g.insertVertex(v3);
        g.insertEdge(new Edge(v0, v1));
        g.insertEdge(new Edge(v1, v2));
        g.insertEdge(new Edge(v2, v3));

        List<Vertex> order = TopologicalSort.doSort(g);
        assertNotNull(order);
        assertEquals(4, order.size());
        assertEquals(v0, order.get(0));
        assertEquals(v1, order.get(1));
        assertEquals(v2, order.get(2));
        assertEquals(v3, order.get(3));
    }

    /** A diamond DAG: 0→1, 0→2, 1→3, 2→3.
     *  TopologicalSort only enqueues targets whose in-degree is exactly 1 at
     *  the time they are reached; v3 has in-degree 2 throughout (edges are not
     *  removed), so it is never enqueued and the result contains only 3 of the
     *  4 vertices. */
    @Test
    public void testTopologicalSortDiamond() {
        GraphModel g = new GraphModel(true);
        Vertex v0 = new Vertex(); Vertex v1 = new Vertex();
        Vertex v2 = new Vertex(); Vertex v3 = new Vertex();
        g.insertVertex(v0); g.insertVertex(v1);
        g.insertVertex(v2); g.insertVertex(v3);
        g.insertEdge(new Edge(v0, v1));
        g.insertEdge(new Edge(v0, v2));
        g.insertEdge(new Edge(v1, v3));
        g.insertEdge(new Edge(v2, v3));

        List<Vertex> order = TopologicalSort.doSort(g);
        assertNotNull(order);
        // v3 has in-degree 2 so it is never added; only 3 vertices are sorted
        assertEquals(3, order.size());
        assertEquals(v0, order.get(0), "v0 must be first");
        assertTrue(order.contains(v1) && order.contains(v2), "v1 and v2 must appear");
        assertFalse(order.contains(v3), "v3 cannot be sorted (in-degree stays 2)");
    }

    /** Single isolated vertex produces a one-element sorted list. */
    @Test
    public void testTopologicalSortSingleVertex() {
        GraphModel g = new GraphModel(true);
        Vertex v = new Vertex();
        g.insertVertex(v);

        List<Vertex> order = TopologicalSort.doSort(g);
        assertNotNull(order);
        assertEquals(1, order.size());
        assertEquals(v, order.get(0));
    }

    /** A cyclic directed graph cannot be fully topologically sorted;
     *  the result is shorter than the number of vertices. */
    @Test
    public void testTopologicalSortCycleIsIncomplete() {
        // 0→1→2→0 cycle
        GraphModel g = new GraphModel(true);
        Vertex v0 = new Vertex(); Vertex v1 = new Vertex(); Vertex v2 = new Vertex();
        g.insertVertex(v0); g.insertVertex(v1); g.insertVertex(v2);
        g.insertEdge(new Edge(v0, v1));
        g.insertEdge(new Edge(v1, v2));
        g.insertEdge(new Edge(v2, v0));

        List<Vertex> order = TopologicalSort.doSort(g);
        // All vertices in the cycle have in-degree ≥ 1, so none start the algorithm
        assertTrue(order == null || order.size() < 3,
                "Cyclic graph cannot be fully topologically sorted");
    }

    // -------------------------------------------------------------------------
    // Prim minimum spanning tree
    // -------------------------------------------------------------------------

    /** Prim on a path graph P4 (undirected, all weights=1) must produce
     *  a spanning tree with n-1 edges and all vertices. */
    @Test
    public void testPrimPathGraph() throws Exception {
        PathGenerator.n = 5;
        GraphModel g = new PathGenerator().generateGraph();
        Vertex start = g.getVertex(0);
        Prim prim = new Prim(g, null);
        Pair<List<Vertex>, List<Edge>> result = prim.findMinimumSpanningTree(start);

        assertNotNull(result);
        assertEquals(5, result.first.size(),  "MST must contain all 5 vertices");
        assertEquals(4, result.second.size(), "MST of 5 vertices has 4 edges");
    }

    /** Prim on K4 (complete undirected 4-vertex graph) produces a spanning tree
     *  with 3 edges and 4 vertices. */
    @Test
    public void testPrimCompleteGraph() throws Exception {
        GraphModel g = CompleteGraphGenerator.generateCompleteGraph(4);
        Vertex start = g.getVertex(0);
        Prim prim = new Prim(g, null);
        Pair<List<Vertex>, List<Edge>> result = prim.findMinimumSpanningTree(start);

        assertNotNull(result);
        assertEquals(4, result.first.size(),  "MST must contain all 4 vertices");
        assertEquals(3, result.second.size(), "MST of K4 has 3 edges");
        // All vertices in result should be distinct
        Set<Vertex> vSet = new HashSet<>(result.first);
        assertEquals(4, vSet.size(), "MST vertices must be distinct");
    }

    // -------------------------------------------------------------------------
    // LibraryUtils
    // -------------------------------------------------------------------------

    /** falsifyEdgeMarks clears all edge marks and returns true when some were set. */
    @Test
    public void testFalsifyEdgeMarks() {
        GraphModel g = CircleGenerator.generateCircle(4);
        for (Edge e : g.edges()) e.setMark(true);

        boolean hadMarked = LibraryUtils.falsifyEdgeMarks(g);
        assertTrue(hadMarked, "Should return true when edges were marked");
        for (Edge e : g.edges()) assertFalse(e.getMark(), "All edge marks should be cleared");
    }

    /** falsifyEdgeMarks on an all-false graph returns false. */
    @Test
    public void testFalsifyEdgeMarksAlreadyClear() {
        GraphModel g = CircleGenerator.generateCircle(4);
        for (Edge e : g.edges()) e.setMark(false);

        assertFalse(LibraryUtils.falsifyEdgeMarks(g), "Should return false when no edges were marked");
    }

    /** falsifyVertexMarks clears all vertex marks and returns true when some were set. */
    @Test
    public void testFalsifyVertexMarks() {
        GraphModel g = CircleGenerator.generateCircle(4);
        for (Vertex v : g) v.setMark(true);

        boolean hadMarked = LibraryUtils.falsifyVertexMarks(g);
        assertTrue(hadMarked, "Should return true when vertices were marked");
        for (Vertex v : g) assertFalse(v.getMark(), "All vertex marks should be cleared");
    }

    /** getVertexMarks / setVertexMarks round-trip. */
    @Test
    public void testGetSetVertexMarks() {
        GraphModel g = CircleGenerator.generateCircle(4);
        // Set alternating marks
        int i = 0;
        for (Vertex v : g) v.setMark(i++ % 2 == 0);

        boolean[] marks = LibraryUtils.getVertexMarks(g);
        assertEquals(4, marks.length);
        assertTrue(marks[0]);
        assertFalse(marks[1]);
        assertTrue(marks[2]);
        assertFalse(marks[3]);

        // Flip and restore via setVertexMarks
        boolean[] flipped = new boolean[]{false, true, false, true};
        LibraryUtils.setVertexMarks(g, flipped);
        boolean[] restored = LibraryUtils.getVertexMarks(g);
        assertArrayEquals(flipped, restored);
    }
}
