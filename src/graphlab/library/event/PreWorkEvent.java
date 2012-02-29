// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphlab.library.event;

import graphlab.library.BaseEdge;
import graphlab.library.BaseGraph;
import graphlab.library.BaseVertex;

public class PreWorkEvent<VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
        implements Event {
    final public VertexType from;
    final public VertexType to;
    final public BaseGraph<VertexType, EdgeType> graph;

    public PreWorkEvent(VertexType from, VertexType to, BaseGraph<VertexType, EdgeType> graph) {
        this.from = from;
        this.to = to;
        this.graph = graph;
    }

    public String getID() {
        return "Prework";
    }

    public String getDescription() {
        return "Prework event occurred.";
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
