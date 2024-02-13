// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

/*
 * DepthFirstSearch.java
 *
 * Created on November 20, 2004, 10:31 PM
 */

package graphtea.library.algorithms.traversal;

import graphtea.library.BaseEdge;
import graphtea.library.BaseGraph;
import graphtea.library.BaseVertex;
import graphtea.library.algorithms.Algorithm;
import graphtea.library.algorithms.AutomatedAlgorithm;
import graphtea.library.algorithms.util.EventUtils;
import graphtea.library.event.GraphRequest;
import graphtea.library.event.PostWorkEvent;
import graphtea.library.event.PreWorkEvent;
import graphtea.library.event.VertexRequest;
import graphtea.library.event.handlers.PreWorkPostWorkHandler;
import graphtea.library.exceptions.InvalidGraphException;
import graphtea.library.exceptions.InvalidVertexException;


/**
 * @author Omid Aladini
 */
public class DepthFirstSearch<VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
        extends Algorithm implements AutomatedAlgorithm {
    private BaseGraph<VertexType, EdgeType> graph;

    public DepthFirstSearch(BaseGraph<VertexType, EdgeType> graph) {
        this.graph = graph;
    }

    public DepthFirstSearch() {
        this.graph = null;
    }


    /**
     * Calling this will not reset the marks.
     *
     * @param vertex  Index of the starting vertex of the traversal.
     * @param handler A reference to a PreWorkPostWorkHandler that contains implementation
     *                of pre-work and post-work operations that depends on the application of DFS.
     * @return Whether the traversal has stopped at the middle by the handler.
     */
    public boolean doSearch(VertexType vertex, PreWorkPostWorkHandler<VertexType> handler) {
        return doSearch(vertex, handler, true);
    }

    /**
     * Runs Depth First Search (DFS) algorithm on the graph starting from vertex <I>vertexId</I>.
     * A reference to a PreWorkPostWorkHandler is supplied that contains implementation
     * of pre-work and post-work operations that depends on the application of DFS.
     *
     * @param vertex     Index of the starting vertex of the traversal.
     * @param handler    A reference to a PreWorkPostWorkHandler that contains implementation
     *                   of pre-work and post-work operations that depends on the application of DFS.
     * @param resetMarks If the search should reset vertex visit marks.
     * @return Whether the traversal has stopped at the middle by the handler.
     */
    public boolean doSearch(VertexType vertex, PreWorkPostWorkHandler<VertexType> handler, boolean resetMarks)
            throws InvalidVertexException, InvalidGraphException {

        if (graph == null)
            throw new InvalidGraphException("Graph object is null.");

        if (resetMarks)
            for (VertexType v : graph)
                v.setMark(false);

        return depthFirstSearchRecursive(vertex, vertex, handler);
    }

    private boolean depthFirstSearchRecursive(VertexType vertex, VertexType fromVertex, PreWorkPostWorkHandler<VertexType> handler)
            throws InvalidVertexException {
        //TODO:Implementing non-recursive version
        //int vertexId = vertex.getId();
        vertex.setMark(true);
        if (handler != null)
            if (handler.doPreWork(fromVertex, vertex))
                return true;
        dispatchEvent(new PreWorkEvent<>(fromVertex, vertex, graph));
        EventUtils.algorithmStep(this, "visit: " + vertex.getId());
        VertexType lastInDepthVertex = vertex;

        for (VertexType i : graph) {
            if (graph.isEdge(vertex, i)) {
                if (!i.getMark()) {
                    lastInDepthVertex = i;
                    if (depthFirstSearchRecursive(i, vertex, handler))
                        return true;
                }
            }
        }

        dispatchEvent(new PostWorkEvent<>(lastInDepthVertex, vertex, graph));
        EventUtils.algorithmStep(this, "leave: " + vertex.getId());

        if (handler != null)
            return handler.doPostWork(lastInDepthVertex, vertex);

        return false;
    }


    public void doAlgorithm() {
        GraphRequest<VertexType, EdgeType> gr = new GraphRequest<>();
        dispatchEvent(gr);
        this.graph = gr.getGraph();
        VertexRequest<VertexType, EdgeType> vr = new VertexRequest<>(graph, "Please choose a vertex for the DFS algorithm.");
        dispatchEvent(vr);
        this.doSearch(vr.getVertex(), null);
    }
}
