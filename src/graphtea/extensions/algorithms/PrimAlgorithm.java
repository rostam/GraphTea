package graphtea.extensions.algorithms;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.library.util.Pair;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.algorithmanimator.core.GraphAlgorithm;
import graphtea.plugins.algorithmanimator.extension.AlgorithmExtension;
import graphtea.plugins.main.core.actions.ResetGraph;

import java.util.*;

/**
 * Created by rostam on 06.03.15.
 * @author M. Ali Rostami
 */
public class PrimAlgorithm extends GraphAlgorithm implements AlgorithmExtension {
    public PrimAlgorithm(BlackBoard blackBoard) {
        super(blackBoard);
        graph = graphData.getGraph();

    }

    private PriorityQueue<Edge> pq;
    private GraphModel graph;


    @Override
    public void doAlgorithm() {
        step("Start of the algorithm.");
        ResetGraph.resetGraph(graphData.getGraph());
        Vertex v = requestVertex(graph, "select a vertex");

        class DefaultEdgeComparator implements Comparator<Edge> {
            public int compare(Edge o1, Edge o2) {
                return Integer.compare(o1.getWeight(), o2.getWeight());
            }
        }

//               graph.checkVertex(v);
        Vector<Vertex> oVertices = new Vector<>();
        Vector<Edge> oEdges = new Vector<>();

        //dispatchEvent(new GraphEvent<VertexType,EdgeType>(oGraph));

        pq = new PriorityQueue<>(1, new DefaultEdgeComparator());

        {    //lovely block
            Iterator<Edge> edgeList = graph.edgeIterator();

            while (edgeList.hasNext())
                pq.add(edgeList.next());
        }

        //for (Vertex searchV : graph)
        //    if (searchV.getId() == v.getId()) {
        //        oVertices.add(searchV);
        //        searchV.setMark(true);
        //        //dispatchEvent(new VertexEvent<VertexType, EdgeType>(graph, searchV, VertexEvent.EventType.MARK));
        //    }
        oVertices.add(v);
        v.setMark(true);
        v.setColor(2);
        step("");

        while (true) {
            Pair<Edge, Vertex> pev = getNewEdgeForSpanningTree(oVertices);

            if (pev == null) {
                break;
            } else {
                step("");
                pev.second.setMark(true);
                pev.second.setColor(2);
                //dispatchEvent(new VertexEvent<VertexType, EdgeType>(graph, pev.second, VertexEvent.EventType.MARK));
                oVertices.add(pev.second);
                oEdges.add(pev.first);
                //dispatchEvent(new EdgeEvent<VertexType, EdgeType>(graph, pev.first, EdgeEvent.EventType.MARK));
                pev.first.setMark(true);
                pev.first.setColor(2);
//                EventUtils.algorithmStep(this, "");
            }
        }

        //return new Pair<Vector<VertexType>, Vector<EdgeType>>(oVertices, oEdges);
    }

    private Pair<Edge, Vertex>
    getNewEdgeForSpanningTree(Vector<Vertex> vertices) {
        ArrayList<Edge> tempEdgeArray = new ArrayList<>();

        try {
            while (true) {
                Edge edge = pq.poll();
                if (edge == null)
                    return null;

                if (!vertices.contains(edge.target) &&
                        vertices.contains(edge.source))
                    return new Pair<>(edge, edge.target);

                if (!graph.isDirected() &&
                        vertices.contains(edge.target) &&
                        !vertices.contains(edge.source))
                    return new Pair<>(edge, edge.source);

                tempEdgeArray.add(edge);

            }
        } finally {
            pq.addAll(tempEdgeArray);
        }
    }

    @Override
    public String getName() {
        return "Prim";
    }

    @Override
    public String getDescription() {
        return "Prim Algorithm";
    }
}