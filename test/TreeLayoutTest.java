import graphtea.extensions.generators.CircleGenerator;
import graphtea.extensions.generators.PathGenerator;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.plugins.visualization.treevisualizations.TreeLayout;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the layered tree layout behind the Hierarchical Tree visualization.
 */
public class TreeLayoutTest {

    /** A star with a centre and {@code leaves} leaves; the centre is vertex 0. */
    private static GraphModel star(int leaves) {
        GraphModel g = new GraphModel(false);
        Vertex c = new Vertex();
        g.addVertex(c);
        for (int i = 0; i < leaves; i++) {
            Vertex v = new Vertex();
            g.addVertex(v);
            g.addEdge(new Edge(c, v));
        }
        return g;
    }

    @Test
    public void everyVertexIsPlacedOnce() {
        GraphModel g = star(6);
        Map<Vertex, GPoint> places = TreeLayout.layered(g);
        assertEquals(g.getVerticesCount(), places.size());
    }

    @Test
    public void starIsRootedAtItsCentreWithLeavesOneLevelDown() {
        GraphModel g = star(5);
        Map<Vertex, GPoint> places = TreeLayout.layered(g);
        assertEquals(0, places.get(g.getVertex(0)).y);
        for (int i = 1; i <= 5; i++) {
            assertEquals(1, places.get(g.getVertex(i)).y, "leaf " + i);
        }
        // The centre sits midway over its leaves.
        assertEquals(2.0, places.get(g.getVertex(0)).x, 1e-9);
    }

    @Test
    public void noTwoVerticesOverlap() {
        GraphModel g = star(12);
        Set<String> seen = new HashSet<>();
        for (GPoint p : TreeLayout.layered(g).values()) {
            assertTrue(seen.add(p.x + "," + p.y), "two vertices at " + p);
        }
    }

    @Test
    public void pathIsRootedInTheMiddle() {
        // The old layout rooted trees at a rim vertex; a path of 7 should hang from vertex 3.
        GraphModel g = PathGenerator.generatePath(7);
        Map<Vertex, GPoint> places = TreeLayout.layered(g);
        int deepest = 0;
        for (GPoint p : places.values()) {
            deepest = Math.max(deepest, (int) p.y);
        }
        assertEquals(3, deepest);
    }

    @Test
    public void longPathDoesNotOverflowTheStack() {
        GraphModel g = PathGenerator.generatePath(20000);
        assertEquals(20000, TreeLayout.layered(g).size());
    }

    @Test
    public void nonTreeUsesASpanningTree() {
        GraphModel g = CircleGenerator.generateCircle(6);
        Map<Vertex, GPoint> places = TreeLayout.layered(g);
        assertEquals(6, places.size());
    }

    @Test
    public void componentsAreSideBySide() {
        GraphModel g = new GraphModel(false);
        Vertex a = new Vertex();
        Vertex b = new Vertex();
        g.addVertex(a);
        g.addVertex(b);
        Map<Vertex, GPoint> places = TreeLayout.layered(g);
        assertNotEquals(places.get(a).x, places.get(b).x);
    }

    @Test
    public void emptyGraphGivesNoPlaces() {
        assertTrue(TreeLayout.layered(new GraphModel(false)).isEmpty());
    }
}
