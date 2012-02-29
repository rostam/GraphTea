// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.main.select;

import graphlab.graph.graph.GraphPoint;
import graphlab.graph.graph.VertexModel;

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

    protected void setNewLocation(VertexModel v, GraphPoint loc, double x, double y) {
        v.setLocation(new GraphPoint(loc.x + x * 1.25, loc.y + y * 1.25));
    }
}
