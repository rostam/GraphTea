// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.boundcheck;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;

/**
 * The broad class of graphs a conjecture is about. Finer restrictions, such as unicyclic
 * graphs or minimum degree two, are written as a condition instead.
 *
 * @author M. Ali Rostami
 */
public enum GraphClass {
    ALL("All graphs"),
    CONNECTED("Connected graphs"),
    TREES("Trees");

    private final String label;

    GraphClass(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    /**
     * @param g a graph
     * @return whether g belongs to this class
     */
    public boolean contains(GraphModel g) {
        switch (this) {
            case CONNECTED:
                return isConnected(g);
            case TREES:
                return g.getEdgesCount() == g.getVerticesCount() - 1 && isConnected(g);
            default:
                return true;
        }
    }

    /**
     * @param g a graph
     * @return whether it is connected; a graph without vertices is not
     */
    public static boolean isConnected(GraphModel g) {
        if (g.getVerticesCount() == 0) {
            return false;
        }
        Vertex start = g.getAVertex();
        Set<Vertex> seen = new HashSet<>();
        ArrayDeque<Vertex> todo = new ArrayDeque<>();
        seen.add(start);
        todo.add(start);
        while (!todo.isEmpty()) {
            for (Vertex u : g.directNeighbors(todo.poll())) {
                if (seen.add(u)) {
                    todo.add(u);
                }
            }
        }
        return seen.size() == g.getVerticesCount();
    }
}
