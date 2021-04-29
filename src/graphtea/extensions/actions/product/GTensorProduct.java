package graphtea.extensions.actions.product;

import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.plugins.graphgenerator.core.PositionGenerators;

public class GTensorProduct extends GProduct {
    @Override
    public boolean compare(Vertex v1OfFirstG, Vertex v2OfFirstG, Vertex v1OfSecondG, Vertex v2OfSecondG) {
        return g1.isEdge(v1OfFirstG, v2OfFirstG) && g2.isEdge(v1OfSecondG, v2OfSecondG);
    }

    @Override
    public void setPositions(GraphModel g) {
        setProductLabel(g);
        g.setDirected(g1.isDirected());
        int n = g.getVerticesCount();
        GPoint[] ps = PositionGenerators.circle(250, 300, 300, n);
        int count = 0;
        for (Vertex v : g) {
            v.setLocation(new GPoint(ps[count].x, ps[count].y));
            count++;
        }
    }
}
