// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphlab.extensions.reports;

import graphlab.graph.graph.GraphColoring;
import graphlab.graph.graph.Vertex;
import graphlab.platform.parameter.Parameter;
import graphlab.platform.parameter.Parametrizable;
import graphlab.plugins.main.GraphData;
import graphlab.plugins.reports.extension.GraphReportExtension;

import java.util.Vector;

/**
 * @author Azin Azadi
 */
public class ColoringReport implements GraphReportExtension, ColoringListener, Parametrizable {
    Partitioner p;
    Vector<GraphColoring> colorings;
    boolean found;

    @Parameter(name = "Lower Bound", description = "Lower Bound for the number of colors, This will make the search Interval smaller")
    public Integer lowerBound = 1;

    @Parameter(name = "All Colorings", description = "Create a list of all colorings of graph using minimum number of colors")
    public Boolean allColorings=false;

    public Object calculate(GraphData gd) {
        p = new Partitioner(gd.getGraph());
        colorings = new Vector<GraphColoring>(1);
        int ct = lowerBound;
        found = false;
        while (!found) {
            tryToColor(ct++);
        }
        return colorings;
    }

    public void tryToColor(int t) {
        p.findAllPartitionings(t, this);
    }

    public boolean coloringFound(final int t) {
        found = true;
        GraphColoring coloring = new GraphColoring();
        for (int i = 0; i < p.vertices.length; i++) {
            coloring.vertexColors.put((Vertex) p.vertices[i], p.color[i]);
        }
        colorings.add(coloring);
        return !allColorings;
    }

    public String getName() {
        return "Graph Coloring";
    }

    public String getDescription() {
        return "Graph Vertex Coloring";
    }

    public String checkParameters() {
        return lowerBound < 0 ? "Lower Bound should be positive" : null;
    }

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return null;
	}
}
