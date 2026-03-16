import graphtea.extensions.algorithms.shortestpath.algs.BellmanFord;
import graphtea.extensions.algorithms.spanningtree.Kruskal;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.core.BlackBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.AbstractList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ShortestPathAndMSTTest {

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    /** Undirected path  v0 - v1 - v2 - v3  with uniform weight 1. */
    private GraphModel makePath4() {
        GraphModel g = new GraphModel(false);
        Vertex v0 = new Vertex(); Vertex v1 = new Vertex();
        Vertex v2 = new Vertex(); Vertex v3 = new Vertex();
        g.addVertex(v0); g.addVertex(v1); g.addVertex(v2); g.addVertex(v3);
        g.addEdge(new Edge(v0, v1)); g.addEdge(new Edge(v1, v2));
        g.addEdge(new Edge(v2, v3));
        return g;
    }

    /**
     * Directed weighted graph:
     *   0 --(1)--> 1 --(2)--> 2 --(3)--> 3
     */
    private GraphModel makeWeightedPath(boolean directed) {
        GraphModel g = new GraphModel(directed);
        Vertex v0 = new Vertex(); Vertex v1 = new Vertex();
        Vertex v2 = new Vertex(); Vertex v3 = new Vertex();
        g.addVertex(v0); g.addVertex(v1); g.addVertex(v2); g.addVertex(v3);
        Edge e01 = new Edge(v0, v1); e01.setWeight(1);
        Edge e12 = new Edge(v1, v2); e12.setWeight(2);
        Edge e23 = new Edge(v2, v3); e23.setWeight(3);
        g.addEdge(e01); g.addEdge(e12); g.addEdge(e23);
        return g;
    }

    // -------------------------------------------------------------------------
    // BellmanFord
    // -------------------------------------------------------------------------

    @Test
    public void testBellmanFordSimplePath_sourceHasNoPredecessor() {
        GraphModel g = makeWeightedPath(true);
        BellmanFord bf = new BellmanFord(new BlackBoard());
        Vertex source = g.getVertex(0);

        List<Vertex> pred = bf.computePaths(g, source);

        assertNotNull(pred);
        // source has no predecessor
        assertNull(pred.get(0));
    }

    @Test
    public void testBellmanFordSimplePath_predecessorChain() {
        GraphModel g = makeWeightedPath(true);
        BellmanFord bf = new BellmanFord(new BlackBoard());
        Vertex source = g.getVertex(0);

        List<Vertex> pred = bf.computePaths(g, source);

        assertNotNull(pred);
        // v1 predecessor is v0
        assertEquals(g.getVertex(0), pred.get(1));
        // v2 predecessor is v1
        assertEquals(g.getVertex(1), pred.get(2));
        // v3 predecessor is v2
        assertEquals(g.getVertex(2), pred.get(3));
    }

    @Test
    public void testBellmanFordSingleVertex() {
        GraphModel g = new GraphModel(true);
        Vertex v = new Vertex();
        g.addVertex(v);
        BellmanFord bf = new BellmanFord(new BlackBoard());

        List<Vertex> pred = bf.computePaths(g, v);

        assertNotNull(pred);
        assertEquals(1, pred.size());
        assertNull(pred.get(0));
    }

    @Test
    public void testBellmanFordNegativeCycleReturnsNull() {
        // 0 --(1)--> 1 --(-3)--> 2 --(1)--> 0  (negative cycle)
        GraphModel g = new GraphModel(true);
        Vertex v0 = new Vertex(); Vertex v1 = new Vertex(); Vertex v2 = new Vertex();
        g.addVertex(v0); g.addVertex(v1); g.addVertex(v2);
        Edge e01 = new Edge(v0, v1); e01.setWeight(1);
        Edge e12 = new Edge(v1, v2); e12.setWeight(-3);
        Edge e20 = new Edge(v2, v0); e20.setWeight(1);
        g.addEdge(e01); g.addEdge(e12); g.addEdge(e20);

        BellmanFord bf = new BellmanFord(new BlackBoard());
        assertNull(bf.computePaths(g, v0));
    }

    @Test
    public void testBellmanFordWithNegativeEdges_noCycle() {
        // 0 --(5)--> 1, 0 --(-2)--> 2, 2 --(1)--> 1
        // shortest 0->1 should go via 2: -2 + 1 = -1
        GraphModel g = new GraphModel(true);
        Vertex v0 = new Vertex(); Vertex v1 = new Vertex(); Vertex v2 = new Vertex();
        g.addVertex(v0); g.addVertex(v1); g.addVertex(v2);
        Edge e01 = new Edge(v0, v1); e01.setWeight(5);
        Edge e02 = new Edge(v0, v2); e02.setWeight(-2);
        Edge e21 = new Edge(v2, v1); e21.setWeight(1);
        g.addEdge(e01); g.addEdge(e02); g.addEdge(e21);

        BellmanFord bf = new BellmanFord(new BlackBoard());
        List<Vertex> pred = bf.computePaths(g, v0);

        assertNotNull(pred);
        // v1's predecessor should be v2 (via the cheaper path)
        assertEquals(v2, pred.get(v1.getId()));
    }

    // -------------------------------------------------------------------------
    // Kruskal
    // -------------------------------------------------------------------------

    @Test
    public void testKruskalTreeHasNMinus1Edges() {
        GraphModel g = makePath4();   // already a tree
        AbstractList<Edge> mst = Kruskal.findMinimumSpanningTree(g);
        assertEquals(g.getVerticesCount() - 1, mst.size());
    }

    @Test
    public void testKruskalOnCompleteGraph_edgeCount() {
        // K4: 4 vertices, 6 edges — MST should have 3 edges
        GraphModel g = new GraphModel(false);
        Vertex[] v = new Vertex[4];
        for (int i = 0; i < 4; i++) { v[i] = new Vertex(); g.addVertex(v[i]); }
        for (int i = 0; i < 4; i++) {
            for (int j = i + 1; j < 4; j++) {
                g.addEdge(new Edge(v[i], v[j]));
            }
        }
        AbstractList<Edge> mst = Kruskal.findMinimumSpanningTree(g);
        assertEquals(3, mst.size());
    }

    @Test
    public void testKruskalPicksCheapestEdges() {
        // Diamond graph:  0--1(w=1), 0--2(w=10), 1--3(w=1), 2--3(w=10), 1--2(w=5)
        // MST should pick edges with weight 1, 1, 5  (total 7), NOT the weight-10 edges
        GraphModel g = new GraphModel(false);
        Vertex v0 = new Vertex(); Vertex v1 = new Vertex();
        Vertex v2 = new Vertex(); Vertex v3 = new Vertex();
        g.addVertex(v0); g.addVertex(v1); g.addVertex(v2); g.addVertex(v3);

        Edge e01 = new Edge(v0, v1); e01.setWeight(1);
        Edge e02 = new Edge(v0, v2); e02.setWeight(10);
        Edge e13 = new Edge(v1, v3); e13.setWeight(1);
        Edge e23 = new Edge(v2, v3); e23.setWeight(10);
        Edge e12 = new Edge(v1, v2); e12.setWeight(5);
        g.addEdge(e01); g.addEdge(e02); g.addEdge(e13);
        g.addEdge(e23); g.addEdge(e12);

        AbstractList<Edge> mst = Kruskal.findMinimumSpanningTree(g);

        assertEquals(3, mst.size());
        int totalWeight = mst.stream().mapToInt(Edge::getWeight).sum();
        assertEquals(7, totalWeight);
        assertFalse(mst.contains(e02));
        assertFalse(mst.contains(e23));
    }

    @Test
    public void testKruskalSingleVertex() {
        GraphModel g = new GraphModel(false);
        g.addVertex(new Vertex());
        AbstractList<Edge> mst = Kruskal.findMinimumSpanningTree(g);
        assertEquals(0, mst.size());
    }
}
