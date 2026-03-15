import graphtea.extensions.generators.CircleGenerator;
import graphtea.extensions.generators.CompleteGraphGenerator;
import graphtea.extensions.generators.PathGenerator;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.library.algorithms.traversal.BreadthFirstSearch;
import graphtea.library.algorithms.traversal.DepthFirstSearch;
import graphtea.library.event.handlers.PreWorkPostWorkHandler;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for BreadthFirstSearch and DepthFirstSearch on GraphModel.
 */
public class TraversalTest {

    // ---- BFS ----

    @Test
    public void testBfsVisitsAllVerticesInConnectedGraph() {
        GraphModel g = CircleGenerator.generateCircle(5);
        Vertex start = g.iterator().next();
        new BreadthFirstSearch<>(g).doSearch(start, null);
        for (Vertex v : g)
            assertTrue(v.getMark(), "BFS should mark every vertex in a connected graph");
    }

    @Test
    public void testBfsOnPath() {
        GraphModel path = PathGenerator.generatePath(4);
        Vertex start = path.iterator().next();
        new BreadthFirstSearch<>(path).doSearch(start, null);
        for (Vertex v : path)
            assertTrue(v.getMark());
    }

    @Test
    public void testBfsDoesNotVisitDisconnectedComponent() {
        // Build a graph with two isolated vertices
        GraphModel g = new GraphModel(false);
        Vertex v1 = new Vertex(), v2 = new Vertex();
        g.insertVertex(v1);
        g.insertVertex(v2);
        // no edges → two components
        new BreadthFirstSearch<>(g).doSearch(v1, null);
        assertTrue(v1.getMark());
        assertFalse(v2.getMark(), "BFS from v1 should not reach isolated v2");
    }

    @Test
    public void testBfsHandlerReceivesVertices() {
        GraphModel g = PathGenerator.generatePath(3);
        Vertex start = g.iterator().next();
        List<Vertex> visited = new ArrayList<>();
        new BreadthFirstSearch<>(g).doSearch(start, (from, to) -> {
            visited.add(to);
            return false;
        });
        assertEquals(g.getVerticesCount(), visited.size());
    }

    @Test
    public void testBfsHandlerCanStopEarly() {
        GraphModel g = CompleteGraphGenerator.generateCompleteGraph(5);
        Vertex start = g.iterator().next();
        List<Vertex> visited = new ArrayList<>();
        // stop after visiting 2 vertices
        new BreadthFirstSearch<>(g).doSearch(start, (from, to) -> {
            visited.add(to);
            return visited.size() >= 2;
        });
        assertEquals(2, visited.size());
    }

    // ---- DFS ----

    @Test
    public void testDfsVisitsAllVerticesInConnectedGraph() {
        GraphModel g = CircleGenerator.generateCircle(6);
        Vertex start = g.iterator().next();
        new DepthFirstSearch<>(g).doSearch(start, null);
        for (Vertex v : g)
            assertTrue(v.getMark(), "DFS should mark every vertex in a connected graph");
    }

    @Test
    public void testDfsOnCompleteGraph() {
        GraphModel g = CompleteGraphGenerator.generateCompleteGraph(4);
        Vertex start = g.iterator().next();
        new DepthFirstSearch<>(g).doSearch(start, null);
        for (Vertex v : g)
            assertTrue(v.getMark());
    }

    @Test
    public void testDfsDoesNotVisitDisconnectedComponent() {
        GraphModel g = new GraphModel(false);
        Vertex v1 = new Vertex(), v2 = new Vertex();
        g.insertVertex(v1);
        g.insertVertex(v2);
        new DepthFirstSearch<>(g).doSearch(v1, null);
        assertTrue(v1.getMark());
        assertFalse(v2.getMark());
    }

    @Test
    public void testDfsHandlerVisitsAllVertices() {
        GraphModel g = PathGenerator.generatePath(5);
        Vertex start = g.iterator().next();
        List<Vertex> preOrder = new ArrayList<>();
        new DepthFirstSearch<>(g).doSearch(start, new PreWorkPostWorkHandler<>() {
            public boolean doPreWork(Vertex from, Vertex to) {
                preOrder.add(to);
                return false;
            }
            public boolean doPostWork(Vertex from, Vertex to) { return false; }
        });
        assertEquals(g.getVerticesCount(), preOrder.size());
    }

    @Test
    public void testDfsHandlerCanStopEarly() {
        GraphModel g = CompleteGraphGenerator.generateCompleteGraph(5);
        Vertex start = g.iterator().next();
        List<Vertex> preOrder = new ArrayList<>();
        new DepthFirstSearch<>(g).doSearch(start, new PreWorkPostWorkHandler<>() {
            public boolean doPreWork(Vertex from, Vertex to) {
                preOrder.add(to);
                return preOrder.size() >= 2;
            }
            public boolean doPostWork(Vertex from, Vertex to) { return false; }
        });
        assertEquals(2, preOrder.size());
    }

    @Test
    public void testDfsNullGraphThrows() {
        DepthFirstSearch<Vertex, Edge> dfs = new DepthFirstSearch<>();
        assertThrows(Exception.class, () -> dfs.doSearch(new Vertex(), null));
    }
}
