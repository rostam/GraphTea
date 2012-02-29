// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.select;

import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.SubGraph;
import graphlab.graph.graph.VertexModel;
import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.plugins.main.core.actions.edge.EdgeSelectData;
import graphlab.plugins.main.core.actions.vertex.VertexSelectData;

/**
 * Author: Azin Azadi
 */
public class DeSelect extends AbstractAction {

    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */
    public DeSelect(BlackBoard bb) {
        super(bb);
        listen4Event(VertexSelectData.EVENT_KEY);
        listen4Event(EdgeSelectData.EVENT_KEY);
        blackboard.setData(Select.EVENT_KEY, new SubGraph());
    }

    public void performAction(String eventName, Object value) {
        if (eventName == VertexSelectData.EVENT_KEY)
            selectVertex();
        if (eventName == EdgeSelectData.EVENT_KEY)
            selectEdge();
    }

    public SubGraph getSelection() {
        return Select.getSelection(blackboard);
    }

    private void selectEdge() {
        EdgeSelectData esd = blackboard.getData(EdgeSelectData.EVENT_KEY);
        SubGraph sd = getSelection();
        EdgeModel e = esd.edge;
        //e.view.isSelected=!e.view.isSelected;

//        if (!e.view.isSelected)
        sd.edges.add(e);
//        else
//            sd.edges.remove(e);
        Select.setSelection(blackboard, sd);
    }

    private void selectVertex() {
        VertexSelectData vsd = blackboard.getData(VertexSelectData.EVENT_KEY);
        SubGraph sd = getSelection();
        VertexModel v = vsd.v;
        //v.view.isSelected=!v.view.isSelected;
//        if (!v.view.isSelected)
        sd.vertices.add(v);
//        else
//            sd.vertices.remove(v);
        Select.setSelection(blackboard, sd);
    }
}
