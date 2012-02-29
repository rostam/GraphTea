// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphlab.library.algorithms.vertexcover;

import graphlab.library.BaseEdge;
import graphlab.library.BaseGraph;
import graphlab.library.BaseVertex;
import graphlab.library.algorithms.Algorithm;
import graphlab.library.algorithms.AutomatedAlgorithm;
import graphlab.library.algorithms.util.EventUtils;
import graphlab.library.event.VertexEvent;
import graphlab.library.event.typedef.BaseGraphRequest;
import graphlab.library.exceptions.InvalidGraphException;
import graphlab.library.exceptions.InvalidVertexException;
import graphlab.library.genericcloners.BaseEdgeVertexCopier;
import graphlab.library.genericcloners.EdgeVertexConverter;

import java.util.Iterator;
import java.util.Vector;

/**
 * @author Soroush Sabet
 */
public class AppVertexCover<VertexType extends BaseVertex,
        EdgeType extends BaseEdge<VertexType>>
        extends Algorithm implements AutomatedAlgorithm {
    /**
     *
     */
    final BaseGraph<VertexType, EdgeType> graph;

    private EdgeVertexConverter<VertexType, VertexType, EdgeType, EdgeType> gc;

    /**
     * @param graph
     * @param gc
     */
    public AppVertexCover(BaseGraph<VertexType, EdgeType> graph,
                          EdgeVertexConverter<VertexType, VertexType, EdgeType, EdgeType> gc) {
//		if (gc == null || graph == null)
//			throw new NullPointerException();

        this.graph = graph;
        this.gc = gc;
    }


    /**
     * @return
     * @throws InvalidGraphException
     * @throws InvalidVertexException
     */

    public Vector<VertexType> findAppCover()
            throws InvalidGraphException, InvalidVertexException {

//        BaseGraph<VertexType,EdgeType> gCopy = graph.copy(gc);
        Vector<VertexType> C = new Vector<VertexType>();
        Vector<VertexType> D = new Vector<VertexType>();
        Vector<VertexType> marked = new Vector<VertexType>();
        Iterator<EdgeType> i;
        //cleat marks
        for (Iterator<EdgeType> ie = graph.edgeIterator(); ie.hasNext();)
            ie.next().setMark(false);
        EdgeType e;

        Iterator<EdgeType> iet = graph.edgeIterator();
        while (iet.hasNext()) {
            e = iet.next();
            if (!(C.contains(e.source) || C.contains(e.target))) {
                e.setMark(true);
//                dispatchEvent(new EdgeEvent<VertexType, EdgeType>(graph, e, EdgeEvent.EventType.MARK));
                EventUtils.algorithmStep(this, "");
                C.add(e.source);
                C.add(e.target);
                //            i = graph.edgeIterator(e.source);
                //            while(i.hasNext()){
                //                gCopy.removeEdge(i.next());
                //
                //            }

                //            i= gCopy.edgeIterator(e.target);
                //            while(i.hasNext()){
                //                gCopy.removeEdge(i.next());
                //            }
                //            iet = gCopy.edgeIterator();
            }
        }

        for (VertexType v : C) {
//            int j = v.getId();
//            for (VertexType u : graph){
//                if (u.getId() == j){
//                    dispatchEvent(new VertexEvent(graph, v, VertexEvent.EventType.MARK));
            v.setMark(true);
            EventUtils.algorithmStep(this, "");
            D.add(v);
//                }
//            }
        }

        return D;

    }

    public void doAlgorithm() {
        BaseGraphRequest gr = new BaseGraphRequest();
        dispatchEvent(gr);
        BaseGraph<BaseVertex, BaseEdge<BaseVertex>> graph = gr.getGraph();

//        BaseVertexRequest vr = new BaseVertexRequest(graph);
//        dispatchEvent(vr);

        AppVertexCover vc = new AppVertexCover(graph, new BaseEdgeVertexCopier());
        vc.acceptEventDispatcher(getDispatcher());

        Vector<VertexType> appCover = vc.findAppCover();
        for (VertexType v : appCover) {
            dispatchEvent(new VertexEvent<BaseVertex, BaseEdge<BaseVertex>>(graph, v, VertexEvent.EventType.MARK));
        }
//        BaseGraph<BaseVertex,BaseEdge<BaseVertex>> output =
//            prim.findMinimumSpanningTree(vr.getVertex());

//        dispatchEvent(new BaseGraphEvent(output));
    }
}