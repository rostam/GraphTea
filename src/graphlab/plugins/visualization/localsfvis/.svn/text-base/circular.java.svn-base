// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.visualization.localsfvis;

import graphlab.graph.atributeset.GraphAttrSet;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.GraphPoint;
import graphlab.graph.graph.SubGraph;
import graphlab.graph.graph.VertexModel;
import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.platform.parameter.Parametrizable;
import graphlab.plugins.graphgenerator.core.PositionGenerators;
import graphlab.ui.UIUtils;

import java.awt.*;
import java.awt.geom.Rectangle2D;


public class circular extends AbstractAction implements Parametrizable {
    GraphModel g;
    String event = UIUtils.getUIEventKey("circular");
    private int n;
    VertexModel[] v;

    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */
    public circular(BlackBoard bb) {
        super(bb);
        listen4Event(event);

    }

    /**
     * like Action
     *
     * @param eventName
     * @param value
     */
    public void performAction(String eventName, Object value) {
        g = blackboard.getData(GraphAttrSet.name);
        circularVisualize(g);

    }

    public static void circularVisualize(GraphModel g) {
        Rectangle2D.Double b = g.getAbsBounds();
        int w = (int) b.width;
        int h = (int) b.height;
        if (w < 50)
            w = 300;
        if (h < 50)
            h = 300;
        circularVisualize(w, h, g);
    }

    public static void circularVisualize(int w, int h, GraphModel g) {
        int n = g.getVerticesCount();
        VertexModel[] v = new VertexModel[n];
        int i = 0;
        for (VertexModel vm : g)
            v[i++] = vm;
        Point[] p = PositionGenerators.circle(25, 25, w + 25, h + 25, n);
        for (i = 0; i < n; i++) {
            v[i].setLocation(new GraphPoint(p[i].x, p[i].y));
        }
    }

    public static void circularVisualize(int r, int x, int y, SubGraph g) {
        int n = g.vertices.size();
        VertexModel[] v = new VertexModel[n];
        int i = 0;
        for (VertexModel vm : g.vertices)
            v[i++] = vm;
        Point[] p = PositionGenerators.circle(r, x, y, n);
        for (i = 0; i < n; i++) {
            v[i].setLocation(new GraphPoint(p[i].x, p[i].y));
        }
    }

    public String checkParameters() {
        return null;
    }
}