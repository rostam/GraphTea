// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphlab.library.algorithms.util;

import graphlab.library.BaseEdge;
import graphlab.library.BaseGraph;
import graphlab.library.BaseVertex;
import graphlab.library.algorithms.traversal.BreadthFirstSearch;
import graphlab.library.exceptions.InvalidGraphException;
import graphlab.library.exceptions.InvalidVertexException;

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
