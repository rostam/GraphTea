// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.Collections;
import java.util.Vector;

/**
 * @author Azin Azadi
 */
public class HeuristicGreedyColoringNumber implements GraphReportExtension<Integer> {

    public String getName() {
        return "Heuristic Greedy Coloring";
    }

    public String getDescription() {
        return "Heuristic Greedy Coloring";
    }

    @Override
    public Integer calculate(GraphModel g) {
        HeuristicGreedyColoring.heuristicColoring(g);
        Vector<Integer> ret = new Vector<>();
        for(Vertex v : g) {
            ret.add(v.getColor());
        }
        return Collections.max(ret);
    }

    @Override
    public String getCategory() {
        return "Coloring";
    }

}
