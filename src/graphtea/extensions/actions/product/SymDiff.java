package graphtea.extensions.actions.product;

/**
 * @author M. Ali Rostami
 */
public class SymDiff extends GProductAction {
    @Override
    public String getName() { return "Symmetric Difference"; }

    @Override
    public String getDescription() { return "Symmetric Difference"; }

    @Override
    protected GProduct createProduct() { return new GSymmDiff(); }
}
