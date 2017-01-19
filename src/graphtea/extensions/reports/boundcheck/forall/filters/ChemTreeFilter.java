package graphtea.extensions.reports.boundcheck.forall.filters;

import graphtea.extensions.reports.boundcheck.forall.GraphFilter;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

/**
 * Created by rostam on 30.09.15.\
 * @author M. Ali Rostami
 */
public class ChemTreeFilter implements GraphFilter {
    public boolean isChemTree(GraphModel g) {
        int maxDeg = 0;
        for(Vertex v : g) {
            if(g.getDegree(v) > maxDeg) maxDeg=g.getDegree(v);
        }
        if(maxDeg > 4) return false;
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
