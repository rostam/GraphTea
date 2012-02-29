// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.reports.basicreports;

import graphlab.graph.graph.GraphModel;
import graphlab.platform.lang.CommandAttitude;
import graphlab.plugins.main.GraphData;
import graphlab.plugins.main.core.AlgorithmUtils;
import graphlab.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohammad Ali Rostami
 */

@CommandAttitude(name = "girth_size", abbreviation = "_gs")
public class GirthSize implements GraphReportExtension {

    private static int bfs(int start, double mat[][], int cc, int girth) {
        int baba[] = new int[cc];
        int dist[] = new int[cc];
        for (int i = 0; i < cc; i++) {
            baba[i] = AlgorithmUtils.Max_Int;
            dist[i] = AlgorithmUtils.Max_Int;
        }

        dist[start] = 0;
        baba[start] = -1;
        List<Integer> ll = new ArrayList<Integer>();
        ll.add(start);
        while (ll.size() > 0) {
            int currentNode = ll.remove(0);
            for (int j = 0; j < cc; j++)
                if (mat[currentNode][j] == 1)
                    if (dist[j] == AlgorithmUtils.Max_Int) {
                        dist[j] = dist[currentNode] + 1;
                        baba[j] = currentNode;
                        if (2 * dist[j] < girth - 1)
                            ll.add(j);
                    } else if (dist[j] + dist[currentNode] < girth - 1 && baba[currentNode] != j)
                        girth = dist[j] + dist[currentNode] + 1;

        }
        return girth;
    }


    public Object calculate(GraphData gd) {
        GraphModel graph = gd.getGraph();
        return getgirthSize(graph);

    }

    /**
     * @return the girth size of the given graph
     */
    public static int getgirthSize(GraphModel graph) {
        int size = graph.getVertexArray().length;
        double mat[][] = graph.getAdjacencyMatrix().getArray();
        int girth = AlgorithmUtils.Max_Int;
        for (int i = 0; i < size; i++) {
            int sizeofsmallestcycle = bfs(i, mat, size, girth);
            if (sizeofsmallestcycle != AlgorithmUtils.Max_Int && girth > sizeofsmallestcycle)
                girth = sizeofsmallestcycle;
        }
        if (girth == AlgorithmUtils.Max_Int) return 0;
        return girth;
    }

    public String getName() {
        return "Graph Girth Size";
    }

    public String getDescription() {
        return "Graph Girth Size";
    }


	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "Property";
	}
}
