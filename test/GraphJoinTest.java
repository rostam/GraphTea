import graphtea.extensions.algorithms.GraphJoin;
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
 * Tests for GraphJoin.join().
 *
 * The join of G1 and G2 is the disjoint union plus all edges between V(G1) and V(G2).
 * |V(G1 join G2)| = |V(G1)| + |V(G2)|
 * |E(G1 join G2)| = |E(G1)| + |E(G2)| + |V(G1)| * |V(G2)|
 */
public class GraphJoinTest {

    // ---- vertex counts ----

    @Test
    public void testJoinVertexCountPathPath() {
        GraphModel p3 = PathGenerator.generatePath(3);
        GraphModel p4 = PathGenerator.generatePath(4);
        GraphModel joined = GraphJoin.join(p3, p4);
        assertEquals(3 + 4, joined.getVerticesCount());
    }

    @Test
    public void testJoinVertexCountCircleComplete() {
        GraphModel c4 = CircleGenerator.generateCircle(4);
        GraphModel k3 = CompleteGraphGenerator.generateCompleteGraph(3);
        GraphModel joined = GraphJoin.join(c4, k3);
        assertEquals(4 + 3, joined.getVerticesCount());
    }

    @Test
    public void testJoinVertexCountSymmetric() {
        GraphModel p3 = PathGenerator.generatePath(3);
        GraphModel c4 = CircleGenerator.generateCircle(4);
        GraphModel j1 = GraphJoin.join(p3, c4);
        GraphModel j2 = GraphJoin.join(c4, p3);
        assertEquals(j1.getVerticesCount(), j2.getVerticesCount());
    }

    // ---- edge counts ----

    @Test
    public void testJoinEdgeCountTwoPaths() {
        // P_2 (2v, 1e) join P_2 (2v, 1e): |E| = 1 + 1 + 2*2 = 6
        GraphModel p2a = PathGenerator.generatePath(2);
        GraphModel p2b = PathGenerator.generatePath(2);
        GraphModel joined = GraphJoin.join(p2a, p2b);
        assertEquals(6, joined.getEdgesCount());
    }

    @Test
    public void testJoinEdgeCountPathAndCircle() {
        // P_3 (3v, 2e) join C_4 (4v, 4e): |E| = 2 + 4 + 3*4 = 18
        GraphModel p3 = PathGenerator.generatePath(3);
        GraphModel c4 = CircleGenerator.generateCircle(4);
        GraphModel joined = GraphJoin.join(p3, c4);
        assertEquals(2 + 4 + 3 * 4, joined.getEdgesCount());
    }

    @Test
    public void testJoinEdgeCountTwoCompleteGraphs() {
        // K_2 (2v, 1e) join K_3 (3v, 3e): |E| = 1 + 3 + 2*3 = 10
        GraphModel k2 = CompleteGraphGenerator.generateCompleteGraph(2);
        GraphModel k3 = CompleteGraphGenerator.generateCompleteGraph(3);
        GraphModel joined = GraphJoin.join(k2, k3);
        assertEquals(1 + 3 + 2 * 3, joined.getEdgesCount());
    }

    @Test
    public void testJoinEdgeCountLarger() {
        // P_4 (4v, 3e) join C_5 (5v, 5e): |E| = 3 + 5 + 4*5 = 28
        GraphModel p4 = PathGenerator.generatePath(4);
        GraphModel c5 = CircleGenerator.generateCircle(5);
        GraphModel joined = GraphJoin.join(p4, c5);
        assertEquals(3 + 5 + 4 * 5, joined.getEdgesCount());
    }

    // ---- originals not mutated ----

    @Test
    public void testJoinDoesNotMutateInputs() {
        GraphModel p3 = PathGenerator.generatePath(3);
        GraphModel c4 = CircleGenerator.generateCircle(4);
        int v1 = p3.getVerticesCount(), e1 = p3.getEdgesCount();
        int v2 = c4.getVerticesCount(), e2 = c4.getEdgesCount();

        GraphJoin.join(p3, c4);

        assertEquals(v1, p3.getVerticesCount());
        assertEquals(e1, p3.getEdgesCount());
        assertEquals(v2, c4.getVerticesCount());
        assertEquals(e2, c4.getEdgesCount());
    }

    // ---- cross-edges between every pair of vertices from G1 and G2 ----

    @Test
    public void testJoinCrossEdgesExist() {
        // In the join, every vertex of G1 copy should be adjacent to every vertex of G2 copy.
        // We verify by checking that the edge count above the original edges is exactly |V1|*|V2|.
        GraphModel p2 = PathGenerator.generatePath(2);  // 2v, 1e
        GraphModel p3 = PathGenerator.generatePath(3);  // 3v, 2e
        GraphModel joined = GraphJoin.join(p2, p3);

        // expected total edges = 1 + 2 + 2*3 = 9
        assertEquals(9, joined.getEdgesCount());
        // expected vertices = 5
        assertEquals(5, joined.getVerticesCount());
    }

    // ---- join with complete graph produces known result ----

    @Test
    public void testJoinK2K2IsK4() {
        // K_2 join K_2: 4 vertices; edges = 1 + 1 + 2*2 = 6 = K_4 has 6 edges
        GraphModel k2a = CompleteGraphGenerator.generateCompleteGraph(2);
        GraphModel k2b = CompleteGraphGenerator.generateCompleteGraph(2);
        GraphModel joined = GraphJoin.join(k2a, k2b);
        assertEquals(4, joined.getVerticesCount());
        assertEquals(6, joined.getEdgesCount());
    }

    @Test
    public void testJoinResultIsNotDirected() {
        GraphModel p3 = PathGenerator.generatePath(3);
        GraphModel c4 = CircleGenerator.generateCircle(4);
        GraphModel joined = GraphJoin.join(p3, c4);
        assertFalse(joined.isDirected());
    }
}
