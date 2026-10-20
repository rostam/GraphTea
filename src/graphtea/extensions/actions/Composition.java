package graphtea.extensions.actions;

import graphtea.extensions.actions.product.GProduct;
import graphtea.extensions.actions.product.GProductAction;

/**
 * Created by rostam on 10.07.15.
 * @author M. Ali Rostami
 */
public class Composition extends GProductAction {
    @Override
    public String getName() {
        return "Composition";
    }

    @Override
    protected GProduct createProduct() {
        return new GComposition();
    }
}
