package graphtea.extensions;

import Jama.Matrix;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.Vertex;
import graphtea.plugins.main.core.AlgorithmUtils;

import java.awt.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by rostam on 30.09.15.
 * @author M. Ali Rostami
 */
public class Utils {
    // get kth minimum degree
    public static double getMinNonPendentDegree(GraphModel g) {
        ArrayList<Integer> al = AlgorithmUtils.getDegreesList(g);
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
}
