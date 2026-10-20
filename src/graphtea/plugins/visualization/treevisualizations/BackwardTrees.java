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
 * Draws a graph as a radial tree: the root in the middle and each level on a ring around it,
 * every subtree in its own wedge.
 *
 * <p>The previous implementation shared the hierarchical layout's faults (edges followed from one
 * end only, the root picked on the rim), placed children on fixed circles around their parent so
 * that deeper levels overlapped, and overwrote each vertex's properties to carry its angular
 * span. The layout now comes from {@link TreeLayout}: a vertex's slot becomes its angle and its
 * depth its ring.
 *
 * @author Rouzbeh Ebrahimi
 */
public class BackwardTrees implements VisualizationExtension {
    String event = UIUtils.getUIEventKey("BackwardTrees");

    /** Least distance between neighbouring leaves on the outer ring. */
    private static final double MIN_LEAF_GAP = 40;
    private static final double MARGIN = 60;

    static GraphModel g;

    @UserModifiableProperty(displayName = "Backward Tree Radius", obeysAncestorCategory = false
            , category = "Visualization Options")
    public static Integer radius = 80;

    public String getName() {
        return "Backward Tree Visualization";
    }

    public String getDescription() {
        return "Draws the graph as a radial tree around its centre";
    }

    public void setWorkingGraph(GraphModel g) {
        BackwardTrees.g = g;
    }

    public HashMap<Vertex, GPoint> getNewVertexPlaces() {
        HashMap<Vertex, GPoint> places = new HashMap<>();
        if (g == null) {
            return places;
        }
        double left = MARGIN;
        for (Map<Vertex, GPoint> component : TreeLayout.components(g)) {
            double slots = 1 + component.values().stream().mapToDouble(p -> p.x).max().orElse(0);
            double depth = component.values().stream().mapToDouble(p -> p.y).max().orElse(0);
            // Rings far enough apart that the outer one has room for every leaf.
            double ring = depth == 0 ? 0 : Math.max(radius, slots * MIN_LEAF_GAP / (2 * Math.PI * depth));
            double discRadius = depth * ring;
            double cx = left + discRadius;
            double cy = MARGIN + discRadius;
            for (Map.Entry<Vertex, GPoint> e : component.entrySet()) {
                GPoint s = e.getValue();
                double angle = 2 * Math.PI * s.x / slots;
                double r = s.y * ring;
                places.put(e.getKey(), new GPoint(cx + r * Math.cos(angle), cy + r * Math.sin(angle)));
            }
            left += 2 * discRadius + Math.max(radius, MIN_LEAF_GAP);
        }
        return places;
    }

    public HashMap<Edge, GPoint> getNewEdgeCurveControlPoints() {
        return null;
    }
}
