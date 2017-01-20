// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.visualization.localsfvis;

import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.graph.*;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.graphgenerator.core.PositionGenerators;
import graphtea.ui.UIUtils;
import java.awt.*;

public class circular extends AbstractAction implements Parametrizable {
    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */
    public circular(BlackBoard bb) {
        super(bb);
        String event = UIUtils.getUIEventKey("circular");
        listen4Event(event);

    }

    /**
     * like Action
     *
     * @param eventName The event name
     * @param value The value
     */
    public void performAction(String eventName, Object value) {
        GraphModel g = blackboard.getData(GraphAttrSet.name);
        circularVisualize(g);

    }

    private static void circularVisualize(GraphModel g) {
        GRect b = g.getAbsBounds();
        int w = (int) b.w;
        int h = (int) b.h;
        if (w < 50)
            w = 300;
        if (h < 50)
            h = 300;
        circularVisualize(w, h, g);
    }

    private static void circularVisualize(int w, int h, GraphModel g) {
        int n = g.getVerticesCount();
        Vertex[] v = new Vertex[n];
        int i = 0;
        for (Vertex vm : g)
            v[i++] = vm;
        Point[] p = PositionGenerators.circle(25, 25, w + 25, h + 25, n);
        for (i = 0; i < n; i++) {
            v[i].setLocation(new GPoint(p[i].x, p[i].y));
        }
    }

    public static void circularVisualize(int r, int x, int y, SubGraph g) {
        int n = g.vertices.size();
        Vertex[] v = new Vertex[n];
        int i = 0;
        for (Vertex vm : g.vertices)
            v[i++] = vm;
        Point[] p = PositionGenerators.circle(r, x, y, n);
        for (i = 0; i < n; i++) {
            v[i].setLocation(new GPoint(p[i].x, p[i].y));
        }
    }

    public String checkParameters() {
        return null;
    }
}