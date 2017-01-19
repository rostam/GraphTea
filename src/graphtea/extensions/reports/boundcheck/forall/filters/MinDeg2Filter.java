package graphtea.extensions.reports.boundcheck.forall.filters;

import graphtea.extensions.reports.boundcheck.forall.GraphFilter;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

/**
 * Created by rostam on 30.09.15.
 * @author M. Ali Rostami
 */
public class MinDeg2Filter implements GraphFilter {
    private boolean isChemTree(GraphModel g) {
        int minDeg = g.numOfVertices();
        for(Vertex v : g) {
            if(g.getDegree(v) < minDeg) minDeg=g.getDegree(v);
        }
        return minDeg >= 2;
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
