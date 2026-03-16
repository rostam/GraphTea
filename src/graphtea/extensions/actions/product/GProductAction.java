package graphtea.extensions.actions.product;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.ui.GTabbedGraphPane;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.extension.GraphActionExtension;

/**
 * Abstract base for graph product action extensions.
 * Subclasses supply the specific {@link GProduct} via {@link #createProduct()}.
 *
 * @author M. Ali Rostami
 */
abstract class GProductAction implements GraphActionExtension, Parametrizable {
    @Parameter(name = "First Graph", description = "First Graph")
    public String g0 = "G0";
    @Parameter(name = "Second Graph", description = "Second Graph")
    public String g1 = "G1";

    protected abstract GProduct createProduct();

    @Override
    public void action(GraphData graphData) {
        GTabbedGraphPane gtp = graphData.getBlackboard().getData(GTabbedGraphPane.NAME);
        GProduct prod = createProduct();
        GraphModel g = prod.multiply(gtp.getGraphs().get(g0), gtp.getGraphs().get(g1));
        prod.setPositions(g);
        graphData.core.showGraph(g);
    }

    @Override
    public String getCategory() {
        return "Products";
    }
}
