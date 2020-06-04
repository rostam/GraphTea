// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.basicreports;

import graphtea.extensions.reports.Partitioner;
import graphtea.extensions.reports.SubSetListener;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayDeque;

/**
 * @author azin azadi
 */

@CommandAttitude(name = "num_of_inds", abbreviation = "_indssize")
public class MaxOfIndSets implements GraphReportExtension<Integer> {
    public String getName() {
        return "Number of independent sets plus empty set";
    }

    public String getDescription() {
        return "Number of independent sets in the Graph";
    }

    public Integer calculate(GraphModel g) {
        Partitioner p = new Partitioner(g);
        return p.findMaxIndSet(false);
    }

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
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