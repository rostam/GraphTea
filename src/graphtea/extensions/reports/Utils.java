package graphtea.extensions.reports;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.plugins.main.core.AlgorithmUtils;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by rostam on 30.09.15.
 */
public class Utils {
    // get kth minimum degree
    public static double getMinNonPendentDegree(GraphModel g) {
        ArrayList<Integer> al = AlgorithmUtils.getDegreesList(g);
        Collections.sort(al);
        if(al.contains(1)) {
            for(int i=0;i<al.size();i++) {
                if(al.get(i)!=1) {
                    return al.get(i);
                }
            }
        }

        return al.get(0);
    }

    //get 2-degree sum of graph
    public static double getDegreeSumOfVertex(GraphModel g, double alpha, Vertex v) {
        double sum = 0;
        for(Vertex u : g.getNeighbors(v)) {
            sum+=Math.pow(g.getDegree(u),alpha);
        }
        return sum;
    }

    public static double getDegreeSum(GraphModel g, double alpha) {
        int sum = 0;
        for(Vertex v: g) {
            sum+=getDegreeSumOfVertex(g,alpha,v);
        }
        return sum;
    }


}
