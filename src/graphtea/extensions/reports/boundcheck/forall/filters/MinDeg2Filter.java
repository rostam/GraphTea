package graphtea.extensions.reports.boundcheck.forall.filters;

import graphtea.extensions.reports.boundcheck.forall.GraphFilter;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

/**
 * Created by rostam on 30.09.15.
 */
public class MinDeg2Filter implements GraphFilter {
    public boolean isChemTree(GraphModel g) {
        int minDeg = g.numOfVertices();
        for(Vertex v : g) {
            if(g.getDegree(v) < minDeg) minDeg=g.getDegree(v);
        }
        if(minDeg < 2) return false;
        return true;
    }

    @Override
    public boolean filter(GraphModel g) {
        return isChemTree(g);
    }

    @Override
    public String getName() {
        return "chemtree";
    }
}
