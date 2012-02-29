// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

/*
 * BreadthFirstSearch.java
 *
 * Created on November 21, 2004, 12:58 AM
 */

package graphlab.library.algorithms.traversal;

import graphlab.library.BaseEdge;
import graphlab.library.BaseGraph;
import graphlab.library.BaseVertex;
import graphlab.library.algorithms.Algorithm;
import graphlab.library.algorithms.AutomatedAlgorithm;
import graphlab.library.algorithms.util.EventUtils;
import graphlab.library.event.GraphRequest;
import graphlab.library.event.PreWorkEvent;
import graphlab.library.event.VertexRequest;
import graphlab.library.event.handlers.PreWorkHandler;
import graphlab.library.exceptions.InvalidVertexException;

import java.util.LinkedList;

/**
 * Description here.
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
