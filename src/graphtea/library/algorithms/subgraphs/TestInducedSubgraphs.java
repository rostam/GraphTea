// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.library.algorithms.subgraphs;

import graphtea.library.BaseEdge;
import graphtea.library.BaseGraph;
import graphtea.library.BaseVertex;
import graphtea.library.algorithms.Algorithm;
import graphtea.library.algorithms.AutomatedAlgorithm;
import graphtea.library.event.GraphEvent;
import graphtea.library.event.typedef.BaseGraphRequest;

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