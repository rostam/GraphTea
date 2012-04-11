// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.algorithmanimator.core.atoms;

import graphlab.graph.graph.Edge;
import graphlab.graph.graph.Vertex;
import graphlab.library.event.Event;
import graphlab.library.event.VertexEdgeLabelEvent;
import graphlab.platform.core.BlackBoard;
import graphlab.plugins.algorithmanimator.core.AtomAnimator;

/**
 * this class not used, because the colors automatically update in Vertex and Edge
 *
 * @author Azin Azadi
 */
public class VertexEdgeLabelAnimator implements AtomAnimator<VertexEdgeLabelEvent<Vertex, Edge>> {

    public boolean isAnimatable(Event event) {
        return event instanceof VertexEdgeLabelEvent;
    }

    public VertexEdgeLabelEvent<Vertex, Edge> animate(VertexEdgeLabelEvent<Vertex, Edge> event, BlackBoard b) {
        if (event.v != null) event.v.setLabel(event.label);
        if (event.e != null) {
            event.e.setLabel(event.label);
        }
        return event;
    }

}

//    public EdgeEvent<? extends BaseVertex,? extends Edge> animate(EdgeEvent<? extends BaseVertex,? extends Edge> event, blackboard b) {
//        Edge e = ((Edge)event.edge).edge;
//        e.view.setRGBColor(Color.black);
//        return event;
//    }
