package graphtea.extensions.actions.product;

import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.plugins.graphgenerator.core.PositionGenerators;

public class GCartesianProduct extends GProduct {
    @Override
    public boolean compare(Vertex v1OfFirstG, Vertex v2OfFirstG, Vertex v1OfSecondG, Vertex v2OfSecondG) {
        return (v1OfFirstG == v2OfFirstG
                && g2.isEdge(v1OfSecondG, v2OfSecondG))
                || (v1OfSecondG == v2OfSecondG
                && g1.isEdge(v1OfFirstG, v2OfFirstG));
    }

    @Override
    public void setPositions(GraphModel g) {
        int n = g.getVerticesCount();
        GPoint[] ps = PositionGenerators.circle(250, 300, 300, n);
        setProductLabel(g);
        int count = 0;
        for (Vertex v : g) {
            v.setLocation(new GPoint(ps[count].x, ps[count].y));
            count++;
        }
    }
}
