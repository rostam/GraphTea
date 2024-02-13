// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.extensions.algorithms.subgraphs;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.library.BaseVertex;

import java.util.AbstractList;
import java.util.Comparator;
import java.util.TreeSet;

public class InducedSubgraphs {

    public static GraphModel getVertexInducedSubgraph(GraphModel graph, AbstractList<Vertex> inducedVertices) {
        GraphModel newGraph = graph.createEmptyGraph();
        newGraph.registerSubgraph(graph);
        newGraph.setSubGraphIndex(graph.getNewSubgraphIndex());

        for (Vertex v : inducedVertices) {
            graph.checkVertex(v);
            newGraph.insertVertex(v);
        }

        for (int i = 0; i < inducedVertices.size(); ++i) {
            for (int j = i + 1; j < inducedVertices.size(); ++j) {
                AbstractList<Edge> edges = graph.getEdges(inducedVertices.get(i), inducedVertices.get(j));

                for (Edge edge : edges)
                    newGraph.insertEdge(edge);
            }
        }

        return newGraph;
    }

    public static GraphModel
    getEdgeInducedSubgraph(GraphModel graph, AbstractList<Edge> inducedEdges) {
        GraphModel newGraph = graph.createEmptyGraph();
        newGraph.registerSubgraph(graph);
        newGraph.setSubGraphIndex(graph.getNewSubgraphIndex());

        TreeSet<Vertex> vertices = new TreeSet<>(Comparator.comparingInt(BaseVertex::getId));

        //Removing duplicate vertices by adding them to a TreeSet
        for (Edge e : inducedEdges) {
            graph.checkVertex(e.source);
            graph.checkVertex(e.target);
            vertices.add(e.source);
            vertices.add(e.target);
        }

        for (Vertex v : vertices)
            newGraph.insertVertex(v);

        for (Edge e : inducedEdges)
            newGraph.insertEdge(e);

        return newGraph;
    }
}
