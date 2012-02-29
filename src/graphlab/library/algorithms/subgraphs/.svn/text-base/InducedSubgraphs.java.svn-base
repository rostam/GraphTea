// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphlab.library.algorithms.subgraphs;

import graphlab.library.BaseEdge;
import graphlab.library.BaseGraph;
import graphlab.library.BaseVertex;

import java.util.AbstractList;
import java.util.Comparator;
import java.util.TreeSet;

public class InducedSubgraphs {

    public static <VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
    BaseGraph<VertexType, EdgeType>
    getVertexInducedSubgraph(BaseGraph<VertexType, EdgeType> graph, AbstractList<VertexType> inducedVertices) {
        BaseGraph<VertexType, EdgeType> newGraph = graph.createEmptyGraph();
        newGraph.registerSubgraph(graph);
        newGraph.setSubGraphIndex(graph.getNewSubgraphIndex());

        for (VertexType v : inducedVertices) {
            graph.checkVertex(v);
            newGraph.insertVertex(v);
        }

        for (int i = 0; i < inducedVertices.size(); ++i) {
            for (int j = i + 1; j < inducedVertices.size(); ++j) {
                AbstractList<EdgeType> edges = graph.getEdges(inducedVertices.get(i), inducedVertices.get(j));

                for (EdgeType edge : edges)
                    newGraph.insertEdge(edge);
            }
        }

        return newGraph;
    }

    public static <VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
    BaseGraph<VertexType, EdgeType>
    getEdgeInducedSubgraph(BaseGraph<VertexType, EdgeType> graph, AbstractList<EdgeType> inducedEdges) {
        BaseGraph<VertexType, EdgeType> newGraph = graph.createEmptyGraph();
        newGraph.registerSubgraph(graph);
        newGraph.setSubGraphIndex(graph.getNewSubgraphIndex());

        TreeSet<VertexType> vertices = new TreeSet<VertexType>(
                new Comparator<VertexType>() {
                    public int compare(VertexType o1, VertexType o2) {
                        if (o1.getId() < o2.getId())
                            return -1;

                        if (o1.getId() == o2.getId())
                            return 0;

                        return 1;
                    }
                }
        );

        //Removing duplicate vertices by adding them to a TreeSet
        for (EdgeType e : inducedEdges) {
            graph.checkVertex(e.source);
            graph.checkVertex(e.target);
            vertices.add(e.source);
            vertices.add(e.target);
        }

        for (VertexType v : vertices)
            newGraph.insertVertex(v);

        for (EdgeType e : inducedEdges)
            newGraph.insertEdge(e);

        return newGraph;
    }


}
