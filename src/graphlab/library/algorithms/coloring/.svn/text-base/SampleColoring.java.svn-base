// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphlab.library.algorithms.coloring;

import graphlab.library.BaseEdge;
import graphlab.library.BaseGraph;
import graphlab.library.BaseVertex;
import graphlab.library.algorithms.Algorithm;
import graphlab.library.algorithms.AutomatedAlgorithm;
import graphlab.library.event.GraphRequest;
import graphlab.library.event.VertexEvent;
import graphlab.library.exceptions.InvalidGraphException;

public class SampleColoring
        <VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
        extends Algorithm
        implements AutomatedAlgorithm

{
    BaseGraph<VertexType, EdgeType> graph;

    public SampleColoring(BaseGraph<VertexType, EdgeType> g) {
        graph = g;

    }

    public SampleColoring() {
        graph = null;

    }

    public void doColoring() {
        if (graph == null)
            throw new InvalidGraphException();

        for (VertexType v : graph) {
            v.setColor(graph.getInDegree(v));
            dispatchEvent(
                    new VertexEvent<VertexType, EdgeType>(graph, v, VertexEvent.EventType.COLOR_CHANGE));
        }
    }

    public void doAlgorithm() {
        GraphRequest<VertexType, EdgeType> gr = new GraphRequest<VertexType, EdgeType>();
        dispatchEvent(gr);
        graph = gr.getGraph();
        doColoring();
    }

}
