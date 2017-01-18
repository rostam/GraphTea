// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.visualization.localsfvis;

import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.GraphPoint;
import graphtea.graph.graph.Vertex;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.ui.UIUtils;

import java.awt.geom.Rectangle2D;


/**
 * @author houshmand hasannia
 */
public class Random extends AbstractAction {
    private GraphModel g;

    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */
    public Random(BlackBoard bb) {
        super(bb);
        String event = UIUtils.getUIEventKey("Random");
        listen4Event(event);
    }

    /**
     * like Action
     *
     * @param eventName The event name
     * @param value The value
     */
    public void performAction(String eventName, Object value) {
        g = blackboard.getData(GraphAttrSet.name);
        n = g.getVerticesCount();
        Vertex[] v = getVertices();
        Rectangle2D.Double b = g.getZoomedBounds();
        int w = (int) b.width;
        int h = (int) b.height;
        if (w < 5)
            w = 150;
        if (h < 5)
            h = 150;
        for (int i = 0; i < n; i++) {
            double x = Math.random() * w;
            double y = Math.random() * h;
            v[i].setLocation(new GraphPoint(x, y));
        }
    }

    private Vertex[] getVertices() {
        Vertex[] ret = new Vertex[n];
        int i = 0;
        for (Vertex vm : g) {
            ret[i++] = vm;
        }
        return ret;
    }

    private int n;
}

