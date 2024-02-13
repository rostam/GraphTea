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
public class HeuristicGreedyColoring implements GraphReportExtension<Vector<Integer>> {

    public String getName() {
        return "Heuristic Greedy Coloring";
    }

    public String getDescription() {
        return "Heuristic Greedy Coloring";
    }

    public static void heuristicColoring(GraphModel g) {
        for(Vertex v : g)
            v.setColor(0);

        for(Vertex v : g) {
            if(v.getColor() == 0) {
                Vector<Integer> colors = new Vector<>();
                for(Vertex u : g.directNeighbors(v))
                    colors.add(u.getColor());
                for(int i = 1;i < g.getVerticesCount();i++) {
                    if(!colors.contains(i)) {
                        v.setColor(i);
                        break;
                    }
                }
                if(v.getColor() == 0) {
                    v.setColor(Collections.max(colors) + 1);
                }
            }
        }
    }

    @Override
    public Vector<Integer> calculate(GraphModel g) {
        heuristicColoring(g);
        Vector<Integer> ret = new Vector<>();
        for(Vertex v : g) {
            ret.add(v.getColor());
        }
        return ret;
    }

    @Override
    public String getCategory() {
        return "Coloring";
    }

}
