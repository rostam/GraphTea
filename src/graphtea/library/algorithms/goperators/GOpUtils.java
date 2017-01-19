package graphtea.library.algorithms.goperators;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

import java.util.HashMap;

/**
 * Created by rostam on 17.08.15.
 */
public class GOpUtils {
    public static GPoint center(GraphModel g) {
        GPoint center = new GPoint(0,0);
        for(Vertex v : g) {
            center.add(v.getLocation());
        }
        center.multiply(1./g.numOfVertices());
        return center;
    }

    public static  HashMap<Integer,GPoint> offsetPositionsToCenter(GraphModel g) {
        GPoint center = center(g);
        HashMap<Integer,GPoint> offsets = new HashMap<>();
        for(Vertex v : g.vertices()) {
            offsets.put(v.getId(), GPoint.sub(v.getLocation(), center));
        }
        return offsets;
    }

    public static void addOffsets(GraphModel g, HashMap<Integer,GPoint> offsets, GPoint center) {
        for(Integer gp : offsets.keySet()) {
            GPoint pos = center;
            pos.add(offsets.get(gp));
            System.out.println("bb"+gp);
            g.getVertex(gp).setLocation(pos);
        }
    }

    public static GPoint midPoint(Edge e) {
        GPoint mid = new GPoint(0,0);
        mid.add(e.source.getLocation());
        mid.add(e.target.getLocation());
        mid.multiply(1./2.);
        return mid;
    }
}
