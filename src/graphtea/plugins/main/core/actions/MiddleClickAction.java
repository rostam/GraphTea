// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.plugins.main.core.actions;

import graphtea.graph.event.EdgeEvent;
import graphtea.graph.event.GraphEvent;
import graphtea.graph.event.VertexEvent;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.Listener;
import graphtea.platform.extension.BasicExtension;
import graphtea.plugins.main.GraphData;

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
