// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.io.g6format;

import Jama.Matrix;
import graphtea.graph.graph.GraphModel;
import graphtea.plugins.main.saveload.core.GraphIOException;
import graphtea.plugins.main.saveload.core.extension.GraphWriterExtension;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveGraph6Format implements GraphWriterExtension {

    public String getName() {
        return "Graph6 Format";
    }

    public String getExtension() {
        return "g6";
    }

    @Override
    public void write(File file, GraphModel graph) throws GraphIOException {
        try {
            FileWriter fw = new FileWriter(file,true);
            String s = encodeGraph(graph.numOfVertices(),createAdjMatrix(graph.getAdjacencyMatrix()));
            fw.write(s);
            fw.write(System.lineSeparator());
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDescription() {
        return "Graph6 File Format";
    }

    public String createAdjMatrix (Matrix m){
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