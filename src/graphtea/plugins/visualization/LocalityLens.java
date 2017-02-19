// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.plugins.visualization;

import graphtea.graph.event.GraphEvent;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.Vertex;
import graphtea.graph.ui.GTabbedGraphPane;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.Listener;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.extension.GraphActionExtension;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Ricado Nussbaum
 */


public class LocalityLens implements GraphActionExtension, Parametrizable {

    @Parameter(name = "Locality Lenses", description = "locality lenses")
    public boolean active = false;

    public String getName() {
        return "Locality Lens";
    }

    public String getDescription() {
        return "Locality Lens";
    }

    public void action(GraphData graphData) {
        if (active) {
            int x = (int) graphData.getGraph().getVertex(0).getSize().getX() + 20;
            int y = (int) graphData.getGraph().getVertex(0).getSize().getY() + 20;
            BufferedImage bi = new BufferedImage(x,y, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.getGraphics();
            g.setColor(Color.blue);
            g.drawOval(0,0,x,y);

            GTabbedGraphPane.getCurrentGTabbedGraphPane(graphData.getBlackboard())
                    .getComponent(graphData.getBlackboard()).setCursor(Toolkit.getDefaultToolkit()
                    .createCustomCursor(bi, new Point(x/2,y/2), Color.blue + " Circle"));
            graphData.getBlackboard().addListener(GraphEvent.EVENT_KEY,
                    new MouseEventListener(graphData.getBlackboard(), graphData.getGraph()));

        }else{
            GTabbedGraphPane.getCurrentGTabbedGraphPane(graphData.getBlackboard())
                    .getComponent(graphData.getBlackboard()).setCursor(Cursor.getDefaultCursor());
            graphData.getBlackboard().getListeners(GraphEvent.EVENT_KEY).clear();
            for(Edge e : graphData.getGraph().getEdges()) e.setColor(0);
        }
    }

    @Override
    public String getCategory() {
        return null;
    }

    @Override
    public String checkParameters() {
        return null;
    }
}

class MouseEventListener implements Listener {

    BlackBoard bb;
    GraphModel g;
    public MouseEventListener(BlackBoard bb, GraphModel g) {this.bb=bb;this.g=g;}

    public Vertex getEnteredVertex(GPoint gp) {
        for(Vertex v : g) {
            if(v.getLocation().distance(gp) < v.getSize().norm()/3) return v;
        }
        return null;
    }

    @Override
    public void keyChanged(String key, Object Value) {

        if (key.equals(GraphEvent.EVENT_KEY)) {
            GraphEvent ge = bb.getData(GraphEvent.EVENT_KEY);
            if (ge.eventType == GraphEvent.MOUSE_MOVED) {
                Vertex v = getEnteredVertex(ge.mousePos);
                if (v != null) {
                    for(Edge e : g.getEdges()) e.setColor(-921360);
                    for(Vertex n : g.directNeighbors(v)) g.getEdge(n,v).setColor(3);
                }
                else{
                    for(Edge e : g.getEdges()) e.setColor(0);
                }
            }
        }
    }



}

