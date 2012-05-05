// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

/*
* PostWorkHandler.java
*
* Created on November 15, 2004, 3:11 AM
*/

package graphtea.library.event.handlers;

import graphtea.library.BaseVertex;

/**
 * Handles postwork used by algorithms such as BFS.
 * Depending on the application, the user can define custom classes that
 * implements PostWorkHandler and pass them to algorithms.
 *
 * @author Omid Aladini
 */
public interface PostWorkHandler<VertexType extends BaseVertex> {
    /**
     * Does postwork when traversing back from w to v.
     *
     * @param returnFrom Id of the vertex traversing back from.
     * @param returnTo   Id of the vertex traversing back to.
     * @return whether the traversal should stop at this point.
     */
    public boolean doPostWork(VertexType returnFrom, VertexType returnTo);
}
