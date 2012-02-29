// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.core.actions.graph;

import graphlab.graph.atributeset.GraphAttrSet;
import graphlab.graph.graph.AbstractGraphRenderer;
import graphlab.graph.graph.GraphModel;
import graphlab.platform.core.BlackBoard;
import graphlab.ui.UIUtils;

import javax.swing.*;


public class ClearGraph extends graphlab.platform.core.AbstractAction {

    public static final String event = UIUtils.getUIEventKey("Clear");

    public ClearGraph(BlackBoard bb) {
        super(bb);
        this.listen4Event(event);
    }

    public void performAction(String eventName, Object value) {
        GraphModel g = ((GraphModel) (blackboard.getData(GraphAttrSet.name)));
        if (true || JOptionPane.showConfirmDialog(null, "Current graph will be REMOVED from screen! Do you want to continue?",
                "GraphLab", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {

//        UndoableActionOccuredData uaod=new UndoableActionOccuredData("Clear Graph", this) ;
//        uaod.properties.put("ClearedGrapg",g);
//            blackboard.set(UndoableActionOccuredData.name, uaod);
            destroyGraph(g);
            AbstractGraphRenderer agr = blackboard.getData(AbstractGraphRenderer.EVENT_KEY);
            agr.repaint();
        }
    }

    public static void destroyGraph(GraphModel g) {
        g.clear();
    }
}
