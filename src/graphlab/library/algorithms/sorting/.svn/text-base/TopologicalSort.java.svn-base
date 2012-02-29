// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphlab.library.algorithms.sorting;

import graphlab.library.BaseEdge;
import graphlab.library.BaseGraph;
import graphlab.library.BaseVertex;
import graphlab.library.algorithms.Algorithm;
import graphlab.library.algorithms.AutomatedAlgorithm;
import graphlab.library.algorithms.util.LibraryUtils;
import graphlab.library.event.MessageEvent;
import graphlab.library.event.typedef.BaseGraphRequest;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Omid Aladini
 */
public class TopologicalSort extends Algorithm implements AutomatedAlgorithm {
    public static <VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
    AbstractList<VertexType>
    doSort(BaseGraph<VertexType, EdgeType> graph) {
        ArrayList<VertexType> alv = new ArrayList<VertexType>();
        ArrayList<VertexType> out = new ArrayList<VertexType>();

        LibraryUtils.falsifyEdgeMarks(graph);

        for (VertexType v : graph)
            if (graph.getInDegree(v) == 0)
                alv.add(v);

        while (alv.size() != 0) {
            VertexType v = alv.remove(0);
            out.add(v);

            Iterator<EdgeType> iet = graph.edgeIterator(v, true);
            while (iet.hasNext()) {
                EdgeType e = iet.next();
                /*
                    if(e.getMark())
                        continue;

                    e.setMark(true);


                    if(alv.contains(e.target))
                        continue;
                    */
                if (graph.getInDegree(e.target) == 1)
                    alv.add(e.target);
            }
        }

        if (LibraryUtils.falsifyEdgeMarks(graph))
            return null;
        else
            return out;
    }

    public void doAlgorithm() {
        BaseGraphRequest gr = new BaseGraphRequest();
        dispatchEvent(gr);
        BaseGraph<BaseVertex, BaseEdge<BaseVertex>> graph = gr.getGraph();
        AbstractList<BaseVertex> alv = doSort(graph);
        if (alv == null)
            dispatchEvent(new MessageEvent("Graph has a cycle"));
        else {
            String s = "Topological sort sequence:";
            for (BaseVertex v : alv)
                s += v.getId() + ',';

            dispatchEvent(new MessageEvent(s));
        }

    }


}

