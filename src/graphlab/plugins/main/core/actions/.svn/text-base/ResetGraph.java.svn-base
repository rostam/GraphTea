// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.core.actions;

import graphlab.graph.atributeset.GraphAttrSet;
import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.VertexModel;
import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.ui.UIUtils;

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
        for (VertexModel v : g) {
            v.setMark(false);
            v.setColor(0);
        }
        Iterator<EdgeModel> ie = g.edgeIterator();
        while (ie.hasNext()) {
            EdgeModel e = ie.next();
            e.setMark(false);
            //            e.model.setWeight(0);
            e.setColor(0);
        }
        g.setShowChangesOnView(b);
    }
}
