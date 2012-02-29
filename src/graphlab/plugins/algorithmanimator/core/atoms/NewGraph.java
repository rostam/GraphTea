// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.algorithmanimator.core.atoms;

//import static graphlab.library.event.GraphEvent.nameType.NEW_GRAPH;

import graphlab.graph.atributeset.GraphAttrSet;
import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.GraphPoint;
import graphlab.graph.graph.VertexModel;
import graphlab.library.BaseEdge;
import graphlab.library.BaseVertex;
import graphlab.library.event.Event;
import graphlab.library.event.GraphEvent;
import static graphlab.library.event.GraphEvent.EventType.NEW_GRAPH;
import graphlab.library.event.typedef.BaseGraphEvent;
import graphlab.platform.core.BlackBoard;
import graphlab.plugins.algorithmanimator.core.AtomAnimator;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Azin Azadi
 */
public class NewGraph implements AtomAnimator<BaseGraphEvent> {
    public boolean isAnimatable(Event event) {
        if (event instanceof GraphEvent) {
            if (((GraphEvent) event).eventType == NEW_GRAPH)
                return true;
        }
        return false;
    }

    public BaseGraphEvent animate(BaseGraphEvent event, BlackBoard b) {
        BlackBoard bb = graphlab.plugins.main.core.actions.graph.NewGraph.doJob(b);
//        System.out.println("blackboard:"+bb);
        Object o = bb.getData(GraphAttrSet.name);
        //*********************** \/ \/ \/ \/ \/ \/ \/ 
//        System.out.println(o.getClass().getName());              //print mikone esme classe graph ro
//        System.out.println("instance:" +(o instanceof Graph));   //print mikone: false
        GraphModel g = ((GraphModel) o);
        //hich vaght 2 chap nemishe, iani too khate bala moshkeli hast
//        System.out.println("2");
        HashMap<BaseVertex, VertexModel> map = new HashMap<BaseVertex, VertexModel>();
        for (BaseVertex v : event.graph) {
            VertexModel vv = new VertexModel();
            map.put(v, vv);
            //vv.setModel((VertexModel) v);
            g.insertVertex(vv);
            vv.setLocation(new GraphPoint(Math.random() * 200, Math.random() * 200));
        }
        Iterator<BaseEdge<BaseVertex>> ie = event.graph.edgeIterator();
        while (ie.hasNext()) {
            BaseEdge e = ie.next();
            VertexModel src = map.get(e.source);
            VertexModel dest = map.get(e.target);
            EdgeModel ee = new EdgeModel(src, dest);
            g.insertEdge(ee);
        }
        return event;
    }
}
