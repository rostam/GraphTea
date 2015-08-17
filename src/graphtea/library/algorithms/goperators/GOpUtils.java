package graphtea.library.algorithms.goperators;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.GraphPoint;
import graphtea.graph.graph.Vertex;

import java.util.HashMap;
import java.util.Vector;

/**
 * Created by rostam on 17.08.15.
 */
public class GOpUtils {
    public static GraphPoint center(GraphModel g) {
        GraphPoint center = new GraphPoint(0,0);
        for(Vertex v : g) {
            center.add(v.getLocation());
        }
        center.multiply(1./g.numOfVertices());
        return center;
    }

    public static  HashMap<Integer,GraphPoint> offsetPositionsToCenter(GraphModel g) {
        GraphPoint center = center(g);
        HashMap<Integer,GraphPoint> offsets = new HashMap<>();
        for(Vertex v : g.vertices()) {
            offsets.put(v.getId(), GraphPoint.sub(v.getLocation(), center));
        }
        return offsets;
    }

    public static void addOffsets(GraphModel g,HashMap<Integer,GraphPoint> offsets,GraphPoint center) {
        for(Integer gp : offsets.keySet()) {
            GraphPoint pos = center;
            pos.add(offsets.get(gp));
            System.out.println("bb"+gp);
            g.getVertex(gp).setLocation(pos);
        }
    }

    public static GraphPoint midPoint(Edge e) {
        GraphPoint mid = new GraphPoint(0,0);
        mid.add(e.source.getLocation());
        mid.add(e.target.getLocation());
        mid.multiply(1./2.);
        return mid;
    }
}
