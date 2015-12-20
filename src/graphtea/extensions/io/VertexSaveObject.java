package graphtea.extensions.io;

import graphtea.graph.atributeset.VertexAttrSet;
import graphtea.graph.graph.GraphPoint;
import graphtea.graph.graph.Vertex;
import graphtea.graph.old.GShape;
import graphtea.graph.old.StrokeSaveObject;

import java.io.Serializable;

/**
 * Created by rostam on 18.12.15.
 * Save Object for correcting the save process
 */

public class VertexSaveObject implements Serializable {
    String shape;
    StrokeSaveObject gs;
    String label;
    int color;
    boolean mark,selected;
    GraphPoint loc, shapeSize, labelLoc;
    SerializedAttrSet<VertexAttrSet> attrs;

    public VertexSaveObject(Vertex v) {
        attrs=new SerializedAttrSet(new VertexAttrSet(v));
    }

    public Vertex getVertex() {
        Vertex v = new Vertex();
        attrs.feed(new VertexAttrSet(v));

        return v;
    }

}
