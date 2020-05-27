// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.extensions.algorithms.spanningtree;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.library.BaseVertex;
import graphtea.library.algorithms.Algorithm;
import graphtea.library.algorithms.AutomatedAlgorithm;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Omid Aladini
 */
public class Kruskal extends Algorithm implements AutomatedAlgorithm {
    public static AbstractList<Edge>
    findMinimumSpanningTree(GraphModel graph) {
        ArrayList<Edge> outputEdges = new ArrayList<>();
        ArrayList<ArrayList<Vertex>> sets = new ArrayList<>();

        for (Vertex v : graph) {
            ArrayList<Vertex> set = new ArrayList<>();
            set.add(v);
            sets.add(set);
        }

        ArrayList<Edge> edges = new ArrayList<>(graph.getEdgesCount());

        Iterator<Edge> eit = graph.edgeIterator();
        while (eit.hasNext())
            edges.add(eit.next());

        edges.sort((o1, o2) -> {
            if (o1.getWeight() < o2.getWeight())
                return -1;
            else if (o1.getWeight() == o2.getWeight())
                return 0;
            return 1;
        });

        for (Edge e : edges)
            System.out.print(e.getWeight() + " ");
        System.out.println();

        for (Edge e : edges) {
            int set1 = findSet(sets, e.source);
            int set2 = findSet(sets, e.target);

            if (set1 != set2) {
                outputEdges.add(e);
                e.source.setMark(true);
                e.setMark(true);
                e.target.setMark(true);
                sets.get(set1).addAll(sets.get(set2));
                sets.remove(set2);
                //EventUtils.algorithmStep(this,600);
            }
        }

        return outputEdges;
    }

    private static <Vertex extends BaseVertex>
    int findSet(ArrayList<ArrayList<Vertex>> aav, Vertex v) {
        int i = -1;
        for (ArrayList<Vertex> set : aav) {
            ++i;
            if (set.contains(v))
                return i;
        }

        throw new RuntimeException("Element is not present in any subset.");
    }

    public void doAlgorithm() {
//        BaseGraphRequest gr = new BaseGraphRequest();
//        dispatchEvent(gr);
//        BaseGraph<BaseVertex, BaseEdge<BaseVertex>> graph = gr.getGraph();
//
//        Kruskal.findMinimumSpanningTree(graph);
    }


}

