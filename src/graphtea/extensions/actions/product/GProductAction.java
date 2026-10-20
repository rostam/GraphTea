package graphtea.extensions.actions.product;

import graphtea.extensions.actions.TwoGraphAction;
import graphtea.graph.graph.GraphModel;
import graphtea.plugins.main.GraphData;

/**
 * Abstract base for graph product action extensions.
 * Subclasses supply the specific {@link GProduct} via {@link #createProduct()}.
 *
 * @author M. Ali Rostami
 */
public abstract class GProductAction extends TwoGraphAction {

    protected abstract GProduct createProduct();

    @Override
    protected GraphModel combine(GraphModel first, GraphModel second, GraphData graphData) {
        GProduct prod = createProduct();
        GraphModel g = prod.multiply(first, second);
        prod.setPositions(g);
        return g;
    }

    @Override
    public String getCategory() {
        return "Products";
    }
}
