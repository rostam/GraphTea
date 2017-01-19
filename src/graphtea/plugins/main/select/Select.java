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

import java.awt.*;

/**
 * Author: Azin Azadi
 */
public class Select extends AbstractAction {
    private boolean deSelectOlderSelections = true;
    private boolean invertOlderSelections;
    public static final String EVENT_KEY = "graph selected V and E";

    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */
    public Select(BlackBoard bb) {
        super(bb);
        listen4Event(VertexSelectData.EVENT_KEY);
        listen4Event(EdgeSelectData.EVENT_KEY);
        blackboard.setData(EVENT_KEY, new SubGraph());
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventPostProcessor(e -> {
            deSelectOlderSelections = true;
            invertOlderSelections = false;
            if (e.isControlDown())
                deSelectOlderSelections = false;
            if (e.isShiftDown())
                invertOlderSelections = true; //not yet implemented //todo: implement
            return false;
        });
    }
    public void track(){}

    public void performAction(String eventName, Object value) {
        if (eventName == VertexSelectData.EVENT_KEY) {
            selectVertex();

        }
        if (eventName == EdgeSelectData.EVENT_KEY)
            selectEdge();
    }

    public static SubGraph getSelection(BlackBoard blackboard) {
        return blackboard.getData(EVENT_KEY);
    }

    public static void setSelection(BlackBoard blackboard, SubGraph sg) {
        blackboard.setData(EVENT_KEY, sg);
    }

    private void selectEdge() {
        EdgeSelectData esd = blackboard.getData(EdgeSelectData.EVENT_KEY);
        SubGraph sd = getSelection(blackboard);
        Edge e = esd.edge;
        if (deSelectOlderSelections) {
            sd.edges.clear();
            sd.vertices.clear();
            sd.edges.add(e);
        } else {
            if (sd.edges.contains(e)) {
                sd.edges.remove(e);
            } else
                sd.edges.add(e);
        }
        blackboard.setData(EVENT_KEY, sd);
    }

    private void selectVertex() {
        VertexSelectData vsd = blackboard.getData(VertexSelectData.EVENT_KEY);
        SubGraph sd = getSelection(blackboard);
        Vertex v = vsd.v;
        if (deSelectOlderSelections) {
            sd.edges.clear();
            sd.vertices.clear();
            sd.vertices.add(v);
        } else {
            if (sd.vertices.contains(v)) {
                sd.vertices.remove(v);
            } else
                sd.vertices.add(v);
        }

        blackboard.setData(EVENT_KEY, sd);
    }
}
