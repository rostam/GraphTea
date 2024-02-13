// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.actions;

import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.GRect;
import graphtea.graph.graph.GraphModel;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.extension.GraphActionExtension;

/**
 * @author houshmand hasannia
 */
public class Random implements GraphActionExtension {
    @Override
    public String getName() {
        return "Random Layout";
    }

    @Override
    public String getDescription() {
        return "Random Layout";
    }

    @Override
    public String getCategory() {
        return "Visualization";
    }

    @Override
    public void action(GraphData graphData) {
        GraphModel g = graphData.getGraph();
        int n = g.getVerticesCount();
        GRect b = g.getZoomedBounds();
        int w = (int) b.w;
        int h = (int) b.h;
        if (w < 5)
            w = 150;
        if (h < 5)
            h = 150;
        for (int i = 0; i < n; i++) {
            double x = Math.random() * w;
            double y = Math.random() * h;
            g.getVertex(i).setLocation(new GPoint(x, y));
        }
    }
}

