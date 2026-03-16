import graphtea.extensions.G6Format;
import graphtea.extensions.generators.CircleGenerator;
import graphtea.extensions.generators.CompleteGraphGenerator;
import graphtea.extensions.generators.PathGenerator;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for G6Format encoding and decoding.
 */
public class G6FormatTest {

    // ---- stringToGraph (adjacency-list map) ----

    @Test
    public void testStringToGraphK1HasNoEdges() {
        HashMap<Integer, List<Integer>> adj = G6Format.stringToGraph("@?");
        assertEquals(0, adj.size());
    }

    @Test
    public void testStringToGraphK2HasOneEdge() {
        HashMap<Integer, List<Integer>> adj = G6Format.stringToGraph("A_");
        // vertex 0 should be adjacent to vertex 1
        assertEquals(1, adj.size());
        assertTrue(adj.containsKey(0));
        assertTrue(adj.get(0).contains(1));
    }

    @Test
    public void testStringToGraphK3HasAllEdges() {
        // "Bw" encodes K_3
        HashMap<Integer, List<Integer>> adj = G6Format.stringToGraph("Bw");
        // edges: (0,1), (0,2), (1,2)
        assertTrue(adj.containsKey(0));
        assertTrue(adj.get(0).contains(1));
        assertTrue(adj.get(0).contains(2));
        assertTrue(adj.containsKey(1));
        assertTrue(adj.get(1).contains(2));
    }

    // ---- stringToGraphModel ----

    @Test
    public void testStringToGraphModelK1() {
        GraphModel g = G6Format.stringToGraphModel("@?");
        assertEquals(1, g.getVerticesCount());
        assertEquals(0, g.getEdgesCount());
    }

    @Test
    public void testStringToGraphModelK2() {
        GraphModel g = G6Format.stringToGraphModel("A_");
        assertEquals(2, g.getVerticesCount());
        assertEquals(1, g.getEdgesCount());
    }

    @Test
    public void testStringToGraphModelK3() {
        GraphModel g = G6Format.stringToGraphModel("Bw");
        assertEquals(3, g.getVerticesCount());
        assertEquals(3, g.getEdgesCount());
    }

    // ---- round-trip: graphToG6 → stringToGraphModel ----

    @Test
    public void testRoundTripPath3() {
        GraphModel original = PathGenerator.generatePath(3);
        String encoded = G6Format.graphToG6(original);
        GraphModel decoded = G6Format.stringToGraphModel(encoded);
        assertEquals(original.getVerticesCount(), decoded.getVerticesCount());
        assertEquals(original.getEdgesCount(), decoded.getEdgesCount());
    }

    @Test
    public void testRoundTripPath5() {
        GraphModel original = PathGenerator.generatePath(5);
        String encoded = G6Format.graphToG6(original);
        GraphModel decoded = G6Format.stringToGraphModel(encoded);
        assertEquals(original.getVerticesCount(), decoded.getVerticesCount());
        assertEquals(original.getEdgesCount(), decoded.getEdgesCount());
    }

    @Test
    public void testRoundTripCircle5() {
        GraphModel original = CircleGenerator.generateCircle(5);
        String encoded = G6Format.graphToG6(original);
        GraphModel decoded = G6Format.stringToGraphModel(encoded);
        assertEquals(original.getVerticesCount(), decoded.getVerticesCount());
        assertEquals(original.getEdgesCount(), decoded.getEdgesCount());
    }

    @Test
    public void testRoundTripCircle6() {
        GraphModel original = CircleGenerator.generateCircle(6);
        String encoded = G6Format.graphToG6(original);
        GraphModel decoded = G6Format.stringToGraphModel(encoded);
        assertEquals(original.getVerticesCount(), decoded.getVerticesCount());
        assertEquals(original.getEdgesCount(), decoded.getEdgesCount());
    }

    @Test
    public void testRoundTripComplete4() {
        GraphModel original = CompleteGraphGenerator.generateCompleteGraph(4);
        String encoded = G6Format.graphToG6(original);
        GraphModel decoded = G6Format.stringToGraphModel(encoded);
        assertEquals(original.getVerticesCount(), decoded.getVerticesCount());
        assertEquals(original.getEdgesCount(), decoded.getEdgesCount());
    }

    @Test
    public void testRoundTripComplete5() {
        GraphModel original = CompleteGraphGenerator.generateCompleteGraph(5);
        String encoded = G6Format.graphToG6(original);
        GraphModel decoded = G6Format.stringToGraphModel(encoded);
        assertEquals(original.getVerticesCount(), decoded.getVerticesCount());
        assertEquals(original.getEdgesCount(), decoded.getEdgesCount());
    }

    @Test
    public void testRoundTripAdjacencyPreserved() {
        // Path P_4: v0-v1-v2-v3; check specific adjacency after round-trip
        GraphModel original = PathGenerator.generatePath(4);
        String encoded = G6Format.graphToG6(original);
        GraphModel decoded = G6Format.stringToGraphModel(encoded);

        // In P_4 the endpoints (v0 and v3) should NOT be adjacent
        Vertex v0 = decoded.getVertex(0);
        Vertex v3 = decoded.getVertex(3);
        assertFalse(decoded.isEdge(v0, v3));

        // Adjacent vertices should be connected
        Vertex v1 = decoded.getVertex(1);
        assertTrue(decoded.isEdge(v0, v1));
    }

    @Test
    public void testRoundTripLargerGraph() {
        GraphModel original = CircleGenerator.generateCircle(10);
        String encoded = G6Format.graphToG6(original);
        GraphModel decoded = G6Format.stringToGraphModel(encoded);
        assertEquals(10, decoded.getVerticesCount());
        assertEquals(10, decoded.getEdgesCount());
    }

    // ---- encodeGraph / createAdjMatrix ----

    @Test
    public void testCreateAdjMatrixMatchesExpected() {
        // For a single-edge graph (K_2), the adjacency matrix row-by-column lower
        // triangle contains exactly one '1'
        GraphModel k2 = CompleteGraphGenerator.generateCompleteGraph(2);
        String adjStr = G6Format.createAdjMatrix(k2.getAdjacencyMatrix());
        // The only entry is m.get(0,1), which is 1 for K_2
        assertEquals("1", adjStr.trim().replace("0", ""));
    }

    @Test
    public void testEncodeDecodeVertexCount() {
        for (int n = 1; n <= 15; n++) {
            GraphModel g = PathGenerator.generatePath(n);
            String g6 = G6Format.graphToG6(g);
            GraphModel decoded = G6Format.stringToGraphModel(g6);
            assertEquals(n, decoded.getVerticesCount(),
                    "vertex count mismatch for n=" + n);
        }
    }
}
