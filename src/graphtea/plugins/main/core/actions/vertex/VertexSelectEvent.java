// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.core.actions.vertex;

import graphtea.graph.event.VertexEvent;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;

import java.awt.event.MouseEvent;

/**
 * @author azin azadi
 */
public class VertexSelectEvent extends AbstractAction {
    public VertexSelectEvent(BlackBoard bb) {
        super(bb);
        listen4Event(VertexEvent.EVENT_KEY);
    }
    public void track(){}

    public void performAction(String eventName, Object value) {
        VertexSelectData d = new VertexSelectData();
        VertexEvent cvd = blackboard.getData(VertexEvent.EVENT_KEY);
        if (cvd.eventType == VertexEvent.CLICKED) {
            if (cvd.mouseBtn == MouseEvent.BUTTON1) {
                d.v = cvd.v;
                blackboard.setData(VertexSelectData.EVENT_KEY, d);
            }
        }
    }
}

