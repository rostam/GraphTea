// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.select;

import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.graph.*;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.core.actions.vertex.DeleteVertex;
import graphtea.plugins.main.extension.GraphActionExtension;

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
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventPostProcessor(e -> {
            AbstractGraphRenderer gv = blackboard.getData(AbstractGraphRenderer.EVENT_KEY);
            Component focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
            if (gv == focusOwner) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    action(new GraphData(blackboard));
                    return true;
                }
            }
            return false;
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

        HashSet<Edge> edges = new HashSet<>();
        edges.addAll(selection.edges);
        HashSet<Vertex> vertices = new HashSet<>();
        vertices.addAll(selection.vertices);
        selection.edges.forEach(g::removeEdge);
        Vector<Edge> ed = new Vector<>();
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

    @Override
    public String getCategory() {
        return "Basic Operations";
    }
}
