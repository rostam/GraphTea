// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.select;

import graphlab.graph.atributeset.GraphAttrSet;
import graphlab.graph.graph.*;
import graphlab.platform.core.BlackBoard;
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
public class DeleteSelected implements GraphActionExtension {
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

        HashSet<Edge> edges = new HashSet<Edge>();
        for (Edge e : selection.edges) {
            edges.add(e);
        }
        HashSet<Vertex> vertices = new HashSet<Vertex>();
        for (Vertex v : selection.vertices) {
            vertices.add(v);
        }

        for (Edge e : selection.edges)
            g.removeEdge(e);
        Vector<Edge> ed = new Vector<Edge>();
        for (Vertex v : selection.vertices) {
            Iterator<Edge> ie = g.edgeIterator(v);
            while (ie.hasNext()) {
                ed.add(ie.next());
            }
//            for (Edge e : v.control)
//                ed.add(e);
            DeleteVertex.doJob(g, v);
        }
        blackboard.setData(Select.EVENT_KEY, new SubGraph());

        ClearSelection.clearSelected(blackboard);

    }
}
