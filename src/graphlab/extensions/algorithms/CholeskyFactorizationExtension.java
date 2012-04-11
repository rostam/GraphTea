package graphlab.extensions.algorithms;

import graphlab.graph.graph.Edge;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.Vertex;
import graphlab.platform.core.BlackBoard;
import graphlab.plugins.algorithmanimator.core.GraphAlgorithm;
import graphlab.plugins.algorithmanimator.extension.AlgorithmExtension;

/**
 * author: rostam
 * author: azin
 */
public class CholeskyFactorizationExtension extends GraphAlgorithm implements AlgorithmExtension {
    public CholeskyFactorizationExtension(BlackBoard blackBoard) {
        super(blackBoard);
    }

    @Override
    public void doAlgorithm() {
        GraphModel g = graphData.getGraph();
        Vertex v1 = requestVertex(g, "select the first vertex");
        Vertex v2 = requestVertex(g, "select the second vertex");
        step("check the first vertex");
        v1.setColor(2);
        step("check the second vertex");
        v2.setColor(3);
        step("do the shit<br>" + getMatrixHTML(g));
        g.addEdge(new Edge(v1, v2));
        step("goodbye<br>" + getMatrixHTML(g));
    }

    @Override
    public String getName() {
        return "Cholesky";
    }

    @Override
    public String getDescription() {
        return "blah blah blah";
    }
}
