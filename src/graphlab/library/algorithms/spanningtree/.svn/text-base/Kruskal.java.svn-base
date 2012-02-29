// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphlab.library.algorithms.spanningtree;

import graphlab.library.BaseEdge;
import graphlab.library.BaseGraph;
import graphlab.library.BaseVertex;
import graphlab.library.algorithms.Algorithm;
import graphlab.library.algorithms.AutomatedAlgorithm;
import graphlab.library.event.typedef.BaseGraphRequest;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

/**
 * @author Omid Aladini
 */
public class Kruskal extends Algorithm implements AutomatedAlgorithm {
    public static <VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
    AbstractList<EdgeType>
    findMinimumSpanningTree(BaseGraph<VertexType, EdgeType> graph) {
        ArrayList<EdgeType> outputEdges = new ArrayList<EdgeType>();
        ArrayList<ArrayList<VertexType>> sets = new ArrayList<ArrayList<VertexType>>();

        for (VertexType v : graph) {
            ArrayList<VertexType> set = new ArrayList<VertexType>();
            set.add(v);
            sets.add(set);
        }

        ArrayList<EdgeType> edges = new ArrayList<EdgeType>(graph.getEdgesCount());

        Iterator<EdgeType> eit = graph.edgeIterator();
        while (eit.hasNext())
            edges.add(eit.next());

        java.util.Collections.sort(edges, new Comparator<EdgeType>() {
            public int compare(EdgeType o1, EdgeType o2) {
                if (o1.getWeight() < o2.getWeight())
                    return -1;
                else if (o1.getWeight() == o2.getWeight())
                    return 0;
                return 1;
            }
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

