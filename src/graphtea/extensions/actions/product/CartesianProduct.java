package graphtea.extensions.actions.product;

/**
 * @author M. Ali Rostami
 */
public class CartesianProduct extends GProductAction {
    @Override
    public String getName() { return "Cartesian Product"; }

    @Override
    public String getDescription() { return "Cartesian Product"; }

    @Override
    protected GProduct createProduct() { return new GCartesianProduct(); }
}
