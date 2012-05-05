// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.main.select;

import graphtea.graph.graph.GraphPoint;
import graphtea.graph.graph.Vertex;

/**
 * @author Azin Azadi
 */
public class ScaleInSelection extends ScaleOutSelection {
    public String getName() {
        return "Scale In Selection";
    }

    public String getDescription() {
        return "Expands the selection";
    }

    protected void setNewLocation(Vertex v, GraphPoint loc, double x, double y) {
        v.setLocation(new GraphPoint(loc.x + x * 1.25, loc.y + y * 1.25));
    }
}
