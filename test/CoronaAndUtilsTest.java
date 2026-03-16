import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.algorithms.VertexCorona;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.core.BlackBoard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for VertexCorona.corona(), AlgorithmUtils.concatArrays(), and
 * AlgorithmUtils.getEccentricities().
 *
 * Corona(G1, G2) formulas:
 *   vertices = n1 * (1 + n2)
 *   edges    = m1 + n1 * (n2 + m2)
 */
public class CoronaAndUtilsTest {

    // -------------------------------------------------------------------------
    // Graph builders
    // -------------------------------------------------------------------------

    /** Single vertex K1 (n=1, m=0). */
    private GraphModel makeK1() {
        GraphModel g = new GraphModel(false);
        g.addVertex(new Vertex());
        return g;
    }

    /** Single edge K2 (n=2, m=1). */
    private GraphModel makeK2() {
        GraphModel g = new GraphModel(false);
        Vertex v0 = new Vertex(); Vertex v1 = new Vertex();
        g.addVertex(v0); g.addVertex(v1);
        g.addEdge(new Edge(v0, v1));
        return g;
    }

    /** P3: path with 3 vertices (n=3, m=2). */
    private GraphModel makePath3() {
        GraphModel g = new GraphModel(false);
        Vertex v0 = new Vertex(); Vertex v1 = new Vertex(); Vertex v2 = new Vertex();
        g.addVertex(v0); g.addVertex(v1); g.addVertex(v2);
        g.addEdge(new Edge(v0, v1));
        g.addEdge(new Edge(v1, v2));
        return g;
    }

    /** P4: path with 4 vertices (n=4, m=3). */
    private GraphModel makePath4() {
        GraphModel g = new GraphModel(false);
        Vertex v0 = new Vertex(); Vertex v1 = new Vertex();
        Vertex v2 = new Vertex(); Vertex v3 = new Vertex();
        g.addVertex(v0); g.addVertex(v1); g.addVertex(v2); g.addVertex(v3);
        g.addEdge(new Edge(v0, v1));
        g.addEdge(new Edge(v1, v2));
        g.addEdge(new Edge(v2, v3));
        return g;
    }

    /** K3 (triangle): n=3, m=3. */
    private GraphModel makeTriangle() {
        GraphModel g = new GraphModel(false);
        Vertex v0 = new Vertex(); Vertex v1 = new Vertex(); Vertex v2 = new Vertex();
        g.addVertex(v0); g.addVertex(v1); g.addVertex(v2);
        g.addEdge(new Edge(v0, v1));
        g.addEdge(new Edge(v1, v2));
        g.addEdge(new Edge(v0, v2));
        return g;
    }

    // -------------------------------------------------------------------------
    // VertexCorona.corona() — vertex count: n1*(1 + n2)
    // -------------------------------------------------------------------------

    @Test
    public void testCorona_vertexCount_path3_coronaK1() {
        // n1=3, n2=1 → 3*(1+1) = 6
        GraphModel result = VertexCorona.corona(makePath3(), makeK1());
        assertEquals(6, result.getVerticesCount());
    }

    @Test
    public void testCorona_vertexCount_triangle_coronaK2() {
        // n1=3, n2=2 → 3*(1+2) = 9
        GraphModel result = VertexCorona.corona(makeTriangle(), makeK2());
        assertEquals(9, result.getVerticesCount());
    }

    @Test
    public void testCorona_vertexCount_k2_coronaK2() {
        // n1=2, n2=2 → 2*(1+2) = 6
        GraphModel result = VertexCorona.corona(makeK2(), makeK2());
        assertEquals(6, result.getVerticesCount());
    }

    // -------------------------------------------------------------------------
    // VertexCorona.corona() — edge count: m1 + n1*(n2 + m2)
    // -------------------------------------------------------------------------

    @Test
    public void testCorona_edgeCount_path3_coronaK1() {
        // m1=2, n1=3, n2=1, m2=0 → 2 + 3*(1+0) = 5
        GraphModel result = VertexCorona.corona(makePath3(), makeK1());
        assertEquals(5, result.getEdgesCount());
    }

    @Test
    public void testCorona_edgeCount_triangle_coronaK2() {
        // m1=3, n1=3, n2=2, m2=1 → 3 + 3*(2+1) = 12
        GraphModel result = VertexCorona.corona(makeTriangle(), makeK2());
        assertEquals(12, result.getEdgesCount());
    }

    @Test
    public void testCorona_edgeCount_k2_coronaPath3() {
        // m1=1, n1=2, n2=3, m2=2 → 1 + 2*(3+2) = 11
        GraphModel result = VertexCorona.corona(makeK2(), makePath3());
        assertEquals(11, result.getEdgesCount());
    }

    // -------------------------------------------------------------------------
    // VertexCorona.corona() — structural invariant
    // -------------------------------------------------------------------------

    @Test
    public void testCorona_resultIsUndirected() {
        GraphModel result = VertexCorona.corona(makePath3(), makeK1());
        assertFalse(result.isDirected());
    }

    // -------------------------------------------------------------------------
    // AlgorithmUtils.concatArrays()
    // -------------------------------------------------------------------------

    @Test
    public void testConcatArrays_basicIntegers() {
        Integer[] a = {1, 2};
        Integer[] b = {3, 4};
        Integer[] result = AlgorithmUtils.concatArrays(a, b);
        assertArrayEquals(new Integer[]{1, 2, 3, 4}, result);
    }

    @Test
    public void testConcatArrays_firstEmpty() {
        Integer[] a = {};
        Integer[] b = {5, 6};
        Integer[] result = AlgorithmUtils.concatArrays(a, b);
        assertArrayEquals(new Integer[]{5, 6}, result);
    }

    @Test
    public void testConcatArrays_secondEmpty() {
        Integer[] a = {1, 2};
        Integer[] b = {};
        Integer[] result = AlgorithmUtils.concatArrays(a, b);
        assertArrayEquals(new Integer[]{1, 2}, result);
    }

    @Test
    public void testConcatArrays_bothEmpty() {
        Integer[] a = {};
        Integer[] b = {};
        Integer[] result = AlgorithmUtils.concatArrays(a, b);
        assertEquals(0, result.length);
    }

    @Test
    public void testConcatArrays_strings() {
        String[] a = {"foo", "bar"};
        String[] b = {"baz"};
        String[] result = AlgorithmUtils.concatArrays(a, b);
        assertArrayEquals(new String[]{"foo", "bar", "baz"}, result);
    }

    // -------------------------------------------------------------------------
    // AlgorithmUtils.getEccentricities()
    // -------------------------------------------------------------------------

    @Test
    public void testGetEccentricities_path4() {
        // P4: 0-1-2-3 → ecc = [3, 2, 2, 3]
        int[] ecc = AlgorithmUtils.getEccentricities(makePath4());
        assertArrayEquals(new int[]{3, 2, 2, 3}, ecc);
    }

    @Test
    public void testGetEccentricities_triangle() {
        // K3: every vertex can reach any other in 1 step → ecc = [1, 1, 1]
        int[] ecc = AlgorithmUtils.getEccentricities(makeTriangle());
        assertArrayEquals(new int[]{1, 1, 1}, ecc);
    }

    @Test
    public void testGetEccentricities_singleVertex() {
        // isolated vertex has no reachable peers → ecc = [0]
        int[] ecc = AlgorithmUtils.getEccentricities(makeK1());
        assertArrayEquals(new int[]{0}, ecc);
    }

    @Test
    public void testGetEccentricities_k2() {
        // K2: each vertex is distance 1 from the other → ecc = [1, 1]
        int[] ecc = AlgorithmUtils.getEccentricities(makeK2());
        assertArrayEquals(new int[]{1, 1}, ecc);
    }
}
