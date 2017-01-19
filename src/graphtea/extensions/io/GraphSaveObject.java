package graphtea.extensions.io;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.util.Vector;

/**
 * Created by rostam on 18.12.15.
 *
 */
public class GraphSaveObject implements Serializable {
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GraphSaveObject){
            GraphSaveObject t = (GraphSaveObject) obj;
            return this.vs.equals(t.vs) && this.es.equals(t.es);
        } else return false;
    }

    public Vector<VertexSaveObject> vs = new Vector<>();
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
        insertIntoGraph(g);
        return g;
    }

    public void insertIntoGraph(GraphModel g){
        for(VertexSaveObject v: vs) {
            g.addVertex(v.getVertex());
        }
        for(EdgeSaveObject e : es) {
            e.addEdge(g);
        }
        g.setDirected(directed);
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
        return bout.toByteArray();
    }

    public static byte[] getBytesOfGraphSaveObject(GraphSaveObject gso) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oop = new ObjectOutputStream(bout);
            oop.writeObject(gso);
            oop.flush();
            oop.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bout.toByteArray();
    }

    public static String graph2String(GraphModel g){
        return DatatypeConverter.printBase64Binary(getBytesOfGraph(g));
    }

    public static GraphModel String2Graph(String s){
        return getGraphFromBytes(DatatypeConverter.parseBase64Binary(s));
    }

    public static GraphSaveObject string2GraphSaveObject(String s){
        return getGraphSaveOobjectfromBytes(DatatypeConverter.parseBase64Binary(s));
    }

    public static GraphSaveObject getGraphSaveOobjectfromBytes(byte[] b){
        try {
            ObjectInputStream ois = new ObjectInputStream(
                    new ByteArrayInputStream(b));
            GraphSaveObject gso = (GraphSaveObject) ois.readObject();
            return gso;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }
    public static GraphModel getGraphFromBytes(byte[] b) {
        return getGraphSaveOobjectfromBytes(b).getG();
    }
}
