// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.visualization.treevisualizations;

import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Layered ("hierarchical") tree layout, independent of any window.
 *
 * <p>Each connected component is laid out as a breadth-first tree from a central root. Leaves
 * take consecutive slots from left to right, each parent is centred over its children, and
 * components follow one another with a one-slot gap. A graph that is not a tree is drawn by its
 * breadth-first spanning tree. Positions are in abstract units: x in slots, y in levels.
 */
public final class TreeLayout {
    private TreeLayout() {
    }

    /**
     * @param g the graph
     * @return for every vertex, its slot (x) and its depth below its component's root (y),
     *         with the components side by side
     */
    public static Map<Vertex, GPoint> layered(GraphModel g) {
        Map<Vertex, GPoint> places = new LinkedHashMap<>();
        double offset = 0;
        for (Map<Vertex, GPoint> component : components(g)) {
            double width = 0;
            for (Map.Entry<Vertex, GPoint> e : component.entrySet()) {
                GPoint p = e.getValue();
                places.put(e.getKey(), new GPoint(p.x + offset, p.y));
                width = Math.max(width, p.x);
            }
            offset += width + 2;
        }
        return places;
    }

    /**
     * Lays out each connected component separately, each with its slots starting at 0.
     *
     * @param g the graph
     * @return one map per component, in the order their first vertices appear in the graph
     */
    public static List<Map<Vertex, GPoint>> components(GraphModel g) {
        List<Map<Vertex, GPoint>> result = new ArrayList<>();
        Set<Vertex> placed = new HashSet<>();
        for (Vertex start : g) {
            if (placed.contains(start)) {
                continue;
            }
            Vertex root = center(g, start);
            Map<Vertex, List<Vertex>> children = new HashMap<>();
            Map<Vertex, Integer> depth = new HashMap<>();
            placed.addAll(bfsTree(g, root, children, depth));
            Map<Vertex, GPoint> places = new LinkedHashMap<>();
            assignSlots(root, children, depth, places, 0);
            result.add(places);
        }
        return result;
    }

    /**
     * An approximate centre of the component containing {@code start}: the middle of a longest
     * shortest path, found with two breadth-first searches. Exact for trees.
     */
    static Vertex center(GraphModel g, Vertex start) {
        Map<Vertex, Vertex> parent = new HashMap<>();
        Vertex far = farthest(g, start, parent);
        parent.clear();
        Vertex other = farthest(g, far, parent);
        List<Vertex> path = new ArrayList<>();
        for (Vertex v = other; v != null; v = parent.get(v)) {
            path.add(v);
        }
        return path.get(path.size() / 2);
    }

    private static Vertex farthest(GraphModel g, Vertex from, Map<Vertex, Vertex> parent) {
        Deque<Vertex> queue = new ArrayDeque<>();
        Set<Vertex> seen = new HashSet<>();
        queue.add(from);
        seen.add(from);
        Vertex last = from;
        while (!queue.isEmpty()) {
            last = queue.poll();
            for (Vertex n : g.getNeighbors(last)) {
                if (seen.add(n)) {
                    parent.put(n, last);
                    queue.add(n);
                }
            }
        }
        return last;
    }

    private static List<Vertex> bfsTree(GraphModel g, Vertex root,
                                        Map<Vertex, List<Vertex>> children, Map<Vertex, Integer> depth) {
        List<Vertex> order = new ArrayList<>();
        Deque<Vertex> queue = new ArrayDeque<>();
        queue.add(root);
        depth.put(root, 0);
        while (!queue.isEmpty()) {
            Vertex v = queue.poll();
            order.add(v);
            List<Vertex> kids = new ArrayList<>();
            for (Vertex n : g.getNeighbors(v)) {
                if (!depth.containsKey(n)) {
                    depth.put(n, depth.get(v) + 1);
                    kids.add(n);
                    queue.add(n);
                }
            }
            children.put(v, kids);
        }
        return order;
    }

    /**
     * Gives leaves consecutive slots and centres each parent over its children. Iterative, so
     * a long path does not overflow the stack.
     *
     * @return the last slot used
     */
    private static double assignSlots(Vertex root, Map<Vertex, List<Vertex>> children,
                                      Map<Vertex, Integer> depth, Map<Vertex, GPoint> places,
                                      double firstSlot) {
        double next = firstSlot;
        double last = firstSlot;
        Deque<Vertex> stack = new ArrayDeque<>();
        Set<Vertex> expanded = new HashSet<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Vertex v = stack.peek();
            List<Vertex> kids = children.get(v);
            if (!kids.isEmpty() && expanded.add(v)) {
                for (int i = kids.size() - 1; i >= 0; i--) {
                    stack.push(kids.get(i));
                }
                continue;
            }
            stack.pop();
            double x;
            if (kids.isEmpty()) {
                x = next;
                next += 1;
            } else {
                x = (places.get(kids.get(0)).x + places.get(kids.get(kids.size() - 1)).x) / 2;
            }
            places.put(v, new GPoint(x, depth.get(v)));
            last = Math.max(last, x);
        }
        return last;
    }
}
