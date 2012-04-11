// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.algorithmanimator.core.atoms;

import graphlab.graph.graph.Edge;
import graphlab.graph.graph.Vertex;
import graphlab.library.BaseGraph;
import graphlab.library.BaseVertex;
import graphlab.library.event.Event;
import graphlab.library.event.PostWorkEvent;
import graphlab.library.event.PreWorkEvent;
import graphlab.platform.core.BlackBoard;
import graphlab.plugins.algorithmanimator.core.AtomAnimator;

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
