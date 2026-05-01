package graphtea.extensions.actions.product;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

/**
 * Symmetric difference product G ⊕ H (also called the XOR product).
 *
 * <p>Vertices (u1,v1) and (u2,v2) are adjacent iff exactly one of
 * {u1,u2} ∈ E(G) or {v1,v2} ∈ E(H) holds (exclusive-or).
 *
 * <p>G ⊕ G always produces a graph with no edges.
 */
public class GSymmDiff extends GProduct {
    @Override
    public boolean compare(Vertex v1OfFirstG, Vertex v2OfFirstG, Vertex v1OfSecondG, Vertex v2OfSecondG) {
        return (g1.isEdge(v1OfFirstG, v2OfFirstG) && !g2.isEdge(v1OfSecondG, v2OfSecondG))
                || (!g1.isEdge(v1OfFirstG, v2OfFirstG) && g2.isEdge(v1OfSecondG, v2OfSecondG));
    }

    @Override
    public void setPositions(GraphModel g) {
        setCircularPositions(g, 200);
    }
}
