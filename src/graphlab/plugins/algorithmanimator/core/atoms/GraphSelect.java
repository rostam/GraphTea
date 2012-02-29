// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.algorithmanimator.core.atoms;

import graphlab.graph.atributeset.GraphAttrSet;
import graphlab.graph.graph.GraphModel;
import graphlab.library.event.Event;
import graphlab.library.event.GraphRequest;
import graphlab.platform.core.BlackBoard;
import graphlab.plugins.algorithmanimator.core.AtomAnimator;

/**
 * @author Azin Azadi
 */
public class GraphSelect implements AtomAnimator<GraphRequest> {
///    public boolean isAnimatable(Event event) {
//        return event instanceof VertexRequest;
//    }

    public boolean isAnimatable(Event e) {
        return e instanceof GraphRequest;
    }

    public GraphRequest animate(GraphRequest gr, BlackBoard b) {
        GraphModel g = b.getData(GraphAttrSet.name);
        gr.setGraph(g);
        return gr;
    }
}

