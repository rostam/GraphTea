// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphlab.library.algorithms.subgraphs;

import graphlab.library.BaseEdge;
import graphlab.library.BaseGraph;
import graphlab.library.BaseVertex;
import graphlab.library.algorithms.Algorithm;
import graphlab.library.algorithms.AutomatedAlgorithm;
import graphlab.library.event.GraphEvent;
import graphlab.library.event.typedef.BaseGraphRequest;

import java.util.ArrayList;

public class TestInducedSubgraphs extends Algorithm implements AutomatedAlgorithm {

    public void doAlgorithm() {
        BaseGraphRequest gr = new BaseGraphRequest();
        dispatchEvent(gr);
        BaseGraph<BaseVertex, BaseEdge<BaseVertex>> graph = gr.getGraph(), induced;

        ArrayList<BaseVertex> inducedVertices = new ArrayList<BaseVertex>();

        int i = 0;
        for (BaseVertex v : graph) {
            if (i > graph.getVerticesCount() / 2)
                break;
            inducedVertices.add(v);
        }

        induced = InducedSubgraphs.getVertexInducedSubgraph(graph, inducedVertices);
        dispatchEvent(new GraphEvent<BaseVertex, BaseEdge<BaseVertex>>(induced, GraphEvent.EventType.NEW_GRAPH));
    }

    ;

}