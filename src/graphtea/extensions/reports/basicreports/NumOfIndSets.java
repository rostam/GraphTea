// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.basicreports;

import graphtea.extensions.reports.Partitioner;
import graphtea.extensions.reports.SubSetListener;
import graphtea.graph.graph.GraphModel;
import graphtea.library.BaseVertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayDeque;

/**
 * @author azin azadi
 */

@CommandAttitude(name = "num_of_inds", abbreviation = "_indssize")
public class NumOfIndSets implements GraphReportExtension<Integer> {
    public String getName() {
        return "Number of independent sets plus empty set";
    }

    public String getDescription() {
        return "Number of independent sets in the Graph";
    }

    public Integer calculate(GraphModel g) {
        Partitioner p = new Partitioner(g);
        IndSetCounter l = new IndSetCounter();
        p.findAllSubsets(l);
        return l.num + 1;
    }

	@Override
	public String getCategory() {
		return "General";
	}
}

class IndSetCounter implements SubSetListener {
    public int num = -1;

    public boolean subsetFound(int t, ArrayDeque<BaseVertex> complement, ArrayDeque<BaseVertex> set) {
        num++;
        return false;
    }
}