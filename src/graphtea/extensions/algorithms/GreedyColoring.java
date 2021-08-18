package graphtea.extensions.algorithms;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.algorithmanimator.core.GraphAlgorithm;
import graphtea.plugins.algorithmanimator.extension.AlgorithmExtension;
import graphtea.plugins.main.core.actions.ResetGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by rostam on 06.03.15.
 * @author M. Ali Rostami
 */
public class GreedyColoring extends GraphAlgorithm implements AlgorithmExtension {
    public GreedyColoring(BlackBoard blackBoard) {
        super(blackBoard);
    }

    @Override
    public void doAlgorithm() {
        GraphModel g = graphData.getGraph();
        step("Start of the algorithm.");
        ResetGraph.resetGraph(g);

        step("Set the colors of vertices to zero.");
        for(Vertex v : g)
            v.setColor(0);

        step("Move over the vertices one by one and find all the colors that the neighbors are colored by.");
        for(Vertex v : g) {
            if(v.getColor() == 0) {
                Vector<Integer> colors = new Vector<>();
                for(Vertex u : g.directNeighbors(v))
                    colors.add(u.getColor());
                for(int i = 1;i < g.getVerticesCount();i++) {
                    if(!colors.contains(i)) {
                        v.setColor(i);
                        break;
                    }
                }
                if(v.getColor() == 0) {
                    v.setColor(Collections.max(colors) + 1);
                }
                step("The vertex " + v.getId() + " is colored with a color that is not used in its neighbors." );
            }
        }
        step("End of the algorithm.") ;
    }

    @Override
    public String getName() {
        return "Greedy Coloring";
    }

    @Override
    public String getDescription() {
        return "Greedy Coloring";
    }
}