package graphtea.extensions.actions.product;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

/**
 * Cartesian product G □ H.
 *
 * <p>Vertices (u1,v1) and (u2,v2) are adjacent iff either
 * u1 = u2 and {v1,v2} ∈ E(H), or v1 = v2 and {u1,u2} ∈ E(G).
 *
 * <p>For paths Pm □ Pn: |V| = m·n, |E| = m·(n−1) + n·(m−1).
 */
public class GCartesianProduct extends GProduct {
    @Override
    public boolean compare(Vertex v1OfFirstG, Vertex v2OfFirstG, Vertex v1OfSecondG, Vertex v2OfSecondG) {
        return (v1OfFirstG == v2OfFirstG && g2.isEdge(v1OfSecondG, v2OfSecondG))
                || (v1OfSecondG == v2OfSecondG && g1.isEdge(v1OfFirstG, v2OfFirstG));
    }

    @Override
    public void setPositions(GraphModel g) {
        setProductLabel(g);
        setCircularPositions(g, 250);
    }
}
