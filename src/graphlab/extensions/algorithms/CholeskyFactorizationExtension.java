package graphlab.extensions.algorithms;

import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.VertexModel;
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
        VertexModel v1 = requestVertex(g, "select the first vertex");
        VertexModel v2 = requestVertex(g, "select the second vertex");
        step("check the first vertex");
        v1.setColor(2);
        step("check the second vertex");
        v2.setColor(3);
        step("do the shit<br>" + getMatrixHTML(g));
        g.addEdge(new EdgeModel(v1, v2));
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
