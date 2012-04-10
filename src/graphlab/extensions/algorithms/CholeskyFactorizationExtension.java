package graphlab.extensions.algorithms;

import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.VertexModel;
import graphlab.library.algorithms.Algorithm;
import graphlab.library.event.EventDispatcher;
import graphlab.plugins.algorithmanimator.core.GraphAlgorithm;
import graphlab.plugins.algorithmanimator.extension.AlgorithmExtension;

/**
 * author: rostam
 * author: azin
 */
public class CholeskyFactorizationExtension  extends GraphAlgorithm implements AlgorithmExtension{
    @Override
    public void doAlgorithm() {
        GraphModel g = requestGraph();
        VertexModel v1 = requestVertex(g, "select the first vertex");
        VertexModel v2 = requestVertex(g, "select the second vertex");
        step("check the first vertex");
        v1.setColor(2);
        step("check the second vertex");
        v2.setColor(3);
        step("do the shit");
        g.addEdge(new EdgeModel(v1,v2));
        step("goodbye");
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
