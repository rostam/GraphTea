// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.spectralreports.maxflowmincut;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

import java.util.ArrayList;
import java.util.List;

/**
 * The source and sink a flow report runs between, taken from the user's selection.
 *
 * <p>Max Flow and Min Cut need two endpoints. The versions of these reports under the
 * <b>Algorithms</b> menu ask for them interactively through the animator, but a report has only
 * the graph to work with, so the two under <b>Reports</b> read the selection instead: select
 * exactly two vertices, then run the report.
 *
 * @param source the vertex flow runs from
 * @param sink   the vertex flow runs to
 */
public record FlowEndpoints(Vertex source, Vertex sink) {

    /** What to tell the user when the selection is not usable. */
    public static final String INSTRUCTION =
            "Select exactly two vertices — the source and the sink — then run this report again.\n\n"
                    + "Click a vertex, then shift-click a second one. For an interactive picker "
                    + "instead, use the same report under the Algorithms menu.";

    /**
     * Reads the selected endpoints from a graph.
     *
     * @param g the graph to inspect
     * @return the two selected vertices in iteration order, or null when the selection does not
     *         hold exactly two
     */
    public static FlowEndpoints fromSelection(GraphModel g) {
        if (g == null) {
            return null;
        }
        List<Vertex> selected = new ArrayList<>();
        for (Vertex v : g) {
            if (v.isSelected()) {
                selected.add(v);
                if (selected.size() > 2) {
                    return null;
                }
            }
        }
        if (selected.size() != 2) {
            return null;
        }
        return new FlowEndpoints(selected.get(0), selected.get(1));
    }
}
