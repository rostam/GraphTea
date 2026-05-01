package graphtea.extensions.actions.product;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

/**
 * Strong product G ⊠ H (also called the strong direct product or normal product).
 *
 * <p>Vertices (u1,v1) and (u2,v2) are adjacent iff:
 * <ul>
 *   <li>u1 = u2 and {v1,v2} ∈ E(H) (Cartesian component), or</li>
 *   <li>v1 = v2 and {u1,u2} ∈ E(G) (Cartesian component), or</li>
 *   <li>{u1,u2} ∈ E(G) and {v1,v2} ∈ E(H) (tensor component).</li>
 * </ul>
 */
public class GStrongProduct extends GProduct {
    @Override
    public boolean compare(Vertex v1OfFirstG, Vertex v2OfFirstG, Vertex v1OfSecondG, Vertex v2OfSecondG) {
        return (v1OfFirstG == v2OfFirstG && g2.isEdge(v1OfSecondG, v2OfSecondG))
                || (v1OfSecondG == v2OfSecondG && g1.isEdge(v1OfFirstG, v2OfFirstG))
                || (g1.isEdge(v1OfFirstG, v2OfFirstG) && g2.isEdge(v1OfSecondG, v2OfSecondG));
    }

    @Override
    public void setPositions(GraphModel g) {
        setProductLabel(g);
        setCircularPositions(g, 250);
    }
}
