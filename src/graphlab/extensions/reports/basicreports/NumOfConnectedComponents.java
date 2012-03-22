// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.extensions.reports.basicreports;

import graphlab.graph.graph.GraphModel;
import graphlab.library.BaseGraph;
import graphlab.platform.lang.CommandAttitude;
import graphlab.plugins.main.GraphData;
import graphlab.plugins.main.core.AlgorithmUtils;
import graphlab.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;

/**
 * @author Mohammad Ali Rostami
 */

@CommandAttitude(name = "num_of_connected_components", abbreviation = "_nocc")
public class NumOfConnectedComponents implements GraphReportExtension {

    public Object calculate(GraphData gd) {
        GraphModel graph = gd.getGraph();
        return getNumOfConnectedComponents(graph);
    }

    /**
     * @return Number of connected components of the given graph
     */
    public static int getNumOfConnectedComponents(GraphModel graph) {
        double[][] mat = graph.getAdjacencyMatrix().getArray();
        int size = mat.length;
        ArrayList untraversed = new ArrayList();
        for (int i = 0; i < size; i++)
            untraversed.add(new Integer(i));

        ArrayList comps = new ArrayList();
        int parent[] = new int[size];
        for (int i = 0; i < size; i++)
            parent[i] = -1;

        ArrayList visit;
        for (; untraversed.size() > 0; untraversed.removeAll(visit)) {
            visit = new ArrayList();
            int currentNode = ((Integer) untraversed.get(0)).intValue();
            parent[currentNode] = currentNode;
            AlgorithmUtils.dfs((BaseGraph) graph, currentNode, visit, parent);
            comps.add(visit);
        }
        return comps.size();
    }

    /**
     * @return connected components of the given graph, each cell of ArrayList is a ArrayList
     * containing indices of the corresponding component vertices indices
     */
    public static ArrayList<ArrayList<Integer>> getConnectedComponents(GraphModel graph) {
        double[][] mat = graph.getAdjacencyMatrix().getArray();
        int size = mat.length;
        ArrayList<Integer> untraversed = new ArrayList<Integer>();
        for (int i = 0; i < size; i++)
            untraversed.add(new Integer(i));

        ArrayList<ArrayList<Integer>> comps = new ArrayList();
        int parent[] = new int[size];
        for (int i = 0; i < size; i++)
            parent[i] = -1;

        ArrayList<Integer> visit;
        for (; untraversed.size() > 0; untraversed.removeAll(visit)) {
            visit = new ArrayList();
            int currentNode = ((Integer) untraversed.get(0)).intValue();
            parent[currentNode] = currentNode;
            AlgorithmUtils.dfs((BaseGraph) graph, currentNode, visit, parent);
            comps.add(visit);
        }
        return comps;
    }


    public String getName() {
        return "Connected Components";
    }

    public String getDescription() {
        return "number of connected componentes";
    }

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "Property";
	}
}
