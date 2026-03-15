import graphtea.extensions.algorithms.EdgeInduced;
import graphtea.extensions.algorithms.GraphUnion;
import graphtea.extensions.algorithms.VertexInduced;
import graphtea.extensions.generators.CircleGenerator;
import graphtea.extensions.generators.CompleteGraphGenerator;
import graphtea.extensions.generators.PathGenerator;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.library.algorithms.util.ConnectivityChecker;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for GraphUnion, VertexInduced, and EdgeInduced.
 */
public class GraphOperationsTest {

    // ==== GraphUnion ====

    @Test
    public void testUnionVertexCount() {
        GraphModel g1 = CircleGenerator.generateCircle(3);
        GraphModel g2 = CircleGenerator.generateCircle(4);
        GraphModel u = GraphUnion.union(g1, g2);
        assertEquals(g1.getVerticesCount() + g2.getVerticesCount(), u.getVerticesCount());
    }

    @Test
    public void testUnionEdgeCount() {
        GraphModel g1 = PathGenerator.generatePath(3);  // 2 edges
        GraphModel g2 = PathGenerator.generatePath(4);  // 3 edges
        GraphModel u = GraphUnion.union(g1, g2);
        assertEquals(g1.getEdgesCount() + g2.getEdgesCount(), u.getEdgesCount());
    }

    @Test
    public void testUnionOfTwoCompleteGraphs() {
        int n = 3;
        GraphModel g1 = CompleteGraphGenerator.generateCompleteGraph(n);
        GraphModel g2 = CompleteGraphGenerator.generateCompleteGraph(n);
        GraphModel u = GraphUnion.union(g1, g2);
        assertEquals(2 * n, u.getVerticesCount());
        assertEquals(2 * (n * (n - 1) / 2), u.getEdgesCount());
    }

    @Test
    public void testUnionPreservesOriginals() {
        GraphModel g1 = CircleGenerator.generateCircle(3);
        GraphModel g2 = CircleGenerator.generateCircle(3);
        int v1 = g1.getVerticesCount(), e1 = g1.getEdgesCount();
        int v2 = g2.getVerticesCount(), e2 = g2.getEdgesCount();
        GraphUnion.union(g1, g2);
        assertEquals(v1, g1.getVerticesCount());
        assertEquals(e1, g1.getEdgesCount());
        assertEquals(v2, g2.getVerticesCount());
        assertEquals(e2, g2.getEdgesCount());
    }

    // ==== VertexInduced ====

    @Test
    public void testVertexInducedOnCompleteGraph() {
        // K4: pick 3 vertices → induced subgraph should be K3 (3 edges)
        GraphModel k4 = CompleteGraphGenerator.generateCompleteGraph(4);
        Iterator<Vertex> it = k4.iterator();
        List<Vertex> subset = new ArrayList<>();
        subset.add(it.next());
        subset.add(it.next());
        subset.add(it.next());
        // leave one vertex out

        GraphModel induced = VertexInduced.induced(k4, subset);
        assertEquals(3, induced.getVerticesCount());
        assertEquals(3, induced.getEdgesCount()); // K3
    }

    @Test
    public void testVertexInducedEmptySubset() {
        GraphModel g = CompleteGraphGenerator.generateCompleteGraph(4);
        GraphModel induced = VertexInduced.induced(g, new ArrayList<>());
        assertEquals(0, induced.getVerticesCount());
        assertEquals(0, induced.getEdgesCount());
    }

    @Test
    public void testVertexInducedAllVertices() {
        GraphModel g = CircleGenerator.generateCircle(5);
        List<Vertex> all = new ArrayList<>();
        for (Vertex v : g) all.add(v);
        GraphModel induced = VertexInduced.induced(g, all);
        assertEquals(g.getVerticesCount(), induced.getVerticesCount());
        assertEquals(g.getEdgesCount(), induced.getEdgesCount());
    }

    @Test
    public void testVertexInducedSingleVertex() {
        GraphModel g = CompleteGraphGenerator.generateCompleteGraph(4);
        Vertex v = g.iterator().next();
        List<Vertex> subset = new ArrayList<>();
        subset.add(v);
        GraphModel induced = VertexInduced.induced(g, subset);
        assertEquals(1, induced.getVerticesCount());
        assertEquals(0, induced.getEdgesCount());
    }

    @Test
    public void testVertexInducedTwoAdjacentVertices() {
        GraphModel g = CompleteGraphGenerator.generateCompleteGraph(4);
        Iterator<Vertex> it = g.iterator();
        List<Vertex> subset = new ArrayList<>();
        subset.add(it.next());
        subset.add(it.next());
        GraphModel induced = VertexInduced.induced(g, subset);
        assertEquals(2, induced.getVerticesCount());
        assertEquals(1, induced.getEdgesCount()); // exactly one edge between them
    }

    // ==== EdgeInduced ====
    // edgeInduced(g, S) produces a copy of g with all edges in S removed;
    // isolated vertices resulting from removal are also dropped.

    @Test
    public void testEdgeInducedRemovesOneEdge() {
        // Path 3: v1-v2-v3 (2 edges). Remove one edge → 1 edge left.
        GraphModel g = PathGenerator.generatePath(3);
        Iterator<Edge> eit = g.edgeIterator();
        List<Edge> toRemove = new ArrayList<>();
        toRemove.add(eit.next());   // remove first edge

        GraphModel result = EdgeInduced.edgeInduced(g, toRemove);
        assertEquals(g.getEdgesCount() - 1, result.getEdgesCount());
    }

    @Test
    public void testEdgeInducedPreservesVertexCount() {
        // K4: removing one edge leaves all 4 vertices (no isolated vertices created)
        GraphModel g = CompleteGraphGenerator.generateCompleteGraph(4);
        Iterator<Edge> eit = g.edgeIterator();
        List<Edge> toRemove = new ArrayList<>();
        toRemove.add(eit.next());

        GraphModel result = EdgeInduced.edgeInduced(g, toRemove);
        assertEquals(g.getEdgesCount() - 1, result.getEdgesCount());
        assertEquals(4, result.getVerticesCount());
    }

    @Test
    public void testEdgeInducedEmptySetLeavesGraphUnchanged() {
        GraphModel g = CircleGenerator.generateCircle(4);
        GraphModel result = EdgeInduced.edgeInduced(g, new ArrayList<>());
        assertEquals(g.getVerticesCount(), result.getVerticesCount());
        assertEquals(g.getEdgesCount(), result.getEdgesCount());
    }

    @Test
    public void testEdgeInducedDropsIsolatedVertices() {
        // Path 2 (one edge, two vertices). Remove the only edge → isolated vertices
        // should both be removed.
        GraphModel g = new GraphModel(false);
        Vertex v1 = new Vertex(), v2 = new Vertex();
        g.insertVertex(v1);
        g.insertVertex(v2);
        Edge e = new Edge(v1, v2);
        g.insertEdge(e);

        List<Edge> toRemove = new ArrayList<>();
        toRemove.add(e);
        GraphModel result = EdgeInduced.edgeInduced(g, toRemove);
        assertEquals(0, result.getEdgesCount());
        assertEquals(0, result.getVerticesCount());
    }
}
