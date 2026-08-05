// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

/*
 * PreWorkPostWorkHandler.java
 *
 * Created on November 13, 2004, 8:33 PM
 */

package graphtea.library.event.handlers;

import graphtea.library.BaseVertex;

/**
 * Handles both prework and postwork used by algorithms such as DFS and BFS.
 * Depending on the application, the user can define custom classes that
 * implements PreWorkPostWorkHandler and pass thems to the algorithm.
 *
 * @author Omid Aladini
 */
public interface PreWorkPostWorkHandler<VertexType extends BaseVertex>
        extends PostWorkHandler<VertexType>, PreWorkHandler<VertexType> {

}
