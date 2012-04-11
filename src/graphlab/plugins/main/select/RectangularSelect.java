// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.select;

import graphlab.graph.atributeset.GraphAttrSet;
import graphlab.graph.event.GraphEvent;
import graphlab.graph.graph.*;
import graphlab.graph.ui.GraphRectRegionSelect;
import graphlab.library.exceptions.InvalidVertexException;
import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.plugins.main.core.actions.VertexTransformer;
import graphlab.ui.UIUtils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

/**
 * @author azin azadi
 */
public class RectangularSelect extends AbstractAction {
    String event = UIUtils.getUIEventKey("rectangular select");
    GraphModel g;
    boolean deleteOlderSelections = true;
    GraphRectRegionSelect graphRectRegionSelector = new GraphRectRegionSelect(blackboard) {

        public void onMouseMoved(GraphEvent data) {
            _onMouseMoved(data);
        }

        public void onDrop(GraphEvent data) {
            _onDrop(data);
        }
    };
    static BlackBoard gb;

    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */
    public RectangularSelect(BlackBoard bb) {
        super(bb);
        gb = bb;
        listen4Event(GraphEvent.EVENT_KEY);
        graphRectRegionSelector.startSelectingRegion();
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventPostProcessor(new KeyEventPostProcessor() {
            public boolean postProcessKeyEvent(KeyEvent e) {
                deleteOlderSelections = true;
                invertOlderSelections = false;
                if (e.isControlDown())
                    deleteOlderSelections = false;
                if (e.isShiftDown())
                    invertOlderSelections = true; //not yet implemented //todo: implement
                return false;
            }
        });
    }

    public void performAction(String eventName, Object value) {
//        StatusBarMessage.showQuickMessage(blackboard, "press Control for continues selection.");
        g = blackboard.getData(GraphAttrSet.name);
        GraphEvent ge = blackboard.getData(GraphEvent.EVENT_KEY);

        if (ge.eventType == GraphEvent.DRAGGING_STARTED)
            graphRectRegionSelector.startSelectingRegion();
    }

    void _onMouseMoved(GraphEvent data) {
        if (VertexTransformer.isPositionOnResizeBoxes(data.mousePos, blackboard))
            return;
        SubGraph selection = RectangularSelect.calculateSelected(g, graphRectRegionSelector.getCurrentRect().getBounds());
        if (!deleteOlderSelections) {
            SubGraph sd = Select.getSelection(blackboard);
            for (Vertex v : sd.vertices)
                selection.vertices.add(v);
            for (Edge e : sd.edges)
                selection.edges.add(e);
        }
        blackboard.setData(Select.EVENT_KEY, selection);
    }

    void _onDrop(GraphEvent data) {
//        if (isEnable())
        graphRectRegionSelector.startSelectingRegion();
    }


    boolean invertOlderSelections = false;

//    public void paint(Graphics p, Component unused) {
//        //System.out.println(x + " " + y + " " + xx + " " + yy);
//        p.setColor(black);
//        p.drawLine(x, y, xx, yy);
//    }

    public static SubGraph calculateSelected(GraphModel g, Rectangle bounds) {
        SubGraph sd = new SubGraph();
        for (Vertex vm : g) {
            GraphPoint loc = vm.getLocation();
            Point cent = vm.getCenter();
            if (bounds.contains(loc.x, loc.y)) {
                sd.vertices.add(vm);
            }
        }
        Edge em;
        for (Vertex v1 : sd.vertices) {
            for (Vertex v2 : sd.vertices) {
                try {
                    Edge edge = g.getEdge(v1, v2);
                    if (edge != null) {
                        sd.edges.add(edge);
                    }
                }
                catch (InvalidVertexException e) {
                }
            }
        }
//        for (Iterator<Edge> ei=gv.edgeIterator();ei.hasNext();){
//            em=ei.next();
//            if (bounds.contains(em.view.getBounds()))
//                sd.edges.add(em);
//        }
        return sd;
    }

    public static boolean isVertexInRect(Vertex v, GraphModel g, Rectangle viewBounds) {
        GraphPoint shapeSize = v.getSize();
        int w = (int) shapeSize.getX();
        int h = (int) shapeSize.getY();

        Rectangle2D.Double selBounds = new Rectangle2D.Double(viewBounds.getX(), viewBounds.getY(), viewBounds.width, viewBounds.height);
        GraphPoint loc = v.getLocation();
        Rectangle2D.Double verBounds = new Rectangle2D.Double(loc.x, loc.y, w, h);
        return selBounds.contains(verBounds);
    }
}