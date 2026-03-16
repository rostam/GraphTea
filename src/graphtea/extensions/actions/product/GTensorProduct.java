package graphtea.extensions.actions.product;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

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
