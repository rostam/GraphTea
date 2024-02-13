// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.select;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.SubGraph;
import graphtea.graph.graph.Vertex;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.main.core.actions.edge.EdgeSelectData;
import graphtea.plugins.main.core.actions.vertex.VertexSelectData;

import java.util.Objects;

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
    public void track(){}

    public void performAction(String eventName, Object value) {
        if (Objects.equals(eventName, VertexSelectData.EVENT_KEY))
            selectVertex();
        if (Objects.equals(eventName, EdgeSelectData.EVENT_KEY))
            selectEdge();
    }

    public SubGraph getSelection() {
        return Select.getSelection(blackboard);
    }

    private void selectEdge() {
        EdgeSelectData esd = blackboard.getData(EdgeSelectData.EVENT_KEY);
        SubGraph sd = getSelection();
        Edge e = esd.edge;
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
        Vertex v = vsd.v;
        //v.view.isSelected=!v.view.isSelected;
//        if (!v.view.isSelected)
        sd.vertices.add(v);
//        else
//            sd.vertices.remove(v);
        Select.setSelection(blackboard, sd);
    }
}
