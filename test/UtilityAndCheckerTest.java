import graphtea.extensions.algorithms.GOpUtils;
import graphtea.extensions.generators.CircleGenerator;
import graphtea.extensions.generators.CompleteGraphGenerator;
import graphtea.extensions.generators.PathGenerator;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.library.algorithms.util.AcyclicChecker;
import graphtea.library.algorithms.util.BipartiteChecker;
import graphtea.library.algorithms.util.ConnectivityChecker;
import graphtea.library.util.Pair;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for utility classes and graph-property checkers that previously had no
 * dedicated test coverage:
 *   - AcyclicChecker
 *   - BipartiteChecker
 *   - ConnectivityChecker
 *   - GOpUtils
 *   - Pair
 */
public class UtilityAndCheckerTest {

    // =========================================================================
    // AcyclicChecker
    // =========================================================================

    /** A single isolated vertex has no edges, so it is a tree (acyclic). */
    @Test
    public void testAcyclicCheckerSingleVertex() {
        GraphModel g = new GraphModel(false);
        g.insertVertex(new Vertex());
        assertTrue(AcyclicChecker.isGraphAcyclic(g));
    }

    /** A path graph is acyclic (it is a tree). */
    @Test
    public void testAcyclicCheckerPathIsAcyclic() {
        GraphModel g = PathGenerator.generatePath(5);
        assertTrue(AcyclicChecker.isGraphAcyclic(g));
    }

    /** A cycle graph C_n (n ≥ 3) contains a cycle, so it is not acyclic. */
    @Test
    public void testAcyclicCheckerCycleIsNotAcyclic() {
        GraphModel g = CircleGenerator.generateCircle(4);
        assertFalse(AcyclicChecker.isGraphAcyclic(g));
    }

    /** A complete graph K_4 has many cycles and is not acyclic. */
    @Test
    public void testAcyclicCheckerCompleteK4IsNotAcyclic() {
        GraphModel g = CompleteGraphGenerator.generateCompleteGraph(4);
        assertFalse(AcyclicChecker.isGraphAcyclic(g));
    }

    /** Two isolated vertices (no edges) form a forest — acyclic. */
    @Test
    public void testAcyclicCheckerTwoIsolatedVerticesIsAcyclic() {
        GraphModel g = new GraphModel(false);
        g.insertVertex(new Vertex());
        g.insertVertex(new Vertex());
        assertTrue(AcyclicChecker.isGraphAcyclic(g));
    }

    /** A single edge (K_2) is a tree — acyclic. */
    @Test
    public void testAcyclicCheckerSingleEdgeIsAcyclic() {
        GraphModel g = PathGenerator.generatePath(2);
        assertTrue(AcyclicChecker.isGraphAcyclic(g));
    }

    /** A triangle (C_3) is not acyclic. */
    @Test
    public void testAcyclicCheckerTriangleIsNotAcyclic() {
        GraphModel g = CircleGenerator.generateCircle(3);
        assertFalse(AcyclicChecker.isGraphAcyclic(g));
    }

    // =========================================================================
    // BipartiteChecker
    // =========================================================================

    /** A path graph is always bipartite (its two colour-classes alternate). */
    @Test
    public void testBipartiteCheckerPathIsBipartite() {
        GraphModel g = PathGenerator.generatePath(5);
        assertTrue(BipartiteChecker.isBipartite(g));
    }

    /** An even cycle C_4 is bipartite. */
    @Test
    public void testBipartiteCheckerEvenCycleIsBipartite() {
        GraphModel g = CircleGenerator.generateCircle(4);
        assertTrue(BipartiteChecker.isBipartite(g));
    }

    /** A larger even cycle C_6 is bipartite. */
    @Test
    public void testBipartiteCheckerC6IsBipartite() {
        GraphModel g = CircleGenerator.generateCircle(6);
        assertTrue(BipartiteChecker.isBipartite(g));
    }

    /** An odd cycle C_3 (triangle) is NOT bipartite. */
    @Test
    public void testBipartiteCheckerOddCycleC3IsNotBipartite() {
        GraphModel g = CircleGenerator.generateCircle(3);
        assertFalse(BipartiteChecker.isBipartite(g));
    }

    /** An odd cycle C_5 is NOT bipartite. */
    @Test
    public void testBipartiteCheckerOddCycleC5IsNotBipartite() {
        GraphModel g = CircleGenerator.generateCircle(5);
        assertFalse(BipartiteChecker.isBipartite(g));
    }

    /** K_4 (complete on 4 vertices) contains odd cycles, so it is NOT bipartite. */
    @Test
    public void testBipartiteCheckerCompleteK4IsNotBipartite() {
        GraphModel g = CompleteGraphGenerator.generateCompleteGraph(4);
        assertFalse(BipartiteChecker.isBipartite(g));
    }

    /** K_2 (single edge) is bipartite — the two endpoints form the two parts. */
    @Test
    public void testBipartiteCheckerK2IsBipartite() {
        GraphModel g = CompleteGraphGenerator.generateCompleteGraph(2);
        assertTrue(BipartiteChecker.isBipartite(g));
    }

    /** A single vertex with no edges is trivially bipartite. */
    @Test
    public void testBipartiteCheckerSingleVertexIsBipartite() {
        GraphModel g = new GraphModel(false);
        g.insertVertex(new Vertex());
        assertTrue(BipartiteChecker.isBipartite(g));
    }

    // =========================================================================
    // ConnectivityChecker
    // =========================================================================

    /**
     * An empty graph (no vertices) is vacuously connected: the implementation
     * iterates over vertices and immediately returns true when there are none,
     * consistent with the convention that the empty graph satisfies all
     * universal (for-all) properties.
     */
    @Test
    public void testConnectivityCheckerEmptyGraphIsConnected() throws Exception {
        GraphModel g = new GraphModel(false);
        assertTrue(ConnectivityChecker.isGraphConnected(g));
    }

    /** A single vertex is connected. */
    @Test
    public void testConnectivityCheckerSingleVertexIsConnected() throws Exception {
        GraphModel g = new GraphModel(false);
        g.insertVertex(new Vertex());
        assertTrue(ConnectivityChecker.isGraphConnected(g));
    }

    /** A path graph P_5 is connected. */
    @Test
    public void testConnectivityCheckerPathIsConnected() throws Exception {
        GraphModel g = PathGenerator.generatePath(5);
        assertTrue(ConnectivityChecker.isGraphConnected(g));
    }

    /** A cycle C_4 is connected. */
    @Test
    public void testConnectivityCheckerCycleIsConnected() throws Exception {
        GraphModel g = CircleGenerator.generateCircle(4);
        assertTrue(ConnectivityChecker.isGraphConnected(g));
    }

    /** K_4 is connected. */
    @Test
    public void testConnectivityCheckerCompleteK4IsConnected() throws Exception {
        GraphModel g = CompleteGraphGenerator.generateCompleteGraph(4);
        assertTrue(ConnectivityChecker.isGraphConnected(g));
    }

    /** Two isolated vertices (no edges) form a disconnected graph. */
    @Test
    public void testConnectivityCheckerTwoIsolatedVerticesIsDisconnected() throws Exception {
        GraphModel g = new GraphModel(false);
        g.insertVertex(new Vertex());
        g.insertVertex(new Vertex());
        assertFalse(ConnectivityChecker.isGraphConnected(g));
    }

    /** A graph with two components (two separate edges) is not connected. */
    @Test
    public void testConnectivityCheckerTwoSeparateEdgesIsDisconnected() throws Exception {
        GraphModel g = new GraphModel(false);
        Vertex v0 = new Vertex();
        Vertex v1 = new Vertex();
        Vertex v2 = new Vertex();
        Vertex v3 = new Vertex();
        g.insertVertex(v0);
        g.insertVertex(v1);
        g.insertVertex(v2);
        g.insertVertex(v3);
        g.insertEdge(new Edge(v0, v1));
        g.insertEdge(new Edge(v2, v3));
        assertFalse(ConnectivityChecker.isGraphConnected(g));
    }

    // =========================================================================
    // GOpUtils
    // =========================================================================

    /** center() of a single vertex at (3, 4) should be (3, 4). */
    @Test
    public void testGOpUtilsCenterSingleVertex() {
        GraphModel g = new GraphModel(false);
        Vertex v = new Vertex();
        v.setLocation(new GPoint(3.0, 4.0));
        g.insertVertex(v);

        GPoint c = GOpUtils.center(g);
        assertEquals(3.0, c.x, 1e-9);
        assertEquals(4.0, c.y, 1e-9);
    }

    /** center() of two vertices at (0,0) and (4,6) should be (2, 3). */
    @Test
    public void testGOpUtilsCenterTwoVertices() {
        GraphModel g = new GraphModel(false);
        Vertex v1 = new Vertex(); v1.setLocation(new GPoint(0.0, 0.0));
        Vertex v2 = new Vertex(); v2.setLocation(new GPoint(4.0, 6.0));
        g.insertVertex(v1); g.insertVertex(v2);

        GPoint c = GOpUtils.center(g);
        assertEquals(2.0, c.x, 1e-9);
        assertEquals(3.0, c.y, 1e-9);
    }

    /** center() of a symmetric arrangement should lie at the origin. */
    @Test
    public void testGOpUtilsCenterSymmetricArrangement() {
        GraphModel g = new GraphModel(false);
        Vertex v1 = new Vertex(); v1.setLocation(new GPoint(-1.0, 0.0));
        Vertex v2 = new Vertex(); v2.setLocation(new GPoint(1.0, 0.0));
        Vertex v3 = new Vertex(); v3.setLocation(new GPoint(0.0, 2.0));
        Vertex v4 = new Vertex(); v4.setLocation(new GPoint(0.0, -2.0));
        g.insertVertex(v1); g.insertVertex(v2);
        g.insertVertex(v3); g.insertVertex(v4);

        GPoint c = GOpUtils.center(g);
        assertEquals(0.0, c.x, 1e-9);
        assertEquals(0.0, c.y, 1e-9);
    }

    /** offsetPositionsToCenter() returns a map with one entry per vertex. */
    @Test
    public void testGOpUtilsOffsetPositionsToCenterEntryCount() {
        GraphModel g = new GraphModel(false);
        Vertex v1 = new Vertex(); v1.setLocation(new GPoint(1.0, 1.0));
        Vertex v2 = new Vertex(); v2.setLocation(new GPoint(3.0, 3.0));
        g.insertVertex(v1); g.insertVertex(v2);

        HashMap<Integer, GPoint> offsets = GOpUtils.offsetPositionsToCenter(g);
        assertEquals(2, offsets.size());
    }

    /** offsetPositionsToCenter() offsets sum to zero for a symmetric graph. */
    @Test
    public void testGOpUtilsOffsetPositionsToCenterSymmetric() {
        GraphModel g = new GraphModel(false);
        Vertex v1 = new Vertex(); v1.setLocation(new GPoint(-1.0, 0.0));
        Vertex v2 = new Vertex(); v2.setLocation(new GPoint(1.0, 0.0));
        g.insertVertex(v1); g.insertVertex(v2);

        HashMap<Integer, GPoint> offsets = GOpUtils.offsetPositionsToCenter(g);
        double sumX = offsets.values().stream().mapToDouble(p -> p.x).sum();
        double sumY = offsets.values().stream().mapToDouble(p -> p.y).sum();
        assertEquals(0.0, sumX, 1e-9);
        assertEquals(0.0, sumY, 1e-9);
    }

    /** midPoint() of an edge between (0,0) and (4,6) should be (2, 3). */
    @Test
    public void testGOpUtilsMidPoint() {
        GraphModel g = new GraphModel(false);
        Vertex v1 = new Vertex(); v1.setLocation(new GPoint(0.0, 0.0));
        Vertex v2 = new Vertex(); v2.setLocation(new GPoint(4.0, 6.0));
        g.insertVertex(v1); g.insertVertex(v2);
        Edge e = new Edge(v1, v2);
        g.insertEdge(e);

        GPoint mid = GOpUtils.midPoint(e);
        assertEquals(2.0, mid.x, 1e-9);
        assertEquals(3.0, mid.y, 1e-9);
    }

    /** midPoint() of an edge between two identical points should equal that point. */
    @Test
    public void testGOpUtilsMidPointSameLocation() {
        GraphModel g = new GraphModel(false);
        Vertex v1 = new Vertex(); v1.setLocation(new GPoint(5.0, 7.0));
        Vertex v2 = new Vertex(); v2.setLocation(new GPoint(5.0, 7.0));
        g.insertVertex(v1); g.insertVertex(v2);
        Edge e = new Edge(v1, v2);
        g.insertEdge(e);

        GPoint mid = GOpUtils.midPoint(e);
        assertEquals(5.0, mid.x, 1e-9);
        assertEquals(7.0, mid.y, 1e-9);
    }

    // =========================================================================
    // Pair
    // =========================================================================

    /** Pair stores its two values and exposes them via the public fields. */
    @Test
    public void testPairHoldsValues() {
        Pair<String, Integer> p = new Pair<>("hello", 42);
        assertEquals("hello", p.first);
        assertEquals(42, p.second);
    }

    /** Pair allows null values. */
    @Test
    public void testPairAllowsNull() {
        Pair<String, String> p = new Pair<>(null, null);
        assertNull(p.first);
        assertNull(p.second);
    }

    /** Pair fields are mutable (public). */
    @Test
    public void testPairFieldsAreMutable() {
        Pair<Integer, Integer> p = new Pair<>(1, 2);
        p.first = 10;
        p.second = 20;
        assertEquals(10, p.first);
        assertEquals(20, p.second);
    }

    /** Pair works with different generic type combinations. */
    @Test
    public void testPairGenericTypes() {
        Pair<Double, Boolean> p = new Pair<>(3.14, true);
        assertEquals(3.14, p.first, 1e-15);
        assertTrue(p.second);
    }
}
