package graphtea.extensions.actions.product;

/**
 * @author M. Ali Rostami
 */
public class StrongProduct extends GProductAction {
    @Override
    public String getName() { return "Strong Product"; }

    @Override
    public String getDescription() { return "Strong Product"; }

    @Override
    protected GProduct createProduct() { return new GStrongProduct(); }
}
