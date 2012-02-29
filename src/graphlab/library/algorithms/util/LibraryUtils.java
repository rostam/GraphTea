// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphlab.library.algorithms.util;

import graphlab.library.BaseEdge;
import graphlab.library.BaseGraph;
import graphlab.library.BaseVertex;
import graphlab.library.algorithms.Algorithm;
import graphlab.library.algorithms.goperators.EdgeInduced;
import graphlab.library.algorithms.goperators.GraphComplement;
import graphlab.library.algorithms.goperators.GraphUnion;
import graphlab.library.algorithms.goperators.VertexInduced;

import java.util.Collection;
import java.util.Iterator;

/**
 * @see graphlab.plugins.main.core.AlgorithmUtils
 */
public class LibraryUtils {

    public static <VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
    boolean falsifyEdgeMarks(BaseGraph<VertexType, EdgeType> g) {
        boolean flag = false;
        EdgeType e;
        Iterator<EdgeType> iet = g.edgeIterator();
        while (iet.hasNext()) {
            e = iet.next();
            flag |= e.getMark();
            e.setMark(false);
        }
        return flag;
    }

    public static <VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
    boolean falsifyVertexMarks(BaseGraph<VertexType, EdgeType> g) {
        boolean flag = false;
        for (VertexType v : g) {
            flag |= v.getMark();
            v.setMark(false);
        }
        return flag;
    }

    /**
     * @see graphlab.library.algorithms.goperators.GraphComplement#complement(graphlab.library.BaseGraph)
     */
    public static <VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
    BaseGraph<VertexType, EdgeType>
    complement(BaseGraph<VertexType, EdgeType> g1) {
        return GraphComplement.complement(g1);
    }

    /**
     * @see graphlab.library.algorithms.goperators.EdgeInduced#edgeInduced(graphlab.library.BaseGraph,java.util.Collection)
     */
    public static <VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
    BaseGraph<VertexType, EdgeType> edgeInduced(BaseGraph<VertexType, EdgeType> g, Collection<EdgeType> S) {
        return EdgeInduced.edgeInduced(g, S);
    }

    /**
     * @see graphlab.library.algorithms.goperators.VertexInduced#induced(graphlab.library.BaseGraph,java.util.Collection)
     */
    public static <VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
    BaseGraph<VertexType, EdgeType> induced(BaseGraph<VertexType, EdgeType> g, Collection<VertexType> S) {
        return VertexInduced.induced(g, S);
    }

    /**
     * @see graphlab.library.algorithms.goperators.GraphUnion#join(graphlab.library.BaseGraph,graphlab.library.BaseGraph)
     */
    public static <VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
    BaseGraph<VertexType, EdgeType> join(BaseGraph<VertexType, EdgeType> g1, BaseGraph<VertexType, EdgeType> g2) {
        return GraphUnion.union(g1, g2);
    }

    /**
     * @see graphlab.library.algorithms.util.EventUtils#algorithmStep(graphlab.library.algorithms.Algorithm,String)
     */
    public static void algorithmStep(Algorithm a, String message) {
        EventUtils.algorithmStep(a, message);
    }

    /**
     * returns all vertex marks in a array
     */
    public static <VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
    boolean[] getVertexMarks(BaseGraph<VertexType, EdgeType> g) {
        boolean ret[] = new boolean[g.getVerticesCount()];
        int i = 0;
        for (BaseVertex v : g) {
            ret[i++] = v.getMark();
        }
        return ret;
    }

    /**
     * sets all the vertex marks
     */
    public static <VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
    void setVertexMarks(BaseGraph<VertexType, EdgeType> g, boolean verexMarks[]) {
        int i = 0;
        for (BaseVertex v : g) {
            v.setMark(verexMarks[i++]);
        }
    }

}