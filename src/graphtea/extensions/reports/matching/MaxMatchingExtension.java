// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.matching;

import graphtea.extensions.AlgorithmUtils;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.SubGraph;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ali Rostami
 */

public class MaxMatchingExtension implements GraphReportExtension<List<Object>> {
    public String getName() {
        return "Maximum Matching";
    }

    public List<Object> calculate(GraphModel gg) {
        SubGraph sg = new SubGraph();
        List<Integer>[] g = new List[gg.getVerticesCount()];
        for (int i = 0; i < gg.getVerticesCount(); i++) {
            g[i] = new ArrayList<>();
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
            if (match[i] >= 0) {
                // The matching is computed on the underlying undirected structure, so on a
                // directed graph the edge often exists only the other way round and getEdge
                // returned null straight into the SubGraph.
                Edge e = AlgorithmUtils.getEdgeBetween(gg, gg.getVertex(i), gg.getVertex(match[i]));
                if (e != null) {
                    sg.edges.add(e);
                }
            }
        }

        List<Object> ret = new ArrayList<>();
        ret.add("Number of Matching:" + sg.edges.size());
        ret.add(sg);

        return ret;
    }

	@Override
	public String getCategory() {
		return "Matching";
	}

	public int numOfMatching(GraphModel gg) {
        SubGraph sg = new SubGraph();
        List<Integer>[] g = new List[gg.getVerticesCount()];
        for (int i = 0; i < gg.getVerticesCount(); i++) {
            g[i] = new ArrayList<>();
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
            if (match[i] >= 0) {
                // The matching is computed on the underlying undirected structure, so on a
                // directed graph the edge often exists only the other way round and getEdge
                // returned null straight into the SubGraph.
                Edge e = AlgorithmUtils.getEdgeBetween(gg, gg.getVertex(i), gg.getVertex(match[i]));
                if (e != null) {
                    sg.edges.add(e);
                }
            }
        }

        return sg.edges.size();
    }
}
