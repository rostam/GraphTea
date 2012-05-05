// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

/*
 * BreadthFirstSearch.java
 *
 * Created on November 21, 2004, 12:58 AM
 */

package graphtea.library.algorithms.traversal;

import graphtea.library.BaseEdge;
import graphtea.library.BaseGraph;
import graphtea.library.BaseVertex;
import graphtea.library.algorithms.Algorithm;
import graphtea.library.algorithms.AutomatedAlgorithm;
import graphtea.library.algorithms.util.EventUtils;
import graphtea.library.event.GraphRequest;
import graphtea.library.event.PreWorkEvent;
import graphtea.library.event.VertexRequest;
import graphtea.library.event.handlers.PreWorkHandler;
import graphtea.library.exceptions.InvalidVertexException;

import java.util.LinkedList;

/**
 * Description here.!
 *
 * @author Omid Aladini
 */
public class BreadthFirstSearch<VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
        extends Algorithm implements AutomatedAlgorithm {
    private BaseGraph<VertexType, EdgeType> graph;

    public BreadthFirstSearch(BaseGraph<VertexType, EdgeType> graph) {
        this.graph = graph;
    }

    public BreadthFirstSearch() {
        this.graph = null;
    }

    public boolean doSearch(VertexType vertex, PreWorkHandler<VertexType> handler)
            throws InvalidVertexException {
        //int vertexId = vertex.getId();

        for (VertexType v : graph)
            v.setMark(false);

        vertex.setMark(true);
        LinkedList<VertexType> queue = new LinkedList<VertexType>();
        LinkedList<VertexType> roots = new LinkedList<VertexType>();
        queue.offer(vertex);
        roots.offer(vertex);
        try {
            while (queue.size() != 0) {
                VertexType index = queue.poll();
                VertexType root = roots.poll();

                if (handler != null)
                    if (handler.doPreWork(root, index))
                        return true;

                dispatchEvent(new PreWorkEvent<VertexType, EdgeType>(root, index, graph));
                EventUtils.algorithmStep(this, "explore: " + index.getId());


                for (VertexType i : graph) {
                    if (graph.isEdge(index, i) && !i.getMark()) {
                        i.setMark(true);
                        EventUtils.algorithmStep(this, "visit: " + i.getId());
                        queue.offer(i);
                        roots.offer(index);
                    }

                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public void doAlgorithm() {
        GraphRequest<VertexType, EdgeType> gr = new GraphRequest<VertexType, EdgeType>();
        dispatchEvent(gr);
        this.graph = gr.getGraph();
        VertexRequest<VertexType, EdgeType> vr = new VertexRequest<VertexType, EdgeType>(graph, "Please choose a vertex for the BFS algorithm.");
        dispatchEvent(vr);
        this.doSearch(vr.getVertex(), null);
    }
}
