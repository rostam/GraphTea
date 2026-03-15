import graphtea.extensions.generators.CircleGenerator;
import graphtea.extensions.generators.CompleteGraphGenerator;
import graphtea.extensions.generators.PathGenerator;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.library.algorithms.util.AcyclicChecker;
import graphtea.library.algorithms.util.BipartiteChecker;
import graphtea.library.algorithms.util.ConnectivityChecker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for AcyclicChecker, ConnectivityChecker, and BipartiteChecker.
 */
public class GraphUtilsTest {

    // ---- helpers ----

    /** Build an undirected path n1-n2-...-nk manually (k vertices, k-1 edges). */
    private GraphModel buildPath(int k) {
        return PathGenerator.generatePath(k);
    }

    /** Build a disconnected graph: two isolated vertices with no edges. */
    private GraphModel buildDisconnected() {
        GraphModel g = new GraphModel(false);
        g.insertVertex(new Vertex());
        g.insertVertex(new Vertex());
        return g;
    }

    // ==== AcyclicChecker ====

    @Test
    public void testPathIsAcyclic() {
        assertTrue(AcyclicChecker.isGraphAcyclic(buildPath(5)));
    }

    @Test
    public void testSingleVertexIsAcyclic() {
        GraphModel g = new GraphModel(false);
        g.insertVertex(new Vertex());
        assertTrue(AcyclicChecker.isGraphAcyclic(g));
    }

    @Test
    public void testTriangleIsNotAcyclic() {
        assertFalse(AcyclicChecker.isGraphAcyclic(CircleGenerator.generateCircle(3)));
    }

    @Test
    public void testCycleIsNotAcyclic() {
        assertFalse(AcyclicChecker.isGraphAcyclic(CircleGenerator.generateCircle(6)));
    }

    @Test
    public void testCompleteGraphK4IsNotAcyclic() {
        assertFalse(AcyclicChecker.isGraphAcyclic(CompleteGraphGenerator.generateCompleteGraph(4)));
    }

    @Test
    public void testSingleEdgeIsAcyclic() {
        GraphModel g = new GraphModel(false);
        Vertex v1 = new Vertex(), v2 = new Vertex();
        g.insertVertex(v1);
        g.insertVertex(v2);
        g.insertEdge(new Edge(v1, v2));
        assertTrue(AcyclicChecker.isGraphAcyclic(g));
    }

    // ==== ConnectivityChecker ====

    @Test
    public void testCircleIsConnected() throws Exception {
        assertTrue(ConnectivityChecker.isGraphConnected(CircleGenerator.generateCircle(5)));
    }

    @Test
    public void testCompleteGraphIsConnected() throws Exception {
        assertTrue(ConnectivityChecker.isGraphConnected(CompleteGraphGenerator.generateCompleteGraph(4)));
    }

    @Test
    public void testPathIsConnected() throws Exception {
        assertTrue(ConnectivityChecker.isGraphConnected(buildPath(4)));
    }

    @Test
    public void testIsolatedVerticesAreDisconnected() throws Exception {
        assertFalse(ConnectivityChecker.isGraphConnected(buildDisconnected()));
    }

    @Test
    public void testEmptyGraphIsConnected() throws Exception {
        assertTrue(ConnectivityChecker.isGraphConnected(new GraphModel(false)));
    }

    @Test
    public void testSingleVertexIsConnected() throws Exception {
        GraphModel g = new GraphModel(false);
        g.insertVertex(new Vertex());
        assertTrue(ConnectivityChecker.isGraphConnected(g));
    }

    @Test
    public void testDisconnectedAfterEdgeRemoval() throws Exception {
        GraphModel g = new GraphModel(false);
        Vertex v1 = new Vertex(), v2 = new Vertex(), v3 = new Vertex();
        g.insertVertex(v1);
        g.insertVertex(v2);
        g.insertVertex(v3);
        Edge e12 = new Edge(v1, v2);
        Edge e23 = new Edge(v2, v3);
        g.insertEdge(e12);
        g.insertEdge(e23);
        assertTrue(ConnectivityChecker.isGraphConnected(g));
        g.removeEdge(e12);
        g.removeEdge(e23);
        assertFalse(ConnectivityChecker.isGraphConnected(g));
    }

    // ==== BipartiteChecker ====

    @Test
    public void testEvenCycleIsBipartite() {
        assertTrue(BipartiteChecker.isBipartite(CircleGenerator.generateCircle(4)));
        assertTrue(BipartiteChecker.isBipartite(CircleGenerator.generateCircle(6)));
    }

    @Test
    public void testOddCycleIsNotBipartite() {
        assertFalse(BipartiteChecker.isBipartite(CircleGenerator.generateCircle(3)));
        assertFalse(BipartiteChecker.isBipartite(CircleGenerator.generateCircle(5)));
    }

    @Test
    public void testCompleteK4IsNotBipartite() {
        // K4 contains odd cycles
        assertFalse(BipartiteChecker.isBipartite(CompleteGraphGenerator.generateCompleteGraph(4)));
    }

    @Test
    public void testPathIsBipartite() {
        assertTrue(BipartiteChecker.isBipartite(buildPath(5)));
    }

    @Test
    public void testSingleEdgeIsBipartite() {
        GraphModel g = new GraphModel(false);
        Vertex v1 = new Vertex(), v2 = new Vertex();
        g.insertVertex(v1);
        g.insertVertex(v2);
        g.insertEdge(new Edge(v1, v2));
        assertTrue(BipartiteChecker.isBipartite(g));
    }

    @Test
    public void testIsolatedVerticesAreBipartite() {
        // No edges → trivially bipartite
        GraphModel g = new GraphModel(false);
        g.insertVertex(new Vertex());
        g.insertVertex(new Vertex());
        assertTrue(BipartiteChecker.isBipartite(g));
    }
}
