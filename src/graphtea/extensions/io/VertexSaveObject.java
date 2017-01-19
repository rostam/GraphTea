package graphtea.extensions.io;

import graphtea.graph.atributeset.VertexAttrSet;
import graphtea.graph.graph.Vertex;

import java.io.Serializable;

/**
 * Created by rostam on 18.12.15.
 * Save Object for correcting the save process
 */

public class VertexSaveObject implements Serializable {
    SerializedAttrSet<VertexAttrSet> attrs;

    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof VertexSaveObject) )  return false;
        return attrs.equals(((VertexSaveObject) obj).attrs);
    }

    public VertexSaveObject(Vertex v) {
        attrs=new SerializedAttrSet(new VertexAttrSet(v));
    }

    public Vertex getVertex() {
        Vertex v = new Vertex();
        attrs.feed(new VertexAttrSet(v));

        return v;
    }

}
