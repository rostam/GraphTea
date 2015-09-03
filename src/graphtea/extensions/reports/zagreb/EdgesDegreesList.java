// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.zagreb;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.Collections;


/**
 * @author Mohammad Ali Rostami
 */

@CommandAttitude(name = "edges_degree_list", abbreviation = "_edl")
public class EdgesDegreesList implements GraphReportExtension {
    public Object calculate(GraphModel g) {
        ArrayList<Integer> al = new ArrayList<Integer>();
        for(Edge e : g.getEdges()) {
                int d = g.getDegree(e.source) +
                g.getDegree(e.target) - 2;
                al.add(d);
        }
        Collections.sort(al);
        return al;
    }

    public String getName() {
        return "Edges Degrees List";
    }

    public String getDescription() {
        return "edges degrees list";
    }

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "General";
	}
}
