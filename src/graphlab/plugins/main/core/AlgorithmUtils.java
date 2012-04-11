// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.main.core;

import graphlab.graph.graph.GraphPoint;
import graphlab.graph.graph.Vertex;
import graphlab.library.BaseEdge;
import graphlab.library.BaseGraph;
import graphlab.library.BaseVertex;
import graphlab.library.Path;
import graphlab.library.algorithms.util.LibraryUtils;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

/**
 * Just some methods helping you to write Graph Algorithms easier,
 *
 * @see graphlab.library.algorithms.util.LibraryUtils
 */
public class AlgorithmUtils {
    public final static int Max_Int = 2100000000;


    /**
     * sets all vertex colors to 0.
     */
    public static void resetVertexColors(BaseGraph<BaseVertex, BaseEdge<BaseVertex>> g) {
        for (BaseVertex v : g) {
            v.setColor(0);
        }
    }

    /**
     * sets all vertex marks to false
     */
    public static void resetVertexMarks(BaseGraph<BaseVertex, BaseEdge<BaseVertex>> g) {
        for (BaseVertex v : g) {
            v.setMark(false);
        }
    }

    /**
     * determines wether g is connected or not
     */
    public static <VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
    boolean isConnected(BaseGraph<VertexType, EdgeType> g) {
        ArrayList vs = new ArrayList();
        int[] parent = new int[g.getVerticesCount()];
        dfs(g, 0, vs, parent);
        return vs.size() == g.getVerticesCount();
    }

    /**
     * determines wether g is complete or not
     */
    public static <VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
    boolean isCompleteGraph(BaseGraph<VertexType, EdgeType> g) {
        int size = g.getVerticesCount();
        for (int i : getDegreesList(g))
            if (i != size - 1)
                return false;
        return true;
    }

    /**
     * returns the adjacency list of g.
     *
     * @deprecated use BaseGraph.getEdgeArray instead
     */
    public static <VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
    ArrayList<ArrayList<Integer>> getAdjList(BaseGraph<VertexType, EdgeType> g) {
        double[][] mat = g.getAdjacencyMatrix().getArray();
        ArrayList<ArrayList<Integer>> alist = new ArrayList();
        int vCount = mat.length;
        for (int i = 0; i < vCount; i++) {
            ArrayList<Integer> adjacencyList = new ArrayList();
            for (int j = 0; j < vCount; j++)
                if (mat[i][j] == 1)
                    adjacencyList.add(j);
            alist.add(adjacencyList);
        }
        return alist;
    }

    /**
     * returns the degree of the node with the id
     * @deprecated

      * @see graphlab.library.BaseGraph#getDegree(graphlab.library.BaseVertex)
     */
    public static <VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
    int getDegree(BaseGraph<VertexType, EdgeType> bg, int node) {
        int result = 0;
        double[][] mat = bg.getAdjacencyMatrix().getArray();
        int vCount = mat.length;
        if (node > vCount)
            return -1;
        for (int i = 0; i < vCount; i++)
            if (mat[node][i] == 1)
                result++;

        return result;

    }

    /**
     * returns all neighbors of the given vertex
     * @deprecated
     * @see graphlab.library.BaseGraph#getNeighbors(graphlab.library.BaseVertex)
     */
    public static <VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
    ArrayList<VertexType> getNeighbors(BaseGraph<VertexType, EdgeType> g, VertexType source) {
        ArrayList<VertexType> ret = new ArrayList<VertexType>();
        Iterator<EdgeType> ie = g.edgeIterator(source);
        while (ie.hasNext()) {
            EdgeType e = ie.next();
            if (e.target == source && !ret.contains(e.source))
                ret.add(e.source);
            if (e.source == source && !ret.contains(e.target))
                ret.add(e.target);
        }
        return ret;
    }

    /**
     * returns all neighbors of the given vertex
     * @deprecated
     * @see graphlab.library.BaseGraph#getNeighbors(graphlab.library.BaseVertex)
     */
    public static <VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
    ArrayList<VertexType> getNeighbors2(BaseGraph<VertexType, EdgeType> g, VertexType source) {
        ArrayList<VertexType> ret = new ArrayList<VertexType>();
        Iterator<EdgeType> ie = g.edgeIterator(source);
        while (ie.hasNext()) {
            EdgeType e = ie.next();
//            if (e.target == source && !ret.contains(e.source))
//                ret.add(e.source);
            if (e.source == source && !ret.contains(e.target))
                ret.add(e.target);
        }
        return ret;
    }

    /**
     * returns a path from source to target
     * path.get(0) = dest
     */
    public static <VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
    Path<VertexType> getPath(BaseGraph<VertexType, EdgeType> g, VertexType source, VertexType dest) {
        boolean vertexMarksBackup[] = LibraryUtils.getVertexMarks(g);
        clearVertexMarks(g);
        Vector<VertexType> q = new Vector<VertexType>();
        q.add(source);
        source.setMark(true);

        BaseVertex[] parents = new BaseVertex[g.getVerticesCount()];
        boolean found = false;
        while (!q.isEmpty() && !found) {
            VertexType v = q.remove(0);

            for (VertexType _ : getNeighbors(g, v)) {
                if (_ == dest) {
                    found = true;
//                    break;
                }
                if (!_.getMark()) {
                    q.add(_);
                    _.setMark(true);
                    parents[_.getId()] = v;
                }
            }
        }

        if (!found) {
            return null;
        }

        //extract the path
        Path<VertexType> ret = new Path<VertexType>();

        int _ = dest.getId();
        ret.insert(dest);
        while (_ != source.getId()) {
//            System.out.println("_=" + _);

            ret.insert((VertexType) parents[_]);
            if (parents[_] == null)
                return null;
            _ = parents[_].getId();
        }
        LibraryUtils.setVertexMarks(g, vertexMarksBackup);
        return ret;
    }

    /**
     * returns the parent of v, if ve DFS on parent
     */
    public static <VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
    VertexType getParent(BaseGraph<VertexType, EdgeType> g, VertexType treeRoot, VertexType v) {
        return getPath(g, treeRoot, v).get(1);
    }

    /**
     * clears all vertex marks
     */
    public static <VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
    void clearVertexMarks(BaseGraph<VertexType, EdgeType> g) {
        for (VertexType _ : g) {
            _.setMark(false);
        }
    }

    /**
     * returns the subtree rooted by subTreeRoot in the rooted tree tree with the root treeRoot
     * the vertices are ordered by their distances to subTreeRoot
     * the exact distance is placed in v.getProp().obj as an Integer, starting distance is 0 which is subTreeRoot
     */
    public static <Vertex extends BaseVertex, Edge extends BaseEdge<Vertex>>
    ArrayList<Vertex> getSubTree(BaseGraph<Vertex, Edge> tree, Vertex treeRoot, Vertex subTreeRoot) {
        boolean vertexMarksBackup[] = LibraryUtils.getVertexMarks(tree);
        Path<Vertex> pathToRoot = getPath(tree, treeRoot, subTreeRoot);

        clearVertexMarks(tree);

//close the path to tree root
        for (Vertex _ : pathToRoot) {
            _.setMark(true);
        }
        ArrayList<Vertex> ret = BFS(tree, subTreeRoot, null);
        LibraryUtils.setVertexMarks(tree, vertexMarksBackup);

        return ret;

    }

    /**
     * gets the vertices in the order of AlgorithmUtils.getSubTree()
     */
    public static <Vertex extends BaseVertex, Edge extends BaseEdge<Vertex>>
    ArrayList<Vertex> BFSOrder(BaseGraph<Vertex, Edge> unRootedTree, Vertex treeRoot) {
        boolean vertexMarksBackup[] = LibraryUtils.getVertexMarks(unRootedTree);
        clearVertexMarks(unRootedTree);
        ArrayList<Vertex> ret = BFS(unRootedTree, treeRoot, null);
        LibraryUtils.setVertexMarks(unRootedTree, vertexMarksBackup);
        return ret;
    }

    /**
     * runs a BFS on graph, starting the given vertex as the root
     */
    public static <Vertex extends BaseVertex, Edge extends BaseEdge<Vertex>>
    void BFSrun(BaseGraph<Vertex, Edge> unRootedTree, Vertex treeRoot, BFSListener<Vertex> listener) {
        boolean vertexMarksBackup[] = LibraryUtils.getVertexMarks(unRootedTree);
        clearVertexMarks(unRootedTree);
        BFS(unRootedTree, treeRoot, listener);
        LibraryUtils.setVertexMarks(unRootedTree, vertexMarksBackup);
    }

    /**
     * performs a full BFS on graph, it selects the vertices with minimum degrees as the
     * roots of the resulting forest
     *
     * @param unRootedTree
     * @param listener
     * @return
     */
    public static <Vertex extends BaseVertex, Edge extends BaseEdge<Vertex>>
    void BFS(BaseGraph<Vertex, Edge> unRootedTree, BFSListener<Vertex> listener) {
        boolean vertexMarksBackup[] = LibraryUtils.getVertexMarks(unRootedTree);
        clearVertexMarks(unRootedTree);
        for (Vertex v : unRootedTree) {
            if (!v.getMark()) {
                BFS(unRootedTree, v, listener);
            }
        }
        LibraryUtils.setVertexMarks(unRootedTree, vertexMarksBackup);
    }

    /**
     * performs a bfs on the given root,
     * this method changes vertex marks, and also marked vertices will not be traversed
     *
     * @param unRootedTree
     * @param treeRoot
     * @param listener
     * @return
     */
    public static <Vertex extends BaseVertex, Edge extends BaseEdge<Vertex>>
    ArrayList<Vertex> BFS(BaseGraph<Vertex, Edge> unRootedTree, Vertex treeRoot, BFSListener<Vertex> listener) {
        //do a bfs on the subTreeRoot
        ArrayList<Vertex> q = new ArrayList<Vertex>();
        ArrayList<Vertex> ret = new ArrayList<Vertex>();
        q.add(treeRoot);
        ret.add(treeRoot);
        treeRoot.setMark(true);
        treeRoot.getProp().obj = 0;
        while (!q.isEmpty()) {
            Vertex v = q.remove(0);
            for (Vertex _ : unRootedTree.getNeighbors(v)) {
                if (!_.getMark()) {
                    q.add(_);
                    ret.add(_);
                    _.setMark(true);
                    _.getProp().obj = ((Integer) v.getProp().obj) + 1;  //set the distance
                    if (listener != null)
                        listener.visit(_, v);
                }
            }
        }
        return ret;
    }


    /**
     * runs a dfs and fills visit and parent, visit is the visiting order of vertices and parent[i] is the id of i'th vertex parent
     */
    public static <VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
    void dfs(BaseGraph<VertexType, EdgeType> g,
             int node, ArrayList visit, int parent[]) {
        visit.add(node);
        ArrayList e = getAdjList(g);
        ArrayList neighbors = (ArrayList) e.get(node);
        for (Object neighbor1 : neighbors) {
            int neighbor = (Integer) neighbor1;
            if (parent[neighbor] == -1) {
                parent[neighbor] = node;
                dfs(g, neighbor, visit, parent);
            }
        }
    }

    /**
     * retunrs the degree of vertex (indegree + outdegree)
     */
    public static int getTotalDegree(BaseGraph g, BaseVertex v) {
        return g.getOutDegree(v) + g.getInDegree(v);
    }

    /**
     * returns the root which is assigned to each vertex
     * it is the minimum id vertex in the corresponding component of vertex
     */
    public static <Vertex extends BaseVertex, Edge extends BaseEdge<Vertex>>
    Vertex getRoot(BaseGraph<Vertex, Edge> g, Vertex v) {
        ArrayList<Vertex> componentVertices = BFSOrder(g, v);
        Vertex rootCandidate = v;

        for (Vertex _ : componentVertices) {
            if (_.getId() < v.getId()) {
                rootCandidate = _;
            }

        }

        return rootCandidate;

    }

    /**
     * returns the angle between 3 vertices in graphical world!
     */
    public static double getAngle(Vertex root, Vertex v1, Vertex v2) {

        GraphPoint rootp = root.getLocation();
        GraphPoint v1p = v1.getLocation();
        GraphPoint v2p = v2.getLocation();

        return getAngle(rootp, v1p, v2p);
    }

    /**
     * returns the angle between 3 points
     */
    public static double getAngle(GraphPoint rootp, GraphPoint v1p, GraphPoint v2p) {
        double px = v1p.x - rootp.x;
        double py = v1p.y - rootp.y;
        double qx = v2p.x - rootp.x;
        double qy = v2p.y - rootp.y;


        double pDOTq = px * qx + py * qy;
        double plength = getLength(px, py);
        double qlength = getLength(qx, qy);

        double cartesianProd = py * qx - px * qy;
        if (plength == 0 || qlength == 0)
            return 0;
        else {
            double alfacos = pDOTq / (plength * qlength);
            double alfa = Math.acos(alfacos);
//            return (Math.signum(cartesianProd) < 0 ? 2 * Math.PI - Math.acos(alfacos) : Math.acos(alfacos));
            return alfa;
        }
    }

    /**
     * returns the length of the given vector
     */
    public static double getLength(double dx, double dy) {
        return Point2D.Double.distance(0, 0, dx, dy);
    }

    /**
     * moves the vertex relative to its current position
     */
    public static void move(Vertex v, double dx, double dy) {
        GraphPoint loc = v.getLocation();
        v.setLocation(new GraphPoint(loc.x + dx, loc.y + dy));
    }

    /**
     * returns the distance between two vertices in pixels, (in graphics not the path length between them)
     */
    public static double getDistance(Vertex v1, Vertex v2) {
        return GraphPoint.distance(v1.getLocation().x, v1.getLocation().y, v2.getLocation().x, v2.getLocation().y);

    }

    /**
     * returns the distance between two points
     */
    public static double getDistance(GraphPoint p1, GraphPoint p2) {
        return GraphPoint.distance(p1.x, p1.y, p2.x, p2.y);

    }

    /**
     * @return the angle between vector p2-p1 and X-Axis
     */
    public static double getAngle(GraphPoint p1, GraphPoint p2) {
        double angle = Math.atan2(p1.y - p2.y,
                p1.x - p2.x);
        if (angle < 0) {
            // atan2 returns getAngle in phase -pi to pi, which means
            // we have to convert the answer into 0 to 2pi range.
            angle += 2 * Math.PI;
        }
        return angle;
    }

    /**
     * locations v in a r-teta cordination
     */
    public static void setLocation(Vertex v, GraphPoint center, double radius, double ang) {
        v.setLocation(new GraphPoint(center.x + radius * Math.cos(ang), center.y + radius * Math.sin(ang)));
    }

    /**
     * @return the bounding rectangle arround vertices
     */
    public static Rectangle2D.Double getBoundingRegion(Collection<Vertex> vertices) {
        Rectangle2D.Double ret = new Rectangle2D.Double();
        boolean first = true;
        for (Vertex v : vertices) {
            GraphPoint p = v.getLocation();
            if (first) {
                ret = new Rectangle2D.Double(p.x, p.y, 0, 0);
                first = false;
            }
            ret.add(p);
        }
        return ret;
    }

    public static GraphPoint getCenter(Collection<Vertex> V) {
        GraphPoint center = new GraphPoint(0, 0);
        for (Vertex v : V) {
            GraphPoint loc = v.getLocation();
            center.x += loc.x;
            center.y += loc.y;
        }
        center.x = center.x / V.size();
        center.y = center.y / V.size();
        return center;
    }

    /**
     * returns the vertex degrees as a list, sorted by vertex ids
     */
    public static <VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
    ArrayList<Integer> getDegreesList(BaseGraph<VertexType, EdgeType> g) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        int vCount = g.getVertexArray().length;
        for (int i = 0; i < vCount; i++)
            result.add(getDegree(g, i));
        return result;
    }

    public interface BFSListener<Vertex extends BaseVertex> {
        public void visit(Vertex v, Vertex parent);
    }

    /**
     * @param p1
     * @param p2
     * @return a point whose x and y are average of the given graph points.
     */
    public static GraphPoint getMiddlePoint(GraphPoint p1, GraphPoint p2) {
        return new GraphPoint((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
    }

    public static GraphPoint normalize(GraphPoint vector) {
        double size = Math.sqrt(Math.pow(vector.x, 2) + Math.pow(vector.y, 2));
        GraphPoint ret = new GraphPoint(vector);
        if (size != 0)
            ret.multiply(1 / size);
        return ret;
    }

}
