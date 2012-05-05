// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.library.algorithms.shortestpath;


import graphtea.library.BaseEdge;
import graphtea.library.BaseGraph;
import graphtea.library.BaseVertex;

import java.util.HashMap;
import java.util.Vector;

/**
 * @author Soroush Sabet
 */
public class ShortestPaths<VertexType extends BaseVertex,
        EdgeType extends BaseEdge<VertexType>> {
    final static HashMap<BaseGraph, Vector> referencePath = new HashMap<BaseGraph, Vector>();
    final static HashMap<BaseGraph, Boolean> hasReferenceComputed = new HashMap<BaseGraph, Boolean>();


}
