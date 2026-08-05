// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.library.event;

import graphtea.library.BaseEdge;
import graphtea.library.BaseGraph;
import graphtea.library.BaseVertex;

public class PostWorkEvent<VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
        implements Event {
    final public VertexType from;
    final public VertexType to;
    final public BaseGraph<VertexType, EdgeType> graph;

    public PostWorkEvent(VertexType from, VertexType to, BaseGraph<VertexType, EdgeType> graph) {
        this.from = from;
        this.to = to;
        this.graph = graph;
    }

    public String getID() {
        return "Postwork";
    }

    public String getDescription() {
        return "Postwork event occurred";
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
