// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

/*
 * PreWorkHandler.java
 *
 * Created on November 15, 2004, 3:13 AM
 */

package graphlab.library.event.handlers;

import graphlab.library.BaseVertex;

/**
 * Handles prework used by algorithms such as BFS and DFS.
 * Depending on the application, the user can define custom classes that
 * implements PreWorkHandler and pass them to algorithms.
 *
 * @author Omid Aladini
 */
public interface PreWorkHandler<VertexType extends BaseVertex> {
    /**
     * Does prework on a vertex of a graph.
     *
     * @param fromVertex Reference to the vertex we just leaved.
     * @param toVertex   Reference to the vertex which prework will apply on.
     * @return whether the traversal should stop at this point.
     */
    public boolean doPreWork(VertexType fromVertex, VertexType toVertex);
}
