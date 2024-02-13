// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.core.actions.edge;

import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
/**
 * User: Shabn
 */
public class EdgeHighlightAction extends AbstractAction {
    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */
    public EdgeHighlightAction(BlackBoard bb) {
        super(bb);
        //deactivated for simplicity reasons (removing highlighted mode from edge)
//        listen4Event(EdgeMouseEnteredExitedData.event);
//        listen4Event(EdgeNotifyData.event);
    }
//

    public void performAction(String eventName, Object value) {
//        if (name.equals(EdgeMouseEnteredExitedData.name)) {
//            EdgeMouseEnteredExitedData e = blackboard.get(EdgeMouseEnteredExitedData.name);
//            Edge edge = e.e;
//            if (e.isEntered) {
//                highLightEdge(edge);
//            }
//            if (e.isExited) {
//                unHighLightEdge(edge);
//            }
//            e.e.view.repaint();
//        } else if (name.equals(EdgeNotifyData.name)) {
//            EdgeNotifyData e = blackboard.get(EdgeNotifyData.name);
////            if (e.isNotified == EdgeNotifyData.NOTIFIED ) {
////                highLightEdge(e.e);
////            }
////            if (e.isNotified == EdgeNotifyData.UN_NOTIFIED){
////                unHighLightEdge(e.e);
////            }
//            e.e.view.repaint();
//        }
    }
//
//    public static void unHighLightEdge(Edge edge) {
//        edge.model.lc = Edge.LineColor.normal;
//    }
//
//    public static void highLightEdge(Edge edge) {
//        edge.model.lc = Edge.LineColor.highlight;
//    }
//
//    public static boolean isEdgeHighlighted(Edge e) {
//        return e.model.lc == Edge.LineColor.normal;
//    }
}
