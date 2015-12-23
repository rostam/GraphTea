package graphtea.extensions.io;

import graphtea.graph.atributeset.EdgeAttrSet;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;

import java.io.Serializable;

/**
 * Created by rostam on 18.12.15.
 */
public class EdgeSaveObject implements Serializable {
    int source, target;
    SerializedAttrSet<EdgeAttrSet> attrs;


    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof EdgeSaveObject) )  return false;
        return attrs.equals(((EdgeSaveObject) obj).attrs);
    }



    public EdgeSaveObject(Edge e) {
        source = e.source.getId();
        target = e.target.getId();
        attrs = new SerializedAttrSet(new EdgeAttrSet(e));
    }

    public void addEdge(GraphModel g) {
        Edge e = new Edge(g.getVertex(source), g.getVertex(target));
        attrs.feed(new EdgeAttrSet(e));
        g.addEdge(e);
    }
}
