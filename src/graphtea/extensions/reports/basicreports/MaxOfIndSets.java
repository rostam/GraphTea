// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.basicreports;

import graphtea.extensions.reports.Partitioner;
import graphtea.extensions.reports.SubSetListener;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayDeque;

/**
 * @author azin azadi
 */

public class MaxOfIndSets implements GraphReportExtension<Integer> {
    // Both the name and the description were copied from NumOfIndSets and describe a count of
    // independent sets. findMaxIndSet returns the size of the largest one, so the report was
    // answering a different question from the one its menu entry asked.
    public String getName() {
        return "Maximum Independent Set Size";
    }

    public String getDescription() {
        return "Number of vertices in the largest independent set of the graph";
    }

    public Integer calculate(GraphModel g) {
        Partitioner p = new Partitioner(g);
        return p.findMaxIndSet(false);
    }

	@Override
	public String getCategory() {
		return "General";
	}
}

class IndSetCounter implements SubSetListener {
    public int num = -1;

    public boolean subsetFound(int t, ArrayDeque<Vertex> complement, ArrayDeque<Vertex> set) {
        num++;
        return false;
    }
}