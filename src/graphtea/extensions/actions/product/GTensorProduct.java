package graphtea.extensions.actions.product;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

/**
 * Tensor (categorical / direct / Kronecker) product G × H.
 *
 * <p>Vertices (u1,v1) and (u2,v2) are adjacent iff
 * {u1,u2} ∈ E(G) AND {v1,v2} ∈ E(H).
 *
 * <p>For paths Pm × Pn: |V| = m·n, |E| = 2·(m−1)·(n−1).
 */
public class GTensorProduct extends GProduct {
    @Override
    public boolean compare(Vertex v1OfFirstG, Vertex v2OfFirstG, Vertex v1OfSecondG, Vertex v2OfSecondG) {
        return g1.isEdge(v1OfFirstG, v2OfFirstG) && g2.isEdge(v1OfSecondG, v2OfSecondG);
    }

    @Override
    public void setPositions(GraphModel g) {
        g.setDirected(g1.isDirected());
        setProductLabel(g);
        setCircularPositions(g, 250);
    }
}
