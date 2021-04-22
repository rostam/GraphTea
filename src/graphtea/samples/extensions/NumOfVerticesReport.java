// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.samples.extensions;

import graphtea.graph.graph.GraphModel;
import graphtea.plugins.reports.extension.GraphReportExtension;

public class NumOfVerticesReport implements GraphReportExtension<Integer> {
    public String getName() {
        return "num of vertices";
    }

    public String getDescription() {
        return "Number of vertices in graph";
    }

    public Integer calculate(GraphModel g) {
        return g.numOfVertices();
    }

	@Override
	public String getCategory() {
		return "General";
	}
}