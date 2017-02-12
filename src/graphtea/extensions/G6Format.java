package graphtea.extensions;

import Jama.Matrix;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

/**
 * Created by rostam on 2/11/17.
 * @author M. Ali Rostami
 */
public class G6Format {
    private static int BIAS6 = 63;
    private static int SMALLN = 62;
    private static int SMALLISHN = 258047;
    private static int TOPBIT6 = 32;
    private static int  WORDSIZE = 32;
    private static String g6_graph;


    static int bit_[] = {020000000000, 010000000000, 04000000000, 02000000000,
            01000000000, 0400000000, 0200000000, 0100000000, 040000000,
            020000000, 010000000, 04000000, 02000000, 01000000, 0400000,
            0200000, 0100000, 040000, 020000, 010000, 04000, 02000, 01000,
            0400, 0200, 0100, 040, 020, 010, 04, 02, 01};

    static int SIZELEN(int n) {
        return (n) <= SMALLN ? 1 : ((n) <= SMALLISHN ? 4 : 8);
    }

    int SETWD(int pos) {
        return ((pos) >> 5);
    }

    int SETBT(int pos) {
        return ((pos) & 037);
    }


    public static HashMap<Integer, Vector<Integer>> stringToGraph(String g6) {
        int n = graphsize(g6);
        HashMap<Integer, Vector<Integer>> graph = new HashMap<>();
        String p = g6;
        if (g6.charAt(0) == ':' || g6.charAt(0) == '&')
            p = g6.substring(1);
        p = p.substring(SIZELEN(n));

        int m = (n + WORDSIZE - 1) / WORDSIZE;
        int x=0;
        long[] g = new long[m * n];
        for (int ii = m * n; --ii > 0; ) g[ii] = 0;
        g[0] = 0;
        int k = 1;
        int it = 0;
        for (int j = 1; j < n; ++j) {
            for (int i = 0; i < j; ++i) {
                if (--k == 0) {
                    k = 6;
                    x = p.charAt(it) - BIAS6;
                    it++;
                }
                if ((x & TOPBIT6) != 0) {
                    if (graph.containsKey(i)) graph.get(i).add(j);
                    else {
                        graph.put(i, new Vector<>());
                        graph.get(i).add(j);
                    }
                }
                x <<= 1;
            }
        }
        return graph;
    }

    public static GraphModel stringToGraphModel(String g6) {
        int n = graphsize(g6);
        GraphModel graph = new GraphModel(false);
        for(int i=0;i<graphsize(g6);i++) {
            graph.addVertex(new Vertex());
        }
        String p = g6;
        if (g6.charAt(0) == ':' || g6.charAt(0) == '&')
            p = g6.substring(1);
        p = p.substring(SIZELEN(n));

        int m = (n + WORDSIZE - 1) / WORDSIZE;
        int x=0;
        long[] g = new long[m * n];
        for (int ii = m * n; --ii > 0; ) g[ii] = 0;
        g[0] = 0;
        int k = 1;
        int it = 0;
        for (int j = 1; j < n; ++j) {
            for (int i = 0; i < j; ++i) {
                if (--k == 0) {
                    k = 6;
                    x = p.charAt(it) - BIAS6;
                    it++;
                }
                if ((x & TOPBIT6) != 0)
                    graph.addEdge(new Edge(graph.getVertex(i),graph.getVertex(j)));
                x <<= 1;
            }
        }
        return graph;
    }

    /* Get size of graph out of graph6 or sparse6 string. */
    static int graphsize(String s) {
        String p;
        if (s.charAt(0) == ':') p = s.substring(1);
        else p = s;
        int n;
        n = p.charAt(0) - BIAS6;

        if (n > SMALLN) {
            n = p.charAt(1) - BIAS6;
            n = (n << 6) | (p.charAt(2) - BIAS6);
            n = (n << 6) | (p.charAt(3) - BIAS6);
        }
        return n;
    }

    ////////////////////////////////////////////////////////////////////
    ////////////// Generate G6 Format
    public static String graphToG6(GraphModel g) {
        return encodeGraph(g.numOfVertices(),createAdjMatrix(g.getAdjacencyMatrix()));
    }

    public static String createAdjMatrix (Matrix m){
        String result="";

        for (int i = 1, k = 1; k < m.getColumnDimension(); i++, k++) {
            for (int j = 0; j < i; j++) {
                if (m.get(j,i) != 0) result += "1";
                else result += "0";
            }
        }
        return result;
    }


    public static String encodeGraph(int NoNodes, String adjmatrix) {
        String rv = "";
        int[] nn = encodeN(NoNodes);
        int[] adj = encodeR(adjmatrix);
        int[] res = new int[nn.length + adj.length];
        System.arraycopy(nn, 0, res, 0, nn.length);
        System.arraycopy(adj, 0, res, nn.length, adj.length);
        for (int re : res) {
            rv = rv + (char) re;
        }
        return rv;
    }

    private static int[] encodeN(long i) {

        if (0 <= i && i <= 62) {
            return new int[] { (int) (i + 63) };
        } else if (63 <= i && i <= 258047) {
            int[] ret = new int[4];
            ret[0] = 126;
            int[] g = R(padL(Long.toBinaryString(i), 18));
            System.arraycopy(g, 0, ret, 1, 3);
            return ret;
        } else {
            int[] ret = new int[8];
            ret[0] = 126;
            ret[1] = 126;
            int[] g = R(padL(Long.toBinaryString(i), 36));
            System.arraycopy(g, 0, ret, 2, 6);
            return ret;
        }

    }

    private static int[] R(String a) {
        int[] bytes = new int[a.length() / 6];
        for (int i = 0; i < a.length() / 6; i++) {
            bytes[i] = Integer.valueOf(a.substring(i * 6, ((i * 6) + 6)), 2);
            bytes[i] = (byte) (bytes[i] + 63);
        }

        return bytes;
    }

    private static int[] encodeR(String a) {
        a = padR(a);
        return R(a);
    }

    private static String padR(String str) {
        int padwith = 6 - (str.length() % 6);
        for (int i = 0; i < padwith; i++) {
            str += "0";
        }
        return str;
    }

    private static String padL(String str, int h) {
        String retval = "";
        for (int i = 0; i < h - str.length(); i++) {
            retval += "0";
        }
        return retval + str;
    }
}
