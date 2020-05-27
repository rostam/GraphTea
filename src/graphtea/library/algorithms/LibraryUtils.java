// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.library.algorithms;

import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.algorithms.EdgeInduced;
import graphtea.extensions.algorithms.GraphComplement;
import graphtea.extensions.algorithms.GraphUnion;
import graphtea.extensions.algorithms.VertexInduced;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.library.BaseEdge;
import graphtea.library.BaseVertex;
import graphtea.library.algorithms.util.EventUtils;

import java.util.Collection;
import java.util.Iterator;

/**
 * @see AlgorithmUtils
 */
public class LibraryUtils {

    public static boolean falsifyEdgeMarks(GraphModel g) {
        boolean flag = false;
        Edge e;
        Iterator<Edge> iet = g.edgeIterator();
        while (iet.hasNext()) {
            e = iet.next();
            flag |= e.getMark();
            e.setMark(false);
        }
        return flag;
    }

    public static boolean falsifyVertexMarks(GraphModel g) {
        boolean flag = false;
        for (Vertex v : g) {
            flag |= v.getMark();
            v.setMark(false);
        }
        return flag;
    }

    /**
     * @see GraphComplement#complement(GraphModel)
     */
    public static GraphModel complement(GraphModel g1) {
        return GraphComplement.complement(g1);
    }

    /**
     *
     * @param g
     * @param S
     * @return
     */
    public static GraphModel edgeInduced(GraphModel g, Collection<Edge> S) {
        return EdgeInduced.edgeInduced(g, S);
    }

    /**
     *
     * @param g
     * @param S
     * @return
     */
    public static GraphModel induced(GraphModel g, Collection<Vertex> S) {
        return VertexInduced.induced(g, S);
    }

    /**
     *
     */
    public static GraphModel join(GraphModel g1, GraphModel g2) {
        return GraphUnion.union(g1, g2);
    }

    /**
     * @see graphtea.library.algorithms.util.EventUtils#algorithmStep(graphtea.library.algorithms.Algorithm,String)
     */
    public static void algorithmStep(Algorithm a, String message) {
        EventUtils.algorithmStep(a, message);
    }

    /**
     * returns all vertex marks in a array
     */
    public static <Vertex extends BaseVertex, Edge extends BaseEdge<Vertex>>
    boolean[] getVertexMarks(GraphModel g) {
        boolean[] ret = new boolean[g.getVerticesCount()];
        int i = 0;
        for (BaseVertex v : g) {
            ret[i++] = v.getMark();
        }
        return ret;
    }

    /**
     * sets all the vertex marks
     */
    public static <Vertex extends BaseVertex, Edge extends BaseEdge<Vertex>>
    void setVertexMarks(GraphModel g, boolean[] verexMarks) {
        int i = 0;
        for (BaseVertex v : g) {
            v.setMark(verexMarks[i++]);
        }
    }



}