// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.library.algorithms.util;

import graphtea.library.BaseEdge;
import graphtea.library.BaseGraph;
import graphtea.library.BaseVertex;
import graphtea.library.algorithms.Algorithm;
import graphtea.library.algorithms.AutomatedAlgorithm;
import graphtea.library.algorithms.traversal.DepthFirstSearch;
import graphtea.library.event.MessageEvent;
import graphtea.library.event.handlers.PreWorkPostWorkHandler;
import graphtea.library.event.typedef.BaseGraphRequest;
import graphtea.library.exceptions.InvalidGraphException;

/**
 * Checks whether a graph is acyclic.
 * Usage:
 * <p/>
 * graphtea.library.algorithms.util.AcyclicChecker.isGraphAcyclic(graph);
 * <p/>
 * The above statement returns true if the graph is acyclic. The graph
 * parameter is your graph object. Note that there is no need to explicitly
 * parameterize the generic method isGraphAcyclic. Types are automatically
 * deduced from the supplied graph object with no java unchecked convertion warning.
 *
 * @author Omid Aladini
 */
public class AcyclicChecker extends Algorithm implements AutomatedAlgorithm {
    /**
     * Checks whether the current graph is acyclic.
     *
     * @return True if graph is acyclic and false otherwise.
     * @throws InvalidGraphException The invalid graph
     */
    public static <VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
    boolean isGraphAcyclic(BaseGraph<VertexType, EdgeType> graph) {
        if (!graph.isDirected()) {
            int n = graph.getVerticesCount();
            int m = graph.getEdgesCount();
            int components = 0;

            if (m >= n)
                return false;

            for (VertexType v : graph) {
                if (v.getMark())
                    continue;

                ++components;

                new DepthFirstSearch<>(graph)
                        .doSearch(v, new AcyclicCheckerHandler<>(graph), false);
            }

            return (n == m + components);
        } else {
            return true;


        }
    }

    /**
     * Prework-PostWork Handler for checking whether the graph is acyclic.
     *
     * @author Omid Aladini
     * @param <VertexType> Graph's vertices type.
     * @param <EdgeType>   Graph's edges type.
     */
    private static class AcyclicCheckerHandler<VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
            implements PreWorkPostWorkHandler<VertexType> {
        BaseGraph<VertexType, EdgeType> graph;

        public AcyclicCheckerHandler(BaseGraph<VertexType, EdgeType> graph) {
            this.graph = graph;
        }

        public boolean doPreWork(VertexType fromVertex, VertexType toVertex) {
            toVertex.setMark(true);
            return false;
        }

        public boolean doPostWork(VertexType returnFrom, VertexType returnTo) {
            return false;
        }
    }

    public void doAlgorithm() {
        BaseGraphRequest gr = new BaseGraphRequest();
        dispatchEvent(gr);
        BaseGraph<BaseVertex, BaseEdge<BaseVertex>> graph = gr.getGraph();

        if (isGraphAcyclic(graph)) {
            dispatchEvent(new MessageEvent("Graph is acyclic."));
        } else
            dispatchEvent(new MessageEvent("Graph is NOT acyclic."));

    }

}
