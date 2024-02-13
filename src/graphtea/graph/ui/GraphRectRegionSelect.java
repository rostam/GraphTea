// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.graph.ui;


import graphtea.graph.GraphUtils;
import graphtea.graph.event.GraphEvent;
import graphtea.graph.graph.AbstractGraphRenderer;
import graphtea.graph.graph.PaintHandler;
import graphtea.graph.old.AcceleratedRenderer;
import graphtea.graph.old.GStroke;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.Listener;

import java.awt.*;

/**
 * @author azin azadi
 * @email
 */
public abstract class GraphRectRegionSelect implements Listener, PaintHandler {
    public Rectangle getCurrentRect() {
        return rect;
    }

    protected Rectangle rect = new Rectangle(0, 0, 0, 0);
    protected int x;
    protected int y;
    protected AbstractGraphRenderer gv;
    protected int xx;
    protected int yy;
    private final BlackBoard blackboard;
    public static boolean isSelecting = false;

    public GraphRectRegionSelect(BlackBoard bb) {
        this.blackboard = bb;
    }

    /**
     * starts the process of selecting a0 rectangular region by the user on the graph
     * this will listen for press the mouse button and drag it on the graph
     * this will finished whenever the mouse released
     */
    public void startSelectingRegion() {
        blackboard.addListener(GraphEvent.EVENT_KEY, this);
        //other things will be done on doJob
    }

    GraphEvent gdrag, gdrop, gmove;
    boolean dragStarted = false;

    public void keyChanged(String eventKey, Object value) {
        gv = blackboard.getData(AbstractGraphRenderer.EVENT_KEY);
        if (eventKey.equals(GraphEvent.EVENT_KEY)) {
            GraphEvent ge = blackboard.getData(GraphEvent.EVENT_KEY);
            if (ge.eventType == GraphEvent.DRAGGING_STARTED) {
                gv.addPostPaintHandler(this);
                gdrag = blackboard.getData(GraphEvent.EVENT_KEY);
                drag();
                dragStarted = true;
                isSelecting = true;
            }
            if (dragStarted && ge.eventType == GraphEvent.DRAGGING) {
                gmove = blackboard.getData(GraphEvent.EVENT_KEY);
                mouseMove();
            }
            if (dragStarted && ge.eventType == GraphEvent.DROPPED) {
                isSelecting = false;
                gdrop = blackboard.getData(GraphEvent.EVENT_KEY);
                blackboard.removeListener(GraphEvent.EVENT_KEY, this);
                gv.removePaintHandler(this);
//                if (!dragStarted) {
//                    rect = new Rectangle((int) gdrop.mousePos.getX(), (int) gdrop.mousePos.getY(), 300, 300);
//                }
                dragStarted = false;
                drop();
            }
        }
    }

    abstract public void onMouseMoved(GraphEvent data);

    abstract public void onDrop(GraphEvent data);

    private void mouseMove() {
        xx = (int) gmove.mousePos.x;
        yy = (int) gmove.mousePos.y;
        int dx = xx - x;
        int dy = yy - y;
        int _x = x;
        int _y = y;
        if (dx < 0) {
            dx *= -1;
            _x -= dx;
        }
        if (dy < 0) {
            dy *= -1;
            _y -= dy;
        }
        rect.setBounds(_x, _y, dx, dy);
        if (gv != null) {
            if (!(gv instanceof AcceleratedRenderer))
                gv.repaint();
        }
        onMouseMoved(gmove);
    }

    private void drag() {
        x = (int) gdrag.mousePos.x;
        y = (int) gdrag.mousePos.y;
        rect = new Rectangle(x, y, 300, 300);
    }

    private void drop() {
        onDrop(gdrop);
        gv.repaint();
    }

    public void paint(Graphics g, Object destinationComponent, Boolean drawExtras) {
        if (!drawExtras)
            return;
        double zoomFactor = gv.getGraph().getZoomFactor();
        Graphics2D gg = (Graphics2D) g;
        gg.setStroke(GStroke.dashed.stroke);
        gg.setColor(Color.DARK_GRAY);
        Rectangle _rect = GraphUtils.createViewRectangle(gv.getGraph(), rect);
        g.drawRoundRect(_rect.x, _rect.y, _rect.width, _rect.height, 5, 5);
    }

    public boolean isEnable() {
        return true;
    }
}
