// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/
package graphtea.library.event.typedef;

import graphtea.library.BaseEdge;
import graphtea.library.BaseGraph;
import graphtea.library.BaseVertex;
import graphtea.library.event.GraphEvent;

/**
 * @author Omid Aladini
 */
public class BaseGraphEvent extends GraphEvent<BaseVertex, BaseEdge<BaseVertex>> {

    public BaseGraphEvent(BaseGraph<BaseVertex, BaseEdge<BaseVertex>> g, EventType et) {
        super(g, et);
        // TODO Auto-generated constructor stub
    }


    public BaseGraphEvent(BaseGraph<BaseVertex, BaseEdge<BaseVertex>> g) {
        super(g);
        // TODO Auto-generated constructor stub
    }

    public String getMessage() {
        return null;
    }
}
