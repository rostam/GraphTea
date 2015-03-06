package graphtea.extensions.algorithms;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.algorithmanimator.core.GraphAlgorithm;
import graphtea.plugins.algorithmanimator.extension.AlgorithmExtension;
import graphtea.plugins.main.core.actions.ResetGraph;

import java.util.*;

/**
 * Created by rostam on 06.03.15.
 */
public class DijkstraAlgorithm extends GraphAlgorithm implements AlgorithmExtension {
    public DijkstraAlgorithm(BlackBoard blackBoard) {
        super(blackBoard);
    }

    @Override
    public void doAlgorithm() {
        step("Start of the algorithm.") ;
        GraphModel graph = graphData.getGraph();
        Vertex vertex = requestVertex(graph,"select a vertex");

        final Integer dist[] = new Integer[graph.getVerticesCount()];
        //the edge connected to i'th vertex
        final HashMap<Vertex, Edge> edges = new HashMap<Vertex, Edge>();
        Vector<Vertex> prev = new Vector<Vertex>();

        for (int i = 0; i < dist.length; i++)
            dist[i] = Integer.MAX_VALUE;

        dist[vertex.getId()] = 0;

        class VertexComparator implements Comparator<Vertex> {
            public int compare(Vertex o1, Vertex o2) {
                if (dist[o1.getId()] < dist[o2.getId()])
                    return -1;
                if (dist[o1.getId()] == dist[o2.getId()])
                    return 0;
                else
                    return 1;
            }
        }

        VertexComparator vComp = new VertexComparator();

        //selected vertices
        HashSet<Vertex> selectedVertices = new HashSet<Vertex>();

        PriorityQueue<Vertex> Q = new PriorityQueue<Vertex>(1, vComp);

        Q.add(vertex);
        //dispatchEvent(new VertexEvent<VertexType, Edge>(graph, vertex, VertexEvent.EventType.MARK));
        vertex.setMark(true);
        while (!Q.isEmpty()) {
            Vertex vMin = Q.poll();
            vMin.setMark(true);
            Edge edg = edges.get(vMin);
            if (edg != null)
                edg.setMark(true);
            vMin.setColor(2);

            step("");
            selectedVertices.add(vMin);
            Iterator<Edge> iet = graph.edgeIterator(vMin);
            while ((iet.hasNext())) {
                Edge edge = iet.next();
                edge.setColor((int) (Math.random() * 10));
                step("");
                Vertex target = vMin == edge.source ? edge.target : edge.source;
                Vertex source = vMin;
                if (!selectedVertices.contains(target)) {
                    if (dist[target.getId()] > dist[source.getId()] + edge.getWeight()) {
                        dist[target.getId()] = dist[source.getId()] + edge.getWeight();
                        //dispatchEvent(new EdgeEvent<VertexType, Edge>(graph, edge, EdgeEvent.EventType.MARK));
                        //dispatchEvent(new VertexEvent<VertexType, Edge>(graph, target, VertexEvent.EventType.MARK));
                        edge.setMark(true);
                        edges.put(target, edge);
                        target.setMark(true);
                        target.setColor(5);
                        Q.add(target);
                        prev.add(edge.target);
                        //prev.add(edge.source.getId(), edge.target);
                    }
                }
            }
        }

        step("End of the algorithm.") ;
    }

    @Override
    public String getName() {
        return "Dijkstra";
    }

    @Override
    public String getDescription() {
        return "Dijkstra Algorithm";
    }
}