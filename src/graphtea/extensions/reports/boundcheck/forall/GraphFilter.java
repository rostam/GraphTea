package graphtea.extensions.reports.boundcheck.forall;

import graphtea.graph.graph.GraphModel;

/**
 * Created by rostam on 30.09.15.
 * @author M. Ali Rostami
 */
public interface GraphFilter {
    boolean filter(GraphModel g);
    String getName();
}
