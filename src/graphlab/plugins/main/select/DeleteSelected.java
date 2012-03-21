// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.select;

import graphlab.graph.atributeset.GraphAttrSet;
import graphlab.graph.graph.*;
import graphlab.library.exceptions.InvalidVertexException;
import graphlab.platform.core.BlackBoard;
import graphlab.plugins.commonplugin.undo.Undoable;
import graphlab.plugins.commonplugin.undo.UndoableActionOccuredData;
import graphlab.plugins.main.GraphData;
import graphlab.plugins.main.core.actions.vertex.DeleteVertex;
import graphlab.plugins.main.extension.GraphActionExtension;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

/**
 * User: root
 */
public class DeleteSelected implements GraphActionExtension, Undoable {
    public static final String SELECTION_DELETED = "selection deleted";
    public BlackBoard blackboard;

    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */
    public DeleteSelected(BlackBoard bb) {
        this.blackboard = bb;
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventPostProcessor(new KeyEventPostProcessor() {
            public boolean postProcessKeyEvent(KeyEvent e) {
                AbstractGraphRenderer gv = blackboard.getData(AbstractGraphRenderer.EVENT_KEY);
                Component focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
                if (gv == focusOwner) {
                    if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                        action(new GraphData(blackboard));
                        return true;
                    }
                }
                return false;
            }
        });

    }

    public void undo(UndoableActionOccuredData uaod) {
        GraphModel g = (GraphModel) uaod.properties.get("Graph");
        for (VertexModel v : (HashSet<VertexModel>) uaod.properties.get("DeletedVertices")) {
            g.insertVertex(v);
            Vector<EdgeModel> ed = (Vector<EdgeModel>) uaod.properties.get("RelatedEdges");
            for (EdgeModel e : ed) {
                g.insertEdge(e);
            }
        }
        for (EdgeModel e : (HashSet<EdgeModel>) uaod.properties.get("DeletedEdges")) {
            g.insertEdge(e);
        }

    }

    public void redo(UndoableActionOccuredData uaod) {
        GraphModel g = (GraphModel) uaod.properties.get("Graph");

        for (VertexModel v : (HashSet<VertexModel>) uaod.properties.get("DeletedVertices")) {
            g.removeVertex(v);
        }
        for (EdgeModel e : (HashSet<EdgeModel>) uaod.properties.get("DeletedEdges")) {
            try {
                g.removeEdge(e);
            } catch (InvalidVertexException ee) {

            }
        }

    }

    @Override
    public String getName() {
        return "Delete Selection";
    }

    @Override
    public String getDescription() {
        return "Deletes the selected vertices and edges";
    }

    @Override
    public void action(GraphData graphData) {
        SubGraph selection = Select.getSelection(blackboard);
        GraphModel g = blackboard.getData(GraphAttrSet.name);
        if (selection.edges.isEmpty() && selection.vertices.isEmpty())
            return;
        UndoableActionOccuredData uaod = new UndoableActionOccuredData(this);

        HashSet<EdgeModel> edges = new HashSet<EdgeModel>();
        for (EdgeModel e : selection.edges) {
            edges.add(e);
        }
        HashSet<VertexModel> vertices = new HashSet<VertexModel>();
        for (VertexModel v : selection.vertices) {
            vertices.add(v);
        }
        uaod.properties.put("DeletedEdges", edges);
        uaod.properties.put("DeletedVertices", vertices);
        uaod.properties.put("Graph", g);

        for (EdgeModel e : selection.edges)
            g.removeEdge(e);
        Vector<EdgeModel> ed = new Vector<EdgeModel>();
        for (VertexModel v : selection.vertices) {
            Iterator<EdgeModel> ie = g.edgeIterator(v);
            while (ie.hasNext()) {
                ed.add(ie.next());
            }
//            for (Edge e : v.control)
//                ed.add(e);
            DeleteVertex.doJob(g, v);
        }
        uaod.properties.put("RelatedEdges", ed);
        blackboard.setData(Select.EVENT_KEY, new SubGraph());
        blackboard.setData(UndoableActionOccuredData.EVENT_KEY, uaod);

        ClearSelection.clearSelected(blackboard);

    }
}
