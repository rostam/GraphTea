// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.extensions.algorithms.vertexcover;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.library.exceptions.InvalidGraphException;
import graphtea.library.exceptions.InvalidVertexException;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.algorithmanimator.core.GraphAlgorithm;
import graphtea.plugins.algorithmanimator.extension.AlgorithmExtension;

import java.util.Iterator;
import java.util.Vector;

/**
 * @author Soroush Sabet
 */
public class AppVertexCover extends GraphAlgorithm implements AlgorithmExtension {
    public AppVertexCover(BlackBoard blackBoard) {
        super(blackBoard);
    }

    /**
     * @param graph The input graph
     */
//    public AppVertexCover(GraphModel graph) {
//        super();
//        this.graph = graph;
//    }


    /**
     * @return Vector of vertices
     * @throws InvalidGraphException The graph is invalid
     * @throws InvalidVertexException The vertex is invalid
     */
    @Override
    public void doAlgorithm() {
        step("Start of the algorithm.") ;
        GraphModel graph = graphData.getGraph();
        GraphModel gCopy = graph.getCopy();
        Vector<Vertex> C = new Vector<>();
        Vector<Vertex> D = new Vector<>();
        Vector<Vertex> marked = new Vector<>();
        Iterator<Edge> i;
        //cleat marks
        for (Iterator<Edge> ie = graph.edgeIterator(); ie.hasNext();)
            ie.next().setMark(false);
        Edge e;

        Iterator<Edge> iet = graph.edgeIterator();
        while (iet.hasNext()) {
            e = iet.next();
            if (!(C.contains(e.source) || C.contains(e.target))) {
                e.setMark(true);
                step("");
                C.add(e.source);
                C.add(e.target);
                            i = graph.edgeIterator(e.source);
                            while(i.hasNext()){
                                gCopy.removeEdge(i.next());

                            }

                            i= gCopy.edgeIterator(e.target);
                            while(i.hasNext()){
                                gCopy.removeEdge(i.next());
                            }
                            iet = gCopy.edgeIterator();
            }
        }

        for (Vertex v : C) {
//            int j = v.getId();
//            for (Vertex u : graph){
//                if (u.getId() == j){
//                    dispatchEvent(new VertexEvent(graph, v, VertexEvent.EventType.MARK));
            v.setMark(true);
            step("");
            D.add(v);
//                }
//            }
        }
    }

    @Override
    public String getName() {
        return "Vertex Cover";
    }

    @Override
    public String getDescription() {
        return "Vertex Cover";
    }

//    public void doAlgorithm() {
//        step("Start of the algorithm.") ;
//        GraphModel graph = graphData.getGraph();
//        BaseGraphRequest gr = new BaseGraphRequest();
//        dispatchEvent(gr);
//        BaseGraph<BaseVertex, BaseEdge<BaseVertex>> graph = gr.getGraph();
//
////        BaseVertexRequest vr = new BaseVertexRequest(graph);
////        dispatchEvent(vr);
//
//        AppVertexCover vc = new AppVertexCover(graph, new BaseEdgeVertexCopier());
//        vc.acceptEventDispatcher(getDispatcher());
//
//        Vector<Vertex> appCover = vc.findAppCover();
//        for (Vertex v : appCover) {
//            dispatchEvent(new VertexEvent<>(graph, v, VertexEvent.EventType.MARK));
//        }
////        BaseGraph<BaseVertex,BaseEdge<BaseVertex>> output =
////            prim.findMinimumSpanningTree(vr.getVertex());
//
////        dispatchEvent(new BaseGraphEvent(output));
//    }
}