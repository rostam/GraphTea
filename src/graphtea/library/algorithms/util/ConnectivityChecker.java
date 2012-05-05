// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.library.algorithms.util;

import graphtea.library.BaseEdge;
import graphtea.library.BaseGraph;
import graphtea.library.BaseVertex;
import graphtea.library.algorithms.traversal.BreadthFirstSearch;
import graphtea.library.exceptions.InvalidGraphException;
import graphtea.library.exceptions.InvalidVertexException;

import java.util.Iterator;

/**
 * @author Omid Aladini
 */
public class ConnectivityChecker {

    /**
     * Checks whether the current graph is a connected graph.
     *
     * @return True if graph is connected and false otherwise.
     * @throws InvalidGraphException
     */
    public static <VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
    boolean isGraphConnected(BaseGraph<VertexType, EdgeType> graph)
            throws InvalidGraphException {
        try {
            Iterator<VertexType> it = graph.iterator();
            if (!it.hasNext())
                return true;

            new BreadthFirstSearch<VertexType, EdgeType>(graph).doSearch(it.next(), null);

        } catch (InvalidVertexException e) {
            throw new InvalidGraphException();
        }

        for (VertexType v : graph)
            if (!v.getMark())
                return false;

        return true;
    }


}
