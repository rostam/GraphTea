// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.plugins.main.core.actions;

import graphtea.graph.GraphUtils;
import graphtea.graph.event.EdgeEvent;
import graphtea.graph.event.GraphEvent;
import graphtea.graph.event.VertexEvent;
import graphtea.graph.graph.*;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.Listener;
import graphtea.platform.extension.BasicExtension;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.extension.GraphActionExtension;
import graphtea.plugins.main.extension.GraphActionInterface;
import graphtea.plugins.main.select.DeleteSelected;
import graphtea.plugins.main.select.MakeSelectionComplementGraph;
import graphtea.plugins.main.select.MakeSelectionCompleteGraph;
import graphtea.plugins.main.select.MakeSelectionEmptyGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;

/**
 * @author Azin Azadi
 */
public class RightClickAction implements BasicExtension, Listener {
    BlackBoard b;
    GraphData gd;
    private JPopupMenu popup = new JPopupMenu();
    public HashMap<String, GraphActionInterface> menus = new HashMap<>();

    public RightClickAction(BlackBoard b) {
        this.b = b;
        gd = new GraphData(b);
        //listening to G/V/E events
        b.addListener(VertexEvent.EVENT_KEY, this);
        b.addListener(EdgeEvent.EVENT_KEY, this);
        b.addListener(GraphEvent.EVENT_KEY, this);
        fillPopupMenu();

    }

    private void addToMenus(GraphActionExtension gae) {
        menus.put(gae.getName(), gae);
    }

    public void fillPopupMenu() {
        addToMenus(new MakeSelectionCompleteGraph());
        addToMenus(new MakeSelectionComplementGraph());
        addToMenus(new MakeSelectionEmptyGraph());
        addToMenus(new DeleteSelected(b));
        for (final String name : menus.keySet()) {
            JMenuItem item = new JMenuItem(name);
            item.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    menus.get(name).action(gd);
                }
            });
            popup.add(item);
        }
//        JMenuItem item = new JMenuItem("Delete");
//        item.addActionListener(new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                gd.select.
//            }
//        });
    }


    public void keyChanged(String key, Object value) {
        if (key == VertexEvent.EVENT_KEY) {
            VertexEvent ve = (VertexEvent) value;
            if (ve.eventType == VertexEvent.CLICKED && ve.mouseBtn == MouseEvent.BUTTON3) {
                if (!gd.select.getSelectedVertices().contains(ve.v)) {
                    gd.select.setSelected(new Vertex[]{ve.v}, new Edge[]{});
                }
                showPopup(ve.posOnGraph());
            }
        }
        if (key == EdgeEvent.EVENT_KEY) {
            EdgeEvent ee = (EdgeEvent) value;
            if (ee.eventType == EdgeEvent.CLICKED && ee.mouseBtn == MouseEvent.BUTTON3) {
                if (!gd.select.getSelectedEdges().contains(ee.e)) {
                    gd.select.setSelected(new Vertex[]{}, new Edge[]{ee.e});
                }
                showPopup(ee.posOnGraph());
            }
        }
        if (key == GraphEvent.EVENT_KEY) {
            GraphEvent ge = (GraphEvent) value;
            GraphModel g = ge.graph;
            if (ge.eventType == GraphEvent.CLICKED && ge.mouseBtn == MouseEvent.BUTTON3) {
                showPopup(ge.mousePos);
            }
        }
    }

    public void showPopup(GPoint p) {
        AbstractGraphRenderer gv = b.getData(AbstractGraphRenderer.EVENT_KEY);
        Point vp = GraphUtils.createViewPoint(gd.getGraph(), p);
        popup.show(gv, vp.x, vp.y);

    }
}
