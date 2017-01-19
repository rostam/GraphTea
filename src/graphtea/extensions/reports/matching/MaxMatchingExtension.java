// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.matching;


import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.SubGraph;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * @author Ali Rostami
 */

@CommandAttitude(name = "maxium_matching", abbreviation = "_max_match")
public class MaxMatchingExtension implements GraphReportExtension {
    public String getName() {
        return "Maximum Matching";
    }

    public String getDescription() {
        return "Maximum Matching";
    }

    public Object calculate(GraphModel gg) {
        SubGraph sg = new SubGraph();
        List<Integer>[] g = new List[gg.getVerticesCount()];
        for (int i = 0; i < gg.getVerticesCount(); i++) {
            g[i] = new ArrayList();
        }

        for(Edge e : gg.getEdges()) {
            g[e.source.getId()].add(e.target.getId());
        }
        MaximumMatching.maxMatching(g);
        int[] match = MaximumMatching.match;

        for(int i=0;i<match.length;i++) {
            if(match[i]>=0) {
                sg.vertices.add(gg.getVertex(i));
                sg.vertices.add(gg.getVertex(match[i]));
            }
        }

        for(int i=0;i<match.length;i++) {
            if(match[i] >= 0)
                sg.edges.add(gg.getEdge(
                        gg.getVertex(i),
                        gg.getVertex(match[i])));
        }

        Vector<Object> ret = new Vector<>();
        ret.add("Number of Matching:" + sg.edges.size());
        ret.add(sg);

        return ret;
    }

	@Override
	public String getCategory() {
		return "Matching";
	}
}
