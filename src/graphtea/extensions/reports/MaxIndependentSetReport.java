// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.SubGraph;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 * @author Azin Azadi
 */

@CommandAttitude(name = "maximum_independent_set", abbreviation = "_mis")
public class MaxIndependentSetReport implements GraphReportExtension<Vector<SubGraph>> {
//    @Parameter(name = "Lower Bound", description = "Lower Bound for the number of independent set members, This will make the search Interval smaller")
//    public Integer lowerBound = 1;
//
//    @Parameter(name = "All Independent Sets", description = "Create a list of all independent sets of graph using minimum number of members")
//    public Boolean allColorings = false;
//

    public String getName() {
        return "Max Independent Set";
    }

    public String getDescription() {
        return "Maximum independent set of graph vertices";
    }


    public Vector<SubGraph> calculate(GraphModel g) {
        Vector<ArrayDeque<Vertex>> maxsets = getMaxIndependentSet(g);
        Vector<SubGraph> ret = new Vector<>();
        for (ArrayDeque<Vertex> maxset : maxsets) {
            SubGraph sd = new SubGraph(g);
            sd.vertices = new HashSet<>();
            sd.vertices.addAll(maxset);
            ret.add(sd);
        }
        return ret;
    }

    public static Vector<ArrayDeque<Vertex>> getMaxIndependentSet(GraphModel graph) {
        Partitioner p = new Partitioner(graph);
        MaxIndSetSubSetListener l = new MaxIndSetSubSetListener();
        p.findAllSubsets(l);
        return l.maxsets.stream().filter(set -> set.size() == l.max).collect(Collectors.toCollection(Vector::new));
    }

    public static int getMaxIndependentSetSize(GraphModel graph, boolean putFirstVertexInSet) {
        Partitioner p = new Partitioner(graph);
        return p.findMaxIndSet(putFirstVertexInSet);
    }

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "General";
	}

}

class MaxIndSetSubSetListener implements SubSetListener {
    Vector<ArrayDeque<Vertex>> maxsets = new Vector<>();
    int max = -1;

    public boolean subsetFound(int t, ArrayDeque<Vertex> complement, ArrayDeque<Vertex> set) {
        if (max <= set.size()) {
            max = set.size();
            maxsets.add(new ArrayDeque<>(set));
        }
        return false;
    }
}

