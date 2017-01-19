// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.main.select;

import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.Vertex;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.core.AlgorithmUtils;
import graphtea.plugins.main.extension.GraphActionExtension;

import java.util.HashSet;

/**
 * @author Azin Azadi
 */
public class ScaleOutSelection implements GraphActionExtension {
    static double scale = 0.25;
    public String getName() {
        return "Scale Out Selection";
    }

    public String getDescription() {
        return "Shrinks the selection. HotKey:(Alt+Control+P)";
    }

    public void action(GraphData gd) {
        if (gd.select.isSelectionEmpty())
            return;
        HashSet<Vertex> V = gd.select.getSelectedVertices();
        GPoint center = AlgorithmUtils.getCenter(V);
        for (Vertex v : V) {
            GPoint loc = v.getLocation();
            GPoint gp = GPoint.sub(loc,center);
            setNewLocation(v, loc, gp.x, gp.y);
        }
    }

    protected void setNewLocation(Vertex v, GPoint loc, double x, double y) {
        v.setLocation(new GPoint(loc.x - x * scale, loc.y - y * scale));
    }

    @Override
    public String getCategory() {
        return "Basic Operations";
    }
}
