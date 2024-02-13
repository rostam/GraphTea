// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.algorithmanimator.core.atoms;

import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.graph.GraphModel;
import graphtea.library.event.Event;
import graphtea.library.event.GraphRequest;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.algorithmanimator.core.AtomAnimator;

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

