// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.library.algorithms.spanningtree;

import graphtea.library.BaseEdge;
import graphtea.library.BaseGraph;
import graphtea.library.BaseVertex;
import graphtea.library.algorithms.Algorithm;
import graphtea.library.algorithms.AutomatedAlgorithm;
import graphtea.library.event.typedef.BaseGraphRequest;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Omid Aladini
 */
public class Kruskal extends Algorithm implements AutomatedAlgorithm {
    public static <VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
    AbstractList<EdgeType>
    findMinimumSpanningTree(BaseGraph<VertexType, EdgeType> graph) {
        ArrayList<EdgeType> outputEdges = new ArrayList<>();
        ArrayList<ArrayList<VertexType>> sets = new ArrayList<>();

        for (VertexType v : graph) {
            ArrayList<VertexType> set = new ArrayList<>();
            set.add(v);
            sets.add(set);
        }

        ArrayList<EdgeType> edges = new ArrayList<>(graph.getEdgesCount());

        Iterator<EdgeType> eit = graph.edgeIterator();
        while (eit.hasNext())
            edges.add(eit.next());

        edges.sort((o1, o2) -> {
            if (o1.getWeight() < o2.getWeight())
                return -1;
            else if (o1.getWeight() == o2.getWeight())
                return 0;
            return 1;
        });

        for (EdgeType e : edges)
            System.out.print(e.getWeight() + " ");
        System.out.println();

        for (EdgeType e : edges) {
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

    private static <VertexType extends BaseVertex>
    int findSet(ArrayList<ArrayList<VertexType>> aav, VertexType v) {
        int i = -1;
        for (ArrayList<VertexType> set : aav) {
            ++i;
            if (set.contains(v))
                return i;
        }

        throw new RuntimeException("Element is not present in any subset.");
    }

    public void doAlgorithm() {
        BaseGraphRequest gr = new BaseGraphRequest();
        dispatchEvent(gr);
        BaseGraph<BaseVertex, BaseEdge<BaseVertex>> graph = gr.getGraph();

        Kruskal.findMinimumSpanningTree(graph);
    }


}

