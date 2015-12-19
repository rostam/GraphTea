package graphtea.extensions.io;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

import java.io.*;
import java.util.Vector;

/**
 * Created by rostam on 18.12.15.
 *
 */
public class GraphSaveObject implements Serializable {
    Vector<VertexSaveObject> vs = new Vector<>();
    Vector<EdgeSaveObject> es = new Vector<>();
    boolean directed = false;
    String label = "";
    public GraphSaveObject(GraphModel g) {
        directed    = g.isDirected();
        label       = g.getLabel();

        for(Vertex v: g) vs.add(new VertexSaveObject(v));
        for(Edge e : g.edges()) es.add(new EdgeSaveObject(e));
    }

    public GraphModel getG() {
        GraphModel g = new GraphModel();
        System.out.println(1);
        for(VertexSaveObject v: vs) {
            g.addVertex(v.getVertex());
        }


        System.out.println(2);
        for(EdgeSaveObject e : es) {
            System.out.println(e);
            e.addEdge(g);
        }
        g.setDirected(directed);
        return g;
    }

    public static byte[] getBytesOfGraph(GraphModel g) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oop = new ObjectOutputStream(bout);
            oop.writeObject(new GraphSaveObject(g));
            oop.flush();
            oop.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static GraphModel getGraphFromBytes(byte[] b) {
        try {
            ObjectInputStream ois = new ObjectInputStream(
                    new ByteArrayInputStream(b));
            GraphSaveObject gso = (GraphSaveObject) ois.readObject();
            return gso.getG();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
