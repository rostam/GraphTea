// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.saveload.matrix;

import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.GraphPoint;
import graphlab.graph.graph.VertexModel;
import graphlab.library.BaseEdge;
import graphlab.library.BaseEdgeProperties;
import graphlab.library.BaseGraph;
import graphlab.library.BaseVertex;

import javax.swing.*;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Hooman Mohajeri
 */
public class WeightedMatrix {

    public static <vt extends BaseVertex, et extends BaseEdge<vt>>
    Object[][] graph2Matrix(BaseGraph<vt, et> g) {
        int n = g.getVerticesCount();
        Object[][] ret = new Object[n][n];
        HashMap<Integer, vt> m = new HashMap<Integer, vt>();
        int i = 0;
        for (vt _ : g) {
            m.put(i++, _);
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
        HashMap<Integer, vt> m = new HashMap<Integer, vt>();
        int i = 0;
        for (vt _ : g) {
            m.put(i++, _);
        }
        for (i = 0; i < n; i++)
        	ret[i][i] = g.getInDegree(m.get(i));
        return ret;
    }
    
    public static <vt extends BaseVertex, et extends BaseEdge<vt>>
    Object[][] OutDegree2Matrix(BaseGraph<vt, et> g) {
        int n = g.getVerticesCount();
        Object[][] ret = new Object[n][n];
        HashMap<Integer, vt> m = new HashMap<Integer, vt>();
        int i = 0;
        for (vt _ : g) {
            m.put(i++, _);
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
        VertexModel[] vertices = new VertexModel[n];
        for (int i = 0; i < n; i++) {
            VertexModel v = new VertexModel();
            v.setLabel(i+"");
            g.insertVertex(v);
            v.setLocation(new GraphPoint(Math.random() * 500, Math.random() * 500));
            vertices[i] = v;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
            	if(mat[i][j]!=0)
            	{
            		EdgeModel em = new EdgeModel(vertices[i], vertices[j],new BaseEdgeProperties(0,mat[i][j],false));
            		em.setLabel(i+""+j);
                    g.insertEdge(em);
            	}
            }
        }
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
