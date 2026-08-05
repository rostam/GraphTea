// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.main.core.actions;

import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.event.GraphModelListener;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.SubGraph;
import graphtea.graph.graph.Vertex;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.main.select.Select;
import graphtea.ui.components.GComponentInterface;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.awt.Component;

/**
 * Keeps the size of the current graph, and of the selection, visible in the status bar.
 *
 * <p>The status bar sat empty except when something briefly wrote a message into it, so the two
 * numbers people check constantly &mdash; how many vertices and edges am I looking at &mdash;
 * could only be had by running a report.
 */
public class GraphStatsStatusBar extends AbstractAction implements GComponentInterface {

    private final JLabel label = new JLabel();

    /** The graph currently being watched, so its listener can be moved when tabs change. */
    private GraphModel watched;

    private final GraphModelListener graphListener = new GraphModelListener() {
        public void vertexAdded(Vertex v) {
            refresh();
        }

        public void vertexRemoved(Vertex v) {
            refresh();
        }

        public void edgeAdded(Edge e) {
            refresh();
        }

        public void edgeRemoved(Edge e) {
            refresh();
        }

        public void graphCleared() {
            refresh();
        }

        public void repaintGraph() {
            refresh();
        }
    };

    /**
     * @param bb the blackboard of the action
     */
    public GraphStatsStatusBar(BlackBoard bb) {
        super(bb);
        // The current graph changes when the user switches tabs; the selection changes
        // constantly. Structural edits are reported by the graph itself.
        listen4Event(GraphAttrSet.name);
        listen4Event(Select.EVENT_KEY);
        refresh();
    }

    public void track() {
    }

    public void performAction(String eventName, Object value) {
        if (GraphAttrSet.name.equals(eventName)) {
            attachTo(blackboard.getData(GraphAttrSet.name));
        }
        refresh();
    }

    private void attachTo(GraphModel g) {
        if (watched == g) {
            return;
        }
        if (watched != null) {
            watched.removeGraphListener(graphListener);
        }
        watched = g;
        if (watched != null) {
            watched.addGraphListener(graphListener);
        }
    }

    private void refresh() {
        SwingUtilities.invokeLater(() -> {
            GraphModel g = blackboard.getData(GraphAttrSet.name);
            attachTo(g);
            if (g == null) {
                label.setText("No graph");
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(count(g.getVerticesCount(), "vertex", "vertices"))
              .append("  ·  ")
              .append(count(g.getEdgesCount(), "edge", "edges"));

            SubGraph selection = Select.getSelection(blackboard);
            if (selection != null
                    && (!selection.vertices.isEmpty() || !selection.edges.isEmpty())) {
                sb.append("  ·  ")
                  .append(selection.vertices.size())
                  .append(" of ")
                  .append(g.getVerticesCount())
                  .append(" selected");
            }
            label.setText(sb.toString());
        });
    }

    private static String count(int n, String singular, String plural) {
        return n + " " + (n == 1 ? singular : plural);
    }

    public Component getComponent(BlackBoard b) {
        label.setOpaque(false);
        return label;
    }
}
