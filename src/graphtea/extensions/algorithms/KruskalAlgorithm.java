package graphtea.extensions.algorithms;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.algorithmanimator.core.GraphAlgorithm;
import graphtea.plugins.algorithmanimator.extension.AlgorithmExtension;
import graphtea.plugins.main.core.actions.ResetGraph;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by rostam on 06.03.15.
 * @author M. Ali Rostami
 */
public class KruskalAlgorithm extends GraphAlgorithm implements AlgorithmExtension {
    public KruskalAlgorithm(BlackBoard blackBoard) {
        super(blackBoard);
    }

    @Override
    public void doAlgorithm() {
        step("Start of the algorithm.") ;
        ResetGraph.resetGraph(graphData.getGraph());

        ArrayList<Edge> outputEdges = new ArrayList<>();
        ArrayList<ArrayList<Vertex>> sets = new ArrayList<>();

        GraphModel graph = graphData.getGraph();
        for (Vertex v : graph) {
            ArrayList<Vertex> set = new ArrayList<>();
            set.add(v);
            sets.add(set);
        }

        ArrayList<Edge> edges = new ArrayList<>(graph.getEdgesCount());

        Iterator<Edge> eit = graph.edgeIterator();
        while (eit.hasNext())
            edges.add(eit.next());

        java.util.Collections.sort(edges, (o1, o2) -> {
            if (o1.getWeight() < o2.getWeight())
                return -1;
            else if (o1.getWeight() == o2.getWeight())
                return 0;
            return 1;
        });

        for (Edge e : edges) {
            int set1 = findSet(sets, e.source);
            int set2 = findSet(sets, e.target);

            if (set1 != set2) {
                outputEdges.add(e);
                e.source.setMark(true);
                e.source.setColor(2);
                e.setMark(true);
                e.setColor(4);
                e.target.setColor(2);
                e.target.setMark(true);
                sets.get(set1).addAll(sets.get(set2));
                sets.remove(set2);
                step("New edge is marked.");
                //EventUtils.algorithmStep(this,600);
            }

        }
        step("End of the algorithm.") ;
    }

    @Override
    public String getName() {
        return "Kruskal";
    }

    @Override
    public String getDescription() {
        return "Kruskal Algorithm";
    }

    int findSet(ArrayList<ArrayList<Vertex>> aav, Vertex v) {
        int i = -1;
        for (ArrayList<Vertex> set : aav) {
            ++i;
            if (set.contains(v))
                return i;
        }
        return i;
    }
}