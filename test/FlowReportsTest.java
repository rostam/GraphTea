import graphtea.extensions.reports.spectralreports.maxflowmincut.FlowEndpoints;
import graphtea.extensions.reports.spectralreports.maxflowmincut.MaximumFlow;
import graphtea.extensions.reports.spectralreports.maxflowmincut.MinimumCut;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Reports ▸ Connectivity ▸ Max Flow and Min Cut both returned null unconditionally — the line
 * that ran the algorithm was commented out — so the two menu entries had never produced
 * anything. They now read the source and sink from the selection.
 */
public class FlowReportsTest {

    /** v0 --5--> v1 --3--> v2, so the flow from v0 to v2 is bounded by the 3 edge. */
    private static GraphModel chain() {
        GraphModel g = new GraphModel(true);
        Vertex[] v = new Vertex[3];
        for (int i = 0; i < 3; i++) {
            v[i] = new Vertex();
            g.insertVertex(v[i]);
        }
        Edge e1 = new Edge(v[0], v[1]);
        e1.setWeight(5);
        g.insertEdge(e1);
        Edge e2 = new Edge(v[1], v[2]);
        e2.setWeight(3);
        g.insertEdge(e2);
        return g;
    }

    private static void select(GraphModel g, int... ids) {
        Vertex[] vs = g.getVertexArray();
        for (int id : ids) {
            vs[id].setSelected(true);
        }
    }

    @Test
    public void maxFlowUsesTheSelectedEndpoints() {
        GraphModel g = chain();
        select(g, 0, 2);
        assertEquals(3, new MaximumFlow().calculate(g));
    }

    @Test
    public void minCutUsesTheSelectedEndpoints() {
        GraphModel g = chain();
        select(g, 0, 2);
        assertEquals(3, new MinimumCut().calculate(g));
    }

    @Test
    public void withoutTwoSelectedVerticesTheReportExplainsItself() {
        GraphModel g = chain();
        Object none = new MaximumFlow().calculate(g);
        assertInstanceOf(String.class, none);
        assertEquals(FlowEndpoints.INSTRUCTION, none);

        select(g, 0, 1, 2);
        assertEquals(FlowEndpoints.INSTRUCTION, new MinimumCut().calculate(g));
    }

    @Test
    public void endpointsAreReadInOrder() {
        GraphModel g = chain();
        select(g, 0, 2);
        FlowEndpoints ends = FlowEndpoints.fromSelection(g);
        assertNotNull(ends);
        assertEquals(g.getVertexArray()[0], ends.source());
        assertEquals(g.getVertexArray()[2], ends.sink());
    }

    @Test
    public void endpointsAreNullWhenTheSelectionIsWrongOrAbsent() {
        assertNull(FlowEndpoints.fromSelection(null));
        assertNull(FlowEndpoints.fromSelection(chain()));

        GraphModel one = chain();
        select(one, 1);
        assertNull(FlowEndpoints.fromSelection(one));
    }
}
