import graphtea.extensions.reports.hamilton.HamiltonianCycleExtension;
import graphtea.extensions.reports.hamilton.HamiltonianPathExtension;
import graphtea.extensions.reports.matching.MaxMatchingExtension;
import graphtea.extensions.reports.spanningtree.MSTPrimExtension;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.SubGraph;
import graphtea.graph.graph.Vertex;
import graphtea.plugins.main.ui.SubGraphRenderer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Several reports ran an algorithm over the adjacency matrix and then looked the resulting Edge
 * objects back up with GraphModel.getEdge, which is direction sensitive and returns null for a
 * non-adjacent pair. The nulls went straight into a SubGraph, and SubGraphRenderer dereferences
 * every edge, so the report died with a NullPointerException on the event dispatch thread and
 * the user got no result window and no explanation.
 *
 * <p>Three separate triggers were reproducible: MSTPrim on a disconnected undirected graph,
 * and Hamiltonian cycle, MSTPrim and maximum matching on any directed graph.
 */
public class SubGraphNullEdgeTest {

    private static GraphModel graph(boolean directed, int vertexCount, int[][] edges) {
        GraphModel g = new GraphModel(directed);
        Vertex[] v = new Vertex[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            v[i] = new Vertex();
            g.insertVertex(v[i]);
        }
        for (int[] e : edges) {
            g.insertEdge(new Edge(v[e[0]], v[e[1]]));
        }
        return g;
    }

    private static void assertNoNullEdges(String what, SubGraph sg) {
        assertNotNull(sg, what + " returned no subgraph");
        assertFalse(sg.edges.contains(null), what + " put a null edge in the subgraph");
        for (Edge e : sg.edges) {
            assertNotNull(e.source, what + " produced an edge with no source");
            assertNotNull(e.target, what + " produced an edge with no target");
        }
    }

    private static final int[][] TRIANGLE = {{0, 1}, {1, 2}, {2, 0}};
    private static final int[][] PATH4 = {{0, 1}, {1, 2}, {2, 3}};
    private static final int[][] K4 = {{0, 1}, {0, 2}, {0, 3}, {1, 2}, {1, 3}, {2, 3}};
    private static final int[][] TWO_COMPONENTS = {{0, 1}, {2, 3}};

    @Test
    public void primOnADisconnectedGraphSkipsAbsentEdges() {
        // prim reports a parent for a vertex in another component, where no edge exists.
        assertNoNullEdges("MSTPrim on a disconnected graph",
                new MSTPrimExtension().calculate(graph(false, 4, TWO_COMPONENTS)));
    }

    @Test
    public void primHandlesDirectedGraphs() {
        SubGraph sg = new MSTPrimExtension().calculate(graph(true, 4, K4));
        assertNoNullEdges("MSTPrim on a directed graph", sg);
        // Looking the edge up in one direction only also silently discarded real edges.
        assertEquals(3, sg.edges.size(), "a 4-vertex spanning tree has 3 edges");
    }

    @Test
    public void hamiltonianCycleClosesInTheRightDirection() {
        SubGraph sg = new HamiltonianCycleExtension().calculate(graph(true, 3, TRIANGLE));
        assertNoNullEdges("Hamiltonian cycle on a directed graph", sg);
        assertEquals(3, sg.edges.size(), "a 3-cycle has 3 edges");
    }

    @Test
    public void hamiltonianReportsOnUndirectedGraphsAreUnchanged() {
        assertNoNullEdges("Hamiltonian cycle",
                new HamiltonianCycleExtension().calculate(graph(false, 3, TRIANGLE)));
        assertNoNullEdges("Hamiltonian path",
                new HamiltonianPathExtension().calculate(graph(false, 4, PATH4)));
    }

    @Test
    public void maximumMatchingHandlesDirectedGraphs() {
        Object result = new MaxMatchingExtension().calculate(graph(true, 4, PATH4));
        for (Object o : (List<?>) result) {
            if (o instanceof SubGraph sg) {
                assertNoNullEdges("Maximum matching on a directed graph", sg);
                assertEquals(2, sg.edges.size(), "a 4-path matches into 2 edges");
            }
        }
    }

    @Test
    public void theRendererSurvivesAMalformedSubgraph() {
        // Whatever produced it, displaying a subgraph must not be able to throw.
        GraphModel g = graph(false, 2, new int[][]{{0, 1}});
        SubGraph sg = new SubGraph();
        sg.vertices.add(g.getVertexArray()[0]);
        sg.vertices.add(null);
        sg.edges.add(null);
        assertDoesNotThrow(() -> new SubGraphRenderer().getRendererComponent(sg));
    }

    @Test
    public void theRendererSurvivesASubgraphOfOnlyNulls() {
        SubGraph sg = new SubGraph();
        sg.edges.add(null);
        assertDoesNotThrow(() -> new SubGraphRenderer().getRendererComponent(sg));
    }
}
