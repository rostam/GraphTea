// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.core.actions.vertex;

import graphlab.graph.event.VertexEvent;
import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;

/**
 * Highlights a vertex when the mouse entered it.
 * User: Shabn
 */
public class VertexHighlightAction extends AbstractAction {
    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */
    public VertexHighlightAction(BlackBoard bb) {
        super(bb);
//        listen4Event(VertexMouseEnteredExitedData.event);
        listen4Event(VertexEvent.EVENT_KEY);
    }

    public void performAction(String eventName, Object value) {
//        if (name.equals(VertexMouseEnteredExitedData.name)) {
        //highlighting is disabled for simplicity reasons
//            VertexMouseEnteredExitedData e = blackboard.get(VertexMouseEnteredExitedData.name);
//            if (e.isEntered) {
//                e.v.lc = (highlight);
//            }
//            if (e.isExited) {
//                e.v.lc = (normal);
//            }
////            e.v.view.repaint();
//        }
//        if (name.equals(VertexNotifyData.name)) {
//            VertexNotifyData vnd = blackboard.get(VertexNotifyData.name);
//            if (vnd.isNotified == VertexNotifyData.NOTIFIED)
//                vnd.v.view.lc = VertexView.LineColor.notified;
//            if (vnd.isNotified == VertexNotifyData.UN_NOTIFIED)
//                vnd.v.view.lc = VertexView.LineColor.normal;
//            vnd.v.view.repaint();
//        }
    }

}
