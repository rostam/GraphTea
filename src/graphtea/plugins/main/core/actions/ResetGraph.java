// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.core.actions;

import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.ui.UIUtils;

import java.util.Iterator;

/**
 * @author azin azadi

 */
public class ResetGraph extends AbstractAction {
    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */
    public ResetGraph(BlackBoard bb) {
        super(bb);
        listen4Event(UIUtils.getUIEventKey("reset"));
    }

    public void performAction(String eventName, Object value) {
        GraphModel g = blackboard.getData(GraphAttrSet.name);
        resetGraph(g);
    }

    /**
     * resets the state of current graph
     * this means to set the properties of vertices and edges to default values,
     * mark=false, model color=0, view color= default color     * @param g
     */
    public static void resetGraph(GraphModel g) {
        boolean b = g.isShowChangesOnView();
        g.setShowChangesOnView(true);
        for (Vertex v : g) {
            v.setMark(false);
            v.setColor(1);
        }
        Iterator<Edge> ie = g.edgeIterator();
        while (ie.hasNext()) {
            Edge e = ie.next();
            e.setMark(false);
            //            e.model.setWeight(0);
            e.setColor(0);
        }
        g.setShowChangesOnView(b);
    }
}
