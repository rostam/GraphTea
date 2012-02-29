// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.select;

import graphlab.graph.event.GraphEvent;
import graphlab.graph.event.VertexEvent;
import graphlab.graph.graph.*;
import graphlab.library.exceptions.InvalidVertexException;
import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.plugins.commonplugin.undo.Undoable;
import graphlab.plugins.commonplugin.undo.UndoableActionOccuredData;
import graphlab.plugins.main.GraphData;

/**
 * @author azin azadi
 */
public class MoveSelected extends AbstractAction implements Undoable {
    public static final String SELECTION_MOVED = "Selection Moved";
    public static final String SELECTION_MOVING = "Selection Moving";

    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */
    public MoveSelected(BlackBoard bb) {
        super(bb);
        listen4Event(VertexEvent.EVENT_KEY);
        listen4Event(GraphEvent.EVENT_KEY);
        gd = new GraphData(blackboard);
    }

    GraphData gd;
    private GraphPoint[] verticesPositionsBackUp;

    public void performAction(String eventName, Object value) {
        gv = blackboard.getData(AbstractGraphRenderer.EVENT_KEY);
        //while resizing the vertex it shouldn't move it
//resize
//        if (b == null || !b) {
        if (eventName == VertexEvent.EVENT_KEY) {

            VertexEvent vdd = blackboard.getData(VertexEvent.EVENT_KEY);


            SubGraph sd = Select.getSelection(blackboard);
            if (sd.vertices.contains(vdd.v))   //start if the vertex selected
            {
                if (vdd.eventType == VertexEvent.DRAGGING_STARTED) {
                    verticesPositionsBackUp = new GraphPoint[gd.getGraph().getVerticesCount()];
                    for (VertexModel _ : gd.getGraph()) {
                        verticesPositionsBackUp[_.getId()] = _.getLocation();
                    }
                    startx = vdd.v.getLocation().x;
                    starty = vdd.v.getLocation().y;
                    drag();
//            listen4Event(GraphMouseMoveData.event);
//todo                        listen4Event(GraphDropData.event);
//                        listen4Event(VertexDropData.event);
                }
                if (vdd.eventType == VertexEvent.RELEASED || vdd.eventType == VertexEvent.DROPPED) {
                    drop();
                    //add undo data
                    GraphPoint[] newPos = new GraphPoint[gd.getGraph().getVerticesCount()];
                    for (VertexModel _ : gd.getGraph()) {
                        newPos[_.getId()] = _.getLocation();
                    }

                    UndoableActionOccuredData uaod = new UndoableActionOccuredData(this);
                    uaod.properties.put("oldPositions", verticesPositionsBackUp);
                    uaod.properties.put("newPositions", newPos);
                    blackboard.setData(UndoableActionOccuredData.EVENT_KEY, uaod);
                    blackboard.setData(SELECTION_MOVED, new GraphPoint(vdd.v.getLocation().x - startx, vdd.v.getLocation().y - starty));

//                v.view.removeMouseMotionListener(mlistener);
//todo                    unListenEvent(VertexMouseDraggingData.event);
//                    unListenEvent(GraphDropData.event);
//                    unListenEvent(VertexDropData.event);
                }
                if (vdd.eventType == VertexEvent.DRAGGING) {
//                    VertexMouseDraggingData d = blackboard.get(VertexMouseDraggingData.name);
                    xx = vdd.mousePos.x;
                    yy = vdd.mousePos.y;
                    moveVertices();
                }

            }
        }
//        if (eventName ==GraphEvent.name)) {
//            drop();
//            unListenEvent(GraphMouseMoveData.event);
//                v.view.removeMouseMotionListener(mlistener);
//todo                unListenEvent(VertexMouseDraggingData.event);
//                unListenEvent(GraphDropData.event);
//                unListenEvent(VertexDropData.event);
//        }

//        }
    }

    private void mouseMove() {
        GraphEvent ge = blackboard.getData(GraphEvent.EVENT_KEY);
//        GraphMouseMoveData gmmd = blackboard.get(GraphMouseMoveData.name);
        xx = ge.mousePos.x;
        yy = ge.mousePos.y;
        moveVertices();
    }

    private void moveVertices() {
        blackboard.setData("MoveSelected.moving", "yes");
//        System.out.println("mm");
        final SubGraph selection = Select.getSelection(blackboard);
        final double dx = xx - x;
        final double dy = yy - y;
//        System.out.println(xx + ", " + x + ", " + dx);
        AbstractGraphRenderer ren = blackboard.getData(AbstractGraphRenderer.EVENT_KEY);
        ren.ignoreRepaints(new Runnable() {
            public void run() {
                for (VertexModel v : selection.vertices) {
                    GraphPoint loc = v.getLocation();
                    v.setLocation(new GraphPoint(loc.x + dx, loc.y + dy));
                }
            }
        });
        blackboard.setData(SELECTION_MOVING, new GraphPoint(dx, dy));
//        blackboard.setData(SELECTION_MOVING, new GraphPoint(vdd.v.getLocation().x - startx, vdd.v.getLocation().y - starty));
    }

    double x;
    double startx;
    double y;
    double starty;
    AbstractGraphRenderer gv;
    VertexModel v;
//    MouseMotionAdapter mlistener = new MouseMotionAdapter() {
//        public synchronized void mouseDragging(MouseEvent e) {
//            xx = e.getX();
//            yy = e.getY();
//            moveVertices();
//        }
//    };

    private void drag() {
        VertexEvent data = blackboard.getData(VertexEvent.EVENT_KEY);
        x = data.mousePos.x;
        y = data.mousePos.y;
//        startx = x;
//        starty = y;
        v = data.v;
//todo        listen4Event(VertexMouseDraggingData.event);
//        data.v.view.addMouseMotionListener(mlistener);
    }

    private void drop() {
        blackboard.setData("MoveSelected.moving", "no");
//        v.view.removeMouseMotionListener(mlistener);
//        GraphDropData data = blackboard.get(GraphDropData.name);
        AbstractGraphRenderer ren = blackboard.getData(AbstractGraphRenderer.EVENT_KEY);
        ren.ignoreRepaints(new Runnable() {
            public void run() {
                VertexEvent ve = blackboard.getData(VertexEvent.EVENT_KEY);
                int _x = (int) ve.mousePos.getX();
                int _y = (int) ve.mousePos.getY();
                int dx = (int) (_x - x);
                int dy = (int) (_y - y);
                x = x + dx;
                y = y + dy;
                SubGraph selection = Select.getSelection(blackboard);
                for (VertexModel v : selection.vertices) {
                    try {
                        v.setLocation(new GraphPoint(v.getLocation().x + dx, v.getLocation().y + dy));
                    } catch (InvalidVertexException e) {
                        selection.vertices.remove(v);     //as in java any thing is references, there is no need to update the log in the blackboard :D:D
                        //exception occurs whenever a selected vertex, or edge was deleted by user
//                ExceptionHandler.catchException(e);
                    }
                }

            }
        });


    }

    double xx;
    double yy;

    public void undo(UndoableActionOccuredData uaod) {
        GraphPoint verticesPositionsBackUp[] = (GraphPoint[]) uaod.properties.get("oldPositions");
        GraphModel g = gd.getGraph();
        if (g.getVerticesCount() != this.verticesPositionsBackUp.length) {
            System.err.println("Graph has changed, Undo can not be done");
            return;
        }

        int i = 0;
        for (VertexModel v : g) {
            v.setLocation(verticesPositionsBackUp[i++]);
        }

    }

    public void redo(UndoableActionOccuredData uaod) {
        GraphPoint newPos[] = (GraphPoint[]) uaod.properties.get("newPositions");
        GraphModel g = gd.getGraph();
        if (g.getVerticesCount() != this.verticesPositionsBackUp.length) {
            System.err.println("Graph has changed, Undo can not be done");
            return;
        }

        int i = 0;
        for (VertexModel v : g) {
            v.setLocation(newPos[i++]);
        }

    }
}
//
//class SelectedVerticesCursorUpdator extends GraphAction {
//    /**
//     * constructor
//     *
//     * @param bb the blackboard of the action
//     */
//    public SelectedVerticesCursorUpdator(blackboard bb) {
//        super(bb);
//        listen4Event(SelectData.event);
//    }
//
//    /**
//     * determines the cursors that the vertices has before changing their cursors,
//     * this also determines the vertices whose cursors changed
//     */
//    HashMap<Vertex, Cursor> lastCursors = new HashMap<Vertex, Cursor>();
//
//    public void performJob(GraphData graphData) {
//        HashSet<Vertex> selectedVertices = graphData.select.getSelectedVertices();
//        for (Vertex v : lastCursors.keySet()) {
//            if (!selectedVertices.contains(v)) {
////                v.view.setCursor(lastCursors.get(v));
//                // last cursors had some problems, so i didn;t use it temporarily
//                v.view.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
////                System.out.println("cleared");
//            }
//        }
//        lastCursors.clear();
//        for (Vertex v : selectedVertices) {
////            lastCursors.put(v, v.view.getCursor());
//            v.view.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
////            System.out.println("setted");
//        }
//    }
//}