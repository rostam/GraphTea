package graphtea.extensions.actions;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.ui.GTabbedGraphPane;
import graphtea.library.algorithms.goperators.GraphSum;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.extension.GraphActionExtension;

/**
 * Created by rostam on 10.07.15.
 * @author M. Ali Rostami
 */
public class Sum implements GraphActionExtension, Parametrizable {
    @Parameter(name = "First Graph",description = "First Graph")
    public String fG = "G0";
    @Parameter(name = "Second Graph",description = "Second Graph")
    public String sG = "G1";

    @Override
    public String getName() {
        return "Sum";
    }

    @Override
    public String getDescription() {
        return "Sum";
    }

    @Override
    public void action(GraphData graphData) {
        GTabbedGraphPane gtp = graphData.getBlackboard().getData(GTabbedGraphPane.NAME);
        GraphModel g1 = gtp.getGraphs().get(fG);
        GraphModel g2 = gtp.getGraphs().get(sG);
        GraphModel g= (GraphModel) GraphSum.sum(g1,g2);
        GraphSum.setUnionLabel(g1,g2,g);
        graphData.core.showGraph(g);
    }

    @Override
    public String checkParameters() {
        return null;
    }

    @Override
    public String getCategory() {
        return "Basic Operations";
    }
}
