package graphtea.extensions.actions.product;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.ui.GTabbedGraphPane;
import graphtea.library.algorithms.goperators.product.GCartesianProduct;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.extension.GraphActionExtension;

/**
 * Created by rostam on 10.07.15.
 * @author M. Ali Rostami
 */
public class CartesianProduct implements GraphActionExtension, Parametrizable {
    @Parameter(name = "First Graph",description = "First Graph")
    public String g0 = "G0";
    @Parameter(name = "Second Graph",description = "Second Graph")
    public String g1 = "G1";

    @Override
    public String getName() {
        return "Cartesian Product";
    }

    @Override
    public String getDescription() {
        return "Cartesian  Product";
    }

    @Override
    public void action(GraphData graphData) {
        GTabbedGraphPane gtp = graphData.getBlackboard().getData(GTabbedGraphPane.NAME);
        GCartesianProduct prod = new GCartesianProduct();
        GraphModel g= (GraphModel) prod.multiply(gtp.getGraphs().get(g0),
                gtp.getGraphs().get(g1));
        prod.setPositions(g);
        graphData.core.showGraph(g);
    }

    @Override
    public String checkParameters() {
        return null;
    }

    @Override
    public String getCategory() {
        return "Products";
    }
}
