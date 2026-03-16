import graphtea.extensions.algorithms.BiconnectedComponents;
import graphtea.extensions.generators.CircleGenerator;
import graphtea.extensions.generators.CompleteGraphGenerator;
import graphtea.extensions.generators.PathGenerator;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for BiconnectedComponents.biconnected_components().
 *
 * A biconnected component (block) is a maximal 2-connected subgraph.
 * In a path Pₙ (n ≥ 2) there are n-1 blocks, each containing 2 vertices.
 * Any cycle Cₙ forms a single block.
 * A complete graph Kₙ (n ≥ 2) forms a single block.
 */
public class BiconnectedComponentsTest {

    private BiconnectedComponents bc() {
        return new BiconnectedComponents();
    }

    // ---- path graphs ----

    @Test
    public void testPathP2HasOneComponent() {
        // P_2: single edge, 2 vertices → 1 block {v0, v1}
        GraphModel g = PathGenerator.generatePath(2);
        Vertex start = g.iterator().next();
        List<HashSet<Vertex>> components = bc().biconnected_components(g, start, g.getVerticesCount());
        assertEquals(1, components.size());
    }

    @Test
    public void testPathP3HasTwoComponents() {
        // P_3: v0-v1-v2 → 2 blocks: {v0,v1} and {v1,v2}
        GraphModel g = PathGenerator.generatePath(3);
        Vertex start = g.getVertex(0);
        List<HashSet<Vertex>> components = bc().biconnected_components(g, start, g.getVerticesCount());
        assertEquals(2, components.size());
    }

    @Test
    public void testPathP4HasThreeComponents() {
        // P_4: v0-v1-v2-v3 → 3 blocks
        GraphModel g = PathGenerator.generatePath(4);
        Vertex start = g.getVertex(0);
        List<HashSet<Vertex>> components = bc().biconnected_components(g, start, g.getVerticesCount());
        assertEquals(3, components.size());
    }

    @Test
    public void testPathP5HasFourComponents() {
        // P_5 → 4 blocks
        GraphModel g = PathGenerator.generatePath(5);
        Vertex start = g.getVertex(0);
        List<HashSet<Vertex>> components = bc().biconnected_components(g, start, g.getVerticesCount());
        assertEquals(4, components.size());
    }

    @Test
    public void testPathBlocksEachHaveTwoVertices() {
        // Every block in a path has exactly 2 vertices
        GraphModel g = PathGenerator.generatePath(5);
        Vertex start = g.getVertex(0);
        List<HashSet<Vertex>> components = bc().biconnected_components(g, start, g.getVerticesCount());
        for (HashSet<Vertex> comp : components) {
            assertEquals(2, comp.size(), "Each block in a path should have exactly 2 vertices");
        }
    }

    // ---- cycle graphs ----

    @Test
    public void testCycleC3HasOneComponent() {
        // C_3 is a triangle → 1 block containing all 3 vertices
        GraphModel g = CircleGenerator.generateCircle(3);
        Vertex start = g.iterator().next();
        List<HashSet<Vertex>> components = bc().biconnected_components(g, start, g.getVerticesCount());
        assertEquals(1, components.size());
    }

    @Test
    public void testCycleC4HasOneComponent() {
        GraphModel g = CircleGenerator.generateCircle(4);
        Vertex start = g.iterator().next();
        List<HashSet<Vertex>> components = bc().biconnected_components(g, start, g.getVerticesCount());
        assertEquals(1, components.size());
    }

    @Test
    public void testCycleC5HasOneComponent() {
        GraphModel g = CircleGenerator.generateCircle(5);
        Vertex start = g.iterator().next();
        List<HashSet<Vertex>> components = bc().biconnected_components(g, start, g.getVerticesCount());
        assertEquals(1, components.size());
    }

    @Test
    public void testCycleC3BlockContainsAllVertices() {
        GraphModel g = CircleGenerator.generateCircle(3);
        Vertex start = g.iterator().next();
        List<HashSet<Vertex>> components = bc().biconnected_components(g, start, g.getVerticesCount());
        assertEquals(1, components.size());
        assertEquals(3, components.get(0).size());
    }

    // ---- complete graphs ----

    @Test
    public void testCompleteK3HasOneComponent() {
        // K_3 = C_3, so 1 block
        GraphModel g = CompleteGraphGenerator.generateCompleteGraph(3);
        Vertex start = g.iterator().next();
        List<HashSet<Vertex>> components = bc().biconnected_components(g, start, g.getVerticesCount());
        assertEquals(1, components.size());
    }

    @Test
    public void testCompleteK4HasOneComponent() {
        // K_4 is 3-connected → 1 block
        GraphModel g = CompleteGraphGenerator.generateCompleteGraph(4);
        Vertex start = g.iterator().next();
        List<HashSet<Vertex>> components = bc().biconnected_components(g, start, g.getVerticesCount());
        assertEquals(1, components.size());
    }

    @Test
    public void testCompleteK4BlockContainsAllVertices() {
        GraphModel g = CompleteGraphGenerator.generateCompleteGraph(4);
        Vertex start = g.iterator().next();
        List<HashSet<Vertex>> components = bc().biconnected_components(g, start, g.getVerticesCount());
        assertEquals(1, components.size());
        assertEquals(4, components.get(0).size());
    }

    // ---- two triangles sharing a cut vertex ----

    @Test
    public void testBowtieTwoComponents() {
        // Build "bowtie": triangles {v0,v1,v2} and {v2,v3,v4} sharing v2.
        // v2 is a cut vertex → 2 biconnected components.
        GraphModel g = new GraphModel(false);
        Vertex[] v = new Vertex[5];
        for (int i = 0; i < 5; i++) {
            v[i] = new Vertex();
            g.insertVertex(v[i]);
        }
        g.insertEdge(new Edge(v[0], v[1]));
        g.insertEdge(new Edge(v[1], v[2]));
        g.insertEdge(new Edge(v[0], v[2]));
        g.insertEdge(new Edge(v[2], v[3]));
        g.insertEdge(new Edge(v[3], v[4]));
        g.insertEdge(new Edge(v[2], v[4]));

        List<HashSet<Vertex>> components = bc().biconnected_components(g, v[0], g.getVerticesCount());
        assertEquals(2, components.size());
    }

    @Test
    public void testBowtieCutVertexAppearsInBothComponents() {
        // The shared vertex v2 should appear in both biconnected components.
        GraphModel g = new GraphModel(false);
        Vertex[] v = new Vertex[5];
        for (int i = 0; i < 5; i++) {
            v[i] = new Vertex();
            g.insertVertex(v[i]);
        }
        g.insertEdge(new Edge(v[0], v[1]));
        g.insertEdge(new Edge(v[1], v[2]));
        g.insertEdge(new Edge(v[0], v[2]));
        g.insertEdge(new Edge(v[2], v[3]));
        g.insertEdge(new Edge(v[3], v[4]));
        g.insertEdge(new Edge(v[2], v[4]));

        List<HashSet<Vertex>> components = bc().biconnected_components(g, v[0], g.getVerticesCount());
        // v[2] (the cut vertex) must be present in both components
        boolean inFirst = components.get(0).contains(v[2]);
        boolean inSecond = components.get(1).contains(v[2]);
        assertTrue(inFirst && inSecond,
                "Cut vertex v[2] must appear in both biconnected components");
    }
}
