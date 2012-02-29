// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.select;

import graphlab.graph.event.GraphEvent;
import graphlab.graph.graph.SubGraph;
import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.plugins.main.core.actions.VertexTransformer;

/**
 * @author Azin Azadi
 */
public class ClearSelection extends AbstractAction {
    /**
     * indicates that wheter the last time GraphSelectPointData fired, it was originally cleared or not (it was cleared by this object)
     * the initially use was for AddVertex, because it only adds vertices on graphselectpoints and if the selection was empty,
     * so AddVertex and ClearSelection are both listening to GraphSelectPoint, so if
     * ClearSelection fired before AddVertex it clears the selection and then AddVertex adds the Vertex
     * but it shouldn't do that, because at the click time the selection wasn'nt clear
     * and it is a mistake (bug) from users point of view,....
     */
    public static final String lastTimeGraphWasClear = "last time graph was cleared";

    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */
    public ClearSelection(BlackBoard bb) {
        super(bb);
//        listen4Event(UI.getUIEvent("clear selection"));
        listen4Event(GraphEvent.EVENT_KEY);
        blackboard.setData(lastTimeGraphWasClear, false);
    }

    SubGraph sd;

    public void performAction(String eventName, Object value) {
        GraphEvent gpd = (GraphEvent) value;
        if (gpd.eventType != GraphEvent.CLICKED) {
            return;
        }

        if (VertexTransformer.isPositionOnResizeBoxes(gpd.mousePos, blackboard))
            return;

        sd = Select.getSelection(blackboard);
        if (sd.vertices.size() != 0 || sd.edges.size() != 0) {
            blackboard.setData(lastTimeGraphWasClear, false);
            clearSelected(blackboard);
        } else
            blackboard.setData(lastTimeGraphWasClear, true);

    }

    public static void clearSelected(BlackBoard bb) {
        bb.setData(Select.EVENT_KEY, new SubGraph());
    }
}
