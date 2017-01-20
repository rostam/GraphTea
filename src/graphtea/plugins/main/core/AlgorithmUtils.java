// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.main.core;

import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.GRect;
import graphtea.graph.graph.Vertex;
import graphtea.library.BaseEdge;
import graphtea.library.BaseGraph;
import graphtea.library.BaseVertex;
import graphtea.library.Path;
import graphtea.library.algorithms.util.LibraryUtils;
import java.util.*;

/**
 * Just some methods helping you to write Graph Algorithms easier,
 *
 * @see graphtea.library.algorithms.util.LibraryUtils
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
        for(int i=0;i < g.getVerticesCount();i++) parent[i] = -1;
        dfs(g, 0, vs, parent);
        return Arrays.stream(vs.toArray()).distinct().toArray().length == g.getVerticesCount();
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
        ArrayList<ArrayList<Integer>> alist = new ArrayList<>();
        int vCount = mat.length;
        for (int i = 0; i < vCount; i++) {
            ArrayList<Integer> adjacencyList = new ArrayList<>();
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

      * @see graphtea.library.BaseGraph#getDegree(graphtea.library.BaseVertex)
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
     * @see graphtea.library.BaseGraph#getNeighbors(graphtea.library.BaseVertex)
     */
    public static <VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
    ArrayList<VertexType> getNeighbors(BaseGraph<VertexType, EdgeType> g, VertexType source) {
        ArrayList<VertexType> ret = new ArrayList<>();
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
     * @see graphtea.library.BaseGraph#getNeighbors(graphtea.library.BaseVertex)
     */
    public static <VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
    ArrayList<VertexType> getNeighbors2(BaseGraph<VertexType, EdgeType> g, VertexType source) {
        ArrayList<VertexType> ret = new ArrayList<>();
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
        Vector<VertexType> q = new Vector<>();
        q.add(source);
        source.setMark(true);

        BaseVertex[] parents = new BaseVertex[g.getVerticesCount()];
        boolean found = false;
        while (!q.isEmpty() && !found) {
            VertexType v = q.remove(0);

            for (VertexType neigh : getNeighbors(g, v)) {
                if (neigh == dest) {
                    found = true;
//                    break;
                }
                if (!neigh.getMark()) {
                    q.add(neigh);
                    neigh.setMark(true);
                    parents[neigh.getId()] = v;
                }
            }
        }

        if (!found) {
            return null;
        }

        //extract the path
        Path<VertexType> ret = new Path<>();

        int did = dest.getId();
        ret.insert(dest);
        while (did != source.getId()) {
            ret.insert((VertexType) parents[did]);
            if (parents[did] == null)
                return null;
            did = parents[did].getId();
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
        for (VertexType type : g) {
            type.setMark(false);
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
        for (Vertex vertex : pathToRoot) {
            vertex.setMark(true);
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
     * @param unRootedTree An unrooted tree
     * @param listener The listener
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
     * @param unRootedTree An unrooted tree
     * @param treeRoot The tree root
     * @param listener The listener
     * @return The results of the BFS algorithm
     */
    public static <Vertex extends BaseVertex, Edge extends BaseEdge<Vertex>>
    ArrayList<Vertex> BFS(BaseGraph<Vertex, Edge> unRootedTree, Vertex treeRoot, BFSListener<Vertex> listener) {
        //do a bfs on the subTreeRoot
        ArrayList<Vertex> q = new ArrayList<>();
        ArrayList<Vertex> ret = new ArrayList<>();
        q.add(treeRoot);
        ret.add(treeRoot);
        treeRoot.setMark(true);
        treeRoot.getProp().obj = 0;
        while (!q.isEmpty()) {
            Vertex v = q.remove(0);
            for (Vertex vertex : unRootedTree.getNeighbors(v)) {
                if (!vertex.getMark()) {
                    q.add(vertex);
                    ret.add(vertex);
                    vertex.setMark(true);
                    vertex.getProp().obj = ((Integer) v.getProp().obj) + 1;  //set the distance
                    if (listener != null)
                        listener.visit(vertex, v);
                }
            }
        }
        return ret;
    }

    /**
     * runs a dfs and fills visit and parent, visit is the visiting order of vertices and parent[i] is the id of i'th vertex parent
     * the parent array should be initialized by -1
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

        for (Vertex vertex : componentVertices) {
            if (vertex.getId() < v.getId()) {
                rootCandidate = vertex;
            }

        }

        return rootCandidate;

    }

    /**
     * returns the angle between 3 vertices in graphical world!
     */
    public static double getAngle(Vertex root, Vertex v1, Vertex v2) {

        GPoint rootp = root.getLocation();
        GPoint v1p = v1.getLocation();
        GPoint v2p = v2.getLocation();

        return getAngle(rootp, v1p, v2p);
    }

    /**
     * returns the angle between 3 points
     */
    public static double getAngle(GPoint rootp, GPoint v1p, GPoint v2p) {
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
        return GPoint.distance(0,0,dx,dy);
    }

    /**
     * moves the vertex relative to its current position
     */
    public static void move(Vertex v, double dx, double dy) {
        GPoint loc = v.getLocation();
        v.setLocation(new GPoint(loc.x + dx, loc.y + dy));
    }

    /**
     * returns the distance between two vertices in pixels, (in graphics not the path length between them)
     */
    public static double getDistance(Vertex v1, Vertex v2) {
        return GPoint.distance(v1.getLocation().x, v1.getLocation().y, v2.getLocation().x, v2.getLocation().y);

    }

    /**
     * returns the distance between two points
     */
    public static double getDistance(GPoint p1, GPoint p2) {
        return GPoint.distance(p1.x, p1.y, p2.x, p2.y);

    }

    /**
     * @return the angle between vector p2-p1 and X-Axis
     */
    public static double getAngle(GPoint p1, GPoint p2) {
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
    public static void setLocation(Vertex v, GPoint center, double radius, double ang) {
        v.setLocation(new GPoint(center.x + radius * Math.cos(ang), center.y + radius * Math.sin(ang)));
    }

    /**
     * @return the bounding rectangle arround vertices
     */
    public static GRect getBoundingRegion(Collection<Vertex> vertices) {
        GRect ret = new GRect();
        boolean first = true;
        for (Vertex v : vertices) {
            GPoint p = v.getLocation();
            if (first) {
                ret = new GRect(p.x, p.y, 0, 0);
                first = false;
            }
            ret.add(p);
        }
        return ret;
    }

    public static GPoint getCenter(Collection<Vertex> V) {
        GPoint center = new GPoint(0, 0);
        for (Vertex v : V) {
            GPoint loc = v.getLocation();
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
        ArrayList<Integer> result = new ArrayList<>();
        int vCount = g.getVertexArray().length;
        for (int i = 0; i < vCount; i++)
            result.add(getDegree(g, i));
        return result;
    }

    public interface BFSListener<Vertex extends BaseVertex> {
        void visit(Vertex v, Vertex parent);
    }

    /**
     * @param p1 The first point
     * @param p2 The second point
     * @return a point whose x and y are average of the given graph points.
     */
    public static GPoint getMiddlePoint(GPoint p1, GPoint p2) {
        return new GPoint((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
    }

    public static GPoint normalize(GPoint vector) {
        double size = Math.sqrt(Math.pow(vector.x, 2) + Math.pow(vector.y, 2));
        GPoint ret = new GPoint(vector);
        if (size != 0)
            ret.multiply(1 / size);
        return ret;
    }

}
