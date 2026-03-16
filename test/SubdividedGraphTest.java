import graphtea.extensions.actions.EdgeSemitotalGraph;
import graphtea.extensions.actions.TotalGraph;
import graphtea.extensions.actions.VertexSemitotalGraph;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for TotalGraph, EdgeSemitotalGraph, and VertexSemitotalGraph.
 *
 * Default subdivision parameter k=2: each original edge is replaced by a
 * path of 3 segments (2 new subdivision vertices per edge).
 *
 * Vertices after subdivision:  n' = n + k*m
 *
 * Extra edges per variant (beyond the k+1 subdivision segments per edge):
 *   VertexSemitotalGraph — adds one curved edge per original edge  (+m)
 *   EdgeSemitotalGraph   — connects subdivision vertices of adjacent edges
 *   TotalGraph           — both of the above
 */
public class SubdividedGraphTest {

    // -------------------------------------------------------------------------
    // Graph builders
    // -------------------------------------------------------------------------

    /** P3: three vertices connected as a path  0-1-2  (n=3, m=2). */
    private GraphModel makePath3() {
        GraphModel g = new GraphModel(false);
        Vertex v0 = new Vertex(); Vertex v1 = new Vertex(); Vertex v2 = new Vertex();
        g.addVertex(v0); g.addVertex(v1); g.addVertex(v2);
        g.addEdge(new Edge(v0, v1));
        g.addEdge(new Edge(v1, v2));
        return g;
    }

    /** K3 (triangle): three vertices all connected  (n=3, m=3). */
    private GraphModel makeTriangle() {
        GraphModel g = new GraphModel(false);
        Vertex v0 = new Vertex(); Vertex v1 = new Vertex(); Vertex v2 = new Vertex();
        g.addVertex(v0); g.addVertex(v1); g.addVertex(v2);
        g.addEdge(new Edge(v0, v1));
        g.addEdge(new Edge(v1, v2));
        g.addEdge(new Edge(v0, v2));
        return g;
    }

    /** Single edge (n=2, m=1). */
    private GraphModel makeSingleEdge() {
        GraphModel g = new GraphModel(false);
        Vertex v0 = new Vertex(); Vertex v1 = new Vertex();
        g.addVertex(v0); g.addVertex(v1);
        g.addEdge(new Edge(v0, v1));
        return g;
    }

    // -------------------------------------------------------------------------
    // Vertex count formula: n' = n + k*m  (k=2 by default)
    // -------------------------------------------------------------------------

    @Test
    public void testVertexCount_path3() {
        // n=3, m=2, k=2 → n' = 3 + 2*2 = 7
        assertEquals(7, new VertexSemitotalGraph().apply(makePath3()).getVerticesCount());
        assertEquals(7, new EdgeSemitotalGraph().apply(makePath3()).getVerticesCount());
        assertEquals(7, new TotalGraph().apply(makePath3()).getVerticesCount());
    }

    @Test
    public void testVertexCount_triangle() {
        // n=3, m=3, k=2 → n' = 3 + 2*3 = 9
        assertEquals(9, new VertexSemitotalGraph().apply(makeTriangle()).getVerticesCount());
        assertEquals(9, new EdgeSemitotalGraph().apply(makeTriangle()).getVerticesCount());
        assertEquals(9, new TotalGraph().apply(makeTriangle()).getVerticesCount());
    }

    @Test
    public void testVertexCount_singleEdge() {
        // n=2, m=1, k=2 → n' = 2 + 2*1 = 4
        assertEquals(4, new VertexSemitotalGraph().apply(makeSingleEdge()).getVerticesCount());
        assertEquals(4, new EdgeSemitotalGraph().apply(makeSingleEdge()).getVerticesCount());
        assertEquals(4, new TotalGraph().apply(makeSingleEdge()).getVerticesCount());
    }

    // -------------------------------------------------------------------------
    // VertexSemitotalGraph (addCurvedOriginalEdge=true, others=false)
    //   Each original edge contributes k+1=3 subdivision edges + 1 curved original
    //   → 4 edges per original edge → total = 4m
    // -------------------------------------------------------------------------

    @Test
    public void testVertexSemitotal_path3_edgeCount() {
        assertEquals(8, new VertexSemitotalGraph().apply(makePath3()).getEdgesCount());
    }

    @Test
    public void testVertexSemitotal_triangle_edgeCount() {
        assertEquals(12, new VertexSemitotalGraph().apply(makeTriangle()).getEdgesCount());
    }

    @Test
    public void testVertexSemitotal_singleEdge_edgeCount() {
        assertEquals(4, new VertexSemitotalGraph().apply(makeSingleEdge()).getEdgesCount());
    }

    // -------------------------------------------------------------------------
    // EdgeSemitotalGraph (markSubdivisionVertices + addSubdivisionVertexEdges,
    //                     no curved original edge)
    // -------------------------------------------------------------------------

    @Test
    public void testEdgeSemitotal_path3_edgeCount() {
        assertEquals(10, new EdgeSemitotalGraph().apply(makePath3()).getEdgesCount());
    }

    @Test
    public void testEdgeSemitotal_triangle_edgeCount() {
        assertEquals(21, new EdgeSemitotalGraph().apply(makeTriangle()).getEdgesCount());
    }

    @Test
    public void testEdgeSemitotal_singleEdge_edgeCount() {
        assertEquals(3, new EdgeSemitotalGraph().apply(makeSingleEdge()).getEdgesCount());
    }

    // -------------------------------------------------------------------------
    // TotalGraph (all three features)
    //   TotalGraph has more edges than both VertexSemitotal and EdgeSemitotal
    // -------------------------------------------------------------------------

    @Test
    public void testTotal_path3_edgeCount() {
        assertEquals(12, new TotalGraph().apply(makePath3()).getEdgesCount());
    }

    @Test
    public void testTotal_triangle_edgeCount() {
        assertEquals(24, new TotalGraph().apply(makeTriangle()).getEdgesCount());
    }

    @Test
    public void testTotal_singleEdge_edgeCount() {
        assertEquals(4, new TotalGraph().apply(makeSingleEdge()).getEdgesCount());
    }

    // -------------------------------------------------------------------------
    // Ordering invariant: TotalGraph >= VertexSemitotal and EdgeSemitotal
    // -------------------------------------------------------------------------

    @Test
    public void testTotal_hasAtLeastAsEdgesAsVertexSemitotal() {
        GraphModel g = makePath3();
        assertTrue(new TotalGraph().apply(g).getEdgesCount()
                >= new VertexSemitotalGraph().apply(g).getEdgesCount());
    }

    @Test
    public void testTotal_hasAtLeastAsEdgesAsEdgeSemitotal() {
        GraphModel g = makeTriangle();
        assertTrue(new TotalGraph().apply(g).getEdgesCount()
                >= new EdgeSemitotalGraph().apply(g).getEdgesCount());
    }

    // -------------------------------------------------------------------------
    // k parameter: changing k affects vertex count
    // -------------------------------------------------------------------------

    @Test
    public void testK1_path3_vertexCount() {
        // k=1: n' = 3 + 1*2 = 5
        VertexSemitotalGraph vst = new VertexSemitotalGraph();
        vst.k = 1;
        assertEquals(5, vst.apply(makePath3()).getVerticesCount());
    }

    @Test
    public void testK3_path3_vertexCount() {
        // k=3: n' = 3 + 3*2 = 9
        VertexSemitotalGraph vst = new VertexSemitotalGraph();
        vst.k = 3;
        assertEquals(9, vst.apply(makePath3()).getVerticesCount());
    }
}
