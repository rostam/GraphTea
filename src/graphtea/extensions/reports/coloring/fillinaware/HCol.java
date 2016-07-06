package graphtea.extensions.reports.coloring.fillinaware;

import graphtea.extensions.reports.coloring.fillinaware.metis.NDMetis;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

import java.util.*;

/**
 * Created by rostam on 30.12.15.
 *
 */
public class HCol {
//    public static int color(GraphModel g) {
//        Map<Integer,Integer> vertexDegree = new HashMap<>();
//
//        for(Vertex v : g) {
//            vertexDegree.put(v.getId(),g.getDegree(v));
//            v.setColor(0);
//        }
//
//        vertexDegree=sortByComparator(vertexDegree, false);
//
//        for(int id : vertexDegree.keySet()) {
//            int col = Helper.minPossibleColor(g,g.getVertex(id));
//            g.getVertex(id).setColor(col);
//        }
//
//        return Helper.numberOfColors(g);
//    }


    public static Vector<Integer> ordering(GraphModel g, String s,SpMat mm) {
        for (Vertex v : g) v.setColor(0);
        Vector<Integer> ret = new Vector<>();
        switch (s) {
            case "Nat":
                for (int i = 0; i < g.numOfVertices(); i++) ret.add(i);
                break;
            case "MaxDeg":
            case "MinDeg":
                Map<Integer, Integer> vertexDegree = new HashMap<>();
                for (Vertex v : g) vertexDegree.put(v.getId(), g.getDegree(v));
                if (s.equals("MaxDeg")) vertexDegree = sortByComparator(vertexDegree, false);
                else if (s.equals("MinDeg")) vertexDegree = sortByComparator(vertexDegree, true);
                for (int i : vertexDegree.keySet()) ret.add(i);
                break;
            case "Metis":
                NDMetis nd = new NDMetis("MetisGraph",g);
                ret = nd.getOrder();
                break;
        }
        return ret;
    }

    public static int colorRest(GraphModel g, Vector<Integer> order) {
        Iterator it = order.iterator();
        g.getVertex((Integer) it.next()).setColor(0);
        while (it.hasNext()) {
            int id = (int) it.next();
            if (incidentToReqEdge(g, g.getVertex(id))) {
                int col = minPossibleColorRestricted(g, g.getVertex(id));
                g.getVertex(id).setColor(col);
            }
        }
        return Helper.numberOfColors(g);
    }

    //order
    //public static boolean ASC = true;
    //public static boolean DESC = false;
    private static Map<Integer, Integer> sortByComparator(Map<Integer, Integer> unsortMap, final boolean order)
    {
        List<Map.Entry<Integer, Integer>> list = new LinkedList<>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>()
        {
            public int compare(Map.Entry<Integer, Integer> o1,
                               Map.Entry<Integer, Integer> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<Integer, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Integer, Integer> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    public static int minPossibleColorRestricted(GraphModel g, Vertex given) {
        Vector<Integer> forbiddenCols = new Vector<>();
        for(int i=0;i<100;i++) forbiddenCols.add(0);
        for(Vertex v : requiredNeighbours(g, given)) {
            forbiddenCols.set(v.getColor(), -1);
        }
        for(int i=0;i<forbiddenCols.size();i++) {
            if(forbiddenCols.get(i) != -1 ) return i;
        }
        return 0;
    }

    public static boolean incidentToReqEdge(GraphModel g, Vertex v) {
        for(Vertex neigh : g.directNeighbors(v)) if (g.getEdge(v, neigh).getWeight() == 1) return true;
        return false;
    }

    public static Vector<Vertex> requiredNeighbours(GraphModel g, Vertex v) {
        Vector<Vertex> ns = new Vector<>();
        for(Vertex neigh : g.directNeighbors(v)) {
            if(g.getEdge(v,neigh).getWeight()==1) {
                ns.add(neigh);
            }
        }
        return ns;
    }
}