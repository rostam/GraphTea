// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.library.genericcloners;

import graphtea.library.BaseEdge;
import graphtea.library.BaseEdgeProperties;
import graphtea.library.BaseVertex;
import graphtea.library.BaseVertexProperties;

/**
 * @author Omid
 */

public class BaseEdgeVertexCopier implements
        EdgeVertexCopier<BaseVertex, BaseEdge<BaseVertex>> {

    public BaseEdge<BaseVertex> convert(BaseEdge<BaseVertex> e, BaseVertex newSource, BaseVertex newTarget) {
        if (e != null)
            return new BaseEdge<>(newSource, newTarget, new BaseEdgeProperties(e.getProp()));
        else
            return new BaseEdge<>(newSource, newTarget);
    }

    public BaseVertex convert(BaseVertex e) {
        if (e != null)
            return new BaseVertex(new BaseVertexProperties(e.getProp()));
        else
            return new BaseVertex();
    }
}
