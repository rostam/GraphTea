package graphtea.extensions.reports;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by rostam on 16.11.15.
 * Dijkstra working only for non negative weights
 */
public class DijkstraNonNegative {
    static {
        Vertex.addGlobalUserDefinedAttribute(DijkstraNonNegative.Prev,-1);
        Vertex.addGlobalUserDefinedAttribute(DijkstraNonNegative.Dist,0.000D);
        Vertex.addGlobalUserDefinedAttribute(DijkstraNonNegative.Seen,false);
    }

    public static final String Prev = "previous";
    public static final String Dist = "dist";
    public static final String Seen = "seen";
    public static void dijkstra ( GraphModel g, Vertex start) {

        PriorityQueue<Vertex> p =        // Priority-Queue zum Verwalten der Laenge
                new PriorityQueue<>(10, new DijkstraNonNegative.VertexComparator()); // des kuerzesten Weges bis zum Knoten


        for (Vertex v : g.vertices()){   // fuer jeden Knoten
            v.setUserDefinedAttribute(DijkstraNonNegative.Dist,Double.MAX_VALUE);
            v.setUserDefinedAttribute(DijkstraNonNegative.Seen,false);
            v.setUserDefinedAttribute(DijkstraNonNegative.Prev,-1);
        }

        start.setUserDefinedAttribute(DijkstraNonNegative.Dist,0.0D);
        p.add(start);                    // erster Eintrag in PriorityQueue

        while (!p.isEmpty()){            // solange noch Eintraege in Priority-Queue

            Vertex v = p.poll();           // billigster Eintrag in PriorityQueue
            if(v.getUserDefinedAttribute(DijkstraNonNegative.Seen)) continue;
            v.setUserDefinedAttribute(DijkstraNonNegative.Seen,true);

            for(Vertex w : g.neighbors(v)) {
                double c = 1;           // besorge Kosten c zum Zielknoten w
                if((Double)w.getUserDefinedAttribute(DijkstraNonNegative.Dist) >
                        (Double)v.getUserDefinedAttribute(DijkstraNonNegative.Dist) + c) {
                    w.setUserDefinedAttribute(DijkstraNonNegative.Dist,
                            (Double) v.getUserDefinedAttribute(DijkstraNonNegative.Dist) + c);
                    w.setUserDefinedAttribute(DijkstraNonNegative.Prev,v.getId());
                    p.add(w);                  // neuer Eintrag in PriorityQueue
                }
            }
        }
    }
    static class VertexComparator implements Comparator<Vertex> {
        public int compare(Vertex o1, Vertex o2) {
            if ((Double) o1.getUserDefinedAttribute(Dist) < (Double) o2.getUserDefinedAttribute(Dist)) return -1;
            else if ((Double) o1.getUserDefinedAttribute(Dist) > (Double) o2.getUserDefinedAttribute(Dist)) return 1;
            return 0;
        }
    }
}
