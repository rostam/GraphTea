// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.library.algorithms.coloring;

import graphtea.library.BaseEdge;
import graphtea.library.BaseGraph;
import graphtea.library.BaseVertex;
import graphtea.library.algorithms.Algorithm;
import graphtea.library.algorithms.AutomatedAlgorithm;
import graphtea.library.event.GraphRequest;
import graphtea.library.event.VertexEvent;
import graphtea.library.exceptions.InvalidGraphException;

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
                    new VertexEvent<>(graph, v, VertexEvent.EventType.COLOR_CHANGE));
        }
    }

    public void doAlgorithm() {
        GraphRequest<VertexType, EdgeType> gr = new GraphRequest<>();
        dispatchEvent(gr);
        graph = gr.getGraph();
        doColoring();
    }

}
