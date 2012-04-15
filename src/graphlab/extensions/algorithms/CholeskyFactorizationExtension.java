package graphlab.extensions.algorithms;

import graphlab.graph.graph.Edge;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.Vertex;
import graphlab.platform.core.BlackBoard;
import graphlab.plugins.algorithmanimator.core.GraphAlgorithm;
import graphlab.plugins.algorithmanimator.extension.AlgorithmExtension;

import java.util.Iterator;
import java.util.Vector;

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
        boolean cont = true;
        while(cont) {
            Vertex v1 = requestVertex(g, "select a vertex");
            step("Clique on neighbours");
            Vector<Vertex> vs = new Vector<Vertex>();
            for(Vertex vit1 : g.getNeighbors(v1))
                vs.add(vit1);

            for(Vertex vv1 : vs)
                for(Vertex vv2 : vs)
                    g.addEdge(new Edge(vv1,vv2));

            g.removeVertex(v1);

       }

        Vertex v2 = requestVertex(g, "select the second vertex");
        step("check the first vertex");
        //v1.setColor(2);
        step("check the second vertex");
        //v2.setColor(3);
        step("do the shit<br>" + getMatrixHTML(g));
        //g.addEdge(new Edge(v1, v2));
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
