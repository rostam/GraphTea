// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.core.actions.edge;

import graphtea.graph.event.EdgeEvent;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;

import java.awt.event.MouseEvent;

/**
 * @author Ruzbeh
 */
public class EdgeSelectEvent extends AbstractAction {
    public EdgeSelectEvent(BlackBoard bb) {
        super(bb);
        listen4Event(EdgeEvent.EVENT_KEY);
    }

    public void track(){}
    public void performAction(String eventName, Object value) {
        EdgeSelectData d = new EdgeSelectData();
        EdgeEvent evd = blackboard.getData(EdgeEvent.EVENT_KEY);
        if (evd.eventType == EdgeEvent.CLICKED) {
            if (evd.mouseBtn == MouseEvent.BUTTON1) {
                d.edge = evd.e;
                blackboard.setData(EdgeSelectData.EVENT_KEY, d);
            }
        }
    }
}