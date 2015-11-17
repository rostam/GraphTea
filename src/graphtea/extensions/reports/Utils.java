package graphtea.extensions.reports;

import graphtea.graph.graph.GraphModel;
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

    //get


}
