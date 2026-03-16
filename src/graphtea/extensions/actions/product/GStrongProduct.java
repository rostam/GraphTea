package graphtea.extensions.actions.product;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

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
