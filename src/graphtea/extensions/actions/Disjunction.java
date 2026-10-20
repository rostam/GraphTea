package graphtea.extensions.actions;

import graphtea.extensions.actions.product.GProduct;
import graphtea.extensions.actions.product.GProductAction;

/**
 * Created by rostam on 10.07.15.
 * @author M. Ali Rostami
 */
public class Disjunction extends GProductAction {
    @Override
    public String getName() {
        return "Graph Disjunction";
    }

    @Override
    protected GProduct createProduct() {
        return new GDisjunction();
    }
}
