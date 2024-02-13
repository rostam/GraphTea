// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.core.actions;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.Vertex;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.main.core.actions.edge.EdgeSelectData;
import graphtea.plugins.main.core.actions.vertex.VertexSelectData;
import graphtea.ui.components.GComponentInterface;
import graphtea.ui.components.gpropertyeditor.utils.ObjectViewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Author: azin azadi
 */
public class LastSelectedStatusAction extends graphtea.platform.core.AbstractAction implements
        GComponentInterface {
    /**
     * @param bb the blackboard of the action
     */
    public LastSelectedStatusAction(BlackBoard bb) {
        super(bb);
        listen4Event(VertexSelectData.EVENT_KEY);
        listen4Event(EdgeSelectData.EVENT_KEY);
    }

    JLabel l;

    boolean labelHandled = false;

    private void handleMouseListener() {
        if (l == null)
            return;
        if (!labelHandled) {
            l.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    ObjectViewer.showObject(last);
                }
            });
            labelHandled = true;
        }

    }

    Object last;

    /**
     * like Action
     *
     * @param eventName The event name
     * @param value The value
     */
    public void performAction(String eventName, Object value) {
        l = label();
        handleMouseListener();
        if (eventName == VertexSelectData.EVENT_KEY) {
            VertexSelectData last = blackboard.getData(VertexSelectData.EVENT_KEY);
            Vertex v = last.v;
            l.setText("Vertex :: " + (v.getLabel()));
            this.last = v;
        }
        if (eventName == EdgeSelectData.EVENT_KEY) {
            Edge e = ((EdgeSelectData) blackboard.getData(EdgeSelectData.EVENT_KEY)).edge;
            l.setText("Edge :: " + (e.source.getLabel()) + "-->"
                    + (e.target.getLabel()));
            last = e;
        }
    }

    private JLabel label() {
        return lab;
    }

    JLabel lab = new JLabel("last select");

    public Component getComponent(BlackBoard b) {
        return lab;
    }
}
