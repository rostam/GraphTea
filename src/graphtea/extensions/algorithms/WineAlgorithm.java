package graphtea.extensions.algorithms;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.library.Path;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.algorithmanimator.core.GraphAlgorithm;
import graphtea.plugins.algorithmanimator.extension.AlgorithmExtension;
import graphtea.plugins.main.core.AlgorithmUtils;

import java.util.Vector;

/**
 * author: rostam
 * author: azin
 */
public class WineAlgorithm extends GraphAlgorithm implements AlgorithmExtension {
    public WineAlgorithm(BlackBoard blackBoard) {
        super(blackBoard);
    }

    @Override
    public void doAlgorithm() {

        GraphModel g = graphData.getGraph();
        Vertex v1 = requestVertex(g, "select the first vertex");

        step("rotate the glass");

        step("have fun :)");
    }

    @Override
    public String getName() {
        return "Just a sample ";
    }

    @Override
    public String getDescription() {
        return "This is just a show case for developers to see how they can make new algorithms";
    }
}
