package graphtea.extensions.algorithms;

import graphtea.extensions.reports.spectralreports.maxflowmincut.MinCut;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.algorithmanimator.core.GraphAlgorithm;
import graphtea.plugins.algorithmanimator.extension.AlgorithmExtension;

/**
 * Created with IntelliJ IDEA.
 * User: rostam
 * Date: 5/1/13
 * Time: 1:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class MinimumCutAlgorithm  extends GraphAlgorithm implements AlgorithmExtension {
    public MinimumCutAlgorithm(BlackBoard blackBoard) {
        super(blackBoard);
    }

    @Override
    public void doAlgorithm() {
        step("Start of the algorithm.") ;

        GraphModel g = graphData.getGraph();
        Vertex v1 = requestVertex(g, "select the first vertex");
        v1.setColor(7);
        v1.setMark(true);
        Vertex v2 = requestVertex(g, "select the second vertex");
        v2.setColor(3);
        v2.setMark(true);
        MinCut mc = new MinCut(g,v1,v2,true);
        mc.perform();
        step("end of the algorithm");
    }

    @Override
    public String getName() {
        return "Minimum Cut";
    }

    @Override
    public String getDescription() {
        return "computes the minimum cut of the graph";
    }
}
