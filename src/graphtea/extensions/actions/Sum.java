package graphtea.extensions.actions;

import graphtea.extensions.algorithms.GraphSum;
import graphtea.graph.graph.GraphModel;
import graphtea.plugins.main.GraphData;

/**
 * Created by rostam on 10.07.15.
 * @author M. Ali Rostami
 */
public class Sum extends TwoGraphAction {
    @Override
    public String getName() {
        return "Sum";
    }

    @Override
    protected GraphModel combine(GraphModel first, GraphModel second, GraphData graphData) {
        GraphModel g = GraphSum.sum(first, second);
        GraphSum.setUnionLabel(first, second, g);
        return g;
    }

    @Override
    public String getCategory() {
        return "Basic Operations";
    }
}
