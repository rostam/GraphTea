package graphtea.extensions.io;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

import java.io.Serializable;
import java.util.Vector;

/**
 * Created by rostam on 18.12.15.
 *
 */
public class GraphSaveObject implements Serializable {
    Vector<VertexSaveObject> vs = new Vector<>();
    Vector<EdgeSaveObject> es = new Vector<>();
    boolean directed = false;
    public GraphSaveObject(GraphModel g) {
        directed=g.isDirected();
        for(Vertex v: g) vs.add(new VertexSaveObject(v));
        for(Edge e : g.edges()) es.add(new EdgeSaveObject(e));
    }

    public GraphModel getG() {
        GraphModel g = new GraphModel();
        for(VertexSaveObject v: vs) {
            g.addVertex(v.getVertex());
        }

        for(EdgeSaveObject e : es) {
            e.addEdge(g);
        }
        g.setDirected(directed);
        return g;
    }
}
