// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.algorithmanimator.core.atoms;

//import static graphtea.library.event.GraphEvent.nameType.NEW_GRAPH;

import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.Vertex;
import graphtea.library.BaseEdge;
import graphtea.library.BaseVertex;
import graphtea.library.event.Event;
import graphtea.library.event.GraphEvent;
import graphtea.library.event.typedef.BaseGraphEvent;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.algorithmanimator.core.AtomAnimator;

import java.util.HashMap;
import java.util.Iterator;

import static graphtea.library.event.GraphEvent.EventType.NEW_GRAPH;

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
        BlackBoard bb = graphtea.plugins.main.core.actions.graph.NewGraph.doJob(b);
//        System.out.println("blackboard:"+bb);
        Object o = bb.getData(GraphAttrSet.name);
        //*********************** \/ \/ \/ \/ \/ \/ \/ 
//        System.out.println(o.getClass().getName());              //print mikone esme classe graph ro
//        System.out.println("instance:" +(o instanceof Graph));   //print mikone: false
        GraphModel g = ((GraphModel) o);
        //hich vaght 2 chap nemishe, iani too khate bala moshkeli hast
//        System.out.println("2");
        HashMap<BaseVertex, Vertex> map = new HashMap<BaseVertex, Vertex>();
        for (BaseVertex v : event.graph) {
            Vertex vv = new Vertex();
            map.put(v, vv);
            //vv.setModel((Vertex) v);
            g.insertVertex(vv);
            vv.setLocation(new GPoint(Math.random() * 200, Math.random() * 200));
        }
        Iterator<BaseEdge<BaseVertex>> ie = event.graph.edgeIterator();
        while (ie.hasNext()) {
            BaseEdge e = ie.next();
            Vertex src = map.get(e.source);
            Vertex dest = map.get(e.target);
            Edge ee = new Edge(src, dest);
            g.insertEdge(ee);
        }
        return event;
    }
}
