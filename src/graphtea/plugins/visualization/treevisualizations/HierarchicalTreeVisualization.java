// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.visualization.treevisualizations;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.preferences.lastsettings.UserModifiableProperty;
import graphtea.plugins.visualization.corebasics.extension.VisualizationExtension;
import graphtea.ui.UIUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Draws a graph as a layered tree: the root at the top, each level below the one before.
 *
 * <p>The previous implementation walked each edge only from its source end, so on an undirected
 * tree most of the tree was never reached and every unreached vertex was stacked on its
 * parent's level; it rooted the tree at a vertex on the rim rather than in the middle; and it
 * divided a fixed 600 pixels evenly per level, so wide levels overlapped. It also swallowed the
 * NullPointerException an empty graph caused. The layout now comes from {@link TreeLayout}.
 *
 * @author Rouzbeh Ebrahimi
 */
public class HierarchicalTreeVisualization implements VisualizationExtension {
    public static final String event = UIUtils.getUIEventKey("HierarchicalTreeVisualization");

    /** Least horizontal distance between neighbouring leaves. */
    private static final double MIN_LEAF_GAP = 40;
    private static final double MARGIN = 60;

    private static GraphModel g;

    @UserModifiableProperty(displayName = "Hierarchical Tree Width", obeysAncestorCategory = false
            , category = "Visualization Options")
    public static Integer width = 600;
    @UserModifiableProperty(displayName = "Hierarchical Tree Level Height", obeysAncestorCategory = false
            , category = "Visualization Options")
    public static Integer eachLevelHeigh = 80;

    public String getName() {
        return "Hierarchical Tree Visualization";
    }

    public String getDescription() {
        return "Draws the graph as a tree in levels, rooted at its centre";
    }

    public void setWorkingGraph(GraphModel g) {
        HierarchicalTreeVisualization.g = g;
    }

    public HashMap<Vertex, GPoint> getNewVertexPlaces() {
        HashMap<Vertex, GPoint> places = new HashMap<>();
        if (g == null) {
            return places;
        }
        Map<Vertex, GPoint> slots = TreeLayout.layered(g);
        double maxSlot = slots.values().stream().mapToDouble(p -> p.x).max().orElse(0);
        // Spread the leaves over the configured width, but never closer than MIN_LEAF_GAP.
        double gap = Math.max(MIN_LEAF_GAP, maxSlot > 0 ? width / maxSlot : 0);
        for (Map.Entry<Vertex, GPoint> e : slots.entrySet()) {
            GPoint s = e.getValue();
            places.put(e.getKey(), new GPoint(MARGIN + s.x * gap, MARGIN + s.y * eachLevelHeigh));
        }
        return places;
    }

    public HashMap<Edge, GPoint> getNewEdgeCurveControlPoints() {
        return null;
    }
}
