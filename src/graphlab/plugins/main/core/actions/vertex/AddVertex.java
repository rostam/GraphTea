// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.core.actions.vertex;

import graphlab.graph.atributeset.GraphAttrSet;
import graphlab.graph.event.GraphEvent;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.GraphPoint;
import graphlab.graph.graph.SubGraph;
import graphlab.graph.graph.VertexModel;
import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.platform.core.Listener;
import graphlab.plugins.commonplugin.undo.Undoable;
import graphlab.plugins.commonplugin.undo.UndoableActionOccuredData;
import graphlab.plugins.main.select.ClearSelection;
import graphlab.plugins.main.select.Select;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Adds a vertex to the graph: listens for "graph select point" & ...
 *
 * @author azin azadi
 */

public class AddVertex extends AbstractAction implements Undoable {
    public final static String DISABLE = "AddVertex.Disable";

    public AddVertex(BlackBoard bb) {
        super(bb);
        listen4Event(GraphEvent.EVENT_KEY);
        blackboard.addListener(DISABLE, new Listener() {
            public void performJob(String name) {
                disable = (Boolean) blackboard.getData(DISABLE);
                if (disable)
                    disable();
                else
                    enable();
            }

            public void keyChanged(String key, Object value) {
                disable = (Boolean) blackboard.getData(DISABLE);
                if (disable)
                    disable();
                else
                    enable();
            }

            public boolean isEnable() {
                return true;
            }
        });
    }

    protected SubGraph sd;
    boolean disable = false;

    public void performAction(String key, Object value) {
        sd = Select.getSelection(blackboard);
        GraphModel g = blackboard.getData(GraphAttrSet.name);
        Boolean aBoolean = blackboard.getData(ClearSelection.lastTimeGraphWasClear);
        if (aBoolean == null)
            return;
        boolean b = (Boolean) aBoolean;
        if (b && (sd == null || (sd.vertices.size() == 0 && sd.edges.size() == 0))) {
            GraphEvent gpd = blackboard.getData(GraphEvent.EVENT_KEY);
            if (gpd.eventType != GraphEvent.CLICKED) {
                return;
            }
            GraphModel graph = gpd.graph;

            VertexModel v = doJob(graph, gpd.mousePos);

            UndoableActionOccuredData uaod = new UndoableActionOccuredData(this);
            uaod.properties.put("AddedVertex", v);
            uaod.properties.put("Graph", g);
            blackboard.setData(UndoableActionOccuredData.EVENT_KEY, uaod);
        }
        blackboard.setData(ClearSelection.lastTimeGraphWasClear, true);

    }

    /**
     * adds a vertex to the given location of graph
     *
     * @return the added vertex
     */
    public static VertexModel doJob(GraphModel g, int x, int y) {
        VertexModel v = new VertexModel();
        Point p = v.getCenter();
        v.setLocation(new GraphPoint(x - p.x, y - p.y));
        g.insertVertex(v);
        v.setLabel(v.getId()+"");

        return v;
    }

    /**
     * adds a vertex to the given location of graph
     *
     * @return the added vertex
     */
    public static VertexModel doJob(GraphModel g, GraphPoint position) {
        VertexModel v = new VertexModel();
        v.setLocation(position);
        g.insertVertex(v);
        v.setLabel(v.getId()+"");
        return v;
    }

    /**
     * adds a vertex to a random position of the graph
     * return the added vertex
     */
    public static VertexModel addVertexToRandomPosition(GraphModel g) {
        Rectangle2D.Double b = g.getZoomedBounds();
        return doJob(g, (int) (b.getWidth() * Math.random()), (int) (b.getHeight() * Math.random()));
    }

    public void undo(UndoableActionOccuredData uaod) {
        VertexModel v = (VertexModel) uaod.properties.get("AddedVertex");
        GraphModel g = (GraphModel) uaod.properties.get("Graph");
        g.removeVertex(v);

    }

    public void redo(UndoableActionOccuredData uaod) {
        VertexModel v = (VertexModel) uaod.properties.get("AddedVertex");
        GraphModel g = (GraphModel) uaod.properties.get("Graph");
        g.insertVertex(v);

    }
}