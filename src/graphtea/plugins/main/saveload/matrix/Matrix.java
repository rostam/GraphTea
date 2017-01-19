// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.saveload.matrix;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.library.BaseEdge;
import graphtea.library.BaseEdgeProperties;
import graphtea.library.BaseGraph;
import graphtea.library.BaseVertex;

import javax.swing.*;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Hooman Mohajeri Moghaddam added weighted save&load support
 * @author Azin Azadi
 */
public class Matrix {

    public static <vt extends BaseVertex, et extends BaseEdge<vt>>
    Object[][] graph2Matrix(BaseGraph<vt, et> g) {
        int n = g.getVerticesCount();
        Object[][] ret = new Object[n][n];
        HashMap<Integer, vt> m = new HashMap<>();
        int i = 0;
        for (vt tmp : g) {
            m.put(i++, tmp);
        }
        int[] res;
        for (i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
            	res=g.weightOfEdge(m.get(i), m.get(j));
            	if(res!=null)
            		ret[i][j] = g.weightOfEdge(m.get(i), m.get(j))[0];
            	else
            		ret[i][j] = 0;
            }
        return ret;
    }
    
    public static <vt extends BaseVertex, et extends BaseEdge<vt>>
    Object[][] InDegree2Matrix(BaseGraph<vt, et> g) {
        int n = g.getVerticesCount();
        Object[][] ret = new Object[n][n];
        HashMap<Integer, vt> m = new HashMap<>();
        int i = 0;
        for (vt vv : g) {
            m.put(i++, vv);
        }
        for (i = 0; i < n; i++)
        	ret[i][i] = g.getInDegree(m.get(i));
        return ret;
    }
    
    public static <vt extends BaseVertex, et extends BaseEdge<vt>>
    Object[][] OutDegree2Matrix(BaseGraph<vt, et> g) {
        int n = g.getVerticesCount();
        Object[][] ret = new Object[n][n];
        HashMap<Integer, vt> m = new HashMap<>();
        int i = 0;
        for (vt vv : g) {
            m.put(i++, vv);
        }
        for (i = 0; i < n; i++)
        	ret[i][i] = g.getOutDegree(m.get(i));
        return ret;
    }

    public static void Matrix2Graph(int[][] mat, GraphModel g) {
        int n = mat.length;
        int nn = mat[1].length;
        if (n != nn) {
            JOptionPane.showMessageDialog(null, "not a valid matrix graph");
            return;
        }
        Vertex[] vertices = new Vertex[n];
        for (int i = 0; i < n; i++) {
            Vertex v = new Vertex();
            v.setLabel((i+1)+"");
            g.insertVertex(v);
            v.setLocation(new GPoint(Math.random() * 500, Math.random() * 500));
            vertices[i] = v;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
            	if(mat[i][j]!=0)
            	{
            		Edge em = new Edge(vertices[i], vertices[j],new BaseEdgeProperties(0,mat[i][j],false));
            		em.setLabel(i+""+j);
                    g.insertEdge(em);
            	}
            }
        }
    }

    public static String Matrix2HTML(Object[][] mat){
        int a = mat.length;
        if (a == 0)
            return "";
        int b = mat[0].length;
        String ret = "<table>";
        for (int i = 0; i < a; i++) {
            ret += "<tr>";
            for (int j = 0; j < b; j++) {
                if(mat[i][j]!=null)
                    ret += "<td>" + ((Number)(mat[i][j])).intValue() + "</td>";
                else
                    ret += "<td>0</td>";
//                ret += (mat[i][j] ? "1" : "0");
//                ret += " ";
            }
            ret += "</tr>\n";

        }
        return ret;

    }
    public static String Matrix2String(Object[][] mat) {
        int a = mat.length;
        if (a == 0)
            return "";
        int b = mat[0].length;
        String ret = "";
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {
            	if(mat[i][j]!=null)
            		ret += ((Number)(mat[i][j])).intValue() + " ";
            	else
            		ret += "0 ";
//                ret += (mat[i][j] ? "1" : "0");
//                ret += " ";
            }
            ret += "\n";
        }
        return ret;
    }

    public static int[][] String2Matrix(String s) {
        int n = 0;
        Scanner sc = new Scanner(s.substring(0, s.indexOf("\n")));
        while(sc.hasNextInt())
        {
        	n++;
        	sc.nextInt();
        }
        int ret[][] = new int[n][n];
        sc = new Scanner(s);
        int i = 0;
        while (sc.hasNextLine()) {
            for (int j = 0; j < n; j++) {
                ret[i][j] = sc.nextInt();
            }
            i++;
            // ## \/ ?
            sc.nextLine();
        }
        return ret;
    }
}
