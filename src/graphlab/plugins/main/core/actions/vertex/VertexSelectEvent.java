// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.core.actions.vertex;

import graphlab.graph.event.VertexEvent;
import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;

import java.awt.event.MouseEvent;

/**
 * @author azin azadi
 */
public class VertexSelectEvent extends AbstractAction {
    public VertexSelectEvent(BlackBoard bb) {
        super(bb);
        listen4Event(VertexEvent.EVENT_KEY);
    }

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

