package graphtea.extensions.actions;

import graphtea.extensions.actions.product.GProduct;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.plugins.graphgenerator.core.PositionGenerators;

import java.awt.*;

public class MyGComposition extends GProduct {
    @Override
    public boolean compare(Vertex v1OfFirstG, Vertex v2OfFirstG, Vertex v1OfSecondG, Vertex v2OfSecondG) {
        return (g1.isEdge(v1OfFirstG, v2OfFirstG) || (v1OfFirstG== v2OfFirstG && g2.isEdge(v1OfSecondG, v2OfSecondG)));
    }

    @Override
    public void setPositions(GraphModel g) {
        int n = g.getVerticesCount();
        Point[] ps = PositionGenerators.circle(200, 300, 300, n);
        int count = 0;
        for (Vertex v : g) {
            v.setLocation(new GPoint(ps[count].x, ps[count].y));
            count++;
        }
    }
}
