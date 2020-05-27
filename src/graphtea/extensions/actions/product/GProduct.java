package graphtea.extensions.actions.product;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.library.util.Pair;

public abstract class GProduct {

    protected GraphModel g1;
    protected GraphModel g2;

    public final GraphModel multiply(GraphModel g1, GraphModel g2) {
        GraphModel g = g1.createEmptyGraph();
        for (Vertex v1 : g1) {
            for (Vertex v2 : g2) {
                Vertex vnew = v1.getCopy();
                vnew.getProp().obj = new Pair<>(v1, v2);
                g.insertVertex(vnew);
            }
        }

        this.g1 = g1;
        this.g2 = g2;

        for (Vertex v1 : g) {
            for (Vertex v2 : g) {
                if (compare(((Pair<Vertex,Vertex>) v1.getProp().obj).first
                        , ((Pair<Vertex,Vertex>) v2.getProp().obj).first
                        , ((Pair<Vertex,Vertex>) v1.getProp().obj).second
                        , ((Pair<Vertex,Vertex>) v2.getProp().obj).second)) {
                    g.insertEdge((Edge) g1.edgeIterator().next().getCopy(v1, v2));
                }
            }
        }

        g.setDirected(g1.isDirected());

        return g;
    }

    public abstract boolean compare(Vertex v1OfFirstG, Vertex v2OfFirstG, Vertex v1OfSecondG, Vertex v2OfSecondG);

    public abstract void setPositions(GraphModel g);

    public void setProductLabel(GraphModel graphModel) {
        //for(Vertex v:graphModel) v.getSize().multiply(1.5);
        for(Vertex v:graphModel) {
            Pair<Vertex,Vertex> pp = (Pair<Vertex, Vertex>) v.getProp().obj;
            v.setLabel(pp.first.getLabel()+ "_"+pp.second.getLabel());
        }
    }
}
