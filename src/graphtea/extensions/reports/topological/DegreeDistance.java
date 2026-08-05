// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.topological;

import graphtea.graph.graph.GraphModel;

/**
 * @author azin azadi
 */
public class DegreeDistance extends PairwiseDistanceReportBase {

    public String getName() {
        return "Degree Distance";
    }

    @Override
    protected double contribution(GraphModel g, int v, int u, int dist) {
        return (g.getDegree(g.getVertex(u)) + g.getDegree(g.getVertex(v))) * dist;
    }
}
