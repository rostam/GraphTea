// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.main.select;

import graphlab.graph.graph.GraphPoint;
import graphlab.graph.graph.Vertex;
import graphlab.plugins.main.GraphData;
import graphlab.plugins.main.core.AlgorithmUtils;
import graphlab.plugins.main.extension.GraphActionExtension;

import java.util.HashSet;

/**
 * @author Azin Azadi
 */
public class ScaleOutSelection implements GraphActionExtension {
    public String getName() {
        return "Scale Out Selection";
    }

    public String getDescription() {
        return "Shrinks the selection";
    }

    public void action(GraphData gd) {
        if (gd.select.isSelectionEmpty())
            return;
        HashSet<Vertex> V = gd.select.getSelectedVertices();

        GraphPoint center = AlgorithmUtils.getCenter(V);
        for (Vertex v : V) {
            GraphPoint loc = v.getLocation();
            double x = loc.x - center.x;
            double y = loc.y - center.y;
            setNewLocation(v, loc, x, y);
        }
    }

    protected void setNewLocation(Vertex v, GraphPoint loc, double x, double y) {
        v.setLocation(new GraphPoint(loc.x - x / 1.25, loc.y - y / 1.25));
    }

}
