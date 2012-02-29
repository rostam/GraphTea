// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphlab.library.genericcloners;

import graphlab.library.BaseEdge;
import graphlab.library.BaseEdgeProperties;
import graphlab.library.BaseVertex;
import graphlab.library.BaseVertexProperties;

/**
 * @author Omid
 */

public class BaseEdgeVertexCopier implements
        EdgeVertexCopier<BaseVertex, BaseEdge<BaseVertex>> {

    public BaseEdge<BaseVertex> convert(BaseEdge<BaseVertex> e, BaseVertex newSource, BaseVertex newTarget) {
        if (e != null)
            return new BaseEdge<BaseVertex>(newSource, newTarget, new BaseEdgeProperties(e.getProp()));
        else
            return new BaseEdge<BaseVertex>(newSource, newTarget);
    }

    public BaseVertex convert(BaseVertex e) {
        if (e != null)
            return new BaseVertex(new BaseVertexProperties(e.getProp()));
        else
            return new BaseVertex();
    }
}
