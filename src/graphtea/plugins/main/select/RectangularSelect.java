// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.select;

import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.event.GraphEvent;
import graphtea.graph.graph.*;
import graphtea.graph.ui.GraphRectRegionSelect;
import graphtea.library.exceptions.InvalidVertexException;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.ui.UIUtils;

import java.awt.*;
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
    public void track(){}
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
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventPostProcessor(e -> {
            deleteOlderSelections = true;
            invertOlderSelections = false;
            if (e.isControlDown())
                deleteOlderSelections = false;
            if (e.isShiftDown())
                invertOlderSelections = true; //not yet implemented //todo: implement
            return false;
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
            GPoint loc = vm.getLocation();
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
        GPoint shapeSize = v.getSize();
        int w = (int) shapeSize.getX();
        int h = (int) shapeSize.getY();

        Rectangle2D.Double selBounds = new Rectangle2D.Double(viewBounds.getX(), viewBounds.getY(), viewBounds.width, viewBounds.height);
        GPoint loc = v.getLocation();
        Rectangle2D.Double verBounds = new Rectangle2D.Double(loc.x, loc.y, w, h);
        return selBounds.contains(verBounds);
    }
}