// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphlab.plugins.main.core.actions;

import graphlab.graph.event.EdgeEvent;
import graphlab.graph.event.GraphEvent;
import graphlab.graph.event.VertexEvent;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.Vertex;
import graphlab.platform.core.BlackBoard;
import graphlab.platform.core.Listener;
import graphlab.platform.extension.BasicExtension;
import graphlab.plugins.main.GraphData;

import java.awt.event.MouseEvent;

/**
 * @author Azin Azadi
 */
public class MiddleClickAction implements BasicExtension, Listener {
    BlackBoard b;
    GraphData gd;

    public MiddleClickAction(BlackBoard b) {
        this.b = b;
        gd = new GraphData(b);
        //listening to G/V/E events
        b.addListener(VertexEvent.EVENT_KEY, this);
        b.addListener(EdgeEvent.EVENT_KEY, this);
        b.addListener(GraphEvent.EVENT_KEY, this);
    }

    public void keyChanged(String key, Object value) {
        if (key == VertexEvent.EVENT_KEY) {
            VertexEvent ve = (VertexEvent) value;
            if (ve.eventType == VertexEvent.CLICKED && ve.mouseBtn == MouseEvent.BUTTON2) {
                ve.v.setMark(!ve.v.getMark());
                GraphModel g = gd.getGraph();
                int di = ve.v.getMark() ? 1 : -1;
                for (Vertex v : g.getNeighbors(ve.v)) {
                    v.setColor(v.getColor() + di);
                }
            }
        }
        if (key == EdgeEvent.EVENT_KEY) {
            EdgeEvent ee = (EdgeEvent) value;
            if (ee.eventType == EdgeEvent.CLICKED && ee.mouseBtn == MouseEvent.BUTTON2) {
                ee.e.setMark(!ee.e.getMark());
            }
        }
        if (key == GraphEvent.EVENT_KEY) {
            GraphEvent ge = (GraphEvent) value;
            GraphModel g = ge.graph;
            if (ge.eventType == GraphEvent.MOUSE_WHEEL_MOVED) {
                if (gd.select.isSelectionEmpty()) {
                    if (ge.mouseWheelMoveAmount > 0)
                        g.zoomIn();
                    else
                        g.zoomOut();
                } else {
                    if (ge.mouseWheelMoveAmount > 0)
                        gd.select.expandSelection();
                    else
                        for (Vertex v : gd.select.getSelectedVertices()) {
                            v.setColor((v.getColor() + 1) % 20);
                        }
                }
            }
        }
    }

}
