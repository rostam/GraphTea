// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.algorithmanimator.core.atoms;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.Vertex;
import graphtea.library.BaseGraph;
import graphtea.library.BaseVertex;
import graphtea.library.event.Event;
import graphtea.library.event.PostWorkEvent;
import graphtea.library.event.PreWorkEvent;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.algorithmanimator.core.AtomAnimator;

import java.util.Collection;

/**
 * @author Azin Azadi
 */
public class PrePostWork implements AtomAnimator<Event> {
    private PostWorkEvent t;
    PreWorkEvent tt;

    public boolean isAnimatable(Event event) {
        if (event instanceof PreWorkEvent)
            return true;
        return event instanceof PostWorkEvent;
    }

    public Event animate(Event event, BlackBoard b) {
        if (event instanceof PreWorkEvent) {
            tt = (PreWorkEvent) event;
            visit(tt.from, tt.to, tt.graph);
            return tt;
        }
        if (event instanceof PostWorkEvent) {
            t = (PostWorkEvent) event;
            leave(t.to);
            return t;
        }
        return null;
    }

    private void visit(BaseVertex from, BaseVertex v, BaseGraph graph) {
        if (from == v)
            return;
//        tt.setMessage("visit:" + v.getId());
        Vertex v2 = ((Vertex) v);
        Vertex v1 = ((Vertex) from);
        if (graph == null) {
            System.out.println("graph = null");
            return;
        }
        Collection edges = graph.getEdges(v1, v2);
        if (edges == null)
            return;
        Edge ee = ((Edge) edges.iterator().next());
        if (ee != null)
            ee.setColor(2);
        v2.setColor(3);
//        try {
//            Thread.sleep(100);
//            wait(400);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private void leave(BaseVertex v) {
//        t.setMessage("leave:" + v.getId());

        Vertex vv = ((Vertex) v);
        vv.setColor(4);
        // vv.view.repaint();

        try {
            Thread.sleep(100);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
