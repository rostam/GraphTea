// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.library.event;

import graphtea.library.BaseEdge;
import graphtea.library.BaseVertex;

/**
 * Happens whenever we want to put labels on vertices or edges (now just for debugging, we don't have label for basevertex/edge yet)
 *
 * @author azin azadi
 */
public class VertexEdgeLabelEvent<VertexType extends BaseVertex, EdgeType extends BaseEdge> implements Event {
    public String label;
    public VertexType v;
    public EdgeType e;

    public VertexEdgeLabelEvent(String label, EdgeType e) {
        this.label = label;
        this.e = e;
    }

    public VertexEdgeLabelEvent(String label, VertexType v) {
        this.label = label;
        this.v = v;
    }

    public String getID() {
        return "label";
    }

    public String getDescription() {
        return "label";
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
