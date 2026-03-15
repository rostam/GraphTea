import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GraphModelTest {

    private GraphModel undirected() { return new GraphModel(false); }
    private GraphModel directed()   { return new GraphModel(true); }

    // ---- vertex operations ----

    @Test
    public void testInsertVertex() {
        GraphModel g = undirected();
        assertEquals(0, g.getVerticesCount());
        g.insertVertex(new Vertex());
        assertEquals(1, g.getVerticesCount());
        g.insertVertex(new Vertex());
        g.insertVertex(new Vertex());
        assertEquals(3, g.getVerticesCount());
    }

    @Test
    public void testRemoveVertex() {
        GraphModel g = undirected();
        Vertex v1 = new Vertex(), v2 = new Vertex();
        g.insertVertex(v1);
        g.insertVertex(v2);
        g.insertEdge(new Edge(v1, v2));
        g.removeVertex(v1);
        assertEquals(1, g.getVerticesCount());
        assertEquals(0, g.getEdgesCount());
    }

    // ---- edge operations ----

    @Test
    public void testInsertEdge() {
        GraphModel g = undirected();
        Vertex v1 = new Vertex(), v2 = new Vertex();
        g.insertVertex(v1);
        g.insertVertex(v2);
        assertEquals(0, g.getEdgesCount());
        g.insertEdge(new Edge(v1, v2));
        assertEquals(1, g.getEdgesCount());
    }

    @Test
    public void testRemoveEdge() {
        GraphModel g = undirected();
        Vertex v1 = new Vertex(), v2 = new Vertex();
        g.insertVertex(v1);
        g.insertVertex(v2);
        Edge e = new Edge(v1, v2);
        g.insertEdge(e);
        g.removeEdge(e);
        assertEquals(0, g.getEdgesCount());
        assertEquals(2, g.getVerticesCount());
    }

    @Test
    public void testInsertEdgeByVertices() {
        GraphModel g = undirected();
        Vertex v1 = new Vertex(), v2 = new Vertex();
        g.insertVertex(v1);
        g.insertVertex(v2);
        g.insertEdge(v1, v2);
        assertEquals(1, g.getEdgesCount());
    }

    // ---- adjacency ----

    @Test
    public void testIsEdgeUndirected() {
        GraphModel g = undirected();
        Vertex v1 = new Vertex(), v2 = new Vertex(), v3 = new Vertex();
        g.insertVertex(v1);
        g.insertVertex(v2);
        g.insertVertex(v3);
        g.insertEdge(new Edge(v1, v2));
        assertTrue(g.isEdge(v1, v2));
        assertTrue(g.isEdge(v2, v1));   // symmetric in undirected graph
        assertFalse(g.isEdge(v1, v3));
    }

    @Test
    public void testIsEdgeDirected() {
        GraphModel g = directed();
        Vertex v1 = new Vertex(), v2 = new Vertex();
        g.insertVertex(v1);
        g.insertVertex(v2);
        g.insertEdge(new Edge(v1, v2));
        assertTrue(g.isEdge(v1, v2));
        assertFalse(g.isEdge(v2, v1));  // reverse direction not present
    }

    @Test
    public void testGetEdge() {
        GraphModel g = undirected();
        Vertex v1 = new Vertex(), v2 = new Vertex(), v3 = new Vertex();
        g.insertVertex(v1);
        g.insertVertex(v2);
        g.insertVertex(v3);
        g.insertEdge(new Edge(v1, v2));
        assertNotNull(g.getEdge(v1, v2));
        assertNull(g.getEdge(v1, v3));
    }

    // ---- degree ----

    @Test
    public void testInOutDegree() {
        GraphModel g = directed();
        Vertex v1 = new Vertex(), v2 = new Vertex(), v3 = new Vertex();
        g.insertVertex(v1);
        g.insertVertex(v2);
        g.insertVertex(v3);
        g.insertEdge(new Edge(v1, v2));
        g.insertEdge(new Edge(v1, v3));
        assertEquals(2, g.getOutDegree(v1));
        assertEquals(0, g.getInDegree(v1));
        assertEquals(1, g.getInDegree(v2));
        assertEquals(0, g.getOutDegree(v2));
    }

    // ---- directNeighbors ----

    @Test
    public void testDirectNeighbors() {
        GraphModel g = undirected();
        Vertex v1 = new Vertex(), v2 = new Vertex(), v3 = new Vertex();
        g.insertVertex(v1);
        g.insertVertex(v2);
        g.insertVertex(v3);
        g.insertEdge(new Edge(v1, v2));
        g.insertEdge(new Edge(v1, v3));
        assertEquals(2, g.directNeighbors(v1).size());
        assertEquals(1, g.directNeighbors(v2).size());
        assertTrue(g.directNeighbors(v1).contains(v2));
        assertTrue(g.directNeighbors(v1).contains(v3));
    }

    // ---- clear ----

    @Test
    public void testClear() {
        GraphModel g = undirected();
        Vertex v1 = new Vertex(), v2 = new Vertex();
        g.insertVertex(v1);
        g.insertVertex(v2);
        g.insertEdge(new Edge(v1, v2));
        g.clear();
        assertEquals(0, g.getVerticesCount());
        assertEquals(0, g.getEdgesCount());
    }

    // ---- directed flag ----

    @Test
    public void testIsDirected() {
        assertFalse(undirected().isDirected());
        assertTrue(directed().isDirected());
    }

    // ---- label ----

    @Test
    public void testLabel() {
        GraphModel g = undirected();
        g.setLabel("MyGraph");
        assertEquals("MyGraph", g.getLabel());
    }
}
