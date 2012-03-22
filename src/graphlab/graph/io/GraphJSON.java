package graphlab.graph.io;

import com.google.gson.Gson;
import graphlab.graph.atributeset.EdgeAttrSet;
import graphlab.graph.atributeset.GraphAttrSet;
import graphlab.graph.atributeset.VertexAttrSet;
import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.GraphPoint;
import graphlab.graph.graph.VertexModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

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
        for (VertexModel v : gm) {
            vertices.add(v.getLabel());
            pos.put(v.getId(), v.getLocation());
        }
        for (EdgeModel e : gm.getEdges()) {
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
        HashMap<Integer, VertexModel> vbyid = new HashMap<Integer, VertexModel>();
        int id = 0;
        for (String s : gs.vertices) {
            VertexModel v = new VertexModel();
            ret.addVertex(v);
            v.setLabel(s);
            v.setLocation(gs.pos.get(id));
            vbyid.put(id, v);
            id++;
        }
        for (Vector<Integer> e : gs.edges) {
            VertexModel v1 = vbyid.get(e.get(0));
            VertexModel v2 = vbyid.get(e.get(1));
            ret.addEdge(new EdgeModel(v1, v2));
        }
        return ret;
    }
}