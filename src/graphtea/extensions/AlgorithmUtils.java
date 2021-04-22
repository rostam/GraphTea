// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import graphtea.extensions.algorithms.shortestpath.algs.FloydWarshall;
import graphtea.graph.graph.*;
import graphtea.library.Path;
import graphtea.library.algorithms.LibraryUtils;

import java.awt.*;
import java.math.BigInteger;
import java.util.*;

/**
 * Just some methods helping you to write Graph Algorithms easier,
 *
 * @see LibraryUtils
 */
public class AlgorithmUtils {
    public final static int Max_Int = 2100000000;


    /**
     * sets all vertex colors to 0.
     */
    public static void resetVertexColors(GraphModel g) {
        for (Vertex v : g) {
            v.setColor(0);
        }
    }

    /**
     * sets all vertex marks to false
     */
    public static void resetVertexMarks(GraphModel g) {
        for (Vertex v : g) {
            v.setMark(false);
        }
    }

    /**
     * determines wether g is connected or not
     */
    public static boolean isConnected(GraphModel g) {
        ArrayList<Integer> vs = new ArrayList<>();
        int[] parent = new int[g.getVerticesCount()];
        for(int i=0;i < g.getVerticesCount();i++) parent[i] = -1;
        dfs(g, 0, vs, parent);
        return Arrays.stream(vs.toArray()).distinct().toArray().length == g.getVerticesCount();
    }

    /**
     * determines wether g is complete or not
     */
    public static 
    boolean isCompleteGraph(GraphModel g) {
        int size = g.getVerticesCount();
        for (int i : getDegreesList(g))
            if (i != size - 1)
                return false;
        return true;
    }

    /**
     * returns a path from source to target
     * path.get(0) = dest
     */
    public static 
    Path<Vertex> getPath(GraphModel g, Vertex source, Vertex dest) {
        boolean[] vertexMarksBackup = LibraryUtils.getVertexMarks(g);
        clearVertexMarks(g);
        Vector<Vertex> q = new Vector<>();
        q.add(source);
        source.setMark(true);

        Vertex[] parents = new Vertex[g.getVerticesCount()];
        boolean found = false;
        while (!q.isEmpty() && !found) {
            Vertex v = q.remove(0);

            for (Vertex neigh : g.directNeighbors(v)) {
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
        Path<Vertex> ret = new Path<>();

        int did = dest.getId();
        ret.insert(dest);
        while (did != source.getId()) {
            ret.insert(parents[did]);
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
    public static Vertex getParent(GraphModel g, Vertex treeRoot, Vertex v) {
        return getPath(g, treeRoot, v).get(1);
    }

    /**
     * clears all vertex marks
     */
    public static 
    void clearVertexMarks(GraphModel g) {
        for (Vertex type : g) {
            type.setMark(false);
        }
    }

    /**
     * returns the subtree rooted by subTreeRoot in the rooted tree tree with the root treeRoot
     * the vertices are ordered by their distances to subTreeRoot
     * the exact distance is placed in v.getProp().obj as an Integer, starting distance is 0 which is subTreeRoot
     */
    public static 
    ArrayList<Vertex> getSubTree(GraphModel tree, Vertex treeRoot, Vertex subTreeRoot) {
        boolean[] vertexMarksBackup = LibraryUtils.getVertexMarks(tree);
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
    public static 
    ArrayList<Vertex> BFSOrder(GraphModel unRootedTree, Vertex treeRoot) {
        boolean[] vertexMarksBackup = LibraryUtils.getVertexMarks(unRootedTree);
        clearVertexMarks(unRootedTree);
        ArrayList<Vertex> ret = BFS(unRootedTree, treeRoot, null);
        LibraryUtils.setVertexMarks(unRootedTree, vertexMarksBackup);
        return ret;
    }

    /**
     * runs a BFS on graph, starting the given vertex as the root
     */
    public static void BFSrun(GraphModel unRootedTree, Vertex treeRoot, BFSListener listener) {
        boolean[] vertexMarksBackup = LibraryUtils.getVertexMarks(unRootedTree);
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
    public static void BFS(GraphModel unRootedTree, BFSListener listener) {
        boolean[] vertexMarksBackup = LibraryUtils.getVertexMarks(unRootedTree);
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
    public static 
    ArrayList<Vertex> BFS(GraphModel unRootedTree, Vertex treeRoot, BFSListener listener) {
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
    public static 
    void dfs(GraphModel g, int node, ArrayList<Integer> visit, int[] parent) {
        visit.add(node);
        int[][] e = g.getEdgeArray();
        for(int neighbor : e[node]) {
            if (parent[neighbor] == -1) {
                parent[neighbor] = node;
                dfs(g, neighbor, visit, parent);
            }
        }
    }

    /**
     * retunrs the degree of vertex (indegree + outdegree)
     */
    public static int getTotalDegree(GraphModel g, Vertex v) {
        return g.getOutDegree(v) + g.getInDegree(v);
    }

    /**
     * returns the root which is assigned to each vertex
     * it is the minimum id vertex in the corresponding component of vertex
     */
    public static 
    Vertex getRoot(GraphModel g, Vertex v) {
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
            return Math.acos(alfacos);
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
    public static 
    ArrayList<Integer> getDegreesList(GraphModel g) {
        ArrayList<Integer> result = new ArrayList<>();
        int vCount = g.getVertexArray().length;
        for (int i = 0; i < vCount; i++)
            result.add(g.getDegree(g.getVertex(i)));
        return result;
    }

    /**
     *
     * @param value the given value
     * @param decimalPlace the decimal place
     * @return the rounded value
     */
    public static double round(double value, int decimalPlace) {
        double power_of_ten = 1;
        while (decimalPlace-- > 0)
            power_of_ten *= 10.0;
        return Math.round(value * power_of_ten)
                / power_of_ten;
    }


    public static double[] round (double[] array, int prec)
    {
        for(int i=0;i<array.length;i++)
            array[i]=round(array[i],prec);
        return array;

    }

    public static String getEigenValues(GraphModel g) {
        Matrix A = g.getWeightedAdjacencyMatrix();
        EigenvalueDecomposition ed = A.eig();
        double[] rv = ed.getRealEigenvalues();
        double[] iv = ed.getImagEigenvalues();
        String res = "";
        for (int i = 0; i < rv.length; i++) {
            if (iv[i] != 0)
                res +="" + AlgorithmUtils.round(rv[i],10) + " + " + AlgorithmUtils.round(iv[i],10) + "i";
            else
                res += "" + AlgorithmUtils.round(rv[i],10);
            if(i!=rv.length-1) {
                res += ",";
            }
        }
        return res;
    }

    /**
     * Computes the sum of the eigenvalues of A
     *
     * @param A the given matrix
     * @return the sum of the eigenvalues of A
     */
    public static double sumOfExpOfEigenValues(Matrix A) {
        EigenvalueDecomposition ed = A.eig();
        double[] rv = ed.getRealEigenvalues();
        double sum = 0;

        //positiv RV
        Double[] prv = new Double[rv.length];
        for (int i = 0; i < rv.length; i++) {
            prv[i] = Math.exp(rv[i]);
            prv[i] = (double)Math.round(prv[i] * 100000d) / 100000d;
            sum += prv[i];
        }

        return sum;
    }

    /**
     * Computes the sum of the eigenvalues of A
     *
     * @param A the given matrix
     * @return the sum of the eigenvalues of A
     */
    public static double sumOfEigenValues(Matrix A) {
        EigenvalueDecomposition ed = A.eig();
        double[] rv = ed.getRealEigenvalues();
        double sum = 0;

        //positiv RV
        Double[] prv = new Double[rv.length];
        for (int i = 0; i < rv.length; i++) {
            prv[i] = Math.abs(rv[i]);
            prv[i] = (double)Math.round(prv[i] * 100000d) / 100000d;
            sum += prv[i];
        }

        return sum;
    }

    /**
     * Computes the eigen values
     *
     * @param A the given matrix
     * @return the eigen values of A
     */
    public static String getEigenValues(Matrix A) {
        EigenvalueDecomposition ed = A.eig();
        double[] rv = ed.getRealEigenvalues();
        double[] iv = ed.getImagEigenvalues();
        String res = "";
        Vector<Double> EigenValues = new Vector<>();
        for (int i = 0; i < rv.length; i++) {
            if (iv[i] != 0)
                res +="" + AlgorithmUtils.round(rv[i],10) + " + " + AlgorithmUtils.round(iv[i],10) + "i";
            else
                EigenValues.add(AlgorithmUtils.round(rv[i],10));
        }
        if(EigenValues.size() > 0) {
            res = "";
            EigenValues.sort((aDouble, t1) -> -aDouble.compareTo(t1));
            for (int i = 0; i < EigenValues.size(); i++) {
                res += EigenValues.get(i);
                if(i != EigenValues.size() - 1) {
                    res+=",";
                }
            }
        }
        return res;
    }

    // get kth minimum degree
    public static double getMinNonPendentDegree(GraphModel g) {
        ArrayList<Integer> al = getDegreesList(g);
        Collections.sort(al);
        if(al.contains(1)) {
            for (Integer anAl : al) {
                if (anAl != 1) {
                    return anAl;
                }
            }
        }

        return al.get(0);
    }

    //get 2-degree sum of graph
    public static double getDegreeSumOfVertex(GraphModel g, double alpha, Vertex v) {
        double sum = 0;
        for(Vertex u : g.directNeighbors(v)) {
            sum+=Math.pow(g.getDegree(u),alpha);
        }
        return sum;
    }

    public static double getDegreeSum(GraphModel g, double alpha) {
        int sum = 0;
        for(Vertex v: g) {
            sum+=getDegreeSumOfVertex(g,alpha,v);
        }
        return sum;
    }

    public static BigInteger choose(int x, int y) {
        if (y < 0 || y > x) return BigInteger.ZERO;
        if (y == 0 || y == x) return BigInteger.ONE;

        BigInteger answer = BigInteger.ONE;
        for (int i = x - y + 1; i <= x; i++) {
            answer = answer.multiply(BigInteger.valueOf(i));
        }
        for (int j = 1; j <= y; j++) {
            answer = answer.divide(BigInteger.valueOf(j));
        }
        return answer;
    }

    public static int getMaxDegree(GraphModel g) {
        int maxDegree = 0;
        for (Vertex v : g) {
            if(maxDegree < g.getDegree(v)) {
                maxDegree = g.getDegree(v);
            }
        }
        return maxDegree;
    }

    public static GraphModel createLineGraph(GraphModel g1) {
        GraphModel g2 = new GraphModel(false);//

        for (Edge e : g1.getEdges()) {
            Vertex v = new Vertex();
            v.setLabel(e.getLabel());
            GPoint loc = new GPoint(e.source.getLocation());
            loc.add(e.target.getLocation());
            loc.multiply(0.5);
            loc.add(e.getCurveControlPoint());
            v.setLocation(loc);
            e.getProp().obj = v;
            v.getProp().obj = e;
            g2.insertVertex(v);
        }
        for (Vertex v : g1) {
            Iterator<Edge> ie = g1.lightEdgeIterator(v);

            while (ie.hasNext()) {
                Edge e = ie.next();
                Iterator<Edge> ie2 = g1.lightEdgeIterator(v);
                while (ie2.hasNext()) {
                    Edge e2 = ie2.next();
                    if (e != e2) {
                        Edge ne = new Edge((Vertex) e.getProp().obj, (Vertex) e2.getProp().obj);
                        g2.insertEdge(ne);
                    }
                }
            }
        }
        return g2;
    }

    public static GraphModel createComplementGraph(GraphModel g1)  {

        GraphModel g2 = new GraphModel(false);//

        for(Vertex v : g1.getVertexArray()) {
            Vertex tmp = new Vertex();
            tmp.setLocation(v.getLocation());
            g2.addVertex(tmp);
        }


       for(Vertex v1 : g1.getVertexArray()) {
           for(Vertex v2 : g1.getVertexArray()) {
               if(v1.getId() != v2.getId()) {
                   if (!g1.isEdge(v1, v2)) {
                       g2.addEdge(new Edge(g2.getVertex(v1.getId()),
                               g2.getVertex(v2.getId())));
                   }
               }
           }
       }
       return g2;
    }

    public static Point[] computeRandomPositions(int numOfVertices) {
        Point[] ret = new Point[numOfVertices];
        int w = 100;
        int h = 100;
        for (int i = 0; i < numOfVertices; i++) {
            int x = (int) (Math.random() * w);
            int y = (int) (Math.random() * h);
            ret[i] = new Point(x, y);
        }
        return ret;
    }

    public static int[][] getBinaryPattern(double[][] mat, int n) {
        int[][] binmat = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 0) binmat[i][j] = 0;
                else binmat[i][j] = 1;
            }
        }
        return binmat;
    }

    /**
     * Maixum degree adjacency matrix
     * based on the paper
     * C. Adiga, M. Smitha
     *       On maximum degree energy of a graph
     *       Int. Journal of Contemp. Math. Sciences, Vol. 4, 2009, no. 5-8, 385-396.
     *
     * @param g the given graph
     * @return the maximum degree adjacency matrix
     */
    public static Matrix getMaxDegreeAdjacencyMatrix (GraphModel g) {
        Matrix adj = g.getAdjacencyMatrix();
        for(int i=0;i < adj.getColumnDimension();i++) {
            for(int j=0;j < adj.getRowDimension();j++) {
                if(g.isEdge(g.getVertex(i), g.getVertex(j))) {
                    adj.set(i,j,Math.max(g.getDegree(g.getVertex(i)),g.getDegree(g.getVertex(j))));
                } else {
                    adj.set(i,j,0);
                }
            }
        }
        return adj;
    }

    /**
     * Distance adjacency matrix
     * Distance Energy based on
     * Gopalapillai Indulal,a Ivan Gutmanb and Vijayakumarc
     * ON DISTANCE ENERGY OF GRAPHS
     * MATCH Commun. Math. Comput. Chem. 60 (2008) 461-472.
     *
     * @param g the given graph
     * @return the maximum degree adjacency matrix
     */
    public static Matrix getDistanceAdjacencyMatrix (GraphModel g) {
        FloydWarshall fw = new FloydWarshall();
        int[][] dist = fw.getAllPairsShortestPathWithoutWeight(g);
        Matrix adj = g.getAdjacencyMatrix();
        for(int i=0;i < adj.getColumnDimension();i++) {
            for(int j=0;j < adj.getRowDimension();j++) {
                adj.set(i,j,dist[i][j]);
            }
        }
        return adj;
    }


    /**
     * Undirected Laplacian.
     *
     * @param A the Adjacency matrix of the graph
     * @return Laplacian of the graph
     */
    public static Matrix getLaplacian(Matrix A) {
        //double[][] res=new double[g.numOfVertices()][g.numOfVertices()];
        int n = A.getArray().length;
        double[][] ATemp = A.getArray();

        Matrix D = new Matrix(n, n);
        double[][] DTemp = D.getArray();
        int sum;
        for (int i = 0; i < n; i++) {
            sum = 0;
            for (int j = 0; j < n; j++) {
                sum += ATemp[j][i];
            }
            DTemp[i][i] = sum;
        }

        return D.minus(A);
    }

    /**
     * Normalized Laplacian.
     *
     * @param g the Adjacency matrix of the graph
     * @return Laplacian of the graph
     */
    public static Matrix getNormalizedLaplacian(GraphModel g) {
        Matrix D = new Matrix(g.numOfVertices(), g.numOfVertices(), 0);
        for (int i = 0; i < g.numOfVertices(); i++) {
            if (g.getDegree(g.getVertex(i)) != 0) {
                D.set(i, i, 1);
            }
        }
        for (int i = 0; i < g.numOfVertices(); i++) {
            for (int j = 0; j < g.numOfVertices(); j++) {
                if (i != j && g.isEdge(g.getVertex(i), g.getVertex(j))) {
                    D.set(i, j, -1/Math.sqrt(g.getDegree(g.getVertex(i))
                            * g.getDegree(g.getVertex(j))));
                }
            }
        }

        return D;
    }

    /**
     * Normalized Laplacian.
     *
     * @param g the Adjacency matrix of the graph
     * @return Laplacian of the graph
     */
    public static Matrix getLaplacian(GraphModel g) {
        Matrix D = new Matrix(g.numOfVertices(), g.numOfVertices(), 0);
        for (int i = 0; i < g.numOfVertices(); i++) {
                D.set(i, i, g.getDegree(g.getVertex(i)));
        }
        for (int i = 0; i < g.numOfVertices(); i++) {
            for (int j = 0; j < g.numOfVertices(); j++) {
                if (i != j && g.isEdge(g.getVertex(i), g.getVertex(j))) {
                    D.set(i, j, -1);
                }
            }
        }

        return D;
    }


    public static Matrix getSignlessLaplacian(Matrix A) {
        //double[][] res=new double[g.numOfVertices()][g.numOfVertices()];
        int n = A.getArray().length;
        double[][] ATemp = A.getArray();

        Matrix D = new Matrix(n, n);
        double[][] DTemp = D.getArray();
        int sum;
        for (int i = 0; i < n; i++) {
            sum = 0;
            for (int j = 0; j < n; j++) {
                sum += ATemp[j][i];
            }
            DTemp[i][i] = sum;
        }

        return D.plus(A);
    }

    public interface BFSListener {
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
