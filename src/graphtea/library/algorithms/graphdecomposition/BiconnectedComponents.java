// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.library.algorithms.graphdecomposition;

import graphtea.library.BaseEdge;
import graphtea.library.BaseGraph;
import graphtea.library.BaseVertex;
import graphtea.library.algorithms.Algorithm;
import graphtea.library.algorithms.AutomatedAlgorithm;
import graphtea.library.algorithms.util.EventUtils;
import graphtea.library.event.GraphRequest;
import graphtea.library.util.Pair;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;
import java.util.Vector;

/**
 * This Method find the biconnected components of a
 * graph.
 * Which are the maximal subgraphs without any cut vertices.
 *
 * @author Azin Azadi    :returning biconnected compoenents
 * @author Soroush Sabet
 */
public class BiconnectedComponents
        <VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
        extends Algorithm implements AutomatedAlgorithm {
    //PreWorkPostWorkHandler handler = new PreWorkPostWorkHandler<VertexType>();
    Integer[] DFS_Number;
    Integer[] High;
    int[] parent;
    Vector<Pair<Vector<VertexType>, Vector<EdgeType>>> BiC = new Vector<Pair<Vector<VertexType>, Vector<EdgeType>>>();
   Vector<HashSet<VertexType>> ret;
    int DFS_N;
    private VertexType root;

    /**
     * The initialization, before searching for biconnected
     * components.
     * Here, DFS_N is the number assigned to vertices in process
     * of dfs traversal, although in decreasing order.
     * High[v] denotes the highest vertex, reachable from v or
     * it's children in the dfs tree.
     *
     * @param g is the input graph.
     */
    private void init(BaseGraph<VertexType, EdgeType> g) {
        DFS_Number = new Integer[g.getVerticesCount()];
        for (int i = 0; i < g.getVerticesCount(); i++)
            DFS_Number[i] = 0;

        DFS_N = g.getVerticesCount();
        High = new Integer[g.getVerticesCount()];
        parent = new int[g.getVerticesCount()];
    }

    public void doAlgorithm() {
        GraphRequest<VertexType, EdgeType> gr = new GraphRequest<VertexType, EdgeType>();
        dispatchEvent(gr);
        BaseGraph<VertexType, EdgeType> g = gr.getGraph();
        BC(g, root);
//        LibraryUtils.algorithmStep(this, "Biconnected Components:" + comps);
    }

    /**
     * This class is used to put both vertices and edges in
     * a stack.
     */
    private class VE {

        VertexType v;
        EdgeType e;

        VE(VertexType v) {
            this.v = v;
            this.e = null;
        }

        VE(EdgeType e) {
            this.e = e;
            this.v = null;
        }

        VE() {
            this.v = null;
            this.e = null;
        }
    }

    int rootChilds = 0;
    int foundDecompositions;
    Stack<VE> S = new Stack<VE>(); //stack is initially empty

    /**
     * This method is in fact dfs, with some preworks and postworks
     * added to it.
     * It finds biconnected components of the input graph g, by
     * starting search from the node v.
     *
     * @param g
     * @param v
     */
    private void BC(BaseGraph<VertexType, EdgeType> g, VertexType v) {
        DFS_Number[v.getId()] = DFS_N;
        DFS_N--;
        S.push(new VE(v));
        High[v.getId()] = DFS_Number[v.getId()];

        Iterator<EdgeType> iet;
        iet = g.edgeIterator(v);

        EdgeType edge;
        VertexType w;

        while (iet.hasNext()) {
            edge = iet.next();
            w = edge.source == v ? edge.target : edge.source;

            if (parent[v.getId()] != w.getId())
                if (DFS_Number[w.getId()] == 0) { //w not visited before
                    parent[w.getId()] = v.getId();
                    edge.setMark(true);
                    w.setMark(true);
                    EventUtils.algorithmStep(this, "");
                    BC(g, w);
                    w.setMark(false);
                    EventUtils.algorithmStep(this, "");
                    if (High[w.getId()] <= DFS_Number[v.getId()]) { //v disconnects w from the rest of the graph
                        if (v == root) {
                            //the root is an articulation point if and only if it has more than one child in the DFS tree
                            rootChilds++;
                            if (rootChilds == 1)
                                continue;
                        }
                        foundDecompositions++;
                        v.setColor(2);
                    }
                    High[v.getId()] = Math.max(High[v.getId()], High[w.getId()]);
                } else   //(v,w) is a back edge or a forward edge
                    High[v.getId()] = Math.max(High[v.getId()], DFS_Number[w.getId()]);
        }
    }


    public Vector<HashSet<VertexType>> biconnected_components(BaseGraph<VertexType, EdgeType> g, VertexType v, int n) {
        DFS_Number=new Integer[n];
        High=new Integer[n];
        parent=new int[n];
        S = new Stack<VE>();
        ret= new Vector<HashSet<VertexType>>();

        for (VertexType scan : g)
            DFS_Number[scan.getId()] = 0;
        DFS_N = n;
        Bicon(g,v);
        return ret;
    }

    /**
     * This is the main method for developing use, wich take the graph g as
     * the input, and returns the biconnected components
     * of g in a vector.
     */
    public void Bicon(BaseGraph<VertexType, EdgeType> g, VertexType v) {
        DFS_Number[v.getId()] = DFS_N;
        DFS_N--;
        S.push(new VE(v));
        High[v.getId()] = DFS_Number[v.getId()];
        for (VertexType w : g.getNeighbors(v)) {
            EdgeType vw = g.getEdges(v,w).get(0);
            S.push(new VE(vw));
            if (parent[v.getId()]!=w.getId()){
                if (DFS_Number[w.getId()]==0){
                    parent[w.getId()]= v.getId();
                    Bicon(g,w);
                    if (High[w.getId()] <= DFS_Number[v.getId()]){
                        VE top=S.pop();
                        HashSet<VertexType> comp=new HashSet<VertexType>();
                        if (top.v!=null)
                            comp.add(top.v);
                        while (v!=top.v){
                            top = S.pop();
                            if (top.v!=null)
                                comp.add(top.v);
                        }
                        ret.add(comp);
                        S.push(new VE(v));
                    }
                    High[v.getId()] = Math.max(High[v.getId()], High[w.getId()]);

                }
                else {
                    High[v.getId()] = Math.max(High[v.getId()], DFS_Number[w.getId()]);
                }
            }
        }

////    public Vector<BaseGraph<VertexType,EdgeType>> Bicon(BaseGraph<VertexType,EdgeType> g){
//        init(g);
//        rootChilds = 0;
//        root = g.iterator().next();
//
//        foundDecompositions = 0;
//        BC(g, root);
//        Vector<HashSet<VertexType>> ret = new Vector<HashSet<VertexType>>();
//        for (VertexType v : g) {
//            v.setMark(false);
//        }
//        for (VertexType v : g) {
//            if (!v.getMark()) {
//                Queue<VertexType> q = new LinkedList<VertexType>();
//                HashSet<VertexType> r = new HashSet<VertexType>();
//                q.add(v);
//                while (!q.isEmpty()) {
//                    VertexType t = q.poll();
//                    t.setMark(true);
//                    r.add(t);
//                    if (t.getColor() != 2)
//                        for (VertexType scan : g.getNeighbors(t)) {
//                            if (!scan.getMark() || scan.getColor() == 2)
//                                q.add(scan);
//                        }
//                }
//                ret.add(r);
//            }
//        }
//        return ret;
//
    }
}