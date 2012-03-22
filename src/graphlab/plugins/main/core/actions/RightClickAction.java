// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphlab.plugins.main.core.actions;

import graphlab.graph.GraphUtils;
import graphlab.graph.event.EdgeEvent;
import graphlab.graph.event.GraphEvent;
import graphlab.graph.event.VertexEvent;
import graphlab.graph.graph.*;
import graphlab.platform.core.BlackBoard;
import graphlab.platform.core.Listener;
import graphlab.platform.extension.BasicExtension;
import graphlab.plugins.main.GraphData;
import graphlab.plugins.main.extension.GraphActionExtension;
import graphlab.plugins.main.extension.GraphActionInterface;
import graphlab.plugins.main.select.DeleteSelected;
import graphlab.plugins.main.select.MakeSelectionComplementGraph;
import graphlab.plugins.main.select.MakeSelectionCompleteGraph;
import graphlab.plugins.main.select.MakeSelectionEmptyGraph;

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
    public HashMap<String, GraphActionInterface> menus = new HashMap<String, GraphActionInterface>();

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
                    gd.select.setSelected(new VertexModel[]{ve.v}, new EdgeModel[]{});
                }
                showPopup(ve.posOnGraph());
            }
        }
        if (key == EdgeEvent.EVENT_KEY) {
            EdgeEvent ee = (EdgeEvent) value;
            if (ee.eventType == EdgeEvent.CLICKED && ee.mouseBtn == MouseEvent.BUTTON3) {
                if (!gd.select.getSelectedEdges().contains(ee.e)) {
                    gd.select.setSelected(new VertexModel[]{}, new EdgeModel[]{ee.e});
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

    public void showPopup(GraphPoint p) {
        AbstractGraphRenderer gv = b.getData(AbstractGraphRenderer.EVENT_KEY);
        Point vp = GraphUtils.createViewPoint(gd.getGraph(), p);
        popup.show(gv, vp.x, vp.y);

    }
}
