package graphtea.graph.io;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.GraphPoint;
import graphtea.graph.graph.Vertex;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import com.google.gson.Gson;

/**
 * this class converts the GraphModel object to json and back
 */
public class GraphJSON {
    public Vector<String> vertices = new Vector<String>();
    public Vector<Vector<Integer>> edges = new Vector<Vector<Integer>>();
    public HashMap<Integer, GraphPoint> pos = new HashMap<Integer, GraphPoint>();
    public String name;
    public boolean directed;

    public void init(GraphModel gm) {
        for (Vertex v : gm) {
            vertices.add(v.getLabel());
            pos.put(v.getId(), v.getLocation());
        }
        for (Edge e : gm.getEdges()) {
            edges.add(new Vector<Integer>(Arrays.asList(e.source.getId(), e.target.getId())));
        }
        name = gm.getLabel();
        directed = gm.isDirected();
    }

    //to be implemented by a nice gentleman
    public static String Graph2Json(GraphModel g) {
        GraphJSON gs = new GraphJSON();
        gs.init(g);
        Gson gson = new Gson();
        String ret = gson.toJson(gs);
        return ret;
    }

    public static GraphModel Json2Graph(String json) {
        Gson gson = new Gson();
        GraphJSON gs = gson.fromJson(json, GraphJSON.class);
        GraphModel ret = new GraphModel(gs.directed);
        HashMap<Integer, Vertex> vbyid = new HashMap<Integer, Vertex>();
        int id = 0;
        for (String s : gs.vertices) {
            Vertex v = new Vertex();
            ret.addVertex(v);
            v.setLabel(s);
            v.setLocation(gs.pos.get(id));
            vbyid.put(id, v);
            id++;
        }
        for (Vector<Integer> e : gs.edges) {
            Vertex v1 = vbyid.get(e.get(0));
            Vertex v2 = vbyid.get(e.get(1));
            ret.addEdge(new Edge(v1, v2));
        }
        return ret;
    }
}