package graphtea.extensions.actions.product;

/**
 * @author M. Ali Rostami
 */
public class TensorProduct extends GProductAction {
    @Override
    public String getName() { return "Tensor Product"; }

    @Override
    public String getDescription() { return "Tensor Product"; }

    @Override
    protected GProduct createProduct() { return new GTensorProduct(); }
}
