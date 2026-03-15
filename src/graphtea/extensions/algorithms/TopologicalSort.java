// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.extensions.algorithms;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.library.algorithms.Algorithm;
import graphtea.library.algorithms.AutomatedAlgorithm;
import graphtea.library.algorithms.LibraryUtils;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Omid Aladini
 */
public class TopologicalSort extends Algorithm implements AutomatedAlgorithm {
    public static AbstractList<Vertex>
    doSort(GraphModel graph) {
        ArrayList<Vertex> alv = new ArrayList<>();
        ArrayList<Vertex> out = new ArrayList<>();

        LibraryUtils.falsifyEdgeMarks(graph);

        for (Vertex v : graph)
            if (graph.getInDegree(v) == 0)
                alv.add(v);

        while (alv.size() != 0) {
            Vertex v = alv.remove(0);
            out.add(v);

            Iterator<Edge> iet;
            iet = graph.edgeIterator(v, true);
            while (iet.hasNext()) {
                Edge e = iet.next();
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
    }


}

