import graphtea.extensions.algorithms.GraphComplement;
import graphtea.extensions.generators.CircleGenerator;
import graphtea.extensions.generators.CompleteGraphGenerator;
import graphtea.extensions.generators.PathGenerator;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for GraphComplement.complement().
 */
public class GraphComplementTest {

    // ---- vertex count preservation ----

    @Test
    public void testComplementPreservesVertexCountPath3() {
        GraphModel g = PathGenerator.generatePath(3);
        GraphModel c = GraphComplement.complement(g);
        assertEquals(g.getVerticesCount(), c.getVerticesCount());
    }

    @Test
    public void testComplementPreservesVertexCountCircle5() {
        GraphModel g = CircleGenerator.generateCircle(5);
        GraphModel c = GraphComplement.complement(g);
        assertEquals(g.getVerticesCount(), c.getVerticesCount());
    }

    @Test
    public void testComplementPreservesVertexCountComplete4() {
        GraphModel g = CompleteGraphGenerator.generateCompleteGraph(4);
        GraphModel c = GraphComplement.complement(g);
        assertEquals(g.getVerticesCount(), c.getVerticesCount());
    }

    // ---- complement of K_n has no edges between distinct vertices ----

    @Test
    public void testComplementOfK3HasNoInterVertexEdges() {
        GraphModel k3 = CompleteGraphGenerator.generateCompleteGraph(3);
        GraphModel comp = GraphComplement.complement(k3);
        // All distinct pairs that were adjacent in K_3 must be non-adjacent in its complement
        List<Vertex> vertices = new ArrayList<>();
        for (Vertex v : comp) vertices.add(v);
        assertEquals(3, vertices.size());
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = i + 1; j < vertices.size(); j++) {
                assertFalse(comp.isEdge(vertices.get(i), vertices.get(j)),
                        "Distinct vertices should not be adjacent in complement of K_3");
            }
        }
    }

    @Test
    public void testComplementOfK4HasNoInterVertexEdges() {
        GraphModel k4 = CompleteGraphGenerator.generateCompleteGraph(4);
        GraphModel comp = GraphComplement.complement(k4);
        List<Vertex> vertices = new ArrayList<>();
        for (Vertex v : comp) vertices.add(v);
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = i + 1; j < vertices.size(); j++) {
                assertFalse(comp.isEdge(vertices.get(i), vertices.get(j)),
                        "Distinct vertices should not be adjacent in complement of K_4");
            }
        }
    }

    // ---- specific edge presence/absence in complement of path ----

    @Test
    public void testComplementOfPath3NonAdjacentPairBecomesAdjacent() {
        // P_3: v0-v1-v2. v0 and v2 are NOT adjacent in P_3, so they SHOULD be
        // adjacent in the complement.
        GraphModel p3 = PathGenerator.generatePath(3);
        // Before complement: v0-v2 is not an edge
        Vertex v0 = p3.getVertex(0);
        Vertex v2 = p3.getVertex(2);
        assertFalse(p3.isEdge(v0, v2));

        GraphModel comp = GraphComplement.complement(p3);
        // After complement, original v0 and v2 are copied — find matching vertices
        // by checking that exactly one pair among (0,2) in comp has the expected adjacency
        // Complement edge count: for P_3 the complement should have the v0-v2 edge
        // (n*(n-1)/2 - |E| = 3 - 2 = 1 non-self distinct-pair edge)
        int distinctPairEdges = countDistinctPairEdges(comp);
        assertEquals(1, distinctPairEdges);
    }

    @Test
    public void testComplementOfPath4DistinctPairEdgeCount() {
        // P_4 has 4 vertices, 3 edges.
        // Total possible distinct pairs = 4*3/2 = 6
        // Complement has 6 - 3 = 3 distinct-pair edges
        GraphModel p4 = PathGenerator.generatePath(4);
        GraphModel comp = GraphComplement.complement(p4);
        assertEquals(4, comp.getVerticesCount());
        assertEquals(3, countDistinctPairEdges(comp));
    }

    @Test
    public void testComplementOfCircle4DistinctPairEdgeCount() {
        // C_4 has 4 vertices, 4 edges.
        // Total distinct pairs = 6. Complement has 6 - 4 = 2 distinct-pair edges.
        GraphModel c4 = CircleGenerator.generateCircle(4);
        GraphModel comp = GraphComplement.complement(c4);
        assertEquals(4, comp.getVerticesCount());
        assertEquals(2, countDistinctPairEdges(comp));
    }

    @Test
    public void testComplementOfCircle5DistinctPairEdgeCount() {
        // C_5 has 5 vertices, 5 edges.
        // Total distinct pairs = 10. Complement has 10 - 5 = 5 distinct-pair edges.
        GraphModel c5 = CircleGenerator.generateCircle(5);
        GraphModel comp = GraphComplement.complement(c5);
        assertEquals(5, comp.getVerticesCount());
        assertEquals(5, countDistinctPairEdges(comp));
    }

    // ---- complement is idempotent under double application ----

    @Test
    public void testDoubleComplementOfPath3HasSameDistinctPairEdgeCount() {
        GraphModel p3 = PathGenerator.generatePath(3);
        int originalEdges = countDistinctPairEdges(p3);
        GraphModel doubleComp = GraphComplement.complement(GraphComplement.complement(p3));
        assertEquals(originalEdges, countDistinctPairEdges(doubleComp));
    }

    @Test
    public void testDoubleComplementOfCircle5HasSameDistinctPairEdgeCount() {
        GraphModel c5 = CircleGenerator.generateCircle(5);
        int originalEdges = countDistinctPairEdges(c5);
        GraphModel doubleComp = GraphComplement.complement(GraphComplement.complement(c5));
        assertEquals(originalEdges, countDistinctPairEdges(doubleComp));
    }

    // ---- helpers ----

    /** Count edges between distinct vertex pairs (excludes any self-loops). */
    private int countDistinctPairEdges(GraphModel g) {
        List<Vertex> verts = new ArrayList<>();
        for (Vertex v : g) verts.add(v);
        int count = 0;
        for (int i = 0; i < verts.size(); i++) {
            for (int j = i + 1; j < verts.size(); j++) {
                if (g.isEdge(verts.get(i), verts.get(j))) {
                    count++;
                }
            }
        }
        return count;
    }
}
