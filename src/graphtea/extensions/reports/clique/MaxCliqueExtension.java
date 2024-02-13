// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.clique;


import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.SubGraph;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.Vector;

/**
 * @author Ali Rostami
 */

@CommandAttitude(name = "mst_prim", abbreviation = "_max_c")
public class MaxCliqueExtension implements GraphReportExtension<Vector<SubGraph>> {
    public String getName() {
        return "Maximal Cliques";
    }

    public String getDescription() {
        return "Maximal Cliques";
    }

    public Vector<SubGraph> calculate(GraphModel g) {
        Vector<SubGraph> ret = new Vector<>();
        MaxCliqueAlg mca = new MaxCliqueAlg(g);
        Vector<Vector<Vertex>> mcs = mca.allMaxCliques();
        for(Vector<Vertex> ss : mcs) {
            SubGraph sg = new SubGraph(g);
            sg.vertices.addAll(ss);
            ret.add(sg);
        }
        return ret;
    }

	@Override
	public String getCategory() {
		return "General";
	}

}