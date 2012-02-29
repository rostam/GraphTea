// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphlab.library.genericcloners;

import graphlab.library.BaseEdge;
import graphlab.library.BaseVertex;

/**
 * @author Omid
 */

public interface EdgeVertexCopier
        <VertexType extends BaseVertex,
                EdgeType extends BaseEdge<VertexType>>
        extends
        EdgeVertexConverter<VertexType, VertexType, EdgeType, EdgeType> {
}
