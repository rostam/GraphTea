import graphtea.extensions.reports.ChromaticNumber;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The chromatic number search tests colourability with {@code t + 1} colours but used to start
 * at {@code t = 1}, so the smallest answer it could give was 2. Every edgeless graph — which
 * needs exactly one colour — came back as 2, and the empty graph, which needs none, came back
 * as 2 as well. These cases pin that down.
 */
public class ChromaticNumberTest {

    private static GraphModel graph(int vertexCount, int[][] edges) {
        GraphModel g = new GraphModel(false);
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

    private static int chromatic(GraphModel g) {
        return new ChromaticNumber().calculate(g);
    }

    @Test
    public void emptyGraphNeedsNoColours() {
        assertEquals(0, chromatic(graph(0, new int[][]{})));
    }

    @Test
    public void edgelessGraphsNeedOneColour() {
        assertEquals(1, chromatic(graph(1, new int[][]{})));
        assertEquals(1, chromatic(graph(3, new int[][]{})));
    }

    @Test
    public void bipartiteGraphsNeedTwoColours() {
        assertEquals(2, chromatic(graph(2, new int[][]{{0, 1}})));
        assertEquals(2, chromatic(graph(3, new int[][]{{0, 1}, {1, 2}})));
        assertEquals(2, chromatic(graph(4, new int[][]{{0, 1}, {1, 2}, {2, 3}, {3, 0}})));
        assertEquals(2, chromatic(graph(5, new int[][]{{0, 1}, {0, 2}, {0, 3}, {0, 4}})));
    }

    @Test
    public void oddCycleNeedsThreeColours() {
        assertEquals(3, chromatic(graph(5, new int[][]{{0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 0}})));
    }

    @Test
    public void completeGraphNeedsOneColourPerVertex() {
        assertEquals(3, chromatic(graph(3, new int[][]{{0, 1}, {1, 2}, {0, 2}})));
        assertEquals(4, chromatic(graph(4,
                new int[][]{{0, 1}, {0, 2}, {0, 3}, {1, 2}, {1, 3}, {2, 3}})));
    }

    @Test
    public void isolatedVerticesDoNotChangeTheAnswer() {
        assertEquals(3, chromatic(graph(4, new int[][]{{0, 1}, {1, 2}, {0, 2}})));
    }
}
