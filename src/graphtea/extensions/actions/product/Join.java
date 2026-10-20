package graphtea.extensions.actions.product;

import graphtea.extensions.actions.TwoGraphAction;
import graphtea.extensions.algorithms.GraphJoin;
import graphtea.extensions.algorithms.GraphSum;
import graphtea.graph.graph.GraphModel;
import graphtea.plugins.main.GraphData;

/**
 * Created by rostam on 10.07.15.
 * @author M. Ali Rostami
 */
public class Join extends TwoGraphAction {
    @Override
    public String getName() {
        return "Join";
    }

    @Override
    protected GraphModel combine(GraphModel first, GraphModel second, GraphData graphData) {
        GraphModel g = GraphJoin.join(first, second);
        GraphSum.setUnionLabel(first, second, g);
        return g;
    }

    @Override
    public String getCategory() {
        return "Basic Operations";
    }
}
