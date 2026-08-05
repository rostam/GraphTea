// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.rightclick;

import graphtea.graph.atributeset.EdgeNotifiableAttrSet;
import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.atributeset.VertexNotifiableAttrSet;
import graphtea.graph.event.EdgeEvent;
import graphtea.graph.event.GraphEvent;
import graphtea.graph.event.VertexEvent;
import graphtea.graph.graph.AbstractGraphRenderer;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.SubGraph;
import graphtea.graph.graph.Vertex;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.plugins.main.ccp.Copy;
import graphtea.plugins.main.core.actions.vertex.AddVertex;
import graphtea.plugins.main.select.Select;
import graphtea.ui.UIUtils;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.event.MouseEvent;

/**
 * The right-click menus for a vertex, an edge, and the empty canvas.
 *
 * <p>Right-clicking used to do nothing anywhere. The vertex and edge branches of this class
 * were commented out, and no code anywhere called
 * {@link #registerGraphPopupMenu} either, so even the canvas menu was an empty box. Each of the
 * three now offers the handful of things that are genuinely useful at that spot, and anything
 * registered by a plugin is appended underneath.
 *
 * @author azin azadi
 */
public class PopupMenuHandler extends AbstractAction {

    private static final JPopupMenu GRAPH_MENU = new JPopupMenu();
    private static final JPopupMenu VERTEX_MENU = new JPopupMenu();
    private static final JPopupMenu EDGE_MENU = new JPopupMenu();

    /** Where the last right-click landed, so "Add vertex here" means here. */
    private double lastX;
    private double lastY;

    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */
    public PopupMenuHandler(BlackBoard bb) {
        super(bb);
        listen4Event(VertexEvent.EVENT_KEY);
        listen4Event(EdgeEvent.EVENT_KEY);
        listen4Event(GraphEvent.EVENT_KEY);
    }

    public void track() {
    }

    public void performAction(String eventName, Object value) {
        AbstractGraphRenderer view = blackboard.getData(AbstractGraphRenderer.EVENT_KEY);
        if (view == null) {
            return;
        }
        if (eventName.equals(VertexEvent.EVENT_KEY)) {
            VertexEvent ve = (VertexEvent) value;
            if (ve.eventType == VertexEvent.CLICKED && ve.mouseBtn == MouseEvent.BUTTON3) {
                showVertexMenu(view, ve.v, ve.mousePos.x, ve.mousePos.y);
                super.track();
            }
        } else if (eventName.equals(EdgeEvent.EVENT_KEY)) {
            EdgeEvent ee = (EdgeEvent) value;
            if (ee.eventType == EdgeEvent.CLICKED && ee.mouseBtn == MouseEvent.BUTTON3) {
                showEdgeMenu(view, ee.e, ee.mousePos.x, ee.mousePos.y);
                super.track();
            }
        } else if (eventName.equals(GraphEvent.EVENT_KEY)) {
            GraphEvent ge = (GraphEvent) value;
            if (ge.eventType == GraphEvent.CLICKED && ge.mouseBtn == MouseEvent.BUTTON3) {
                lastX = ge.mousePos.x;
                lastY = ge.mousePos.y;
                showGraphMenu(view, ge.mousePos.x, ge.mousePos.y);
                super.track();
            }
        }
    }

    private GraphModel graph() {
        return blackboard.getData(GraphAttrSet.name);
    }

    private void repaint() {
        AbstractGraphRenderer view = blackboard.getData(AbstractGraphRenderer.EVENT_KEY);
        if (view != null) {
            view.repaintGraph();
        }
    }

    private void showVertexMenu(AbstractGraphRenderer view, Vertex v, double x, double y) {
        VERTEX_MENU.removeAll();
        add(VERTEX_MENU, "Delete this vertex", () -> {
            GraphModel g = graph();
            if (g != null) {
                blackboard.setData("undo point", null);
                g.removeVertex(v);
                repaint();
            }
        });
        add(VERTEX_MENU, "Select this vertex", () -> selectOnly(v, null));
        add(VERTEX_MENU, "Select its neighbours", () -> {
            GraphModel g = graph();
            if (g == null) {
                return;
            }
            SubGraph sub = new SubGraph();
            sub.vertices.add(v);
            for (Vertex n : g.getNeighbors(v)) {
                sub.vertices.add(n);
            }
            Select.setSelection(blackboard, sub);
            repaint();
        });
        VERTEX_MENU.addSeparator();
        add(VERTEX_MENU, "Properties...",
                () -> UIUtils.showEditDialog(new VertexNotifiableAttrSet(v), false));
        appendRegistered(VERTEX_MENU, registeredVertexItems);
        VERTEX_MENU.show(view, (int) x, (int) y);
    }

    private void showEdgeMenu(AbstractGraphRenderer view, Edge e, double x, double y) {
        EDGE_MENU.removeAll();
        add(EDGE_MENU, "Delete this edge", () -> {
            GraphModel g = graph();
            if (g != null) {
                blackboard.setData("undo point", null);
                g.removeEdge(e);
                repaint();
            }
        });
        add(EDGE_MENU, "Select this edge", () -> selectOnly(null, e));
        EDGE_MENU.addSeparator();
        add(EDGE_MENU, "Properties...",
                () -> UIUtils.showEditDialog(new EdgeNotifiableAttrSet(e), false));
        appendRegistered(EDGE_MENU, registeredEdgeItems);
        EDGE_MENU.show(view, (int) x, (int) y);
    }

    private void showGraphMenu(AbstractGraphRenderer view, double x, double y) {
        GRAPH_MENU.removeAll();
        add(GRAPH_MENU, "Add a vertex here", () -> {
            GraphModel g = graph();
            if (g != null) {
                blackboard.setData("undo point", null);
                AddVertex.doJob(g, (int) lastX, (int) lastY);
                repaint();
            }
        });
        GRAPH_MENU.addSeparator();
        add(GRAPH_MENU, "Select all", () -> {
            GraphModel g = graph();
            if (g == null) {
                return;
            }
            SubGraph sub = new SubGraph();
            for (Vertex v : g) {
                sub.vertices.add(v);
            }
            for (java.util.Iterator<Edge> it = g.edgeIterator(); it.hasNext(); ) {
                sub.edges.add(it.next());
            }
            Select.setSelection(blackboard, sub);
            repaint();
        });
        add(GRAPH_MENU, "Copy selection", () -> {
            SubGraph sel = Select.getSelection(blackboard);
            if (sel != null) {
                Copy.copy(sel);
            }
        });
        add(GRAPH_MENU, "Paste", () -> fire("Paste"));
        appendRegistered(GRAPH_MENU, registeredGraphItems);
        GRAPH_MENU.show(view, (int) x, (int) y);
    }

    private void selectOnly(Vertex v, Edge e) {
        SubGraph sub = new SubGraph();
        if (v != null) {
            sub.vertices.add(v);
        }
        if (e != null) {
            sub.edges.add(e);
        }
        Select.setSelection(blackboard, sub);
        repaint();
    }

    private void fire(String uiEventId) {
        blackboard.setData(UIUtils.getUIEventKey(uiEventId), "popup menu");
    }

    private void add(JPopupMenu menu, String label, Runnable action) {
        JMenuItem item = new JMenuItem(label);
        item.addActionListener(e -> {
            try {
                action.run();
            } catch (RuntimeException ex) {
                ExceptionHandler.catchException(label, ex);
            }
        });
        menu.add(item);
    }

    // ---- registration API kept for plugins -------------------------------------------------

    private static final java.util.List<JMenuItem> registeredGraphItems = new java.util.ArrayList<>();
    private static final java.util.List<JMenuItem> registeredVertexItems = new java.util.ArrayList<>();
    private static final java.util.List<JMenuItem> registeredEdgeItems = new java.util.ArrayList<>();

    private static void appendRegistered(JPopupMenu menu, java.util.List<JMenuItem> items) {
        if (items.isEmpty()) {
            return;
        }
        menu.addSeparator();
        for (JMenuItem item : items) {
            menu.add(item);
        }
    }

    /**
     * Adds an item to the canvas right-click menu.
     *
     * @param id          the label to show
     * @param index       ignored; items appear in registration order after the built-in ones
     * @param n           the action to run
     * @param forceEnable run the action even when it reports itself disabled
     */
    public static void registerGraphPopupMenu(String id, int index, AbstractAction n, boolean forceEnable) {
        registeredGraphItems.add(buildItem(id, n, forceEnable));
    }

    /**
     * Adds an item to the vertex right-click menu.
     *
     * @param id          the label to show
     * @param index       ignored; items appear in registration order after the built-in ones
     * @param n           the action to run
     * @param forceEnable run the action even when it reports itself disabled
     */
    public static void registerVertexPopupMenu(String id, int index, AbstractAction n, boolean forceEnable) {
        registeredVertexItems.add(buildItem(id, n, forceEnable));
    }

    /**
     * Adds an item to the edge right-click menu.
     *
     * @param id          the label to show
     * @param index       ignored; items appear in registration order after the built-in ones
     * @param n           the action to run
     * @param forceEnable run the action even when it reports itself disabled
     */
    public static void registerEdgePopupMenu(String id, int index, AbstractAction n, boolean forceEnable) {
        registeredEdgeItems.add(buildItem(id, n, forceEnable));
    }

    private static JMenuItem buildItem(final String id, final AbstractAction n, final boolean forceEnable) {
        JMenuItem item = new JMenuItem(id);
        item.addActionListener(e -> {
            if (!forceEnable || n.isEnable()) {
                n.performAction("popup menu: " + id, null);
            }
        });
        return item;
    }
}
