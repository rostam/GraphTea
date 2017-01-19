// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports;

import graphtea.graph.graph.Vertex;
import graphtea.library.BaseEdge;
import graphtea.library.BaseGraph;
import graphtea.library.BaseVertex;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Iterator;

/**
 * generates all partitionings of the given graph with t or less partitions
 * which in each partition does not have any edges
 *
 * @author Azin Azadi
 */
public class Partitioner {
    public BaseVertex[] vertices;
    private int[][] edgeArray;
    public int[] color;
    private BaseGraph g;


    public Partitioner(final BaseGraph g) {
        this.g = g;
        edgeArray = g.getEdgeArray();
        vertices = g.getVertexArray();
    }

    public boolean findAllSubsets(SubSetListener listener) {
        color = new int[vertices.length];
        ArrayDeque<BaseVertex> v = new ArrayDeque<>();
        Collections.addAll(v, g.getVertexArray());
        return findAllSubsetsRecursively(1, listener, v, new ArrayDeque<>(), new ArrayDeque<>());
    }

    public int findMaxIndSet(boolean putFirstVertexInSet) {
        color = new int[vertices.length];

        v = new ArrayDeque<>();
        set = new boolean[g.getVerticesCount()];
        int i = 0;
        for (BaseVertex baseVertx : g.getVertexArray()) {
            v.add(baseVertx);
            set[i++] = false;
        }
        maxSet = Integer.MIN_VALUE;
        curSet = 0;
        if (putFirstVertexInSet) {
            curSet = 1;
            set[0] = true;
            v.remove(vertices[0]);
            for (int ii : edgeArray[0]) {
                v.remove(vertices[ii]);
            }
        }

        V = new Vertex[v.size()];
        N = v.size();
        v.toArray(V);

        ID = new int[vertices.length];
        for (i = 0; i < vertices.length; i++) {
            ID[i] = vertices[i].getId();
        }
        mark = new boolean[vertices.length];
        found = 0;
        findMaxIndSetsRecursively(0);
        return maxSet;
    }

    boolean set[];
    boolean mark[];
//    boolean markbck[];

    Vertex[] V;
    int ID[];
    int N;
    ArrayDeque<BaseVertex> v;
    int maxSet = Integer.MIN_VALUE;
    int curSet;
    //    int curRejectedFromSet;
    //    int minRejectedFromSet;
    int found = 0;
    int iter = 0;

    int i;

    private void findMaxIndSetsRecursively(int iv) {
        while (iv < N && mark[iv]) {
            iv++;
        }
        iter++;
        if (curSet + (N - iv) < maxSet) {
            return;
        }
        if (iv >= N) {
            if (curSet > maxSet) {
                maxSet = curSet;
                found++;
            }
            return;
        }

        int vid = V[iv].getId();
        //set and backup marks
        int[] nv = edgeArray[vid];
        int ss = nv.length;

        boolean markbck[] = new boolean[ss];
        for (i = 0; i < ss; i++) {
            markbck[i] = mark[nv[i]];
            mark[nv[i]] = true;
        }

        //put vid in set
        int iiv = iv + 1;
        while (iiv < N && mark[iiv]) {
            iiv++;
        }

        set[vid] = true;
        curSet++;
        /*****************************/
        findMaxIndSetsRecursively(iiv);
        /*****************************/
        curSet--;
        set[vid] = false;


        for (i = 0; i < ss; i++) {
            mark[nv[i]] = markbck[i];
        }
        //BackTrak on complement
        iiv = iv + 1;
        while (iiv < N && mark[iiv]) {
            iiv++;
        }
        /*****************************/
        findMaxIndSetsRecursively(iiv);
        /*****************************/
    }


    public boolean findAllPartitionings(final int t, final ColoringListener listener) {
        color = new int[vertices.length];

        ArrayDeque<BaseVertex> v = new ArrayDeque<>();
        for (BaseVertex baseVertex : g.getVertexArray()) {
            v.add(baseVertex);
        }
        if (findAllPartitioningsRecursively(t, t1 -> {
            if (checkColoring(g)) {
                boolean b = listener.coloringFound(t1);
                return b;
            }
            return false;
        }, v)) return true;
        return false;
    }

    public boolean findAllPartitioningsRecursively(final int tt, final ColoringListener listener, final ArrayDeque<BaseVertex> v) {
        if (tt == 0 || v.size() == 0) {
            return listener.coloringFound(tt);
        }
        BaseVertex fv = v.removeFirst();
        color[fv.getId()] = tt;
        ArrayDeque<BaseVertex> compl = new ArrayDeque<>();
        if (findAllSubsetsRecursively(tt, (t, complement, set1) -> findAllPartitioningsRecursively(tt - 1, listener, complement)
                , v, new ArrayDeque<>(), compl)) return true;
        color[fv.getId()] = 0;
        v.addFirst(fv);
        return false;
    }

    private boolean findAllSubsetsRecursively(final int t, SubSetListener listener, ArrayDeque<BaseVertex> v, ArrayDeque<BaseVertex> set, ArrayDeque<BaseVertex> complement) {
        if (t == 0 || v.size() == 0) {
            //all colorings of valid and checked before except the remaining vertices which all have color 0
            for (BaseVertex baseVertex : v) {
                for (int i : edgeArray[baseVertex.getId()])
                    if (color[i] == 0)
                        return false;
            }
            //so it is a valid partitioning (coloring)
            return listener.subsetFound(t, complement, set);
        }
        BaseVertex baseVertex = v.removeFirst();

        boolean canColoredT = true;
        for (int i : edgeArray[baseVertex.getId()]) {
            if (color[i] == t) {
                canColoredT = false;
                break;
            }
        }
        if (canColoredT) {
            color[baseVertex.getId()] = t;
            set.add(baseVertex);
            if (findAllSubsetsRecursively(t, listener, v, set, complement)) return true;
            set.remove(baseVertex);
        }

        color[baseVertex.getId()] = 0;
        complement.add(baseVertex);
        if (findAllSubsetsRecursively(t, listener, v, set, complement)) return true;
        complement.remove(baseVertex);
        v.addFirst(baseVertex);
        return false;
    }

    /**
     * @param g The given graph
     * @return true if the coloring of g is a valid vertex coloring
     */
    public boolean checkColoring(BaseGraph g) {
        Iterator<BaseEdge> ie = g.edgeIterator();
        while (ie.hasNext()) {
            BaseEdge e = ie.next();
            if (color[e.source.getId()] == color[e.target.getId()]) {
                return false;
            }
        }
        return true;
    }

}
