// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.actions.EdgeSemitotalGraph;
import graphtea.extensions.actions.GComposition;
import graphtea.extensions.actions.GDisjunction;
import graphtea.extensions.actions.GraphPower;
import graphtea.extensions.actions.TotalGraph;
import graphtea.extensions.actions.VertexSemitotalGraph;
import graphtea.extensions.actions.product.GSymmDiff;
import graphtea.extensions.algorithms.GraphSum;
import graphtea.extensions.algorithms.GraphUnion;
import graphtea.extensions.generators.CompleteGraphGenerator;
import graphtea.extensions.generators.PathGenerator;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for graph operators not covered by existing test files:
 *   LineGraph, GraphUnion, GraphSum, TotalGraph, VertexSemitotalGraph,
 *   EdgeSemitotalGraph, GComposition, GDisjunction, GSymmDiff, GraphPower.
 *
 * Naming convention for known counts:
 *   n = vertex count, m = edge count.
 */
public class OperatorsTest {

    // -----------------------------------------------------------------------
    // Helpers
    // -----------------------------------------------------------------------

    private GraphModel path(int n) { return PathGenerator.generatePath(n); }
    private GraphModel complete(int n) { return CompleteGraphGenerator.generateCompleteGraph(n); }

    /** Builds a simple path P_n manually (avoids generator side-effects). */
    private GraphModel makePath(int n) {
        GraphModel g = new GraphModel(false);
        Vertex prev = null;
        for (int i = 0; i < n; i++) {
            Vertex v = new Vertex();
            g.insertVertex(v);
            if (prev != null) g.insertEdge(new Edge(prev, v));
            prev = v;
        }
        return g;
    }

    // -----------------------------------------------------------------------
    // Line graph  L(G)
    //   |V(L)| = |E(G)|
    //   |E(L)| = Σ_v C(deg(v), 2)
    // -----------------------------------------------------------------------

    @Test
    public void testLineGraphP3VertexCount() {
        // P_3: 3 vertices, 2 edges → L(P_3) has 2 vertices
        GraphModel L = AlgorithmUtils.createLineGraph(path(3));
        assertEquals(2, L.getVerticesCount());
    }

    @Test
    public void testLineGraphP3EdgeCount() {
        // L(P_3): degrees [1,2,1] → C(1,2)+C(2,2)+C(1,2) = 0+1+0 = 1 edge
        GraphModel L = AlgorithmUtils.createLineGraph(path(3));
        assertEquals(1, L.getEdgesCount());
    }

    @Test
    public void testLineGraphK3() {
        // K_3: 3 vertices (all degree 2), 3 edges → L(K_3) = K_3: 3v, 3e
        GraphModel L = AlgorithmUtils.createLineGraph(complete(3));
        assertEquals(3, L.getVerticesCount());
        assertEquals(3, L.getEdgesCount());
    }

    @Test
    public void testLineGraphP4() {
        // P_4: degrees [1,2,2,1], 3 edges → L(P_4) = P_3: 3v, 2e
        GraphModel L = AlgorithmUtils.createLineGraph(path(4));
        assertEquals(3, L.getVerticesCount());
        assertEquals(2, L.getEdgesCount());
    }

    @Test
    public void testLineGraphDegreeProperty() {
        // For each vertex e=(u,v) in L(G): deg_L(e) = deg_G(u) + deg_G(v) - 2
        GraphModel g = path(5);
        GraphModel L = AlgorithmUtils.createLineGraph(g);
        for (Vertex lv : L) {
            Edge e = (Edge) lv.getProp().obj;
            int expected = g.getDegree(e.source) + g.getDegree(e.target) - 2;
            assertEquals(expected, L.getDegree(lv),
                    "Degree of line-graph vertex should equal deg(u)+deg(v)-2");
        }
    }

    // -----------------------------------------------------------------------
    // GraphUnion
    //   |V| = n1 + n2,  |E| = m1 + m2  (disjoint union, no cross edges)
    // -----------------------------------------------------------------------

    @Test
    public void testGraphUnionVertexCount() {
        // P_3 (3v) ∪ K_3 (3v) = 6v
        GraphModel g = GraphUnion.union(path(3), complete(3));
        assertEquals(6, g.getVerticesCount());
    }

    @Test
    public void testGraphUnionEdgeCount() {
        // P_3 (2e) ∪ K_3 (3e) = 5e
        GraphModel g = GraphUnion.union(path(3), complete(3));
        assertEquals(5, g.getEdgesCount());
    }

    @Test
    public void testGraphUnionIsDisjoint() {
        // Union must add no cross edges between the two parts.
        // Join adds n1*n2 extra; union adds 0.
        // Verify by comparing edge counts with those of both inputs.
        GraphModel p2 = path(2);
        GraphModel p3 = path(3);
        GraphModel u = GraphUnion.union(p2, p3);
        assertEquals(p2.getEdgesCount() + p3.getEdgesCount(), u.getEdgesCount());
    }

    // -----------------------------------------------------------------------
    // GraphSum (graph join — union + all cross edges between the two parts)
    //
    // Bug fixed: the cross-edge loop previously used temp.get(temp.get(v2))
    // which always returned null (causing NPE), instead of temp.get(v2).
    //
    //   |V| = n1 + n2,  |E| = m1 + m2 + n1*n2
    // -----------------------------------------------------------------------

    @Test
    public void testGraphSumVertexCount() {
        // P_2 + P_2: 2+2 = 4 vertices
        GraphModel g = GraphSum.sum(path(2), path(2));
        assertEquals(4, g.getVerticesCount());
    }

    @Test
    public void testGraphSumEdgeCount() {
        // P_2 (1e) + P_2 (1e): 1+1+2*2 = 6 edges
        GraphModel g = GraphSum.sum(path(2), path(2));
        assertEquals(6, g.getEdgesCount());
    }

    @Test
    public void testGraphSumCrossEdgesPresent() {
        // Sum must have strictly more edges than union (cross edges added).
        GraphModel p2a = path(2);
        GraphModel p2b = path(2);
        GraphModel sum  = GraphSum.sum(p2a, p2b);
        GraphModel union = GraphUnion.union(path(2), path(2));
        assertTrue(sum.getEdgesCount() > union.getEdgesCount(),
                "Sum must have more edges than union (cross edges)");
    }

    @Test
    public void testGraphSumP3K3() {
        // P_3 (3v,2e) + K_3 (3v,3e): 6v, 2+3+9 = 14e
        GraphModel g = GraphSum.sum(path(3), complete(3));
        assertEquals(6, g.getVerticesCount());
        assertEquals(14, g.getEdgesCount());
    }

    // -----------------------------------------------------------------------
    // TotalGraph T(G) with k=1 (one subdivision vertex per edge)
    //   |V| = n + m
    //   |E| = 2m (subdivision edges)
    //       + m (curved original edges)
    //       + |E(L(G))| (subdivision-vertex adjacency = line-graph edges)
    //
    // For P_3 (n=3, m=2, L(P_3) has 1 edge):
    //   |V| = 5,  |E| = 4 + 2 + 1 = 7
    // -----------------------------------------------------------------------

    @Test
    public void testTotalGraphP3k1Vertices() {
        TotalGraph t = new TotalGraph();
        t.k = 1;
        GraphModel g = t.apply(path(3));
        assertEquals(5, g.getVerticesCount());
    }

    @Test
    public void testTotalGraphP3k1Edges() {
        TotalGraph t = new TotalGraph();
        t.k = 1;
        GraphModel g = t.apply(path(3));
        assertEquals(7, g.getEdgesCount());
    }

    @Test
    public void testTotalGraphK3k1() {
        // K_3: n=3, m=3.  L(K_3)=K_3 has 3 edges.
        // |V| = 3+3 = 6,  |E| = 6 + 3 + 3 = 12
        TotalGraph t = new TotalGraph();
        t.k = 1;
        GraphModel g = t.apply(complete(3));
        assertEquals(6, g.getVerticesCount());
        assertEquals(12, g.getEdgesCount());
    }

    // -----------------------------------------------------------------------
    // VertexSemitotalGraph (k=1): subdivision + curved original edges only
    //   |V| = n + m,  |E| = 2m + m = 3m
    //
    // For P_3: |V| = 5,  |E| = 6
    // -----------------------------------------------------------------------

    @Test
    public void testVertexSemitotalP3k1Vertices() {
        VertexSemitotalGraph vst = new VertexSemitotalGraph();
        vst.k = 1;
        GraphModel g = vst.apply(path(3));
        assertEquals(5, g.getVerticesCount());
    }

    @Test
    public void testVertexSemitotalP3k1Edges() {
        VertexSemitotalGraph vst = new VertexSemitotalGraph();
        vst.k = 1;
        GraphModel g = vst.apply(path(3));
        assertEquals(6, g.getEdgesCount());
    }

    // -----------------------------------------------------------------------
    // EdgeSemitotalGraph (k=1): subdivision + subdivision-vertex adjacency edges
    //   |V| = n + m,  |E| = 2m + |E(L(G))|
    //
    // For P_3 (L(P_3) has 1 edge): |V| = 5,  |E| = 4 + 1 = 5
    // -----------------------------------------------------------------------

    @Test
    public void testEdgeSemitotalP3k1Vertices() {
        EdgeSemitotalGraph est = new EdgeSemitotalGraph();
        est.k = 1;
        GraphModel g = est.apply(path(3));
        assertEquals(5, g.getVerticesCount());
    }

    @Test
    public void testEdgeSemitotalP3k1Edges() {
        EdgeSemitotalGraph est = new EdgeSemitotalGraph();
        est.k = 1;
        GraphModel g = est.apply(path(3));
        assertEquals(5, g.getEdgesCount());
    }

    // -----------------------------------------------------------------------
    // GComposition (lexicographic product)  G[H]
    //   |V| = n1 * n2
    //   |E| = |E(G)| * n2^2 + n1 * |E(H)|
    //
    // For P_3[K_2]: |V| = 6,  |E| = 2*4 + 3*1 = 11
    // -----------------------------------------------------------------------

    @Test
    public void testCompositionVertexCount() {
        GraphModel g = new GComposition().multiply(path(3), path(2));
        assertEquals(6, g.getVerticesCount());
    }

    @Test
    public void testCompositionEdgeCount() {
        // P_3 (n=3, m=2) [ K_2/P_2 (n=2, m=1) ]: 2*4 + 3*1 = 11
        GraphModel g = new GComposition().multiply(path(3), path(2));
        assertEquals(11, g.getEdgesCount());
    }

    @Test
    public void testCompositionP2K2IsK4() {
        // P_2[K_2]: every pair of 4 product vertices is connected → K_4 (6 edges)
        GraphModel g = new GComposition().multiply(path(2), path(2));
        assertEquals(4, g.getVerticesCount());
        assertEquals(6, g.getEdgesCount());
    }

    // -----------------------------------------------------------------------
    // GDisjunction
    //   (v1,u1)-(v2,u2)  iff  v1-v2 in G  OR  u1-u2 in H
    //   |V| = n1 * n2
    //   |E| = |E(G)| * n2^2 + n1^2 * |E(H)| - 2*|E(G)|*|E(H)|
    //
    // For P_3 ∨ K_2: |V| = 6,  |E| = 2*4 + 9*1 - 2*2*1 = 13
    // -----------------------------------------------------------------------

    @Test
    public void testDisjunctionVertexCount() {
        GraphModel g = new GDisjunction().multiply(path(3), path(2));
        assertEquals(6, g.getVerticesCount());
    }

    @Test
    public void testDisjunctionEdgeCount() {
        GraphModel g = new GDisjunction().multiply(path(3), path(2));
        assertEquals(13, g.getEdgesCount());
    }

    @Test
    public void testDisjunctionP2P2IsK4() {
        // P_2 ∨ P_2: every pair of vertices satisfies "some edge in G or H"  → K_4
        GraphModel g = new GDisjunction().multiply(path(2), path(2));
        assertEquals(4, g.getVerticesCount());
        assertEquals(6, g.getEdgesCount());
    }

    // -----------------------------------------------------------------------
    // GSymmDiff (symmetric difference)
    //   (v1,u1)-(v2,u2)  iff  v1-v2 in G  XOR  u1-u2 in H
    //
    // For P_2 ⊕ P_2: 4 vertices.
    //   A=(v0,u0), B=(v0,u1), C=(v1,u0), D=(v1,u1)
    //   Edges: A-B (only H), A-C (only G), B-D (only G), C-D (only H) → 4 edges
    // -----------------------------------------------------------------------

    @Test
    public void testSymmDiffVertexCount() {
        GraphModel g = new GSymmDiff().multiply(path(2), path(2));
        assertEquals(4, g.getVerticesCount());
    }

    @Test
    public void testSymmDiffP2P2EdgeCount() {
        GraphModel g = new GSymmDiff().multiply(path(2), path(2));
        assertEquals(4, g.getEdgesCount());
    }

    @Test
    public void testSymmDiffNoEdgesWhenBothHaveEdge() {
        // When both graphs have the same edge, symmetric difference excludes it.
        // P_2 ⊕ P_2: pair (v0,u0)-(v1,u1) has G-edge AND H-edge → NOT in result.
        // Verify by checking the result is a proper cycle (not K4).
        GraphModel g = new GSymmDiff().multiply(path(2), path(2));
        assertNotEquals(6, g.getEdgesCount(), "SymmDiff of P_2 with P_2 must not be K_4");
    }

    // -----------------------------------------------------------------------
    // GraphPower G^k  (adds edges between vertices at distance ≤ k)
    //   P_4^2: adds v0-v2 and v1-v3  →  3 original + 2 new = 5 edges
    //   K_3^2: already complete, no new edges  →  3 edges
    //   P_5^2: adds v0-v2, v1-v3, v2-v4  →  4 + 3 = 7 edges
    // -----------------------------------------------------------------------

    @Test
    public void testPowerP4k2VertexCount() {
        GraphModel g = makePath(4);
        GraphPower.applyPower(g, 2);
        assertEquals(4, g.getVerticesCount());
    }

    @Test
    public void testPowerP4k2EdgeCount() {
        GraphModel g = makePath(4);
        GraphPower.applyPower(g, 2);
        assertEquals(5, g.getEdgesCount());
    }

    @Test
    public void testPowerK3k2NoNewEdges() {
        // K_3 is already complete; G^2 = G when G is complete.
        GraphModel g = complete(3);
        GraphPower.applyPower(g, 2);
        assertEquals(3, g.getEdgesCount(), "K_3 squared should still have 3 edges");
    }

    @Test
    public void testPowerP5k2EdgeCount() {
        // P_5 (4 edges) + distance-2 pairs (v0v2, v1v3, v2v4) = 7 edges
        GraphModel g = makePath(5);
        GraphPower.applyPower(g, 2);
        assertEquals(7, g.getEdgesCount());
    }

    @Test
    public void testPowerP4k3IsComplete() {
        // P_4^3: diameter of P_4 is 3, so every pair is within distance 3 → K_4
        GraphModel g = makePath(4);
        GraphPower.applyPower(g, 3);
        assertEquals(6, g.getEdgesCount(), "P_4^3 should be K_4 (all 6 edges)");
    }
}
